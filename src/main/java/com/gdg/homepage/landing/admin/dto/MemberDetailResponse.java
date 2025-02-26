package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDetailResponse {

    /// List에 있던 정보
    private MemberListResponse member;
    private String major;

    private String field;
    private String stack;

    public static MemberDetailResponse from(Member member) {

        RegisterSnippet snippet = member.getRegister().getSnippet();

        return MemberDetailResponse.builder()
                .member(MemberListResponse.from(member))
                .major(snippet.getMajor())
                .field(String.valueOf(snippet.getTechField()))
                .stack(String.valueOf(snippet.getTechStack()))
                .build();

    }


}
