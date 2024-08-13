package com.hottabych04.app.exception;

public class YandexCloudNotAvailable extends RuntimeException {

    public YandexCloudNotAvailable(){
        super("Yandex Cloud Service is not available");
    }

}
