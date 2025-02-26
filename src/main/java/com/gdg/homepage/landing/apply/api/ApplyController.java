package com.gdg.homepage.landing.apply.api;


import com.gdg.homepage.landing.apply.domain.Apply;
import com.gdg.homepage.landing.apply.service.ApplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apply")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyServiceImpl registerServiceImpl;

//    @PostMapping("/craate")
//    public ResponseEntity<Register> createRegister(@RequestBody ApplyRequest applyRequest) {
//        try {
//            ApplyResponse applyresponseDto = applyservice.createApply()
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apply> deleteRegister(@PathVariable Long id) {
        registerServiceImpl.deleteRegister(id);
        return ResponseEntity.noContent().build();
    }

}
