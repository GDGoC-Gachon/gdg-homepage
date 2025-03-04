package com.gdg.homepage.landing.register.api.dto;

import com.gdg.homepage.landing.register.domain.Role;
import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
@Getter
public class RegisterRequest {

    private Role role;
    private int grade;
    private String studentId;
    private String major;

    private TechField techField;
    private TechStack techStack;



}
