package com.gdg.homepage.landing.faq.presentation.swagger;

import com.gdg.homepage.core.response.ApiResponse;
import com.gdg.homepage.landing.faq.application.dto.request.FAQRequest;
import com.gdg.homepage.landing.faq.application.dto.request.FAQUpdateRequest;
import com.gdg.homepage.landing.faq.application.dto.response.FAQResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "FAQ 관리 API", description = "관리자용 FAQ 생성, 수정, 삭제 및 조회 기능")
public interface FAQApiSpec {

    @Operation(
            summary = "FAQ 생성",
            description = "새로운 FAQ 항목을 생성합니다."
    )
    ApiResponse<Void> createFAQ(@RequestBody FAQRequest faqRequest);

    @Operation(
            summary = "FAQ 수정",
            description = "기존 FAQ 항목을 ID를 기반으로 수정합니다."
    )
    ApiResponse<FAQResponse> updateFAQ(@RequestBody FAQUpdateRequest request);

    @Operation(
            summary = "FAQ 삭제",
            description = "FAQ 항목을 ID를 기반으로 삭제합니다."
    )
    ApiResponse<Void> deleteFAQ(@PathVariable("id") Long id);


    @Operation(
            summary = "FAQ 전체 조회",
            description = "모든 FAQ 항목을 조회합니다."
    )
    ApiResponse<List<FAQResponse>> getAllFAQs();

}
