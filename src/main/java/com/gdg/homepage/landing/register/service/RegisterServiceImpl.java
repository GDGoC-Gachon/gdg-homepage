package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.service.AdminService;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;
    private final AdminService adminService;

    /*
        지원서 생성은 회원가입과 동일하게 이루어지기에
        해당 부분은 API에서 사용하지 않는다.
     */

    @Override
    public Register createRegister(RegisterRequest request) {
        LocalDateTime now = LocalDateTime.now();
        JoinPeriod period = adminService.checkJoinPeriod(now);

        if (!period.getStatus()) {
            throw new IllegalStateException("가입시간이 조기종료 되었습니다.");
        }

        RegisterSnippet snippet = RegisterSnippet.of(request.getGrade(), request.getStudentId(), request.getMajor(), request.getTechField(), request.getTechStack());
        Register register = Register.of(period, snippet, request.getRole());
        return registerRepository.save(register);
    }

    /*
        마이페이지에서 지원서 수정 기능이 생긴다면,
        해당 기능을 사용한다.
     */
    @Override
    public Register updateRegister(Long memberId, RegisterRequest request) {
        Register existingRegister = getRegisterByMemberId(memberId);

        RegisterSnippet existingSnippet = existingRegister.getSnippet();

        // 기존 값과 새로운 값 비교하여 업데이트
        RegisterSnippet updatedSnippet = RegisterSnippet.of(
                request.getGrade() != null ? request.getGrade() : existingSnippet.getGrade(),
                request.getStudentId() != null ? request.getStudentId() : existingSnippet.getStudentId(),
                request.getMajor() != null ? request.getMajor() : existingSnippet.getMajor(),
                request.getTechField() != null ? request.getTechField() : existingSnippet.getTechField(),
                request.getTechStack() != null ? request.getTechStack() : existingSnippet.getTechStack()
        );

        existingRegister.updateSnippet(updatedSnippet);

        return registerRepository.save(existingRegister);
    }

    @Override
    public void deleteRegister(Long memberId) {
        Register register = getRegisterByMemberId(memberId);
        registerRepository.delete(register);
    }

    @Override
    public long checkNowRegister(LocalDateTime now) {
        return registerRepository.countByCurrentJoinPeriod(now);
    }

    // 내부 함수
    private Register getRegisterByMemberId(Long memberId) {
        return registerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 작성한 신청서가 존재하지 않습니다."));
    }

}
