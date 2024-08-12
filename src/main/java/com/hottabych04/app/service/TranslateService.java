package com.hottabych04.app.service;

import com.hottabych04.app.http.client.YandexCloudClient;
import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationTextResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final YandexCloudClient cloudClient;


    public TranslationTextResp translate(TranslationReq req) {

        String translate = cloudClient.translate(req.getSourceLang(), req.getTargetLang(), req.getText());
        return new TranslationTextResp(translate);

    }
}
