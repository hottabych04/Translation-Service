package com.hottabych04.app.dto;

import com.hottabych04.app.database.entity.Translation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TranslationPageDto {

    Long totalElements;

    Integer totalPages;

    Integer number;

    Integer pageSize;

    Integer numberOfElements;

    Boolean first, last, empty;

    List<Translation> content;

}
