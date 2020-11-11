package com.neo.visitor.domain.personalinfo.controller;

import com.neo.visitor.domain.personalinfo.application.PersonalinfoApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonalinfoController {

    @Autowired PersonalinfoApplication personalinfoApplication;

	@GetMapping(path = "personalinfo")
	public String personalinfoList(Model model) {
		// model and view , model
		return "personalinfo/list";
    }

    @GetMapping(path = "personalinfo/view")
    public String personalinfoAdd(Model model) {
        model.addAttribute("PersonalinfoID", 0);
        return "personalinfo/insert";
    }

    @GetMapping(path = "personalinfo/view/{PersonalinfoID}")
    public String personalinfo(Model model, @PathVariable int PersonalinfoID) {
        model.addAttribute("personalinfo", personalinfoApplication.personalinfoDetail(PersonalinfoID));
        return "personalinfo/view";
    }

    @PostMapping(path = "personalinfo/view/{PersonalinfoID}")
    public String personalinfoUpdate(Model model, @PathVariable int PersonalinfoID) {
        model.addAttribute("PersonalinfoID", PersonalinfoID);
        return "personalinfo/insert";
    }
}
