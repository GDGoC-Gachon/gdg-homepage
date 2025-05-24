package com.gdg.homepage.landing.faq.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.faq.dto.FAQRequest;
import com.gdg.homepage.landing.faq.dto.FAQResponse;
import com.gdg.homepage.landing.faq.service.FAQServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "FAQ 관리 API",
        description = "관리자용 FAQ 생성, 수정, 삭제 및 조회 기능"
)
@RestController
@RequestMapping("/admin/faq")
@RequiredArgsConstructor
public class FAQApi {

    private final FAQServiceImpl FAQService;

    @Operation(
            summary = "FAQ 생성",
            description = "새로운 FAQ 항목을 생성합니다."
    )
    @PostMapping("/create")
    public ApiResponse<FAQResponse> createFAQ(@RequestBody FAQRequest faqRequest) {
        try {
            FAQResponse faqResponseDto = FAQService.createFAQ(faqRequest);
            return ApiResponse.created(faqResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "FAQ 수정",
            description = "기존 FAQ 항목을 ID를 기반으로 수정합니다."
    )
    @PutMapping("/update/{id}")
    public ApiResponse<FAQResponse> updateFAQ(@PathVariable("id") Long id, @RequestBody FAQRequest faqRequest) {
        try {
            FAQResponse faqResponseDto = FAQService.updateFAQ(id, faqRequest);
            return ApiResponse.ok(faqResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "FAQ 삭제",
            description = "FAQ 항목을 ID를 기반으로 삭제합니다."
    )
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteFAQ(@PathVariable("id") Long id) {
        try {
            FAQService.deleteFAQ(id);
            return ApiResponse.ok("FAQ is deleted.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "FAQ 전체 조회",
            description = "모든 FAQ 항목을 조회합니다."
    )
    @GetMapping("/all")
    public ApiResponse<List<FAQResponse>> getAllFAQs() {
        try {
            List<FAQResponse> faqResponseDtos = FAQService.getAllFAQs();
            return ApiResponse.ok(faqResponseDtos);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}