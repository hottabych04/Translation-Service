--liquibase formatted sql

--changeset hottabych04:1
CREATE TABLE IF NOT EXISTS translation
(
    id BIGSERIAL PRIMARY KEY ,
    ip VARCHAR(16) NOT NULL ,
    source_text TEXT NOT NULL ,
    translate_text TEXT NOT NULL
);