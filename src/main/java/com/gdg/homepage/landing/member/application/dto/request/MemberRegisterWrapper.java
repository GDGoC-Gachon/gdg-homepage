package com.gdg.homepage.landing.member.application.dto.request;

import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
public class MemberRegisterWrapper {

    private MemberRegisterRequest member;
    private RegisterRequest apply;
}
