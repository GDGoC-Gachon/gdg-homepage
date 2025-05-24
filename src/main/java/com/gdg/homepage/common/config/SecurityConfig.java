package com.gdg.homepage.common.config;

import com.gdg.homepage.common.security.jwt.filter.JwtAccessDeniedHandler;
import com.gdg.homepage.common.security.jwt.filter.JwtAuthenticationFailureHandler;
import com.gdg.homepage.common.security.jwt.filter.JwtAuthenticationFilter;
import com.gdg.homepage.landing.member.domain.MemberRole;
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

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger 및 에러 접근 허용
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/error"
                        ).permitAll()

                        // 기존 설정 유지
                        .requestMatchers("/", "/pageView/increment", "/api/v1/member/register", "/api/v1/member/login", "/api/v1/member/email", "/api/v1/member/email/verify").permitAll()
                        .requestMatchers("/api/v1/register/**").permitAll()
                        .requestMatchers("/api/v1/member/**").hasAnyAuthority(
                                MemberRole.MEMBER.getRole(), MemberRole.NON_MEMBER.getRole(),
                                MemberRole.TEAM_MEMBER.getRole(), MemberRole.ORGANIZER.getRole())
                        .requestMatchers("/admin/**").hasAnyAuthority(
                                MemberRole.TEAM_MEMBER.getRole(), MemberRole.ORGANIZER.getRole())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        excep -> excep
                                .authenticationEntryPoint(jwtAuthenticationFailureHandler)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
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
