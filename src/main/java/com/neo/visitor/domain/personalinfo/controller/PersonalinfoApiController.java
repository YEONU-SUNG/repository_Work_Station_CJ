package com.neo.visitor.domain.personalinfo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.neo.visitor.domain.personalinfo.application.PersonalinfoApplication;
import com.neo.visitor.domain.personalinfo.entity.Personalinfo;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.personalinfo.service.PersonalinfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class PersonalinfoApiController {

    @Autowired PersonalinfoService personalinfoService;
    @Autowired PersonalinfoApplication personalinfoApplication;
    
    @PostMapping(path = "personalinfo-list/{PersonalinfoID}")
    public Personalinfo updatePersonalinfoActiveFlag(@PathVariable int PersonalinfoID) {
        return personalinfoService.updatePersonalinfoActiveFlag(PersonalinfoID);
    }
    
    @GetMapping(path = "personalinfo-list")
    public PagenationResponse<Personalinfo> getPersonalinfos(@RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            case "작성자" : conditionKey = "AdminID"; break;
            case "제목" : conditionKey = "Title"; break;
            case "상태" : conditionKey = "ActiveFlag"; break;
        }
		return personalinfoService.findAll(new Pagenation(page, size, conditionKey, conditionValue));
    }
    
    @PostMapping(value = "personalinfo-list")
    public void updatePersonalinfosDeleteFlag(@RequestParam(value = "PersonalinfoID[]") int[] PersonalinfoIDs) {
        personalinfoService.updatePersonalinfosDeleteFlag(PersonalinfoIDs);
    }

    @GetMapping(path = "personalinfo-view/{PersonalinfoID}")
    public Personalinfo getPersonalinfo(@PathVariable int PersonalinfoID) {
        return personalinfoApplication.personalinfoDetail(PersonalinfoID);
    }

    @PostMapping(path = "personalinfo-add")
    public Personalinfo setPersonalinfo(HttpServletRequest request, @RequestParam String title, @RequestParam String contents) {
        return personalinfoApplication.personalinfoIntert(new Personalinfo().makePersonalinfo(title, contents), request);
    }
}