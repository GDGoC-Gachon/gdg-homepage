package com.gdg.homepage.landing.register.application.usecase;

import com.gdg.homepage.landing.register.application.dto.response.MemberRegisterResponse;
import com.gdg.homepage.landing.register.application.dto.request.MemberRequest;
import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import com.gdg.homepage.landing.register.domain.entity.Register;

import java.time.LocalDateTime;

public interface RegisterUseCase {

    /**
     * 회원가입을 처리합니다.
     */
    MemberRegisterResponse registerMember(MemberRequest request, RegisterRequest registerRequest);

    // 수정 관련 메서드
    Register updateRegister(Long memberId, RegisterRequest request);

    // 삭제 관련 메서드
    void deleteRegister(Long memberId);

}
