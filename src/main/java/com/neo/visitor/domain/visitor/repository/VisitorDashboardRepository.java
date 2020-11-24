package com.neo.visitor.domain.visitor.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.visitor.entity.VisitorDashboard;

@PrimaryMapperScan
public interface VisitorDashboardRepository {
    
    void save(VisitorDashboard visitorDashBoard);
    List<VisitorDashboard> findAll(String date);
    void visitApplicationCount(VisitorDashboard visitorDashBoard);
    void visitApplicationCountOnce(VisitorDashboard visitorDashBoard);
    void approvalStandbyCount(VisitorDashboard visitorDashBoard);
    void visitStandbyCount(VisitorDashboard visitorDashBoard);
    void updateVisitPlusCount(VisitorDashboard visitorDashBoard);
    void updateVisitMinusCount(VisitorDashboard visitorDashBoard);
    void updateAllCount(VisitorDashboard visitorDashBoard);
}