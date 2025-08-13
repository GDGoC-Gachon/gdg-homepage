package com.gdg.homepage.landing.faq.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * FAQ 등록/수정 요청 DTO입니다.
 * 관리자 화면 등에서 새로운 FAQ를 등록하거나,
 * 기존 FAQ를 수정할 때 사용되는 데이터 구조를 정의합니다.
 * </p>
 */
@Getter
public class FAQUpdateRequest {

    @Schema(description = "수정할 FAQ ID", example = "1")
    private Long id;

    /**
     * 자주 묻는 질문(질문 내용)
     * 예: "회원가입은 어떻게 하나요?"</p>
     */
    @Schema(description = "FAQ 질문 내용", example = "회원가입은 어떻게 하나요?")
    private String question;

    /**
     * 질문에 대한 답변(답변 내용)
     * 예: "홈페이지 우측 상단의 회원가입 버튼을 눌러 진행하실 수 있습니다."
     */
    @Schema(description = "FAQ 답변 내용", example = "홈페이지 우측 상단의 회원가입 버튼을 눌러 진행하실 수 있습니다.")
    private String answer;
}
