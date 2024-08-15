package com.hottabych04.app.service;

import com.hottabych04.app.database.entity.Translation;
import com.hottabych04.app.database.repository.TranslationRepository;
import com.hottabych04.app.dto.TranslationDto;
import com.hottabych04.app.dto.TranslationPageDto;
import com.hottabych04.app.exception.SymbolsLimitExceeded;
import com.hottabych04.app.http.client.YandexCloudClient;
import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationTextResp;
import com.hottabych04.app.mapper.PageTranslationMapper;
import com.hottabych04.app.mapper.TranslationMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final TranslationRepository translationRepository;
    private final YandexCloudClient cloudClient;
    private final Integer translateThreadCount;

    private final TranslationMapper translationMapper;
    private final PageTranslationMapper pageTranslationMapper;

    private Semaphore translateSemaphore;
    private ExecutorService translateThreadPool;
    private ScheduledExecutorService scheduleTranslateThreadPool;
    private AtomicLong availableSymbols;

    @PostConstruct
    private void init(){
        translateSemaphore = new Semaphore(20);
        translateThreadPool = Executors.newFixedThreadPool(translateThreadCount);
        scheduleTranslateThreadPool = Executors.newScheduledThreadPool(2);
        availableSymbols = new AtomicLong(1000000);

        runScheduleThreads();
    }

    private void runScheduleThreads(){
        scheduleTranslateThreadPool.scheduleAtFixedRate(
                () -> translateSemaphore.release(20 - translateSemaphore.availablePermits()),
                1000L - System.currentTimeMillis() % 1000L,
                1000L,
                TimeUnit.MILLISECONDS);

        scheduleTranslateThreadPool.scheduleAtFixedRate(
                () -> availableSymbols.set(1000000L),
                1000L - System.currentTimeMillis() % 1000L,
                3600000L,
                TimeUnit.MILLISECONDS
        );
    }

    public TranslationTextResp translate(TranslationReq translationReq, HttpServletRequest httpServletRequest) {

        String[] translationWords = translateWords(translationReq);

        String translateText = String.join(" ", translationWords);

        Translation translation = Translation.builder()
                .ip(httpServletRequest.getRemoteAddr().replaceAll("0:0:0:0:0:0:0:1", "127.0.0.1"))
                .sourceText(translationReq.getText())
                .translateText(translateText)
                .build();

        translationRepository.save(translation);

        return new TranslationTextResp(translateText);
    }

    private String[] translateWords(TranslationReq req) {

        String[] words = parseWords(req);

        String[] result = new String[words.length];

        List<CompletableFuture<Void>> futures = IntStream.range(0, words.length)
                .mapToObj(index -> CompletableFuture.runAsync(() -> {
                        try {

                            translateSemaphore.acquire();

                            String translatedWord = cloudClient.translate(req.getSourceLang(), req.getTargetLang(), words[index]);

                            synchronized (result) {
                                result[index] = translatedWord;
                            }

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }, translateThreadPool))
                .toList();


        futures.forEach(CompletableFuture::join);

        return result;
    }

    private String[] parseWords(TranslationReq req){
        String[] words = Arrays.stream(req.getText().split(" "))
                .filter(it -> !it.isEmpty())
                .toArray(String[]::new);

        int requestSymbolsLength = Arrays.stream(words)
                .mapToInt(String::length)
                .sum();

        if (requestSymbolsLength <= availableSymbols.get()){
            availableSymbols.set(availableSymbols.get() - requestSymbolsLength);
        } else {
            throw new SymbolsLimitExceeded();
        }

        return words;
    }

    public TranslationDto findById(Long id){
        return translationMapper.map(translationRepository.findById(id).orElseThrow());
    }

    public TranslationPageDto findAll(Pageable pageable){
        return pageTranslationMapper.map(translationRepository.findAll(pageable));
    }

    @PreDestroy
    private void shutdown() throws InterruptedException {

        translateThreadPool.shutdown();
        translateThreadPool.awaitTermination(1, TimeUnit.MINUTES);

        scheduleTranslateThreadPool.shutdown();
        scheduleTranslateThreadPool.awaitTermination(1, TimeUnit.MINUTES);

    }
}
