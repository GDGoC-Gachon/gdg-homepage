package com.gdg.homepage.landing.faq.application.usecase;

import com.gdg.homepage.landing.faq.application.dto.request.FAQRequest;
import com.gdg.homepage.landing.faq.application.dto.response.FAQResponse;

import java.util.List;

public interface FAQService {
    // FAQ 생성
    FAQResponse createFAQ(FAQRequest faqRequest);

    // FAQ 수정
    FAQResponse updateFAQ(Long id, FAQRequest faqRequest);

    // FAQ 삭제
    void deleteFAQ(Long id);

    // FAQ 목록 조회
    List<FAQResponse> getAllFAQs();
}
