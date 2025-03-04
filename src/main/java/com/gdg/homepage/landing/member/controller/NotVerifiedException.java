package com.gdg.homepage.landing.member.controller;

public class NotVerifiedException extends RuntimeException {

    public NotVerifiedException(String message) {
        super(message); // 부모 클래스(RuntimeException)에 메시지 전달
    }
}
