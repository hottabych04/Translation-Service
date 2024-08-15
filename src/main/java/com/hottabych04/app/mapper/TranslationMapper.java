package com.hottabych04.app.mapper;

import com.hottabych04.app.database.entity.Translation;
import com.hottabych04.app.dto.TranslationDto;
import org.springframework.stereotype.Component;

@Component
public class TranslationMapper implements Mapper<Translation, TranslationDto>{

    @Override
    public TranslationDto map(Translation object) {
        return TranslationDto.builder()
                .ip(object.getIp())
                .sourceText(object.getSourceText())
                .translateText(object.getTranslateText())
                .build();
    }
}
