package com.gdg.homepage.common.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdg.homepage.common.response.ApiResponse;
import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> fail = ApiResponse.fail(
                new CustomException(ErrorCode.UNAUTHORIZED, authException.getMessage())
        );

        objectMapper.writeValue(response.getWriter(), fail);
    }
}
