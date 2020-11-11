package com.neo.visitor.domain.education.controller;

import com.neo.visitor.domain.education.service.ContentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EdumanagelistController {

    @Autowired ContentsService contentsService;
	
	@GetMapping(path = "edumanagelist")
	public String edumanagelist(Model model) {
        // model and view , model
		return "education/list";
    }
    
    @GetMapping(path = "edumanage/view/{type}")
	public String edumanagesafty(@PathVariable String type, Model model) {
        // model and view , model
		return "education/view";
	}

}
