package com.neo.visitor.domain.buildingSiteMapping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuildingSiteMappingController {
    
    @GetMapping(value = "building/site/mapping/list")
	public String buildingSiteMappingList() {
		// model and view , model
		return "buildingSiteMapping/list";
    }
}