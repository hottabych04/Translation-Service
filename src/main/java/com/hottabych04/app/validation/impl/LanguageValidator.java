package com.hottabych04.app.validation.impl;

import com.hottabych04.app.http.body.TranslationReq;
import com.hottabych04.app.manager.LanguageManager;
import com.hottabych04.app.validation.Lang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageValidator implements ConstraintValidator<Lang, TranslationReq> {

    private final LanguageManager manager;

    @Override
    public boolean isValid(TranslationReq value, ConstraintValidatorContext context) {
        if (value.getSourceLang() != null && value.getTargetLang() != null){
            return (manager.isAvailable(value.getSourceLang()) && manager.isAvailable(value.getTargetLang()));
        }

        return false;
    }

}
