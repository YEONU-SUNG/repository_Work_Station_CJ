package com.neo.visitor.domain.visitor.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.visitor.application.VisitorApplication;
import com.neo.visitor.domain.visitor.application.VisitorHistoryApplication;
import com.neo.visitor.domain.visitor.entity.Visiter;
import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.VisitorInoutTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitorHistoryApiController {

    @Autowired VisitorApplication visitorApplication;
    @Autowired VisitorHistoryApplication visitorHistoryApplication;

    @GetMapping(value = "visitor/history-list")
	public PagenationResponse<VisitorInoutTime> getVisitorHistory(HttpServletRequest request, @RequestParam int page, @RequestParam int size
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
        return visitorApplication.history(request, new Pagenation(page, size, conditionKey, conditionValue), visitorFromDateTime, visitorToDateTime);
    }

    @PostMapping(path = "visitor")
    public void setVisitor(HttpServletRequest req) {
        // String hostName = req.getParameter("hostName");
        // String hostDept = req.getParameter("hostDept");
        String hostId = req.getParameter("hostId");
        String planFromDateTime = req.getParameter("planFromDate");
        String planToDateTime = req.getParameter("planToDate");
        String visitPurpose = req.getParameter("visitPurpose");
        String visitPurposeDetail = req.getParameter("visitPurposeDetail");
        String visitorPosition1 = req.getParameter("visitPosition1");
        String visitorPosition2 = req.getParameter("visitPosition2");
        String visitorPosition3 = req.getParameter("visitPosition3");
        String visitorCar = req.getParameter("visitorCar");

        VisitorHistory visitorHistory = new VisitorHistory();
        visitorHistory.makeVisitorHistory(hostId, planFromDateTime, planToDateTime, visitPurpose, visitPurposeDetail, visitorPosition1, visitorPosition2, visitorPosition3, visitorCar);

        String[] visitorIds = req.getParameterValues("visitorId");
        String[] visitorNames = req.getParameterValues("visitorName");
        String[] visitorGenders = req.getParameterValues("visitorGender");
        String[] visitorLocations = req.getParameterValues("visitorLocation");
        String[] visitorBirths = req.getParameterValues("visitorBirth");
        String[] visitorPhones = req.getParameterValues("visitorPhone");
        String[] visitorCompanys = req.getParameterValues("visitorCompany");
        String[] visitorWares = req.getParameterValues("visitorCompany");
        String[] visitorSerials = req.getParameterValues("visitorSerial");
        String[] visitorPurpose = req.getParameterValues("visitorPurpose");
        String[] visitorUseds = req.getParameterValues("visitorUsed");

        List<Visiter> visiters = new ArrayList<>();
        for(int i=0; i<visitorIds.length; i++) {
            Visiter visiter = 
                (!visitorIds[i].equals("") && !visitorIds[i].equals("undefined"))
                // 임직원인경우
                ? new Host().makeHost(visitorIds[i])
                // 외부인인경우
                : new Visitor().makeVisitor(visitorNames[i], visitorGenders[i], visitorLocations[i], visitorBirths[i], visitorPhones[i], visitorCompanys[i]);

            // 반입물품
            visiter.setCarryInWareByVisiter(visitorWares[i], visitorSerials[i], visitorPurpose[i], visitorUseds[i]);
            visiters.add(visiter);
        }

        visitorHistoryApplication.saveHistoryVisit(visitorHistory, visiters);
        
    }
}