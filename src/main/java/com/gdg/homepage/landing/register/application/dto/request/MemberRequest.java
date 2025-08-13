package com.gdg.homepage.landing.register.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@Getter
public class MemberRequest {

    // 가입
    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "example")
    private String password;

    @Schema(description = "가입자 이름", example = "김가천")
    private String name;

    @Schema(description = "휴대폰 번호", example = "010-0000-0000")
    private String phoneNumber;

}
