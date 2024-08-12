package com.hottabych04.app.http.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Languages {

    @JsonProperty("languages")
    private List<Language> languages;

}
