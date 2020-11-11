package com.neo.visitor.domain.user.controller;

import java.io.IOException;
import java.util.List;

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

import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.service.HostService;
import com.neo.visitor.domain.user.service.LoginApiConnect;
import com.neo.visitor.domain.user.service.LoginService;

@Controller
public class AdminUserController {

    @Autowired
    LoginService loginService;
    @Autowired
    LoginApiConnect loginApiConnect;
    @Autowired
    HostService hostService;

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
            //System.out.println(loginApiConnect.httpURLConnection(AdminID, AdminPW));
            // if(loginApiConnect.httpURLConnection(AdminID, AdminPW).equals("(false)")) {
            //     if(loginService.getUserInfo(session, new AdminUser().login(AdminID, AdminPW))) {
            //         return "Y";
            //     } else {
            //         return "N";
            //     }
            // } else {
            //     return "Y";
            // }
            if(LoginType.equals("L"))
            {
                if(loginService.getUserInfo(session, new AdminUser().login(AdminID, AdminPW))) {
                    return "Y";
                } else {
                    return "N";
                }
            }
            else if(LoginType.equals("S"))
            {
                if(loginService.getUserInfosso(session, new AdminUser().login(AdminID,""))) {
                    return "Y";
                } else {
                    return "N";
                }
            }
            
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
        return hostService.findByHostName(hostName);
    }
}
