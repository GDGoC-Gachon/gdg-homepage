package com.gdg.homepage.landing.register.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechField {
    FRONT_END("Front-end"),
    BACK_END("Back-end"),
    MOBILE("Mobile"),
    AI_ML("AI/ML"),
    DEVOPS_CLOUD("DevOps/Cloud"),
    ORGANIZER("Organizer");

    private final String displayName;
}
