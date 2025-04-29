package com.gdg.homepage.landing.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginResponse {

    private String email;
    private String jwtToken;

    public static MemberLoginResponse from(String email, String jwtToken) {
        return MemberLoginResponse.builder()
                .email(email)
                .jwtToken(jwtToken)
                .build();
    }

}
