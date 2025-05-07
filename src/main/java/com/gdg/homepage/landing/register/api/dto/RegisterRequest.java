package com.gdg.homepage.landing.register.api.dto;

import com.gdg.homepage.landing.register.domain.Grade;
import com.gdg.homepage.landing.register.domain.Role;
import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RegisterRequest {

    private Role role;
    private Grade grade;
    private String studentId;
    private String major;

    private List<TechField> techField;
    private List<TechStack> techStack;



}
