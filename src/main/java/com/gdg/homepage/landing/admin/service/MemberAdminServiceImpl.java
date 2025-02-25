package com.gdg.homepage.landing.admin.service;

import com.gdg.homepage.common.response.page.PageRequest;
import com.gdg.homepage.common.response.page.PageResponse;
import com.gdg.homepage.landing.admin.dto.MemberApproveRequest;
import com.gdg.homepage.landing.member.domain.Member;
import com.gdg.homepage.landing.admin.dto.MemberDetailResponse;
import com.gdg.homepage.landing.admin.dto.MemberListResponse;
import com.gdg.homepage.landing.admin.dto.MemberUpgradeRequest;
import com.gdg.homepage.landing.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository repository;

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
                .orElseThrow(() -> new EntityNotFoundException("해당하는 어드민이 존재하지 않습니다."));

        Member member = repository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("권한을 수정할 멤버가 존재하지 않습니다."));

        // 권한 수정
        member.upgradeRole(admin, request.getRole());
    }

    @Override
    public MemberDetailResponse loadMember(Long memberId) {
        Member member = repository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버가 존재하지 않습니다."));

        return MemberDetailResponse.from(member);
    }

    @Override
    public int getTotalMembers() {
        return repository.findAll().size();
    }

    @Override
    public void approveMember(MemberApproveRequest request) {
        Member admin = repository.findById(request.getAdminId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 어드민이 존재하지 않습니다."));

        Member member = repository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("승인할 멤버가 존재하지 않습니다."));

        // 승인
        member.getRegister().approve();

        // 역할 변경
        member.changeRole(admin, member);
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
