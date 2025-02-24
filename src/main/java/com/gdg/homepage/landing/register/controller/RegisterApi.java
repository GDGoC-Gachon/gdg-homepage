package com.gdg.homepage.landing.register.controller;


import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.service.RegisterService;
import com.gdg.homepage.landing.register.service.RegisterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registers")
@RequiredArgsConstructor
public class RegisterApi {
    private final RegisterServiceImpl registerServiceImpl;

    @PostMapping
    public ResponseEntity<Register> createRegister(@RequestBody Register register) {
        return ResponseEntity.ok(registerServiceImpl.CreateRegister(register));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Register> deleteRegister(@PathVariable Long id) {
        registerServiceImpl.deleteRegister(id);
        return ResponseEntity.noContent().build();
    }

}
