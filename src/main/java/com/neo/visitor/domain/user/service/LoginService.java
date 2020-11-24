package com.neo.visitor.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.repository.AdminUserRepository;
import com.neo.visitor.util.MailSenderUtil;

@Service
public class LoginService {

    @Autowired AdminUserRepository adminUserRepository;
    @Autowired MailSenderUtil mailSenderUtil;
    
	public boolean getUserInfo(HttpSession session, AdminUser adminUser) {
        AdminUser resultHost = adminUserRepository.getLoginUserInfo(adminUser);
        if(isNull(resultHost)) {
            session.setAttribute("login", resultHost);
            // if(resultHost.getHost().getAuth().equals("2") || resultHost.getHost().getAuth().equals("1")) {
                session.setMaxInactiveInterval(60*60*12);
            // }
        } else {
            return false;
        }
        
		return true;
    }

    public boolean getUserInfosso(HttpSession session, AdminUser adminUser) {
        AdminUser resultHost = adminUserRepository.getLoginUserInfosso(adminUser);
        if(isNull(resultHost)) {
            session.setAttribute("login", resultHost);
            if(resultHost.getHost().getAuth().equals("2") || resultHost.getHost().getAuth().equals("1")) {
                session.setMaxInactiveInterval(60*60*12);
            }
        } else {
            return false;
        }
        
		return true;
    }

    public AdminUser getUserSessionInfo(HttpServletRequest request) {
        return (AdminUser) request.getSession().getAttribute("login");
    }
    
    private boolean isNull(AdminUser adminUser) {
        if(adminUser==null) return false;
        return true; 
    }

    // private void isPasswordExpiration() {
    //     // 패스워드 만료 정책 검증
    // }
	
}


