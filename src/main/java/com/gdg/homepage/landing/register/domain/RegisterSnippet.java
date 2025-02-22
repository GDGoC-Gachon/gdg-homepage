package com.gdg.homepage.landing.register.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
public class RegisterSnippet {

    private String name;
    private String email;
    private String phoneNumber;

    private int grade;
    private String studentId;
    private String major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechField techField;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechStack techStack;


}
