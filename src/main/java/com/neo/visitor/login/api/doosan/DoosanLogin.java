package com.neo.visitor.login.api.doosan;

import java.io.IOException;
import java.util.Map;

import com.neo.visitor.login.LoginAuthApplication;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.insa.repository.InsaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoosanLogin extends LoginAuthApplication {

    @Autowired
    private InsaRepository insaRepository;
    
    @Override
    public String checkAuthorityAD(String id, String password) {
        try {
            DoosanADResponse doosanADResponse = DoosanLoginApiConnect.postRequest(id, password);
            if(doosanADResponse==null)
                throw new IllegalArgumentException("로그인정보가 존재하지않습니다.");
            
            return doosanADResponse.getUser().getEmail();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("관리자에게 문의해주세요.");
        }
    }

    @Override
    public Host isEmptyOrganizationChart(String id) {
        Map<String, Object> response = insaRepository.findByEmail("singhal.anju@doosan.com");
        if(response != null) throw new IllegalArgumentException("로그인정보가 존재하지않습니다.");
        return new Host().insaInterface(response);
    }
    
}