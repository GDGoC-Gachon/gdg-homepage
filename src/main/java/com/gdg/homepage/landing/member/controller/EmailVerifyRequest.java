package com.gdg.homepage.landing.member.controller;

import lombok.Data;

@Data
public class EmailVerifyRequest {

    private String email;

    private String code;
}
