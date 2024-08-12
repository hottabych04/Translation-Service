package com.hottabych04.app.http.client;

import com.hottabych04.app.http.body.Languages;
import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationResp;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class YandexCloudClient {

    private final String apiKey, apiUrl;
    private final RestTemplate restTemplate;

    public String translate(@Nonnull String sourceLang,@Nonnull String targetLang,@Nonnull String text){

        TranslationReq requestBody =  TranslationReq.builder()
                .sourceLang(sourceLang)
                .targetLang(targetLang)
                .text(text)
                .build();

        HttpEntity<TranslationReq> request = new HttpEntity<>(requestBody, headerWithApi());

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

    public Languages languages(){

        HttpEntity<TranslationReq> request = new HttpEntity<>(headerWithApi());

        try {

            ResponseEntity<Languages> response = this.restTemplate.postForEntity(apiUrl + "/languages", request, Languages.class);

            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    !response.getBody().getLanguages().isEmpty()) {
                return response.getBody();
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 401) {
                throw new RuntimeException("Api-key is invalid: " + apiKey);
            }
        }

        return null;

    }

    private HttpHeaders headerWithApi(){
        return new HttpHeaders() {{
            add("Authorization", "Api-Key " + apiKey);
        }};
    }

}
