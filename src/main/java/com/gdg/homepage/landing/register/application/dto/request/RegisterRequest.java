package com.gdg.homepage.landing.register.application.dto.request;

import com.gdg.homepage.landing.register.domain.entity.Grade;
import com.gdg.homepage.landing.register.domain.entity.Role;
import com.gdg.homepage.landing.register.domain.entity.TechField;
import com.gdg.homepage.landing.register.domain.entity.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 등록(지원) 요청 DTO입니다.
 * <p>
 * 회원가입 시 추가로 입력받는 지원(신청) 정보들을 포함합니다.<br>
 * 지원 역할, 학년, 학번, 전공, 관심 분야, 관심 스택, 기타 정보 등으로 구성되어 있습니다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    /**
     * 지원하는 역할
     *
     * <p>예: TEAM_MEMBER</p>
     */
    @Schema(description = "지원 역할", example = "TEAM_MEMBER")
    private Role role;

    /**
     * 학년
     *
     * <p>예: 1</p>
     */
    @Schema(description = "학년", example = "1")
    private Grade grade;

    /**
     * 학번
     *
     * <p>예: 202433444</p>
     */
    @Schema(description = "학번", example = "202433444")
    private String studentId;

    /**
     * 전공
     *
     * <p>예: 인공지능학과</p>
     */
    @Schema(description = "전공", example = "인공지능학과")
    private String major;

    /**
     * 관심 분야(여러 개 선택 가능)
     *
     * <p>예: ["FRONT_END", "BACK_END"]</p>
     */
    @Schema(
            description = "관심 분야(여러 개 선택 가능)",
            example = "[\"FRONT_END\", \"BACK_END\"]",
            type = "array",
            implementation = TechField.class
    )
    private List<TechField> techField;

    /**
     * 관심 기술 스택(여러 개 선택 가능)
     *
     * <p>예: ["SPRING_BOOT", "KOTLIN"]</p>
     */
    @Schema(
            description = "관심 스택(여러 개 선택 가능)",
            example = "[\"SPRING_BOOT\", \"KOTLIN\"]",
            type = "array",
            implementation = TechStack.class
    )
    private List<TechStack> techStack;

    /**
     * 기타(자유 기입란)
     *
     * <p>예: UI/UX 디자이너도 가능합니다.</p>
     */
    @Schema(description = "기타(자유 기입란)", example = "UI/UX 디자이너도 가능합니다.")
    private String other;
}
