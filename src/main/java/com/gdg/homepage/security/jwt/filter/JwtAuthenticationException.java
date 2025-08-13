package com.gdg.homepage.security.jwt.filter;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String s) {
        super(s);
    }
}
