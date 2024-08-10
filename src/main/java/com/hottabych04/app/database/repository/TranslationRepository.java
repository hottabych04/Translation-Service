package com.hottabych04.app.database.repository;

import com.hottabych04.app.database.entity.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TranslationRepository extends CrudRepository<Translation, Long> {

    Page<Translation> findAll(Pageable pageable);

}
