package com.gdg.homepage.landing.register.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RegisterSnippet {

    private Grade grade;
    private String studentId;
    private String major;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "register_snippet_tech_field", joinColumns = @JoinColumn(name = "register_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<TechField> techField;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "register_snippet_tech_stack", joinColumns = @JoinColumn(name = "register_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<TechStack> techStack;

    // of() 메서드 대신 빌더 패턴으로 객체 생성
    public static RegisterSnippet of(
            Grade grade,
            String studentId,
            String major,
            List<TechField> techFields,
            List<TechStack> techStacks
    ) {
        return RegisterSnippet.builder()
                .grade(grade)
                .studentId(studentId)
                .major(major)
                .techField(techFields)
                .techStack(techStacks)
                .build();
    }
}
