package com.gdg.homepage.landing.application.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Embeddable
@Getter
public class ApplicationSnippet {

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
