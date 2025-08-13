package com.gdg.homepage.security.filter;

public class AuthenticationException extends org.springframework.security.core.AuthenticationException {
    public AuthenticationException(String s) {
        super(s);
    }
}
