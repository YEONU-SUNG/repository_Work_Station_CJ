package com.neo.visitor.domain.faq.application;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.faq.domain.Faq;
import com.neo.visitor.domain.faq.service.FaqService;
import com.neo.visitor.domain.user.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqApplication {

    @Autowired LoginService loginService;
    @Autowired FaqService faqService;

    public Faq faqIntert(Faq faq, HttpServletRequest request) {
        faq.setAdminID(loginService.getUserSessionInfo(request).getAdminID());
        try {
            faq.setFaqID(Integer.parseInt(request.getParameter("faqId")));
            faqService.update(faq);
        } catch (NullPointerException | NumberFormatException e) {
            faqService.save(faq);
        }
        return faq;
    }
 
    public Faq faqDetail(int FaqID) {
        Faq faq = faqService.findById(FaqID);
        return faq;
    }
}