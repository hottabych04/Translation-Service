package com.hottabych04.app.http.client.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TranslationReq {

    @JsonProperty("sourceLanguageCode")
    private String sourceLang;
    @JsonProperty("targetLanguageCode")
    private String targetLang;
    @JsonProperty("texts")
    private List<String> text;

}
