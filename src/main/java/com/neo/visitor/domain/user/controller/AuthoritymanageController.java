package com.neo.visitor.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthoritymanageController {
	
	@RequestMapping(value = "authoritymanage", method = RequestMethod.GET)
	public String visitor(Model model) {
		// model and view , model

		return "authoritymanage/list";
	}

}
