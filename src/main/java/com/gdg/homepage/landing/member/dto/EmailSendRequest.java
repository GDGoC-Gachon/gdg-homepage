package com.gdg.homepage.landing.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class EmailSendRequest {

    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;
}
