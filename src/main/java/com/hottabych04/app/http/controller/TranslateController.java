package com.hottabych04.app.http.controller;

import com.hottabych04.app.dto.TranslationDto;
import com.hottabych04.app.dto.TranslationPageDto;
import com.hottabych04.app.http.body.Language;
import com.hottabych04.app.http.body.Languages;
import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.http.body.TranslationTextResp;
import com.hottabych04.app.service.TranslateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/translation")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService translateService;

    @PostMapping("/translate")
    @ResponseStatus(HttpStatus.OK)
    public TranslationTextResp translate(@RequestBody @Validated TranslationReq translationReq, HttpServletRequest httpServletRequest){
        return translateService.translate(translationReq, httpServletRequest);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TranslationDto getById(@PathVariable Long id){
        return translateService.findById(id);
    }

    @GetMapping("/get/page/{page}")
    @ResponseStatus(HttpStatus.OK)
    public TranslationPageDto getById(@PathVariable Integer page){
        return translateService.findAll(
                PageRequest.of(page, 10, Sort.by("id").descending())
        );
    }

    @GetMapping("/languages")
    @ResponseStatus(HttpStatus.OK)
    public Languages getAvailableLanguages(){
        return translateService.getAvailableLanguages();
    }

}
