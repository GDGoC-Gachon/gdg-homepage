package com.gdg.homepage.landing.register.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Grade {
    ONE(1, "1학년"),
    TWO(2, "2학년"),
    THREE(3, "3학년"),
    FOUR(4, "4학년"),
    FIVE(5, "5학년"),
    GRADUATE(6, "졸업"),
    ORGANIZER(7, "운영진");

    private final int value;
    private final String label; // 한글 표시용

    @JsonIgnore
    public int getValue() {
        return value;
    }

    @JsonIgnore
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Grade fromValue(int value) {
        return Grade.of(value);
    }


    // 숫자 value로 enum 조회
    public static Grade of(int value) {
        for (Grade grade : values()) {
            if (grade.getValue() == value) {
                return grade;
            }
        }
        throw new IllegalArgumentException("잘못된 학년 값: " + value);
    }
}
