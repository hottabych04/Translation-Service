package com.hottabych04.app.config;

import com.hottabych04.app.http.body.Language;
import com.hottabych04.app.http.client.YandexCloudClient;
import com.hottabych04.app.manager.LanguageManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ApplicationConfig {

    @Bean
    public String apiKey(@Value("${yandex.cloud.api.key}") String key){
        return key;
    }

    @Bean
    public String apiUrl(@Value("${yandex.cloud.api.url}") String url){
        return url;
    }

    @Bean
    public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters){
        return new RestTemplate(messageConverters);
    }

    @Bean
    public LanguageManager languageManager(YandexCloudClient cloudClient){
        List<String> langCodes = null;
        try {
            langCodes = cloudClient.languages().getLanguages().stream()
                    .map(Language::getCode)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new LanguageManager(langCodes);
    }

}
