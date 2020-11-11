package com.neo.visitor.domain.education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentsmanageController {

	@GetMapping(value = "contentsmanage")
	public String contents(Model model) {
		// model and view , model

		return "contents/list";
    }
    
    //@GetMapping(value = "contentsmanage/view/{type}")
	@GetMapping(value = "contentsmanage/view")
	public String contentsAdd() {
        // model and view , model
        //model.addAttribute("type", type);
		return "contents/view";
	}
}
