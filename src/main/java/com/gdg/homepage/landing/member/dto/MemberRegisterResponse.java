package com.gdg.homepage.landing.member.dto;

import com.gdg.homepage.landing.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterResponse {


    private Long Id;
    private String userId;
    private String jwtToken;

    // from
    public static MemberRegisterResponse from (Member member){

        return MemberRegisterResponse.builder()
                .Id(member.getId())
                .userId(member.getEmail())
                .build();
    }

}
