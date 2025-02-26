package com.gdg.homepage.landing.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmailVerification(String email) {
    }

    @Override
    public boolean verifyEmail(String email, String code) {
        return false;
    }
}
