package com.gdg.homepage.landing.register.domain.entity;

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


    /**
     * 문자열 값을 해당 Enum으로 변환해줍니다.
     *
     * @param value 문자열 값 (예: "FRONT_END")
     * @return 대응되는 TechField Enum
     * @throws IllegalArgumentException 일치하는 값이 없으면 예외
     */
    public static TechField from(String value) {
        for (TechField field : TechField.values()) {
            if (field.name().equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 TechField 값입니다. value=" + value);
    }
}
