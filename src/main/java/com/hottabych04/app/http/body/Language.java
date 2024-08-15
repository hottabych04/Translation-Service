package com.hottabych04.app.http.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Language {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

}
