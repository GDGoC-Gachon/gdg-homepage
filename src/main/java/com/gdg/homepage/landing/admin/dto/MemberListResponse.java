package com.gdg.homepage.landing.admin.dto;

import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberListResponse {

    private Long memberId;
    private String email;
    private String name;
    private int grade;
    private String studentId;
    private String phoneNumber;
    private String role;


    // from
    public static MemberListResponse from(Member member) {

        RegisterSnippet snippet = member.getRegister().getSnippet();

        return MemberListResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(snippet.getName())
                .grade(snippet.getGrade())
                .studentId(snippet.getStudentId())
                .phoneNumber(snippet.getPhoneNumber())
                .build();
    }

    // from
    public static List<MemberListResponse> from(List<Member> members) {

        return members.stream()
                .map(MemberListResponse::from).toList();
    }
}
