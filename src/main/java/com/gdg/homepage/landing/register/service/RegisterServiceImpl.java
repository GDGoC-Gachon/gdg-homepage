package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.admin.service.AdminService;
import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.api.dto.RegisterResponse;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.domain.RegisterSnippet;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RegisterRepository registerRepository;
    private final AdminService adminService;

    @Override
    public Register createRegister(RegisterRequest request) {
        LocalDateTime now = LocalDateTime.now();
        JoinPeriod period = adminService.checkJoinPeriod(now);

        RegisterSnippet snippet = RegisterSnippet.of(request.getGrade(), request.getStudentId(), request.getMajor(), request.getTechField(), request.getTechStack());
        Register register = Register.of(period, snippet, request.getRole());
        return registerRepository.save(register);
    }

    @Override
    public Register getRegisterById(Long id) {
        return registerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Register not found with id: " + id));
    }

    @Override
    public List<RegisterResponse> getAllRegisters() {
        List<Register> registers = registerRepository.findAll();
        return registers.stream()
                .map(register -> RegisterResponse.from(
                        register.getSnippet().getStudentId(),
                        register.getRegisteredRole(),
                        register.getSnippet().getGrade(),
                        register.getSnippet().getMajor(),
                        register.getSnippet().getTechField(),
                        register.getSnippet().getTechStack()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public Register updateRegister(Long id, RegisterRequest request) {
        Register existingRegister = getRegisterById(id);
        RegisterSnippet snippet = RegisterSnippet.of(
                request.getGrade(),
                request.getStudentId(),
                request.getMajor(),
                request.getTechField(),
                request.getTechStack()
        );
        existingRegister.updateSnippet(snippet);
        existingRegister.updateRole(request.getRole());
        return registerRepository.save(existingRegister);
    }

    @Override
    public void deleteRegister(Long id) {
        Register register = getRegisterById(id);
        registerRepository.delete(register);
    }

}
