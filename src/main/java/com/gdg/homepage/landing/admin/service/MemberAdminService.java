package com.gdg.homepage.landing.admin.service;


import com.gdg.homepage.common.response.page.PageRequest;
import com.gdg.homepage.common.response.page.PageResponse;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.admin.dto.MemberListResponse;
import com.gdg.homepage.landing.admin.dto.MemberUpgradeRequest;

public interface MemberAdminService {
    /*
        회원 목록 조회 (승인)
        회원 목록 조회 (승인 대기중)
        회원 역할 변경 (Organizer만)
        회원 탈퇴 (Organizer, Team Member만)
        회원 조회
        총 멤버 수 가져오기
        탈퇴 인원 수 가져오기
     */

    // 회원 목록 조회 (승인)
    PageResponse<MemberListResponse> findAll(PageRequest pageRequest);

    // 회원 목록 조회 (미승인)
    PageResponse<MemberListResponse> findAllNotApproved(PageRequest pageRequest);

    // 회원 역할 변경
    void changeRole(MemberUpgradeRequest request);

    // 회원 개인정보 상세 조회하기
    MemberDetailResponse loadMember(Long memberId);

    // 총 멤버 수 갸져오기
    int getTotalMembers();

    // 회원 탈퇴 시키기
    void removeMember(Long memberId);

    // 최근 탈퇴 인원 수 가져오기
    // 최근 한 달을 기준으로 잡는다.
    int findRecentWithdrawnMembers();


}
