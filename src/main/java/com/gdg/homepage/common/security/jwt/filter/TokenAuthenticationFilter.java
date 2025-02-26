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
import org.springframework.util.StringUtils;
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
        String accessToken = resolveToken(request);

        if (accessToken != null && tokenProvider.validateToken(accessToken)) {
            setAuthentication(accessToken);
        } else {
        }

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
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
