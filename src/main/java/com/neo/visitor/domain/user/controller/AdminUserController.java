package com.neo.visitor.domain.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.visitor.login.api.doosan.DoosanLogin;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.service.HostService;
import com.neo.visitor.domain.user.service.LoginApiConnect;
import com.neo.visitor.domain.user.service.LoginService;
import com.neo.visitor.insa.repository.InsaRepository;

@Controller
public class AdminUserController {

    @Autowired
    LoginService loginService;
    @Autowired
    LoginApiConnect loginApiConnect;
    @Autowired
    HostService hostService;

    @Autowired DoosanLogin login;

    @Autowired InsaRepository insaRepository;

    @RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        // model and view , model
        session.removeAttribute("login");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginAction(HttpSession session, @RequestParam(name = "AdminID") String AdminID,
            @RequestParam(name = "AdminPW") String AdminPW, @RequestParam(name = "LoginType") String LoginType) {
        try {
            login.authorize(session, AdminID, AdminPW);
            //loginService.getUserInfo(session, new AdminUser().login(AdminID, AdminPW));
            return "Y";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N";
    }
	
	@RequestMapping(value = "logout")
	public String logout(HttpSession session) {
        session.removeAttribute("login");
		return "redirect:/login";
    }
    
    @GetMapping(path = "host-list")
    @ResponseBody
    public List<Host> getHostList(HttpServletRequest request
    , @RequestParam(defaultValue = "") String hostName) {
        if(hostName.length() < 2) throw new IllegalArgumentException("최소 2글자는 입력해주세요.");
        
        // return insaRepository.findByLikeName(hostName);
        
        return hostService.findByHostName(hostName);
    }
	
    @GetMapping(path = "hostpartner-list")
    @ResponseBody
    public List<Host> getHostWithPartnerList(HttpServletRequest request
    , @RequestParam(defaultValue = "") String hostName) {
        if(hostName.length() < 2) throw new IllegalArgumentException("최소 2글자는 입력해주세요.");
        
        return insaRepository.findByLikeNameWithPartner(hostName);
        
        // return hostService.findByHostName(hostName);
    }

}
