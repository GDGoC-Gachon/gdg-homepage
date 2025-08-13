package com.gdg.homepage.security.config;

import com.gdg.homepage.security.filter.AuthenticationFilter;
import com.gdg.homepage.security.filter.RequestMatcherHolder;
import com.gdg.homepage.security.handler.AuthenticationDeniedHandler;
import com.gdg.homepage.security.handler.AuthenticationFailureHandler;
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
import org.springframework.web.cors.CorsConfigurationSource;

import static com.gdg.homepage.landing.member.domain.entity.MemberRole.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final AuthenticationDeniedHandler jwtAccessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;
    private final RequestMatcherHolder requestMatcherHolder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .httpBasic(basic -> basic.disable())
                .cors(cors -> {
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(null))
                        .permitAll()
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(MEMBER))
                        .hasAnyAuthority(MEMBER.getRole(), TEAM_MEMBER.getRole(), ORGANIZER.getRole())
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(TEAM_MEMBER))
                        .hasAnyAuthority(TEAM_MEMBER.getRole(), ORGANIZER.getRole())
                        .requestMatchers(requestMatcherHolder.getRequestMatchersByMinRole(ORGANIZER))
                        .hasAnyAuthority(ORGANIZER.getRole())
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
