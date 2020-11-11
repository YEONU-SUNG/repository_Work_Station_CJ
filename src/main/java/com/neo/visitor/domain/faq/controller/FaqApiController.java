package com.neo.visitor.domain.faq.controller;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.faq.application.FaqApplication;
import com.neo.visitor.domain.faq.domain.Faq;
import com.neo.visitor.domain.faq.service.FaqService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class FaqApiController {

    @Autowired FaqService faqService;
    @Autowired FaqApplication faqApplication;
    
    @PostMapping(path = "faq-list/{FaqID}")
    public Faq updateFaqActiveFlag(@PathVariable int FaqID) {
        return faqService.updateActiveFlag(FaqID);
    }
    
    @GetMapping(path = "faq-list")
    public PagenationResponse<Faq> getFaqs(@RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            case "작성자" : conditionKey = "AdminID"; break;
            case "질문" : conditionKey = "Question"; break;
            case "상태" : conditionKey = "ActiveFlag"; break;
        }
		return faqService.findAll(new Pagenation(page, size, conditionKey, conditionValue));
    }
    
    @PostMapping(value = "faq-list")
    public void updateDeleteFlag(@RequestParam(value = "FaqID[]") int[] faqIDs) {
        faqService.updateDeleteFlag(faqIDs);
    }

    @GetMapping(path = "faq-view/{FaqID}")
    public Faq getFaq(@PathVariable int FaqID) {
        return faqApplication.faqDetail(FaqID);
    }

    @PostMapping(path = "faq-add")
    public Faq setFaq(HttpServletRequest request, @RequestParam String question, @RequestParam String answer) {
        return faqApplication.faqIntert(new Faq().makeFaq(question, answer), request);
    }
}