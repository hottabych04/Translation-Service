package com.hottabych04.app.http.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hottabych04.app.validation.Lang;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TranslationReq {

    @Lang
    @JsonProperty("sourceLanguageCode")
    private String sourceLang;
    @Lang
    @JsonProperty("targetLanguageCode")
    private String targetLang;
    @NotEmpty
    @JsonProperty("texts")
    private String text;

}
