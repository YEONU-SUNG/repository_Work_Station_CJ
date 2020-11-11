package com.neo.visitor.domain.user.application;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.service.AdminUserService;
import com.neo.visitor.domain.user.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserAuthApplication {

    @Autowired AdminUserService adminUserService;
    @Autowired LoginService loginService;

    public PagenationResponse<AdminUser> getAdminUserAuthList(HttpServletRequest request, Pagenation pagenation) {
        PagenationResponse<AdminUser> pagenationResponse = new PagenationResponse<>();
        Map<String, Object> map = new HashMap<>();
        map.put("admin", loginService.getUserSessionInfo(request));
        map.put("pagenation", pagenation);

        pagenationResponse.setResponse(adminUserService.findByNotAdminId(map));
        pagenationResponse.setPagenation(pagenation.makePagenation(adminUserService.count(map), PagenationType.AUTH));
        return pagenationResponse;
    }
}