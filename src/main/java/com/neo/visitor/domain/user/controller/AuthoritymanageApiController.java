package com.neo.visitor.domain.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.user.application.AdminUserAuthApplication;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.service.HostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthoritymanageApiController {

    @Autowired AdminUserAuthApplication adminUserAuthApplication;
    @Autowired HostService hostService;
	
	@GetMapping(path = "auth-list")
    public PagenationResponse<Host> getUserList(HttpSession session
    , @RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            case "임직원명" : conditionKey = "HostName"; break;
            case "아이디" : conditionKey = "AdminID"; break;
            case "부서" : conditionKey = "DeptCD"; break;
        }
        
        // return adminUserAuthApplication.getAdminUserAuthList(request, new Pagenation(page, size, conditionKey, conditionValue));
        return hostService.findAll(session, new Pagenation(page, size, conditionKey, conditionValue));
    }
    
    @PostMapping(path = "auth-list/{hostID}")
    public Host updateUserAuth(@PathVariable String hostID, @RequestParam(name = "auth") String auth) {
        return hostService.updateHostAuth(hostID, auth);
    }

    @PostMapping(path = "active-list/{hostID}")
    public Host updateFaqActiveFlag(@PathVariable String hostID) {
        return hostService.updateActiveFlag(hostID);
    }
    
}
