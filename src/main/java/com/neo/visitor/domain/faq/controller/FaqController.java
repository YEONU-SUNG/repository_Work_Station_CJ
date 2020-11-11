package com.neo.visitor.domain.faq.controller;

import com.neo.visitor.domain.faq.application.FaqApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FaqController {

    @Autowired FaqApplication faqApplication;

	@GetMapping(path = "faq")
	public String faqList(Model model) {
		// model and view , model
		return "faq/list";
    }

    @GetMapping(path = "faq/view")
    public String faqAdd(Model model) {
        model.addAttribute("FaqID", 0);
        return "faq/insert";
    }

    @GetMapping(path = "faq/view/{FaqID}")
    public String faq(Model model, @PathVariable int FaqID) {
        model.addAttribute("faq", faqApplication.faqDetail(FaqID));
        return "faq/view";
    }

    @PostMapping(path = "faq/view/{FaqID}")
    public String faqUpdate(Model model, @PathVariable int FaqID) {
        model.addAttribute("FaqID", FaqID);
        return "faq/insert";
    }
}
