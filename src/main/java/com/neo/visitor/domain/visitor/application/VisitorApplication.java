package com.neo.visitor.domain.visitor.application;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.config.AES256Util;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.service.LoginService;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.VisitorInoutTime;
import com.neo.visitor.domain.visitor.service.VisitorBlackListService;
import com.neo.visitor.domain.visitor.service.VisitorInoutTimeService;
import com.neo.visitor.domain.visitor.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorApplication {

    @Autowired
    LoginService loginService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    VisitorInoutTimeService visitorInoutTimeService;

    @Autowired
    VisitorBlackListService visitorBlackListservice;

    public PagenationResponse<VisitorInoutTime> history(HttpServletRequest request, Pagenation pagenation,
            String visitorFromDateTime, String visitorToDateTime) {
        PagenationResponse<VisitorInoutTime> pagenationResponse = new PagenationResponse<>();
        pagenation.PagenationExpansionDate(
                visitorFromDateTime.equals("") ? LocalDate.now().toString() : visitorFromDateTime,
                visitorToDateTime.equals("") ? LocalDate.now().toString() : visitorToDateTime);

        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        map.put("host", loginService.getUserSessionInfo(request));
        // pagenationResponse.setResponse(visitorInoutTimeService.findAllHistory(pagenation));
        List<VisitorInoutTime> findAllHistoryList = visitorInoutTimeService.findAllHistory(map);
        for (int i = 0, len = findAllHistoryList.size(); i < len; i++) {
            String visitorName = findAllHistoryList.get(i).getVisitorHistory().getVisitorName().toString();
            String visitorMobile = findAllHistoryList.get(i).getVisitorHistory().getVisitorMobile();
            try {
                findAllHistoryList.get(i).getVisitorHistory().setVisitorName(AES256Util.decrypt(visitorName));
                findAllHistoryList.get(i).getVisitorHistory().setVisitorMobile(AES256Util.decrypt(visitorMobile));
            } catch (Exception e) {
                continue;
            }
        }

        pagenationResponse.setResponse(findAllHistoryList);
        // pagenationResponse.setPagenation(pagenation.makePagenation(visitorInoutTimeService.findAllHistoryCount(pagenation),
        // PagenationType.VISITOR_HISTORY));
        pagenationResponse.setPagenation(pagenation.makePagenation(visitorInoutTimeService.findAllHistoryCount(map),
                PagenationType.VISITOR_HISTORY));
        return pagenationResponse;
    }

    public PagenationResponse<VisitorBlackList> blacklist(HttpServletRequest request, Pagenation pagenation,
            String visitorFromDateTime, String visitorToDateTime) {
        PagenationResponse<VisitorBlackList> pagenationResponse = new PagenationResponse<>();
        pagenation.PagenationExpansionDate(
                visitorFromDateTime.equals("") ? LocalDate.now().toString() : visitorFromDateTime,
                visitorToDateTime.equals("") ? LocalDate.now().toString() : visitorToDateTime);

        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        map.put("host", loginService.getUserSessionInfo(request));
        // List<VisitorBlackList> visitorBlackListfindAll =
        // visitorBlackListservice.findAll();
        List<VisitorBlackList> visitorBlackListfindAll = visitorBlackListservice.findSearch(map);

        for (int i = 0, len = visitorBlackListfindAll.size(); i < len; i++) {
            String visitorName = visitorBlackListfindAll.get(i).getVisitor().getVisitorName().toString();
            String visitorMobile = visitorBlackListfindAll.get(i).getVisitor().getMobile();
            try {
                visitorBlackListfindAll.get(i).getVisitor().setVisitorName(AES256Util.decrypt(visitorName));
                visitorBlackListfindAll.get(i).getVisitor().setMobile(AES256Util.decrypt(visitorMobile));
            } catch (Exception e) {
                continue;
            }
        }

        pagenationResponse.setResponse(visitorBlackListfindAll);
        pagenationResponse.setPagenation(
                pagenation.makePagenation(visitorBlackListfindAll.size(), PagenationType.VISITOR_BLACKLIST));
        return pagenationResponse;
    }

    public PagenationResponse<VisitorHistory> dashboard(HttpServletRequest request, Pagenation pagenation,
            String searchFromDateTime, String searchToDateTime) {

        AdminUser adminUser = loginService.getUserSessionInfo(request);
        if (adminUser == null)
            throw new IllegalArgumentException("세션연결이 해제되었습니다. 다시 로그인해주세요.");

        pagenation.PagenationExpansionDate(
                searchFromDateTime.equals("") ? LocalDate.now().toString() : searchFromDateTime,
                searchToDateTime.equals("") ? LocalDate.now().toString() : searchToDateTime);

        PagenationResponse<VisitorHistory> pagenationResponse = new PagenationResponse<>();
        Map<String, Object> map = new HashMap<>();
        map.put("host", adminUser);
        map.put("pagenation", pagenation);
        map.put("searchFromDateTime", pagenation.getVisitFromDateTime());
        map.put("searchToDateTime", pagenation.getVisitToDateTime());

        // pagenationResponse.setResponse(visitorService.findByPlanDateTime(map));
        // pagenationResponse.setPagenation(pagenation.makePagenation(visitorService.countByDeleteFlag(map),
        // PagenationType.VISITOR_APPROVE));
        List<VisitorHistory> findByPlanDateTimeList = visitorService.findByPlanDateTime(map);
        for (int i = 0, len = findByPlanDateTimeList.size(); i < len; i++) {
            String visitorName = findByPlanDateTimeList.get(i).getVisitorName().toString();
            String visitorMobile = findByPlanDateTimeList.get(i).getVisitorMobile();

            try {
                findByPlanDateTimeList.get(i).setVisitorName(AES256Util.decrypt(visitorName));
                findByPlanDateTimeList.get(i).setVisitorMobile(AES256Util.decrypt(visitorMobile));
            } catch (Exception e) {
                continue;
            }
        }
        pagenationResponse.setResponse(findByPlanDateTimeList);
        pagenationResponse.setPagenation(
                pagenation.makePagenation(visitorService.countByDeleteFlag(map), PagenationType.VISITOR_APPROVE));
        // pagenationResponse.setPagenation(pagenation.makePagenation(findByPlanDateTimeList.size(),
        // PagenationType.VISITOR_APPROVE));
        return pagenationResponse;
    }

    public PagenationResponse<VisitorHistory> confirm(HttpServletRequest request, Pagenation pagenation,
            String searchFromDateTime, String searchToDateTime) {
        PagenationResponse<VisitorHistory> pagenationResponse = new PagenationResponse<>();
        pagenation.PagenationExpansionDate(
                searchFromDateTime.equals("") ? LocalDate.now().toString() : searchFromDateTime,
                searchToDateTime.equals("") ? LocalDate.now().toString() : searchToDateTime);
        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        map.put("searchFromDateTime", pagenation.getVisitFromDateTime());
        map.put("searchToDateTime", pagenation.getVisitToDateTime());
        map.put("host", loginService.getUserSessionInfo(request));

        List<VisitorHistory> findByConfirmList = visitorService.findByConfirm(map);
        for (int i = 0, len = findByConfirmList.size(); i < len; i++) {
            String visitorName = findByConfirmList.get(i).getVisitorName().toString();
            String visitorMobile = findByConfirmList.get(i).getVisitorMobile();

            try {
                findByConfirmList.get(i).setVisitorName(AES256Util.decrypt(visitorName));
                findByConfirmList.get(i).setVisitorMobile(AES256Util.decrypt(visitorMobile));
            } catch (Exception e) {
                continue;
            }
        }

        pagenationResponse.setResponse(findByConfirmList);
        pagenationResponse.setPagenation(
                pagenation.makePagenation(visitorService.countByConfirm(map), PagenationType.VISITOR_CONFIRM));
        return pagenationResponse;
    }

    public String[][] approveExcel(String[] header, HttpServletRequest request, Pagenation pagenation,
            String visitorFromDateTime, String visitorToDateTime) {
        pagenation.PagenationExpansionDate(
                visitorFromDateTime.equals("") ? LocalDate.now().toString() : visitorFromDateTime,
                visitorToDateTime.equals("") ? LocalDate.now().toString() : visitorToDateTime);

        Map<String, Object> map = new HashMap<>();
        map.put("host", loginService.getUserSessionInfo(request));
        map.put("pagenation", pagenation);
        map.put("searchFromDateTime", pagenation.getVisitFromDateTime());
        map.put("searchToDateTime", pagenation.getVisitToDateTime());
        map.put("excel", "");

        List<VisitorHistory> visitorHistorys = visitorService.findByPlanDateTime(map);

        String[][] excelData = new String[visitorHistorys.size() + 1][header.length];
        excelData[0] = header;
        for (int i = 1; i <= visitorHistorys.size(); i++) {
            VisitorHistory visitorHistory = visitorHistorys.get(i-1);
            try {
                excelData[i] = new String[] {
                    AES256Util.decrypt(visitorHistory.getVisitorName()),
                    visitorHistory.getVisitorCompany(),
                    AES256Util.decrypt(visitorHistory.getVisitorMobile()),
                    visitorHistory.getVisitPurpose(),
                    visitorHistory.getVisitorPosition1(),
                    visitorHistory.getPlanFromDateTime(),
                    visitorHistory.getPlanToDateTime(),
                    visitorHistory.getCarNo(),
                    visitorHistory.getHostName(),
                    visitorHistory.getHostCompany(),
                    visitorHistory.getHostDept(),
                    visitorHistory.getCardID(),
                    visitorHistory.getVisitApprovalYN().equals("Y") ? "승인완료" : "승인대기"
                };
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return excelData;
    }

    public String[][] historyExcel(String[] header, HttpServletRequest request, Pagenation pagenation, String visitorFromDateTime, String visitorToDateTime) {
        pagenation.PagenationExpansionDate(
                visitorFromDateTime.equals("") ? LocalDate.now().toString() : visitorFromDateTime,
                visitorToDateTime.equals("") ? LocalDate.now().toString() : visitorToDateTime);

        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        map.put("host", loginService.getUserSessionInfo(request));
        map.put("excel", "");
        List<VisitorInoutTime> visitorHistorys = visitorInoutTimeService.findAllHistory(map);
      
        String[][] excelData = new String[visitorHistorys.size() + 1][header.length];
        excelData[0] = header;
        for(int i=1; i <= visitorHistorys.size(); i++) {
            VisitorInoutTime visitorHistory = visitorHistorys.get(i-1);
            try {
                excelData[i] = new String[] { 
                    AES256Util.decrypt(visitorHistory.getVisitorHistory().getVisitorName()),
                    visitorHistory.getVisitorHistory().getVisitorCompany(),
                    AES256Util.decrypt(visitorHistory.getVisitorHistory().getVisitorMobile()),
                    visitorHistory.getVisitorHistory().getVisitDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    visitorHistory.getVisitorHistory().getCardID(),
                    visitorHistory.getVisitorHistory().getVisitPurpose(),
                    visitorHistory.getVisitorHistory().getVisitorPosition1(),
                    visitorHistory.getVisitorHistory().getPlanFromDateTime()+"~"+visitorHistory.getVisitorHistory().getPlanToDateTime(),  //방문일자
                    visitorHistory.getVisitFromDateTime(),
                    visitorHistory.getVisitToDateTime(),  
                    visitorHistory.getVisitorHistory().getHostName(),
                    visitorHistory.getVisitorHistory().getHostCompany(),
                    visitorHistory.getVisitorHistory().getHostDept()
                };
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return excelData;
    }

    public String[][] blackListExcel(String[] header, HttpServletRequest request, Pagenation pagenation) {
        
        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        map.put("host", loginService.getUserSessionInfo(request));
        map.put("excel", "");
        List<VisitorBlackList> visitorBlackListfindAll = visitorBlackListservice.findSearch(map);

        String[][] excelData = new String[visitorBlackListfindAll.size() + 1][header.length];
        excelData[0] = header;
        for(int i=1; i <= visitorBlackListfindAll.size(); i++) {
            VisitorBlackList visitorBlackList = visitorBlackListfindAll.get(i-1);
            try {
                excelData[i] = new String[] { 
                    AES256Util.decrypt(visitorBlackList.getVisitor().getVisitorName()),
                    visitorBlackList.getVisitor().getCompany(),
                    AES256Util.decrypt(visitorBlackList.getVisitor().getMobile()),
                    visitorBlackList.getPlanFromDate(),
                    visitorBlackList.getPlanToDate(),
                    visitorBlackList.getBlacklistState(),
                    visitorBlackList.getBlacklistReason()
                };
            } catch (UnsupportedEncodingException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return excelData;
    }
}