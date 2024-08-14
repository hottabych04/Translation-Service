package com.hottabych04.app.http.controller;

import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationTextResp;
import com.hottabych04.app.service.TranslateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping("/translate")
    @ResponseStatus(HttpStatus.OK)
    public TranslationTextResp translate(@RequestBody @Validated TranslationReq translationReq, HttpServletRequest httpServletRequest){
        return translateService.translate(translationReq, httpServletRequest);
    }

}
