package com.gdg.homepage.landing.member.service;

import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.member.dto.CustomUserDetails;
import com.gdg.homepage.landing.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    /*
        시큐리티 관련한 서비스 계층
     */

    private final MemberRepository repository;

    /// 시큐리티 관련 서비스
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일에 해당하는 유저가 존재하지 않습니다."));

        return new CustomUserDetails(member);
    }

}
