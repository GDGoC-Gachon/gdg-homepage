package com.gdg.homepage.landing.register.api.dto;

import com.gdg.homepage.landing.register.domain.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterResponse {

    private String studentId;
    private Role role;
    private int grade;
    private String major;
    private TechField techField;
    private TechStack techStack;

    public static RegisterResponse from(String studentId, Role role, int grade, String major, TechField techField, TechStack techStack) {
        return RegisterResponse.builder()
                .studentId(studentId)
                .role(role)  // Role 자체를 저장 (String 변환 X)
                .grade(grade)
                .major(major)
                .techField(techField)  // Enum 그대로 사용
                .techStack(techStack)
                .build();
    }

    public static RegisterResponse from(Register register) {
        RegisterSnippet snippet = register.getSnippet();

        return RegisterResponse.builder()
                .studentId(snippet.getStudentId())
                .role(register.getRegisteredRole())  // Role 자체를 저장 (String 변환 X)
                .grade(snippet.getGrade())
                .major(snippet.getMajor())
                .techField(snippet.getTechField())  // Enum 그대로 사용
                .techStack(snippet.getTechStack())
                .build();
    }
}