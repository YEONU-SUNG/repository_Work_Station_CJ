package com.neo.visitor.domain.visitor.application;

import com.neo.visitor.domain.visitor.entity.VisitorDashboard;
import com.neo.visitor.domain.visitor.repository.VisitorDashboardRepository;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VisitorScheduler {

    @Autowired VisitorHistoryRepository visitorHistoryRepository;
    @Autowired VisitorDashboardRepository visitorDashboardRepository;

    private final static String POSITION1 = "화성";
    private final static String POSITION2 = "아산";

    @Scheduled(cron = "10 0 0 * * *")
    //@Scheduled(cron = "30 * * * * *")
    public void contentsStateSchedulCheckRun() {
        
        VisitorDashboard position1 = new VisitorDashboard().makeDashBoard(POSITION1);
        position1.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position1));
        visitorDashboardRepository.save(position1);
        
        VisitorDashboard position2 = new VisitorDashboard().makeDashBoard(POSITION2);
        position2.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position2));
        visitorDashboardRepository.save(position2);
    }
}
