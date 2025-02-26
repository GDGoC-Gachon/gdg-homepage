package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.service.AdminService;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;
    private final AdminService adminService;

    public Register CreateRegister(Register register) {
        return registerRepository.save(register);
    }

    @Override
    public Register createRegister(RegisterRequest request) {
        LocalDateTime now = LocalDateTime.now();
        JoinPeriod period = adminService.checkJoinPeriod(now);

        RegisterSnippet snippet = RegisterSnippet.of(request.getGrade(), request.getStudentId(), request.getMajor(), request.getTechField(), request.getTechStack());
        Register register = Register.of(period, snippet, request.getRole());
        return registerRepository.save(register);
    }

    public void deleteRegister(Long id){
        registerRepository.deleteById(id);
    }

//    public List<Register> getRegisterByJoinPeriod(JoinPeriod joinPeriod) {
//        return registerRepository.findById(joinPeriod);
//    }


}
