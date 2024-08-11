package com.hottabych04.app.http.client;

import com.hottabych04.app.http.client.body.TranslationReq;
import com.hottabych04.app.http.client.body.TranslationResp;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Component
@RequiredArgsConstructor
public class YandexCloudClient {

    private final String apiKey, apiUrl;
    private final RestTemplate restTemplate;

    public String translate(@Nonnull String sourceLang,@Nonnull String targetLang,@Nonnull String text){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Api-Key " + apiKey);

        TranslationReq requestBody =  TranslationReq.builder()
                .sourceLang(sourceLang)
                .targetLang(targetLang)
                .text(List.of(text))
                .build();

        HttpEntity<TranslationReq> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<TranslationResp> response = this.restTemplate.postForEntity(apiUrl + "/translate", request, TranslationResp.class);

            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    !response.getBody().getTranslations().isEmpty()) {
                return response.getBody().getTranslations().getFirst().getText();
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                throw new RuntimeException("Api-key is invalid: " + apiKey);
            }
        }

        return null;

    }

}
