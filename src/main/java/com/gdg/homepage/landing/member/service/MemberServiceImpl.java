package com.gdg.homepage.landing.member.service;

import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.member.dto.MemberRegisterRequest;
import com.gdg.homepage.landing.member.dto.MemberRegisterResponse;
import com.gdg.homepage.landing.member.repository.MemberRepository;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.service.RegisterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RegisterService registerService;
    private final EmailService emailService;

    /// 비즈니스 로직 처리
    @Override
    public MemberRegisterResponse registerMember(MemberRegisterRequest request, RegisterRequest registerRequest) {

        /// 이메일이 중복되었는지 확인
        if (repository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
        }

        /// 신청서 작업까지 마무리 되었는지 확인
        Register register = registerService.createRegister(registerRequest);

        /// 신청서도 작업이 완료되었다면, 신청서와 멤버를 함께 저장
        Member member = Member.of(request.getEmail(), bCryptPasswordEncoder.encode(request.getPassword()), request.getName(), request.getPhoneNumber(), register);

        return MemberRegisterResponse.from(repository.save(member));
    }

    @Override
    public Member loadMyMember(Long memberId) {
        return repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버가 존재하지 않습니다."));
    }

    @Override
    public void requestPasswordChange(Long memberId) {
        Member member = repository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다."));
        String email = member.getEmail();

        emailService.sendEmailVerification(email);
    }

    @Override
    public void changePassword(Long memberId, String newPassword, String confirmPassword) {
        Member member = repository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("해당 멤버가 존재하지 않습니다."));

        if (!bCryptPasswordEncoder.matches(newPassword, member.getPassword())) {
            throw new IllegalStateException("설정한 비밀번호가 서로 다릅니다.");
        }

        member.changePassword(bCryptPasswordEncoder.encode(newPassword));
    }


}

