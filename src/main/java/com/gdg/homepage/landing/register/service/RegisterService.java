package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;

import java.time.LocalDateTime;

public interface RegisterService {

    // 회원 생성과 함께 사용할 내부 메서드
    Register createRegister(RegisterRequest request);

    // 수정 관련 메서드
    Register updateRegister(Long memberId, RegisterRequest request);

    // 삭제 관련 메서드
    void deleteRegister(Long memberId);

    // 현재 가입자 수 체크
    long checkNowRegister(LocalDateTime now);
}
