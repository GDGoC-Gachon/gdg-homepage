package com.gdg.homepage.landing.register.application.dto.response;

import com.gdg.homepage.landing.register.domain.entity.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RegisterResponse {

    private String studentId;
    private Role role;
    private String grade;
    private String major;
    private List<TechField> techField;
    private List<TechStack> techStack;

    public static RegisterResponse from(String studentId, Role role, Grade grade, String major, List<TechField> techField, List<TechStack> techStack) {
        return RegisterResponse.builder()
                .studentId(studentId)
                .role(role)
                .grade(grade.getLabel())
                .major(major)
                .techField(techField)
                .techStack(techStack)
                .build();
    }

    public static RegisterResponse from(Register register) {
        RegisterSnippet snippet = register.getSnippet();

        return RegisterResponse.builder()
                .studentId(snippet.getStudentId())
                .role(register.getRegisteredRole())
                .grade(snippet.getGrade().getLabel())
                .major(snippet.getMajor())
                .techField(snippet.getTechField())
                .techStack(snippet.getTechStack())
                .build();
    }
}
