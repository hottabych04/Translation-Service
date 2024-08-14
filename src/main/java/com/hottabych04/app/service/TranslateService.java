package com.hottabych04.app.service;

import com.hottabych04.app.http.client.YandexCloudClient;
import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationTextResp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final YandexCloudClient cloudClient;
    private final Semaphore translateSemaphore;
    private final ExecutorService translateThreadPool;


    public TranslationTextResp translate(TranslationReq translationReq, HttpServletRequest httpServletRequest) {

        String[] translationWords = translateWords(translationReq);

        String translate = String.join(" ", translationWords);

        return new TranslationTextResp(translate);

    }

    private String[] translateWords(TranslationReq req) {

        String[] words = Arrays.stream(req.getText().split(" "))
                .filter(it -> !it.isEmpty())
                .toArray(String[]::new);

        String[] result = new String[words.length];

        List<CompletableFuture<Void>> futures = IntStream.range(0, words.length)
                .mapToObj(index -> CompletableFuture.runAsync(() -> {
                        try {
                            translateSemaphore.acquire();

                            String translatedWord = cloudClient.translate(req.getSourceLang(), req.getTargetLang(), words[index]);

                            synchronized (result) {
                                result[index] = translatedWord;
                            }

                            translateSemaphore.release();

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }, translateThreadPool))
                .toList();


        futures.forEach(CompletableFuture::join);

        return result;
    }
}
