package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

//    @Autowired
//    public RegisterServiceImpl(RegisterRepository registerRepository) {
//        this.registerRepository = registerRepository;
//    }

    private final RegisterRepository registerRepository;


    public Register CreateRegister(Register register) {
        return registerRepository.save(register);
    }

    public void deleteRegister(Long id){
        registerRepository.deleteById(id);
    }

//    public List<Register> getRegisterByJoinPeriod(JoinPeriod joinPeriod) {
//        return registerRepository.findById(joinPeriod);
//    }


}
