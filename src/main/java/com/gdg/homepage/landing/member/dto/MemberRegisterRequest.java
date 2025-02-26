package com.gdg.homepage.landing.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

    // 가입
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

}
