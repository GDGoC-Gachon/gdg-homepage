package com.gdg.homepage.landing.faq.application.usecase;

import com.gdg.homepage.landing.faq.application.dto.request.FAQRequest;
import com.gdg.homepage.landing.faq.application.dto.request.FAQUpdateRequest;
import com.gdg.homepage.landing.faq.application.dto.response.FAQResponse;
import java.util.List;

/**
 * FAQ(자주 묻는 질문) 관련 UseCase 인터페이스입니다.
 * <p>
 * 관리자 또는 운영자가 FAQ 등록, 수정, 삭제, 목록 조회 등
 * FAQ 관리에 필요한 핵심 비즈니스 기능을 정의합니다.
 * </p>
 */
public interface FAQUseCase {

    /**
     * 새로운 FAQ를 등록합니다.
     *
     * @param faqRequest FAQ 등록 요청 DTO
     * @return 생성된 FAQ 정보 응답 DTO
     */
    FAQResponse createFAQ(FAQRequest faqRequest);

    /**
     * FAQ 정보를 수정합니다.
     *
     * @param request FAQ 수정 요청 DTO
     * @return 수정된 FAQ 정보 응답 DTO
     */
    FAQResponse updateFAQ(FAQUpdateRequest request);

    /**
     * FAQ를 삭제합니다.
     *
     * @param id 삭제 대상 FAQ의 식별 값(ID)
     */
    void deleteFAQ(Long id);

    /**
     * FAQ 전체 목록을 조회합니다.
     *
     * @return FAQ 정보 응답 DTO 리스트
     */
    List<FAQResponse> getAllFAQs();
}
