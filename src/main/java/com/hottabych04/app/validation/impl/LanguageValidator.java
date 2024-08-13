package com.hottabych04.app.validation.impl;

import com.hottabych04.app.manager.LanguageManager;
import com.hottabych04.app.validation.Lang;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageValidator implements ConstraintValidator<Lang, String> {

    private final LanguageManager manager;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null){
            return manager.isAvailable(value);
        }

        return false;
    }

}
