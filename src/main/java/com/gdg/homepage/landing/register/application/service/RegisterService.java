package com.gdg.homepage.landing.register.application.service;

import com.gdg.homepage.landing.admin.domain.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.application.usecase.AdminUseCase;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import com.gdg.homepage.landing.register.application.dto.response.MemberRegisterResponse;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.register.application.dto.request.MemberRequest;
import com.gdg.homepage.landing.register.application.dto.request.RegisterRequest;
import com.gdg.homepage.landing.register.application.usecase.RegisterUseCase;
import com.gdg.homepage.landing.register.domain.entity.Register;
import com.gdg.homepage.landing.register.domain.entity.RegisterSnippet;
import com.gdg.homepage.landing.register.domain.repository.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.gdg.homepage.core.response.ErrorCode.DUPLICATE_EMAIL;
import static com.gdg.homepage.core.response.ErrorCode.NOT_PERIOD;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {

    private final RegisterRepository registerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /// 외부 의존성
    private final MemberRepository memberRepository;
    private final AdminUseCase adminService;

    /// 비즈니스 로직 처리
    @Override
    public MemberRegisterResponse registerMember(MemberRequest request, RegisterRequest registerRequest) {

        /// 이메일이 중복되었는지 확인
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException(DUPLICATE_EMAIL.getMessage());
        }

        /// 신청서 작업까지 마무리 되었는지 확인
        Register register = createRegister(registerRequest);

        /// 신청서도 작업이 완료되었다면, 신청서와 멤버를 함께 저장
        Member member = Member.of(request.getEmail(), bCryptPasswordEncoder.encode(request.getPassword()), request.getName(), request.getPhoneNumber(), register);

        return MemberRegisterResponse.from(memberRepository.save(member));
    }


    private Register createRegister(RegisterRequest request) {

        /// 현재 시간 바탕
        LocalDateTime now = LocalDateTime.now();

        /// 가입 기간 체크
        JoinPeriod period = adminService.checkJoinPeriod(now);

        /// 아니라면, 예외 처리
        if (!period.getStatus()) {
            throw new IllegalStateException(NOT_PERIOD.getMessage());
        }

        /// 객체 생성
        RegisterSnippet snippet = RegisterSnippet.of(request.getGrade(), request.getStudentId(), request.getMajor(), request.getTechField(), request.getTechStack(), request.getOther());
        Register register = Register.of(period, snippet, request.getRole());

        /// 저장 후 리턴
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
                request.getTechStack() != null ? request.getTechStack() : existingSnippet.getTechStack(),
                request.getOther() != null ? request.getOther() : existingSnippet.getOther()
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
