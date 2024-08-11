package com.hottabych04.app.config;

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

}
