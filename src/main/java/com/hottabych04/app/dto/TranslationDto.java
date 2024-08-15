package com.hottabych04.app.dto;

import lombok.*;

@Value
@Builder
public class TranslationDto {

    String ip;

    String sourceText;

    String translateText;

}
