package com.hottabych04.app.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Table(name = "translation")
public class Translation {

    @Id
    private Long id;
    private String ip;
    private String sourceText;
    private String translateText;

}
