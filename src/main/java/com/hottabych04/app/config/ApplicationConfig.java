package com.hottabych04.app.config;

import com.hottabych04.app.exception.YandexCloudNotAvailable;
import com.hottabych04.app.http.body.Language;
import com.hottabych04.app.http.client.YandexCloudClient;
import com.hottabych04.app.manager.LanguageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

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
    public LanguageManager languageManager(YandexCloudClient cloudClient) throws InterruptedException {

        List<String> langCodes;
        while (true){
            try {
                langCodes = cloudClient.languages().getLanguages().stream()
                        .map(Language::getCode)
                        .toList();

                return new LanguageManager(langCodes);
            } catch (YandexCloudNotAvailable ignored) {
                log.warn("Restart connect to Yandex Cloud for init Language Manager...");
                Thread.sleep(1000L);
            }
        }

    }

}
