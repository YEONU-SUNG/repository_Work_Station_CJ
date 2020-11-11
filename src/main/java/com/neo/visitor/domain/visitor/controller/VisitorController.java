package com.neo.visitor.domain.visitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitorController {
	
	@GetMapping(value = "dashboard")
	public String dashboard(Model model) {
		// model and view , model
		return "visitor/dashboard";
    }
    
    @GetMapping(value = "visitor")
	public String visitor(Model model) {
		// model and view , model
		return "visitor/list";
    }
    
    @GetMapping(value = "visitor/view")
	public String visitorAdd(Model model) {
		// model and view , model
		return "visitor/insert";
    }

    @GetMapping(value="visitor/blacklist")
    public String blacklist(Model model) {
        return "visitor/blacklist";
    }

}
