package com.gdg.homepage.common.config;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.repository.JoinPeriodRepository;
import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.member.domain.MemberRole;
import com.gdg.homepage.landing.member.repository.MemberRepository;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.domain.Role;
import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final RegisterRepository registerRepository;
    private final JoinPeriodRepository joinPeriodRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            // JoinPeriod 생성
            JoinPeriod forAdmin = JoinPeriod.builder()
                    .title("어드민 생성")
                    .startDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                    .maxMember(1)
                    .build();

            joinPeriodRepository.save(forAdmin);

            // Register 생성
            RegisterSnippet snippet = RegisterSnippet.of(5, "가천어드민", "가천", TechField.OTHER, TechStack.OTHER);
            Register register = Register.of(forAdmin, snippet, Role.ORGANIZER);
            register.approve();

            registerRepository.save(register);

            // Member 생성
            Member member = Member.builder()
                    .name("어드민")
                    .email(adminEmail)
                    .phoneNumber("010-0000-0000")
                    .password(bCryptPasswordEncoder.encode(adminPassword))
                    .role(MemberRole.ORGANIZER)
                    .build();
            member.setRegister(register);

            memberRepository.save(member);

            log.info("✅ 어드민 계정이 생성되었습니다. email: {}", adminEmail);
        };
    }
}
