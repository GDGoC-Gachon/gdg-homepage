package com.gdg.homepage.landing.apply.domain;

import com.gdg.homepage.landing.apply.domain.TechField;
import com.gdg.homepage.landing.apply.domain.TechStack;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RegisterSnippet {

    private int grade;
    private String studentId;
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechField techField;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechStack techStack;

    // of() 메서드 대신 빌더 패턴으로 객체 생성
    public static RegisterSnippet of(int grade, String studentId, String major, TechField techField, TechStack techStack) {
        return RegisterSnippet.builder()
                .grade(grade)
                .studentId(studentId)
                .major(major)
                .techField(techField)
                .techStack(techStack)
                .build();
    }
}
