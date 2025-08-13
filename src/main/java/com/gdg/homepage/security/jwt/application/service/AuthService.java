package com.gdg.homepage.security.jwt.application.service;

import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.member.application.dto.request.MemberLoginRequest;
import com.gdg.homepage.landing.member.application.dto.response.MemberLoginResponse;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.security.jwt.application.usecase.AuthUseCase;
import com.gdg.homepage.security.jwt.application.usecase.JwtTokenUseCase;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final MemberRepository repository;
    private final JwtTokenUseCase tokenService;
    /// 외부 의존성
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void login(MemberLoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        /// 유저
        Member member = getMember(request.getEmail());

        /// 비밀번호
        if (!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())) {
            repository.save(member);
            throw new BadCredentialsException(ErrorCode.PASSWORD_ERROR.getMessage());
        }

        /// 시큐리티에 저장할 객체
        CustomUserDetails userDetails = CustomUserDetails.of(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /// 토큰 발급
        tokenService.createAccessToken(httpServletResponse, authentication);
    }

    @Override
    public void logout(Long userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        /// 유저 예외 처리
        Member member = getMember(userId);

        /// 로그 아웃 하기
        tokenService.logout(member.getId(), httpServletRequest, httpServletResponse);

    }


    @Override
    public void reissueRefreshToken(Long userId, HttpServletRequest request, HttpServletResponse response) {

    }


    private Member getMember(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private Member getMember(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

}
