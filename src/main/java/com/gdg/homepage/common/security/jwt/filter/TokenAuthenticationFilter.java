package com.gdg.homepage.common.security.jwt.filter;

import com.gdg.homepage.common.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Access Token 추출
        String accessToken = resolveToken(request);

        // Access Token 검증 및 인증 설정
        if (accessToken != null && tokenProvider.validateToken(accessToken)) {
            setAuthentication(accessToken);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * SecurityContext에 인증 정보 설정
     * @param accessToken 유효한 Access Token
     */
    private void setAuthentication(String accessToken) {
        try {
            // Access Token을 통해 인증 객체 생성
            Authentication authentication = tokenProvider.getAuthentication(accessToken);

            // 인증 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("로그인 인증 실패: {}", e.getMessage()); // 인증 실패 로그
            SecurityContextHolder.clearContext(); // 인증 실패 시 컨텍스트 초기화
        }
    }

    /**
     * Authorization 헤더에서 토큰을 추출
     * @param request HTTP 요청
     * @return Bearer 토큰 문자열 (없으면 null)
     */
    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null; // 토큰이 없으면 null 반환
        }

        return authorization.substring(7); // "Bearer " 이후의 토큰 부분만 반환
    }
}
