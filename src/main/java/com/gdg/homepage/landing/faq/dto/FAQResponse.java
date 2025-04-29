package com.gdg.homepage.landing.faq.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FAQResponse {
    private Long id;
    private String question;
    private String answer;

    @Builder
    public static FAQResponse from(Long id, String question, String answer) {
        return FAQResponse.builder()
                .id(id)
                .question(question)
                .answer(answer)
                .build();
    }
}
