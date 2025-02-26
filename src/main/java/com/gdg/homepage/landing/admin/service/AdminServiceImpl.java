package com.gdg.homepage.landing.admin.service;

import com.gdg.homepage.common.response.CustomException;
import com.gdg.homepage.common.response.ErrorCode;
import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.dto.JoinPeriodRequest;
import com.gdg.homepage.landing.admin.dto.JoinPeriodResponse;
import com.gdg.homepage.landing.admin.repository.JoinPeriodRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final JoinPeriodRepository joinPeriodRepository;

    @Override
    public void createJoinPeriod(JoinPeriodRequest joinPeriodRequest) {
        // 가입 기간 생성
        JoinPeriod joinPeriod=JoinPeriod.builder()
                .title(joinPeriodRequest.getTitle())
                .startDate(joinPeriodRequest.getStartDate())
                .endDate(joinPeriodRequest.getEndDate())
                .maxMember(joinPeriodRequest.getMaxMember())
                .status(true)
                .build();

        joinPeriodRepository.save(joinPeriod);
    }

    @Override
    public JoinPeriodResponse updateJoinPeriod(Long id, JoinPeriodRequest joinPeriodRequest) {
        //
        JoinPeriod joinPeriod = joinPeriodRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_END_POINT));
        // 요청 값이 존재하면 수정
        if(joinPeriodRequest!=null) {
            joinPeriod.updateJoinPeriod(joinPeriodRequest);
        }
        JoinPeriod updatedJoinPeriod = joinPeriodRepository.save(joinPeriod);
        return JoinPeriodResponse.from(updatedJoinPeriod);
    }

    @Override
    public List<JoinPeriodResponse> getAllJoinPeriods() {
        List<JoinPeriod> joinPeriods = joinPeriodRepository.findAll();
        return joinPeriods.stream()
                .map(joinPeriod -> JoinPeriodResponse.from(joinPeriod))
                .collect(Collectors.toList());
    }

    @Override
    public void terminateJoinPeriod(Long id) {
        JoinPeriod joinPeriod = joinPeriodRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_END_POINT));

        joinPeriod.terminateJoinPeriod();  // status 값을 false로 변경
        joinPeriodRepository.save(joinPeriod); // 변경 사항 저장
    }

    @Override
    public JoinPeriod checkJoinPeriod(LocalDateTime now) {
        return joinPeriodRepository.findActiveJoinPeriod(now)
                .orElseThrow(() -> new EntityNotFoundException("현재 시간에 대한 가입기간 설정이 존재하지 않습니다."));
    }

}
