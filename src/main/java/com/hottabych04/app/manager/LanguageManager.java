package com.hottabych04.app.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LanguageManager {

    private final List<String> languages;

    public boolean isAvailable(String code){
        return languages.contains(code);
    };

}
