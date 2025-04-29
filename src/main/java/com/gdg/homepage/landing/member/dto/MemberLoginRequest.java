package com.gdg.homepage.landing.member.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberLoginRequest {

    private String email;
    private String password;

}
