package com.gdg.homepage.landing.faq.controller;

import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.faq.dto.FAQRequest;
import com.gdg.homepage.landing.faq.dto.FAQResponse;
import com.gdg.homepage.landing.faq.service.FAQServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/faq")
@RequiredArgsConstructor
public class FAQApi {

    private final FAQServiceImpl FAQService;

    // FAQ 추가
    @PostMapping("/create")
    public ApiResponse<FAQResponse> createFAQ(@RequestBody FAQRequest faqRequest) {
        try {
            FAQResponse faqResponseDto = FAQService.createFAQ(faqRequest);
            return ApiResponse.created(faqResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // FAQ 수정
    @PutMapping("/update/{id}")
    public ApiResponse<FAQResponse> updateFAQ(@PathVariable("id") Long id, @RequestBody FAQRequest faqRequest) {
        try {
            FAQResponse faqResponseDto = FAQService.updateFAQ(id, faqRequest);
            return ApiResponse.ok(faqResponseDto);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // FAQ 삭제
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteFAQ(@PathVariable("id") Long id) {
        try {
            FAQService.deleteFAQ(id);
            return ApiResponse.ok("FAQ is deleted.");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // FAQ 목록 조회
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
