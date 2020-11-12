package com.neo.visitor.domain.visitor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.domain.user.entity.Host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VisitorHistory {
    
    private int visitorHistorySeq;
    private String representativeVisitorHistorySeq;
    private String visitorReservationNumber = "";
    private String visitorID;
    private String visitorName;
    private String visitorBirth;
    private String visitorMobile;
    private String visitorCompany;
    private String visitorPosition1;
    private String visitorPosition2;
    private String visitorPosition3;
    private String visitApprovalYN;
    private String visitApprovalComment;
    private LocalDateTime visitDate;
    private String agreeFirst;
    private String agreeSecond;
    private String cardID = "";
    private String carNo = "";
    private String hostID;
    private String hostName;
    private String hostMobile;
    private String hostTel;
    private String hostDept;
    private String hostCompany;
    private String hostGradeName;
    private String hostPositionName;
    private String planFromDateTime;
    private String planToDateTime;
    private String approval;
    private String visitApprovalDateTime;
    private String eduCompleteDateTime;
    private String carryStuff = "";
    private String carryStuffSerialNo = "";
    private String carryStuffUsed = "";
    private String carryStuffPurpose = "";
    private String deleteFlag;
    private String visitPurpose = "";
    private String visitPurposeDetail = "";
    private String toDayYN = ""; //가상 컬럼- 방문일자가 오늘날짜에 포함되는지
    private String rejectFlag = "";
    private String rejectType = "";
    private String rejectComment = "";

    private Host host;
    private Visitor visitor;
    private List<VisitorInoutTime> visitorInoutTimes = new ArrayList<>();

    public VisitorHistory updateCardID(int visitorHistorySeq, String cardID) {
        if(visitorHistorySeq==0) throw new IllegalArgumentException("잘못된 요청 값");
        this.visitorHistorySeq = visitorHistorySeq;
        this.cardID = cardID;
        return this;
    }

    public void updateApproval(String visitApprovalComment, String carryStuff) {
        if(this.visitApprovalYN=="Y") throw new IllegalArgumentException("잘못된 요청 값");
        this.visitApprovalYN = "Y";
        this.visitApprovalDateTime = LocalDateTime.now().toString();
        this.visitApprovalComment = visitApprovalComment;
        //this.carryStuff = carryStuff;
    }

    // 추가본
    public void updateReject(String rejectComment) {
        if(this.rejectFlag=="Y") throw new IllegalArgumentException("잘못된 요청 값");
        this.rejectFlag = "Y";
        this.rejectComment = rejectComment;
        //this.carryStuff = carryStuff;
    }

    // 방문객 정보 생성
    public void makeVisitorHistory(String hostName, String hostDept, String planFromDate, String planToDateTime, String visitPurpose, String visitPurposeDetail, String visitorPosition1, String visitorPosition2, String visitorCompany) {
        this.hostName = hostName;
        this.hostDept = hostDept;
        this.planFromDateTime = planFromDate;
        this.planToDateTime = planToDateTime;
        this.visitPurpose = visitPurpose;
        this.visitPurposeDetail = visitPurposeDetail;
        this.visitorPosition1 = visitorPosition1;
        this.visitorPosition2 = visitorPosition2;
        this.visitorCompany = visitorCompany;
    }

    // 호스트 정보 바인딩
    public void addHost(Host host) {
        this.hostID = host.getHostID();
        this.hostName = host.getHostName();
        this.hostMobile = host.getMobile();
        this.hostTel = host.getTel();
        this.hostDept = host.getDeptCD();
        this.hostCompany = host.getCompany();
        this.hostGradeName = host.getGradeName();
        this.hostPositionName = host.getPositionName();
    }

    // 방문객 방문목적 바인딩 (납품)
    public void addVisitPurposeCar(Visitor visitor, String visitorCarType, String visitorCar) {
        this.visitorID = visitor.getVisitorID()+"";
        this.visitorName = visitor.getVisitorName();
        this.visitorBirth = visitor.getVisitorBirth();
        this.visitorMobile = visitor.getMobile();
        if(!visitorCarType.equals("도보")) this.carNo = visitorCar;
    }

    // 방문객 방문목적 바인딩 (작업)
    public void addVisitPurposeWork(Visitor visitor, String visitorSerial,  String visitorPurpose2, String visitorUsed) {
        this.visitorID = visitor.getVisitorID()+"";
        this.visitorName = visitor.getVisitorName();
        this.visitorBirth = visitor.getVisitorBirth();
        this.visitorMobile = visitor.getMobile();
        if(!visitorSerial.trim().equals(""))
        {
            this.carryStuff = "PC";
            this.carryStuffSerialNo = visitorSerial;
            this.carryStuffPurpose = visitorPurpose2;
            this.carryStuffUsed = visitorUsed;
        }
        
    }

    // 방문객 교육이력 체크
    public void educationCheck(VisitorHistory visitorHistory) {
        if(visitorHistory!=null) 
            this.eduCompleteDateTime = visitorHistory.getEduCompleteDateTime();
        else 
            this.eduCompleteDateTime = null;
    }

    public void makeReservationNumber() {
        String strReserveNo = this.visitorPosition1.equals("아산")  ? "A" :"H";
        strReserveNo += LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        strReserveNo += String.format("%04d", (this.visitorHistorySeq)%10000);
        this.visitorReservationNumber = strReserveNo;
    }

    public void addVisitorInout(VisitorInoutTime visitorInoutTime) {
        this.visitorInoutTimes.add(visitorInoutTime);
    }

    public void isVaildVisitCard() {
        if(this.cardID.length()==0 || this.cardID.equals("null") || this.cardID==null || this.cardID.equals("undefined"))
            throw new IllegalArgumentException("잘못된 카드정보입니다.");
    }
}