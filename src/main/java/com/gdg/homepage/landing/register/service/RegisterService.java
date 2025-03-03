package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;

public interface RegisterService {

    // 회원 생성과 함께 사용할 내부 메서드
    Register createRegister(RegisterRequest request);

    // 수정 관련 메서드
    Register updateRegister(Long memberId, RegisterRequest request);

}
