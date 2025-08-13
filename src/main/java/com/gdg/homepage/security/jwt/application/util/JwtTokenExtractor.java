package com.gdg.homepage.security.jwt.application.util;

import com.gdg.homepage.core.response.ErrorCode;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import com.gdg.homepage.security.jwt.domain.entity.CustomUserDetails;
import com.gdg.homepage.security.filter.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

import static com.gdg.homepage.security.jwt.application.util.TokenNameUtil.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    @Value("${gdg.jwt.key}")
    private String key;

    private SecretKey secretKey;

    /// 의존성
    private final MemberRepository repository;
    private final CookieUtil cookieUtil;


    /// JWT 의존성은 SecretKey 필요
    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    /// 토큰 추출하기
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return cookieUtil.getAccessTokenFromCookie(request);
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return cookieUtil.getRefreshTokenFromCookie(request);
    }

    /// 내부 함수
    private Claims parseClaims(String token) {
        try {
            // JWT 파서를 빌드하고 서명된 토큰을 파싱
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // 서명 키를 설정
                    .build()
                    .parseClaimsJws(token)  // 서명된 JWT 토큰을 파싱
                    .getBody();  // Claims 객체 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims();

        } catch (MalformedJwtException e) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN.getMessage());
        }
    }

    /// 검증 여부
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            log.info("토큰 유효성 검사 성공: {}", claims);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("토큰 만료됨", e);
            throw new AuthenticationException("토큰이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("토큰 검증 실패: {}", e.getMessage(), e);
            throw new AuthenticationException("토큰이 생성되지 않았습니다."); // 여기가 문제라면 메시지 바꿔도 좋음
        }
    }

    public Authentication getAuthentication(String token) {

        /// JWT 파싱
        Claims claims = parseClaims(token);

        /// 권한 정보 가져오기
        List<String> authoritiesList = (List<String>) claims.get(ROLE_CLAIM);

        // 권한을 SimpleGrantedAuthority로 변환
        Collection<? extends GrantedAuthority> authorities =
                authoritiesList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        // userId를 String 변환
        Long claimUserId = Long.valueOf(claims.get(ID_CLAIM, String.class));

        // 해당 userId로 Member를 조회
        Member user = repository.findById(claimUserId)
                .orElseThrow(() -> new NoSuchElementException(ErrorCode.USER_NOT_FOUND.getMessage()));

        CustomUserDetails details = CustomUserDetails.of(user);

        /// SecurityContext에 저장하기 위한 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(details, token, authorities);
    }

    /// @Getter
    // 사용자 정보 추출
    public String getId(String token) {
        return getIdFromToken(token, ID_CLAIM);
    }

    public String getEmail(String token) {
        return getClaimFromToken(token, EMAIL_CLAIM);
    }

    public String getRole(String token) {
        return getClaimFromToken(token, ROLE_CLAIM);
    }

    public Boolean isExpired(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private String getClaimFromToken(String token, String claimName) {
        Claims claims = parseClaims(token);
        return claims.get(claimName, String.class);
    }

    private String getIdFromToken(String token, String claimName) {
        Claims claims = parseClaims(token);
        return claims.get(claimName, String.class);
    }
}
