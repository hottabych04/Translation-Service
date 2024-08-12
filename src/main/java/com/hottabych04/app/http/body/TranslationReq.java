package com.hottabych04.app.http.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TranslationReq {

    @NotEmpty
    @JsonProperty("sourceLanguageCode")
    private String sourceLang;
    @NotEmpty
    @JsonProperty("targetLanguageCode")
    private String targetLang;
    @NotEmpty
    @JsonProperty("texts")
    private String text;

}
