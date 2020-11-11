package com.neo.visitor.config.intercepter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class UserInfoIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 요청이 controller 로 들어오기전에 세션 체크
        HttpSession session = request.getSession();
        if(session.getAttribute("login")==null) {
            if(request.getHeader("custom")!=null && request.getHeader("custom").equals("1")) {
                //response.sendRedirect("/login");
                throw new IllegalArgumentException("재 로그인 후 다시 이용해주세요.");
            } else {
                response.sendRedirect("/admin/login");
            }
            return false;
        }
   		return true;
	}
}