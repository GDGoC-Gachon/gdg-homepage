package com.gdg.homepage.common.security.jwt.filter;

import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.common.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = resolveToken(request);

            if (accessToken == null) {
                throw new JwtAuthenticationException(ErrorCode.UNAUTHORIZED.getMessage());
            }

            if (tokenProvider.validateToken(accessToken)) {
               setAuthentication(accessToken);
               filterChain.doFilter(request, response);
            }
            else {
              throw new JwtAuthenticationException(ErrorCode.NOT_VALID.getMessage());
            }

        } catch (JwtAuthenticationException e) {
            log.warn("JWT 인증 실패: {}", e.getMessage());
            // 실패 핸들러 호출
            failureHandler.commence(request, response, e);
            return;
        }

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

        } catch (AccessDeniedException e) {
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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.equals("/") ||
                path.equals("/pageView/increment") ||
                path.equals("/api/v1/member/register") ||
                path.equals("/api/v1/member/login") ||
                path.equals("/api/v1/member/email") ||
                path.equals("/api/v1/member/email/verify") ||
                path.startsWith("/api/v1/register/");
    }


}
