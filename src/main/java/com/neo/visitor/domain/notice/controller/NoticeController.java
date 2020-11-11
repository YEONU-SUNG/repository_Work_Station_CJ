package com.neo.visitor.domain.notice.controller;

import com.neo.visitor.domain.notice.application.NoticeApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoticeController {

    @Autowired NoticeApplication noticeApplication;

	@GetMapping(path = "notice")
	public String noticeList(Model model) {
		// model and view , model
		return "notice/list";
    }

    @GetMapping(path = "notice/view")
    public String noticeAdd(Model model) {
        model.addAttribute("NoticeID", 0);
        return "notice/insert";
    }

    @GetMapping(path = "notice/view/{NoticeID}")
    public String notice(Model model, @PathVariable int NoticeID) {
        model.addAttribute("notice", noticeApplication.noticeDetail(NoticeID));
        return "notice/view";
    }

    @PostMapping(path = "notice/view/{NoticeID}")
    public String noticeUpdate(Model model, @PathVariable int NoticeID) {
        model.addAttribute("NoticeID", NoticeID);
        return "notice/insert";
    }
}
