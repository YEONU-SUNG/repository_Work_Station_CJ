package com.neo.visitor.domain.visitor.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.visitor.application.VisitorApplication;
import com.neo.visitor.domain.visitor.entity.VisitorCompany;
import com.neo.visitor.domain.visitor.entity.VisitorDashboard;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.WorkSite;
import com.neo.visitor.domain.visitor.application.VisitorHistoryApplication;
import com.neo.visitor.domain.visitor.service.VisitorDashboardService;
import com.neo.visitor.domain.visitor.service.VisitorInoutTimeService;
import com.neo.visitor.domain.visitor.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorApiController {

    @Autowired VisitorService visitorService;
    @Autowired VisitorInoutTimeService visitorInoutTimeService;
    @Autowired VisitorHistoryApplication visitorHistoryApplication;
    @Autowired VisitorApplication visitorApplication;
    @Autowired VisitorDashboardService visitorDashboardService;

    @GetMapping(path = "visitor/dashboard")
	public List<VisitorDashboard> getDashboard() {
        return visitorDashboardService.findAll();
    }
	
	@GetMapping(path = "visitor/approve-list")
	public PagenationResponse<VisitorHistory> getApproveList(HttpServletRequest request
    , @RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            //case "예약번호" : conditionKey = "VisitorHistorySeq"; break;
            case "예약번호" : conditionKey = "VisitorReservationNumber"; break;
            case "성명" : conditionKey = "VisitorName"; break;
            case "연락처" : conditionKey = "VisitorMobile"; break;
            case "회사" : conditionKey = "VisitorCompany"; break;
            case "차량번호" : conditionKey = "CarNo"; break;
            case "임직원" : conditionKey = "HostName"; break;
            case "방문목적" : conditionKey = "VisitPurpose"; break;
            case "방문증번호" : conditionKey = "CardID"; break;
        }
        return visitorApplication.dashboard(request, new Pagenation(page, size, conditionKey, conditionValue), LocalDateTime.now());
    }

    @GetMapping(path = "visitor/confirm-list")
	public PagenationResponse<VisitorHistory> getConfirmList(HttpServletRequest request
    , @RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue
    , @RequestParam(defaultValue = "") String searchFromDateTime
    , @RequestParam(defaultValue = "") String searchToDateTime ){
        switch (conditionKey) {
            case "방문자" : conditionKey = "VisitorName"; break;
            case "연락처" : conditionKey = "VisitorMobile"; break;
            case "업체명" : conditionKey = "VisitorCompany"; break;
        }
        return visitorApplication.confirm(request, new Pagenation(page, size, conditionKey, conditionValue), searchFromDateTime, searchToDateTime);
    }

    @PostMapping(path = "visitor/card-no")
	public void updateVisitorCardNo(@RequestParam(name = "visitorHistorySeq[]") int[] visitorHistorySeq
        , @RequestParam(name = "cardID[]") String[] cardID) {
		visitorService.updateCardNo(visitorHistorySeq, cardID);
    }
    
    @PostMapping(path = "visitor-inout/{visitorHistorySeq}")
	public VisitorHistory updateVisitorInOut(@PathVariable int visitorHistorySeq) {
		return visitorInoutTimeService.updateVisitorInout(visitorHistorySeq,"");
    }
    @PostMapping(path = "visitor-inout/{visitorHistorySeq}/{cardID}")
	public VisitorHistory updateVisitorInOutCard(@PathVariable int visitorHistorySeq, @PathVariable String cardID) {
        //visitorService.updateCardNoOne(visitorHistorySeq, cardID);
		return visitorInoutTimeService.updateVisitorInout(visitorHistorySeq, cardID);
    }
    @PostMapping(path = "visitor-cardid/{visitorHistorySeq}/{cardID}")
	public VisitorHistory updateVisitorCardID(@PathVariable int visitorHistorySeq, @PathVariable String cardID) {
        //visitorService.updateCardNoOne(visitorHistorySeq, cardID);
		return visitorInoutTimeService.updateVisitorCardID(visitorHistorySeq, cardID);
    }

    @PostMapping(path = "visitor-approval/{visitorHistorySeq}")
    public VisitorHistory updateVisitApproval(HttpServletRequest request,@PathVariable int visitorHistorySeq
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff) {
        return visitorService.updateVisitorApproval(request, visitorHistorySeq, visitApprovalComment, carryStuff);
    }

    @PostMapping(path = "visitor-approval/all")
    public void updateVisitApprovalall(HttpServletRequest request
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff
        , @RequestParam(name = "visitorHistorySeq[]") int[] visitorHistorySeq) {
            visitorService.updateVisitorApprovalAll(request, visitorHistorySeq, visitApprovalComment, carryStuff);
        //return visitorService.updateVisitorApprovalAll(request, visitorHistorySeq, visitApprovalComment, carryStuff);
    }

    // 원본
    /* @PostMapping(path = "visitor-reject/{visitorHistorySeq}")
    public VisitorHistory updateVisitApprovalReject(HttpServletRequest request,@PathVariable int visitorHistorySeq
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff) {
        return visitorService.updateVisitorApprovalReject(request, visitorHistorySeq, visitApprovalComment, carryStuff);
    }

    @PostMapping(path = "visitor-reject/all")
    public void updateVisitApprovalallReject(HttpServletRequest request
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff
        , @RequestParam(name = "visitorHistorySeq[]") int[] visitorHistorySeq) {
            visitorService.updateVisitorApprovalAllReject(request, visitorHistorySeq, visitApprovalComment, carryStuff);
        //return visitorService.updateVisitorApprovalAll(request, visitorHistorySeq, visitApprovalComment, carryStuff);
    } */
    // 수정본
    @PostMapping(path = "visitor-reject/{visitorHistorySeq}")
    public VisitorHistory updateVisitApprovalReject(HttpServletRequest request
        , @PathVariable int visitorHistorySeq
        , @RequestParam(defaultValue = "") String visitRejectComment
        , @RequestParam(defaultValue = "") String visitRejectType) {
        return visitorService.updateVisitorApprovalReject(request, visitorHistorySeq, visitRejectComment);
    }

    @PostMapping(path = "visitor-reject/all")
    public void updateVisitApprovalallReject(HttpServletRequest request
        , @RequestParam(defaultValue = "") String visitRejectComment
        , @RequestParam(defaultValue = "") String visitRejectType
        , @RequestParam(name = "visitorHistorySeq[]") int[] visitorHistorySeq) {
            visitorService.updateVisitorApprovalAllReject(request, visitorHistorySeq, visitRejectComment, visitRejectType);
        //return visitorService.updateVisitorApprovalAll(request, visitorHistorySeq, visitApprovalComment, carryStuff);
    }
    
    @GetMapping(path = "visitor/worksite")
	public List<WorkSite> getWorkSite() {
        return visitorService.getWorkSite();
    }

    @GetMapping(path = "visitor/company")
	public List<VisitorCompany> getVisitorCompany() {
        return visitorService.getVisitorCompany();
    }

}
