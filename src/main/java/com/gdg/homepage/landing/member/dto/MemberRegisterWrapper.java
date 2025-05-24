package com.gdg.homepage.landing.member.dto;

import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterWrapper {

    private MemberRegisterRequest member;
    private RegisterRequest apply;
}
