package com.neo.visitor.domain.visitor.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.buildingSiteMapping.entity.Building;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.service.LoginService;
import com.neo.visitor.domain.visitor.application.VisitorApplication;
import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;
import com.neo.visitor.domain.visitor.entity.VisitorCompany;
import com.neo.visitor.domain.visitor.entity.VisitorDashboard;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.WorkSite;
import com.neo.visitor.domain.visitor.application.VisitorHistoryApplication;
import com.neo.visitor.domain.visitor.service.VisitorBlackListService;
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
    @Autowired VisitorBlackListService visitorBlackListService;

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
    , @RequestParam(defaultValue = "") String visitorFromDateTime
    , @RequestParam(defaultValue = "") String visitorToDateTime ){
        switch (conditionKey) {
            case "방문자" : conditionKey = "VisitorName"; break;
            case "연락처" : conditionKey = "VisitorMobile"; break;
            case "업체명" : conditionKey = "VisitorCompany"; break;
        }
        return visitorApplication.confirm(request, new Pagenation(page, size, conditionKey, conditionValue), visitorFromDateTime, visitorToDateTime);
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
        //visitorService.updateCardNoOne(visitorHupdateVisitorInoutistorySeq, cardID);
		return visitorInoutTimeService.updateVisitorInout(visitorHistorySeq, cardID);
    }
    @PostMapping(path = "visitor-cardid/{visitorHistorySeq}/{cardID}")
	public VisitorHistory updateVisitorCardID(@PathVariable int visitorHistorySeq, @PathVariable String cardID) {
        //visitorService.updateCardNoOne(visitorHistorySeq, cardID);
		return visitorInoutTimeService.updateVisitorCardID(visitorHistorySeq, cardID);
    }

    @GetMapping(path = "visitor-approval/site")
    public List<Building> getSiteVisitApproval(HttpSession session) {
        AdminUser adminUser = (AdminUser) session.getAttribute("login");
        return adminUser.getHost().getMappingBuildings();
    }

    // 외부인용
    @PostMapping(path = "visitor-approval/{visitorHistorySeq}")
    public VisitorHistory updateVisitApproval(HttpServletRequest request
        , @PathVariable int visitorHistorySeq
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff) {
        return visitorService.updateVisitorApproval(request, visitorHistorySeq, visitApprovalComment, carryStuff, null, null);
    }

    // 건물접근권한 부여로 버전업, 임직원용
    @PostMapping(path = "visitor-approval/staffe/{visitorHistorySeq}")
    public VisitorHistory updateVisitApproval(HttpServletRequest request
        , @PathVariable int visitorHistorySeq
        , @RequestParam String buildingName
        , @RequestParam("buildingFloor") String[] buildingFloor
        , @RequestParam(defaultValue = "") String visitApprovalComment
        , @RequestParam(defaultValue = "") String carryStuff) {
        return visitorService.updateVisitorApproval(request, visitorHistorySeq, visitApprovalComment, carryStuff, buildingName, buildingFloor);
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
        return visitorService.updateVisitorApprovalReject(request, visitorHistorySeq, visitRejectComment, visitRejectType);
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

    @PostMapping(path = "visitor/blacklist")
    public void addBlackList(HttpSession session
        , @RequestParam(defaultValue = "") String visitorId
        , @RequestParam String visitorName
        , @RequestParam String visitorCompany
        , @RequestParam String visitorPhone
        , @RequestParam String blacklistState
        , @RequestParam String blacklistReason
        , @RequestParam String blacklistReasonComment
        , @RequestParam String planFromDate
        , @RequestParam String planToDate
    ) {
        if(blacklistReason.equals("기타") && blacklistReasonComment.trim().length()==0) throw new IllegalArgumentException("기타 선택 시 상세내용입력은 필수입니다.");
        AdminUser adminUser = (AdminUser) session.getAttribute("login");
        Visitor visitor = new Visitor().makeVisitor(visitorName, "", "", "", visitorPhone, visitorCompany);
        if(visitorId.equals("")) {
            VisitorBlackList visitorBlackList
                = new VisitorBlackList().makeBlackList(adminUser.getAdminID(), blacklistState, blacklistReason, blacklistReasonComment, planFromDate, planToDate);
            visitorBlackListService.addBlacklist(visitor, visitorBlackList);
        } else {
            visitorBlackListService.updateBlackList(
                new VisitorBlackList()
                .updateBlackList(Integer.parseInt(visitorId), adminUser.getAdminID(), blacklistState, blacklistReason, blacklistReasonComment, planFromDate, planToDate));        
        }
    }

    @GetMapping(value = "visitor/black-list")
	public PagenationResponse<VisitorBlackList> getBlackList(HttpServletRequest request, @RequestParam int page, @RequestParam int size
        , @RequestParam(defaultValue = "") String visitorFromDateTime
        , @RequestParam(defaultValue = "") String visitorToDateTime
        , @RequestParam(defaultValue = "") String conditionKey
        , @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            // case "예약번호" : conditionKey = "VisitorHistorySeq"; break;
            case "예약번호" : conditionKey = "VisitorReservationNumber"; break;
            case "이름" : conditionKey = "VisitorName"; break;
            case "회사명" : conditionKey = "VisitorCompany"; break;
            case "방문목적" : conditionKey = "VisitPurpose"; break;
            case "방문증번호" : conditionKey = "CardID"; break;
            //default : conditionKey = ""; break;
        }
        return visitorApplication.blacklist(request, new Pagenation(page, size, conditionKey, conditionValue), visitorFromDateTime, visitorToDateTime);
    }
}
