package com.gdg.homepage.security.filter;

import com.gdg.homepage.landing.member.domain.entity.MemberRole;
import io.micrometer.common.lang.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpMethod.*;

@Component
public class RequestMatcherHolder {

    private static final List<RequestInfo> REQUEST_INFO_LIST = List.of(

            // 공통
            new RequestInfo(OPTIONS, "/**", null),
            new RequestInfo(GET, "/", null),

            // auth
            new RequestInfo(POST, "/api/v1/auth/login", null),
            new RequestInfo(POST, "/api/v1/auth/logout", MemberRole.MEMBER),
            new RequestInfo(POST, "/api/v1/auth/reissue", MemberRole.MEMBER),

            // 운영진
            new RequestInfo(GET, "/admin/**", MemberRole.TEAM_MEMBER),

            // 이메일
            new RequestInfo(POST, "/api/v1/email/**", null),


            // 유저
            new RequestInfo(POST, "/api/v1/member/**", MemberRole.MEMBER),
            new RequestInfo(GET, "/api/v1/member/**", MemberRole.MEMBER),
            new RequestInfo(PUT, "/api/v1/member/**", MemberRole.MEMBER),
            new RequestInfo(DELETE, "/api/v1/member/**", MemberRole.MEMBER),


            // 신청
            new RequestInfo(POST, "/api/v1/register/**", null),


            // static resources
            new RequestInfo(GET, "/docs/**", null),
            new RequestInfo(GET, "/*.ico", null),
            new RequestInfo(GET, "/resources/**", null),
            new RequestInfo(GET, "/index.html", null),
            new RequestInfo(GET, "/error", null),
            new RequestInfo(GET, "/gdg.png", null),

            // Swagger UI 및 API 문서 관련 요청
            new RequestInfo(GET, "/v3/api-docs/**", null),
            new RequestInfo(GET, "/swagger-ui/**", null),
            new RequestInfo(GET, "/swagger-resources/**", null),
            new RequestInfo(GET, "/webjars/**", null),
            new RequestInfo(GET, "/swagger-ui.html", null),

            // 정적 아이콘 요청
            new RequestInfo(GET, "/favicon.ico", null),
            new RequestInfo(GET, "/apple-touch-icon.png", null)

    );




    private final ConcurrentHashMap<String, RequestMatcher> reqMatcherCacheMap = new ConcurrentHashMap<>();

    /**
     * 최소 권한이 주어진 요청에 대한 RequestMatcher 반환
     * @param minRole 최소 권한 (Nullable)
     * @return 생성된 RequestMatcher
     */
    public RequestMatcher getRequestMatchersByMinRole(@Nullable MemberRole minRole) {
        var key = getKeyByRole(minRole);
        return reqMatcherCacheMap.computeIfAbsent(key, k ->
                new OrRequestMatcher(REQUEST_INFO_LIST.stream()
                        .filter(reqInfo -> isAccessible(reqInfo.minRole(), minRole))
                        .map(reqInfo -> new AntPathRequestMatcher(reqInfo.pattern(),
                                reqInfo.method().name()))
                        .toArray(AntPathRequestMatcher[]::new)));
    }

    private boolean isAccessible(@Nullable MemberRole requiredRole, @Nullable MemberRole currentRole) {
        if (requiredRole == null) return true; // 누구나 접근 가능
        if (currentRole == null) return false; // 권한 없음
        return currentRole.ordinal() >= requiredRole.ordinal(); // ADMIN이면 MEMBER 포함
    }

    private String getKeyByRole(@Nullable MemberRole minRole) {
        return minRole == null ? "VISITOR" : minRole.name();
    }

    private record RequestInfo(HttpMethod method, String pattern, MemberRole minRole) {}

}
