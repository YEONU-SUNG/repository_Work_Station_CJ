package com.neo.visitor.domain.visitor.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public class VisitorDashboard {
    
    private int seq;
    private String date;
    private String siteCode;
    private int insertCnt;
    private int visitApplicationCount;
    private int approvalStandbyCount;
    private int visitStandbyCount;
    private int visitCount;

    public VisitorDashboard makeDashBoard(String siteCode) {
        this.date = LocalDate.now().toString();
        this.siteCode = siteCode;
        return this;
    }

    public VisitorDashboard makeDashBoardOnce(String siteCode, int insertCnt) {
        this.date = LocalDate.now().toString();
        this.siteCode = siteCode;
        this.insertCnt = insertCnt;
        return this;
    }

    public void addDefaultVisitApplicationCount(List<VisitorHistory> visitApplicationCount) {
        this.visitApplicationCount = visitApplicationCount.size();
        //this.approvalStandbyCount = (int) visitApplicationCount.stream().filter(row -> row.getEduCompleteDateTime()!=null && !row.getEduCompleteDateTime().equals("")).count();
        this.approvalStandbyCount = (int) visitApplicationCount.stream().filter(row -> row.getVisitApprovalYN()!=null && !row.getVisitApprovalYN().equals("Y") && (row.getEduCompleteDateTime()!=null &&!row.getEduCompleteDateTime().equals(""))).count();
        this.visitStandbyCount = (int) visitApplicationCount.stream().filter(row ->
            row.getVisitApprovalYN()!=null &&
            row.getVisitApprovalYN().equals("Y") &&
            (row.getEduCompleteDateTime()!=null &&!row.getEduCompleteDateTime().equals("")) &&
            row.getVisitorInoutTimes().size()<1).count();
            //row.getVisitorInoutTimes().stream().map(x->x.getVisitFromDateTime()!=null).count() < 1).count();

        Stream<VisitorInoutTime> list4 = visitApplicationCount.stream().flatMap(x-> x.getVisitorInoutTimes().parallelStream());
        List<VisitorInoutTime> test = list4.collect(Collectors.toList());
        this.visitCount = (int)test.stream().filter(x -> x.getVisitToDateTime().equals("")).count();
        //visitApplicationCount.get(3).getVisitorInoutTimes().get(0).getVisitFromDateTime();
    }
}