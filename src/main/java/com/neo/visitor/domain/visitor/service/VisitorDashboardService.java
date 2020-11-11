package com.neo.visitor.domain.visitor.service;

import java.time.LocalDate;
import java.util.List;

import com.neo.visitor.domain.visitor.entity.VisitorDashboard;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.repository.VisitorDashboardRepository;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorDashboardService {
    
    @Autowired VisitorHistoryRepository visitorHistoryRepository;
    @Autowired VisitorDashboardRepository visitorDashboardRepository;

    private final static String POSITION1 = "화성";
    private final static String POSITION2 = "아산";

    public List<VisitorDashboard> findAll() {
        VisitorDashboard position1 = new VisitorDashboard().makeDashBoard(POSITION1);
        position1.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position1));
        visitorDashboardRepository.updateAllCount(position1);
        
        VisitorDashboard position2 = new VisitorDashboard().makeDashBoard(POSITION2);
        position2.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position2));
        visitorDashboardRepository.updateAllCount(position2);

        return visitorDashboardRepository.findAll(LocalDate.now().toString());
    }

    public void updateVisitApplicationCount(VisitorHistory visitorHistory) {
        visitorDashboardRepository.visitApplicationCount(new VisitorDashboard().makeDashBoard(visitorHistory.getVisitorPosition1()));
    }

    public void updateVisitApplicationCountOnce(VisitorHistory visitorHistory, int TotalCnt) {
        visitorDashboardRepository.visitApplicationCountOnce(new VisitorDashboard().makeDashBoardOnce(visitorHistory.getVisitorPosition1(), TotalCnt));
    }

    public void updateApprovalStandbyCount(VisitorHistory visitorHistory) {
        if(visitorHistory.getEduCompleteDateTime()!=null && !visitorHistory.getEduCompleteDateTime().equals("")) {
            visitorDashboardRepository.approvalStandbyCount(new VisitorDashboard().makeDashBoard(visitorHistory.getVisitorPosition1()));
        }
    }

    public void updateVisitStandbyCount(VisitorHistory visitorHistory) {
        visitorDashboardRepository.visitStandbyCount(new VisitorDashboard().makeDashBoard(visitorHistory.getVisitorPosition1()));
    }

    public void updateVisitPlusCount(VisitorHistory visitorHistory) {
        visitorDashboardRepository.updateVisitPlusCount(new VisitorDashboard().makeDashBoard(visitorHistory.getVisitorPosition1()));
    }

    public void updateVisitMinusCount(VisitorHistory visitorHistory) {
        visitorDashboardRepository.updateVisitMinusCount(new VisitorDashboard().makeDashBoard(visitorHistory.getVisitorPosition1()));
    }
    
}