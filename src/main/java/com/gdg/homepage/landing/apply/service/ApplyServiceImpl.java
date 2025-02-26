package com.gdg.homepage.landing.apply.service;

import com.gdg.homepage.landing.apply.api.dto.ApplyRequest;
import com.gdg.homepage.landing.apply.api.dto.ApplyResponse;
import com.gdg.homepage.landing.apply.domain.Apply;
import com.gdg.homepage.landing.apply.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {

    private final ApplyRepository registerRepository;

    // 가입서 추가(createApply)
    public ApplyResponse createApply(ApplyRequest applyRequest) {
//        Apply apply = Apply.builder()

    }

    // 가입서 수정(udateApply)=
    public ApplyResponse updateApply(ApplyRequest applyRequest) {
        return null;
    }

    // 가입서 삭제(deleteApply)
    public void deleteApply(ApplyRequest applyRequest) {}

    // 가입서 조회(getAllApply)
    public List<ApplyResponse> getAllApply() {}



}
