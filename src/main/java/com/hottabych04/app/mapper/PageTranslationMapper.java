package com.hottabych04.app.mapper;

import com.hottabych04.app.database.entity.Translation;
import com.hottabych04.app.dto.TranslationPageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageTranslationMapper implements Mapper<Page<Translation>, TranslationPageDto>{

    @Override
    public TranslationPageDto map(Page<Translation> object) {
        return TranslationPageDto.builder()
                .totalElements(object.getTotalElements())
                .totalPages(object.getTotalPages())
                .pageSize(object.getPageable().getPageSize())
                .numberOfElements(object.getNumberOfElements())
                .number(object.getNumber())
                .first(object.isFirst())
                .last(object.isLast())
                .empty(object.isEmpty())
                .content(object.getContent())
                .build();
    }
}
