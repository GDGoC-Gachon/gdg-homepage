package com.gdg.homepage.landing.admin.application.service;

import com.gdg.homepage.core.response.page.PageRequest;
import com.gdg.homepage.core.response.page.PageResponse;
import com.gdg.homepage.landing.admin.application.dto.request.MemberApprovalDecisionRequest;
import com.gdg.homepage.landing.admin.application.usecase.MemberAdminUseCase;
import com.gdg.homepage.landing.member.domain.entity.Member;
import com.gdg.homepage.landing.admin.application.dto.response.MemberDetailResponse;
import com.gdg.homepage.landing.admin.application.dto.response.MemberListResponse;
import com.gdg.homepage.landing.admin.application.dto.request.MemberUpgradeRequest;
import com.gdg.homepage.landing.member.domain.repository.MemberRepository;
import com.gdg.homepage.landing.register.domain.repository.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.gdg.homepage.core.response.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAdminService implements MemberAdminUseCase {

    private final MemberRepository repository;
    private final RegisterRepository registerRepository;

    @Override
    public PageResponse<MemberListResponse> findAll(PageRequest pageRequest) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Member> result = repository.findAllMemberApproved(pageable);
        List<MemberListResponse> dtoList = MemberListResponse.from(result.getContent());

        return new PageResponse<>(dtoList, pageRequest, result.getTotalElements());
    }

    @Override
    public PageResponse<MemberListResponse> findAllNotApproved(PageRequest pageRequest) {

        Pageable pageable = org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage() - 1,
                pageRequest.getSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<Member> result = repository.findAllMemberNotApproved(pageable);
        List<MemberListResponse> dtoList = MemberListResponse.from(result.getContent());

        return new PageResponse<>(dtoList,pageRequest, result.getTotalElements());
    }

    @Override
    public void changeRole(MemberUpgradeRequest request) {
        Member admin = repository.findById(request.getAdminId())
                .orElseThrow(() -> new NoSuchElementException(ADMIN_NOT_FOUND.getMessage()));

        Member member = repository.findById(request.getMemberId())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND.getMessage()));

        // 권한 수정
        member.upgradeRole(admin, request.getRole());
    }

    @Override
    public MemberDetailResponse loadMember(Long memberId) {
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND.getMessage()));

        return MemberDetailResponse.from(member);
    }

    @Override
    public int getTotalMembers() {
        return repository.findAll().size();
    }

    @Override
    public void approveMember(MemberApprovalDecisionRequest request) {
        Member admin = repository.findById(request.getAdminId())
                .orElseThrow(() -> new NoSuchElementException(ADMIN_NOT_FOUND.getMessage()));

        Member member = repository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException(MEMBER_APPROVE_TARGET_NOT_FOUND.getMessage()));

        // 승인
        member.getRegister().approve();

        // 역할 변경
        member.changeRole(admin, member);
    }

    @Override
    public void rejectMember(MemberApprovalDecisionRequest request) {

        Member admin = repository.findById(request.getAdminId())
                .orElseThrow(() -> new NoSuchElementException(ADMIN_NOT_FOUND.getMessage()));

        Member member = repository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException(MEMBER_APPROVE_TARGET_NOT_FOUND.getMessage()));

        // 승인
        member.getRegister().reject();

        // 삭제 처리 ?
        repository.delete(member);
        registerRepository.deleteByMember(member);

    }

    @Override
    public void removeMember(Long memberId) {
        repository.deleteById(memberId);
    }

    @Override
    public int findRecentWithdrawnMembers() {
        /// 최근 탈퇴한 인원에 대한 정보를 DB에 저장해야한다.
        return 0;
    }


}
