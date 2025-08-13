package com.gdg.homepage.landing.member.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

/**
 * 비밀번호 변경 요청을 위한 DTO 클래스입니다.
 * 사용자로부터 새로운 비밀번호(newPassword)와 비밀번호 확인(confirmPassword)를 입력받습니다.
 */
@Getter
@Data
public class MemberPasswordChangeRequest {

    /**
     * 새로운 비밀번호
     * 예시: "Example@password123"
     */
    @Schema(description = "변경할 새로운 비밀번호", example = "Example@password123")
    private String newPassword;

    /**
     * 새로운 비밀번호 확인
     * 예시: "Example@password123"
     */
    @Schema(description = "새 비밀번호 확인", example = "Example@password123")
    private String confirmPassword;

}
