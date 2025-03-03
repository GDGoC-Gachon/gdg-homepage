package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.api.dto.RegisterResponse;
import com.gdg.homepage.landing.register.domain.Register;
import java.util.List;

public interface RegisterService {

    // 회원 생성과 함께 사용할 내부 메서드
    Register createRegister(RegisterRequest request);

    // 조회 관련 메서드 (관리자용)
    Register getRegisterById(Long id);

    // 지원서 조회
    List<RegisterResponse>getAllRegisters();

    // 수정 관련 메서드
    Register updateRegister(Long id, RegisterRequest request);

    // register 삭제
    void deleteRegister(Long id);


    // 검증 관련 메서드
//    boolean validateRegisterRequest(RegisterRequest request);
//    boolean isDuplicateRegister(String studentId);
}
