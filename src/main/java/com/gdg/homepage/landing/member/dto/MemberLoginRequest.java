package com.gdg.homepage.landing.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberLoginRequest {

    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;

    @Schema(description = "패스워드", example = "example")
    private String password;

}
