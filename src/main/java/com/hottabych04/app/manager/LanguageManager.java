package com.hottabych04.app.manager;

import com.hottabych04.app.http.body.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LanguageManager {

    private final List<Language> languages;

    public boolean isAvailable(String code){
        return languages.stream()
                .anyMatch(it -> it.getCode().equals(code));
    }

    public List<Language> getAllLanguages(){
        return languages;
    }

}
