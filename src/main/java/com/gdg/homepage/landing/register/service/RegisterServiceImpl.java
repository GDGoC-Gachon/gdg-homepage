package com.gdg.homepage.landing.register.service;

import com.gdg.homepage.landing.admin.domain.JoinPeriod;
import com.gdg.homepage.landing.register.domain.Register;
import com.gdg.homepage.landing.register.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegisterServiceImpl implements RegisterService {

//    @Autowired
//    public RegisterServiceImpl(RegisterRepository registerRepository) {
//        this.registerRepository = registerRepository;
//    }

    private final RegisterRepository registerRepository;


    public Register CreateRegister() {
        return registerRepository.save(new Register());
    }

    public void deleteRegister(Register register) {
        registerRepository.delete(register);
    }

//    public List<Register> getRegisterByJoinPeriod(JoinPeriod joinPeriod) {
//        return registerRepository.findById(joinPeriod);
//    }


}
