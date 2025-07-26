package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.domain.TechField;
import com.gdg.homepage.landing.register.domain.TechStack;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MemberDetailResponse {

    /// List에 있던 정보
    private MemberListResponse member;
    private String major;

    private List<TechField> field;
    private List<TechStack> stack;
    private LocalDateTime approvedAt;

    public static MemberDetailResponse from(Member member) {

        RegisterSnippet snippet = member.getRegister().getSnippet();

        return MemberDetailResponse.builder()
                .member(MemberListResponse.from(member))
                .major(snippet.getMajor())
                .field(snippet.getTechField())
                .stack(snippet.getTechStack())
                .build();

    }


}
