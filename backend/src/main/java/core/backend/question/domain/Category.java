package core.backend.question.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    BLANK("빈칸 문제 유형"),
    MULTIPLE("객관식 문제 유형"),
    ORDER("순서 문제 유형"),
    SHORT("단답식 문제 유형");

    private final String description;
}