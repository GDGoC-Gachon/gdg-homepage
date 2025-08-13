package com.gdg.homepage.landing.faq.presentation;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.faq.application.dto.request.FAQRequest;
import com.gdg.homepage.landing.faq.application.dto.response.FAQResponse;
import com.gdg.homepage.landing.faq.application.usecase.FAQUseCase;
import com.gdg.homepage.landing.faq.presentation.swagger.FAQApiSpec;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/faq")
@RequiredArgsConstructor
public class FAQApi implements FAQApiSpec {

    private final FAQUseCase service;

    /**
     * 생성
     */
    @PostMapping("/create")
    public ApiResponse<Void> createFAQ(@RequestBody @Valid FAQRequest faqRequest) {

        /// 서비스 호출
        service.createFAQ(faqRequest);

        /// 응답
        return ApiResponse.created();
    }


    /**
     * 수정
     */
    @PutMapping("/{id}")
    public ApiResponse<FAQResponse> updateFAQ(@PathVariable Long id, @RequestBody @Valid FAQRequest request) {

        /// 서비스 호출
        FAQResponse faqResponseDto = service.updateFAQ(id,request);

        /// 응답
        return ApiResponse.updated(faqResponseDto);
    }

    /**
     * 조회
     */
    @GetMapping("/all")
    public ApiResponse<List<FAQResponse>> getAllFAQs() {

        /// 서비스 호출
        List<FAQResponse> responses = service.getAllFAQs();

        /// 응답
        return ApiResponse.ok(responses);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFAQ(@PathVariable("id") Long id) {

        /// 서비스 호출
        service.deleteFAQ(id);

        /// 응답
        return ApiResponse.deleted();
    }
}
