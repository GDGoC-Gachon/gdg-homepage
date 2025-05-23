package com.gdg.homepage.landing.register.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Grade {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    GRADUATE(6),
    ORGANIZER(7);

    private final int value;

}
