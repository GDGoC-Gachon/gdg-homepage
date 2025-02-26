package com.gdg.homepage.landing.register.api.dto;

import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import com.gdg.homepage.landing.register.domain.Role;
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
}