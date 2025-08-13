package com.gdg.homepage.landing.faq.application.dto.response;

import com.gdg.homepage.landing.faq.domain.entity.FAQ;
import lombok.Builder;
import java.util.List;

@Builder
public record FAQResponse(
        Long id,
        String question,
        String answer
) {

    /// 정적 팩토리 메서드
    public static FAQResponse from(FAQ faq) {
        return FAQResponse.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .build();
    }

    /// 정적 팩토리 메서드
    public static List<FAQResponse> from(List<FAQ> faqs) {
        return faqs.stream()
                .map(FAQResponse::from)
                .toList();
    }
}
