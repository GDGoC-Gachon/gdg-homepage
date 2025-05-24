package com.gdg.homepage.landing.register.api.dto;

import com.gdg.homepage.landing.register.domain.Grade;
import com.gdg.homepage.landing.register.domain.Role;
import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RegisterRequest {

    @Schema(description = "지원역할", example = "TEAM_MEMBER")
    private Role role;

    @Schema(description = "학년", example = "1")
    private Grade grade;

    @Schema(description = "학번", example = "202433444")
    private String studentId;

    @Schema(description = "전공", example = "인공지능학과")
    private String major;

    @Schema(
            description = "관심분야",
            example = "[\"FRONT\", \"BACK\"]",
            type = "array",
            implementation = TechField.class
    )
    private List<TechField> techField;

    @Schema(
            description = "관심스택",
            example = "[\"SPRING_BOOT\", \"KOTLIN\"]",
            type = "array",
            implementation = TechStack.class
    )
    private List<TechStack> techStack;

    private String other;



}
