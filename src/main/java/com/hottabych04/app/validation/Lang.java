package com.hottabych04.app.validation;


import com.hottabych04.app.validation.impl.LanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LanguageValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface Lang {

    String message() default "Language is not supported";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
