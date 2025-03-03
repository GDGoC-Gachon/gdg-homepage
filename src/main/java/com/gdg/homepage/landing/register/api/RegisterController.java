package com.gdg.homepage.landing.register.api;

import com.gdg.homepage.landing.register.api.dto.RegisterRequest;
import com.gdg.homepage.landing.register.api.dto.RegisterResponse;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registers")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    // 📌 2. 특정 회원 조회 (Read)
    @GetMapping("/{id}")
    public ResponseEntity<RegisterResponse> getRegisterById(@PathVariable Long id) {
        Register register = registerService.getRegisterById(id);
        RegisterResponse response = RegisterResponse.from(
                register.getSnippet().getStudentId(),
                register.getRegisteredRole(),
                register.getSnippet().getGrade(),
                register.getSnippet().getMajor(),
                register.getSnippet().getTechField(),
                register.getSnippet().getTechStack()
        );
        return ResponseEntity.ok(response);
    }

    // 📌 3. 모든 회원 조회 (Read)
    @GetMapping("/all")
    public ResponseEntity<List<RegisterResponse>> getAllRegisters() {
        List<RegisterResponse> responses = registerService.getAllRegisters();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/")
    public ResponseEntity<List<RegisterResponse>> getRegisters() {
        return getAllRegisters();
    }

    // 📌 4. 회원 정보 수정 (Update)
    @PutMapping("/update/{id}")
    public ResponseEntity<RegisterResponse> updateRegister(@PathVariable Long id, @RequestBody RegisterRequest request) {
        Register updatedRegister = registerService.updateRegister(id, request);
        RegisterResponse response = RegisterResponse.from(
                updatedRegister.getSnippet().getStudentId(),
                updatedRegister.getRegisteredRole(),
                updatedRegister.getSnippet().getGrade(),
                updatedRegister.getSnippet().getMajor(),
                updatedRegister.getSnippet().getTechField(),
                updatedRegister.getSnippet().getTechStack()
        );
        return ResponseEntity.ok(response);
    }

    // 📌 5. 회원 삭제 (Delete)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRegister(@PathVariable Long id) {
        registerService.deleteRegister(id);
        return ResponseEntity.noContent().build();
    }
}