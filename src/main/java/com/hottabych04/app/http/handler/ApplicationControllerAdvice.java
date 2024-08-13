package com.hottabych04.app.http.handler;

import com.hottabych04.app.exception.YandexCloudNotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(YandexCloudNotAvailable.class)
    public ProblemDetail cloudClientNotAvailable(YandexCloudNotAvailable exc){
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, exc.getMessage());
    }

}
