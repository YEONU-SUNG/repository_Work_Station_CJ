package com.neo.visitor.domain.notice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.neo.visitor.domain.notice.application.NoticeApplication;
import com.neo.visitor.domain.notice.entity.Notice;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.notice.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class NoticeApiController {

    @Autowired NoticeService noticeService;
    @Autowired NoticeApplication noticeApplication;
    
    @PostMapping(path = "notice-list/{NoticeID}")
    public Notice updateNoticeActiveFlag(@PathVariable int NoticeID) {
        return noticeService.updateNoticeActiveFlag(NoticeID);
    }
    
    @GetMapping(path = "notice-list")
    public PagenationResponse<Notice> getNotices(@RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            case "작성자" : conditionKey = "AdminID"; break;
            case "제목" : conditionKey = "Title"; break;
            case "상태" : conditionKey = "ActiveFlag"; break;
        }
		return noticeService.findAll(new Pagenation(page, size, conditionKey, conditionValue));
    }
    
    @PostMapping(value = "notice-list")
    public void updateNoticesDeleteFlag(@RequestParam(value = "NoticeID[]") int[] NoticeIDs) {
        noticeService.updateNoticesDeleteFlag(NoticeIDs);
    }

    @GetMapping(path = "notice-view/{NoticeID}")
    public Notice getNotice(@PathVariable int NoticeID) {
        return noticeApplication.noticeDetail(NoticeID);
    }

    @PostMapping(path = "notice-add")
    public Notice setNotice(HttpServletRequest request, @RequestParam String title, @RequestParam String contents) {
        return noticeApplication.noticeIntert(new Notice().makeNotice(title, contents), request);
    }
}