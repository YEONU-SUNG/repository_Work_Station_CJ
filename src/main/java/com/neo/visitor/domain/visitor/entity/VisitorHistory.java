package com.neo.visitor.domain.visitor.entity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.config.AES256Util;
import com.neo.visitor.domain.user.entity.Host;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorHistory {

    private int visitorHistorySeq;
    private String representativeVisitorHistorySeq;
    private String visitorReservationNumber = "";
    private String visitorID;
    private int visitorType;
    private String visitorName;
    private String visitorGender;
    private String visitorLocation;
    private String visitorBirth;
    private String visitorMobile;
    private String visitorCompany;
    private String visitorDept;
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
    private String carryStuffWare = "";
    private String carryStuffSerialNo = "";
    private String carryStuffUsed = "";
    private String carryStuffPurpose = "";
    private String deleteFlag;
    private String visitPurpose = "";
    private String visitPurposeDetail = "";
    private String toDayYN = ""; // 가상 컬럼- 방문일자가 오늘날짜에 포함되는지
    private String rejectFlag = "";
    private String rejectType = "";
    private String rejectComment = "";

    private String visitorAccessBuilding = "";
    private String visitorAceessFloor = "";

    private Host host;
    // private Visitor visitor;
    private List<VisitorInoutTime> visitorInoutTimes = new ArrayList<>();

    public VisitorHistory updateCardID(int visitorHistorySeq, String cardID) {
        if (visitorHistorySeq == 0)
            throw new IllegalArgumentException("잘못된 요청 값");
        this.visitorHistorySeq = visitorHistorySeq;
        this.cardID = cardID;
        return this;
    }

    public void updateApproval(String visitApprovalComment, String carryStuff) {
        if (this.visitApprovalYN == "Y")
            throw new IllegalArgumentException("잘못된 요청 값");
        this.visitApprovalYN = "Y";
        this.visitApprovalDateTime = LocalDateTime.now().toString();
        this.visitApprovalComment = visitApprovalComment;
        // this.carryStuff = carryStuff;
    }

    // 추가본
    public void updateReject(String rejectComment) {
        if (this.rejectFlag == "Y")
            throw new IllegalArgumentException("잘못된 요청 값");
        this.rejectFlag = "Y";
        this.rejectComment = rejectComment;
        // this.carryStuff = carryStuff;
    }

    // 방문객 정보 생성
    public void makeVisitorHistory(String hostId, String planFromDate, String planToDateTime, String visitPurpose,
            String visitPurposeDetail, String visitorPosition1, String visitorPosition2, String visitorPosition3,
            String visitorCar) {
        this.hostID = hostId;
        this.planFromDateTime = planFromDate;
        this.planToDateTime = planToDateTime;
        this.visitPurpose = visitPurpose;
        this.visitPurposeDetail = visitPurposeDetail;
        this.visitorPosition1 = visitorPosition1;
        this.visitorPosition2 = visitorPosition2;
        this.visitorPosition3 = visitorPosition3;
        this.carNo = visitorCar;
    }

    // 호스트 정보 바인딩
    public void addHost(Host host) {
        this.hostID = host.getHostID();
        this.hostTel = host.getTel();
        this.hostDept = host.getDeptCD();
        this.hostCompany = host.getCompany();
        this.hostGradeName = host.getGradeName();
        this.hostPositionName = host.getPositionName();
        this.hostName = host.getHostName();
        this.hostMobile = host.getMobile();
        // try {
        //     this.hostName = AES256Util.encrypt(host.getHostName());
        //     this.hostMobile = AES256Util.encrypt(host.getMobile());
        // } catch (UnsupportedEncodingException | GeneralSecurityException e) {
        //     e.printStackTrace();
        // }
    }

    // 방문객 방문목적 바인딩 (납품)
    @Deprecated
    public void addVisitPurposeCar(Visitor visitor, String visitorCarType, String visitorCar) {
        this.visitorID = visitor.getVisitorID() + "";
        this.visitorName = visitor.getVisitorName();
        this.visitorBirth = visitor.getVisitorBirth();
        this.visitorMobile = visitor.getMobile();
        if (!visitorCarType.equals("도보"))
            this.carNo = visitorCar;
    }

    // 방문객 방문목적 바인딩 (작업)
    @Deprecated
    public void addVisitPurposeWork(Visitor visitor, String visitorSerial, String visitorPurpose2, String visitorUsed) {
        this.visitorID = visitor.getVisitorID() + "";
        this.visitorName = visitor.getVisitorName();
        this.visitorBirth = visitor.getVisitorBirth();
        this.visitorMobile = visitor.getMobile();
        if (!visitorSerial.trim().equals("")) {
            this.carryStuff = "PC";
            this.carryStuffSerialNo = visitorSerial;
            this.carryStuffPurpose = visitorPurpose2;
            this.carryStuffUsed = visitorUsed;
        }
    }

    /**
     * 외부인 방문자 정보 셋팅
     * 
     * @param _visitor
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void setVisitorByVisiter(Visitor _visitor) {
        // 외부인
        this.visitorType = 2;
        this.visitorID = _visitor.getVisitorID()+"";
        this.visitorCompany = _visitor.getCompany();
        this.visitorGender = _visitor.getVisitorGender();
        this.visitorLocation = _visitor.getVisitorLocation();
        try {
            this.visitorName = AES256Util.encrypt(_visitor.getVisitorName());
            this.visitorBirth = AES256Util.encrypt(_visitor.getVisitorBirth());
            this.visitorMobile = AES256Util.encrypt(_visitor.getMobile());
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 임직원 방문자 정보 셋팅
     * 
     * @param _host
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void setHostByVisiter(Host _host) {
        // 임직원
        this.visitorType = 1;
        this.visitorID = _host.getHostID();
        this.visitorBirth = "";
        this.visitorCompany = _host.getCompany();
        this.visitorDept = _host.getDeptCD();
        try {
            this.visitorName = AES256Util.encrypt(_host.getHostName());
            this.visitorMobile = _host.getMobile()!=null ? AES256Util.encrypt(_host.getMobile()) : _host.getMobile();
            this.hostTel = _host.getTel()!=null ? AES256Util.encrypt(_host.getTel()) : _host.getTel();
        } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * 방문자가 요청한 반입물품을 기록
     * @param visiter
     */
    public void setCarryinWareByVisitor(Visiter visiter) {
        this.carryStuff = "";
        this.carryStuffWare = visiter.getCarryStuffWare();
        this.carryStuffSerialNo = visiter.getCarryStuffSerialNo();
        this.carryStuffPurpose = visiter.getCarryStuffPurpose();
        this.carryStuffUsed = visiter.getCarryStuffUsed();
    }


    /**
     * 방문자 교육완료 여부 체크
     * @param visitorHistory
     * @return true(완료), false(미완료)
     */
    public boolean isEducationCheck(VisitorHistory visitorHistory) {
        if(visitorHistory!=null) {
            this.eduCompleteDateTime = visitorHistory.getEduCompleteDateTime();
            return true;
        } else {
            this.eduCompleteDateTime = null;
            return false;
        }
    }

    /**
     * 방문자 예약번호 생성
     * @return (South : S, North : N) + yyMMdd + seq%10000
     */
    public void makeReservationNumber() {
        String strReserveNo = this.visitorPosition2.equals("South")  ? "S" :"N";
        strReserveNo += LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        strReserveNo += String.format("%04d", (this.visitorHistorySeq)%10000);
        this.visitorReservationNumber = strReserveNo;
    }

    /**
     * 임직원 인 경우건물 내 층에 대한 접근권한 부여
     * @param buildingName
     * @param floor
     */
    public void addAccessBuildingFloor(String buildingName, String[] floor) {
        try {
            if(this.visitorType == 1) {
                this.visitorAccessBuilding = buildingName;
                for (String _floor : floor) {
                    this.visitorAceessFloor += _floor+" ,";
                }
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("건물 접근 권한을 부여해주세요.");
        }
    }

    public void addVisitorInout(VisitorInoutTime visitorInoutTime) {
        this.visitorInoutTimes.add(visitorInoutTime);
    }

    public void isVaildVisitCard() {
        if(this.cardID.length()==0 || this.cardID.equals("null") || this.cardID==null || this.cardID.equals("undefined"))
            throw new IllegalArgumentException("잘못된 카드정보입니다.");
    }

}