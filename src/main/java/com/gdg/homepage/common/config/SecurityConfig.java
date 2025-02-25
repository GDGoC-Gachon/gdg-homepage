package com.gdg.homepage.common.config;

import com.gdg.homepage.common.security.jwt.filter.TokenAuthenticationFilter;
import com.gdg.homepage.common.security.jwt.provider.JwtTokenProvider;
import com.gdg.homepage.landing.member.domain.MemberRole;
import com.gdg.homepage.landing.member.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberDetailsService memberDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/v1/member/register", "/api/v1/member/login").permitAll()
                        .requestMatchers("/api/v1/member/myPage").hasAnyAuthority(
                                MemberRole.MEMBER.getRole(), MemberRole.NON_MEMBER.getRole(),
                                MemberRole.TEAM_MEMBER.getRole(), MemberRole.ORGANIZER.getRole())
                        .requestMatchers("/api/v1/registers/").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority(
                                MemberRole.TEAM_MEMBER.getRole(), MemberRole.ORGANIZER.getRole())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new TokenAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
