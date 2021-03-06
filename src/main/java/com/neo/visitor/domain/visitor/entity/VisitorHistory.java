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
    private String hostSiteCode;
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
    private String toDayYN = ""; // ?????? ??????- ??????????????? ??????????????? ???????????????
    private String rejectFlag = "";
    private int rejectType;
    private String rejectComment = "";

    private String visitorAccessBuilding = "";
    private String visitorAceessFloor = "";

    private Host host;
    // private Visitor visitor;
    private List<VisitorInoutTime> visitorInoutTimes = new ArrayList<>();

    public VisitorHistory updateCardID(int visitorHistorySeq, String cardID) {
        if (visitorHistorySeq == 0)
            throw new IllegalArgumentException("????????? ?????? ???");
        this.visitorHistorySeq = visitorHistorySeq;
        this.cardID = cardID;
        return this;
    }

    public void updateApproval(String visitApprovalComment, String carryStuff) {
        if (this.visitApprovalYN == "Y")
            throw new IllegalArgumentException("????????? ?????? ???");
        this.visitApprovalYN = "Y";
        this.visitApprovalDateTime = LocalDateTime.now().toString();
        this.visitApprovalComment = visitApprovalComment;
        // this.carryStuff = carryStuff;
    }

    // ?????????
    public void updateReject(String rejectType, String rejectComment) {
        if (this.rejectFlag == "Y")
            throw new IllegalArgumentException("????????? ?????? ???");
        this.rejectFlag = "Y";
        this.rejectType = Integer.parseInt(rejectType);
        this.rejectComment = rejectComment;
        // this.carryStuff = carryStuff;
    }

    // ????????? ?????? ??????
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

    // ????????? ?????? ?????????
    public void addHost(Host host) {
        this.hostID = host.getHostID();
        this.hostTel = host.getTel();
        this.hostDept = host.getDeptCD();
        this.hostCompany = host.getCompany();
        this.hostGradeName = host.getGradeName();
        this.hostPositionName = host.getPositionName();
        this.hostName = host.getHostName();
        this.hostMobile = host.getMobile();
        this.hostSiteCode = host.getSiteCode();
        // try {
        //     this.hostName = AES256Util.encrypt(host.getHostName());
        //     this.hostMobile = AES256Util.encrypt(host.getMobile());
        // } catch (UnsupportedEncodingException | GeneralSecurityException e) {
        //     e.printStackTrace();
        // }
    }

    // ????????? ???????????? ????????? (??????)
    @Deprecated
    public void addVisitPurposeCar(Visitor visitor, String visitorCarType, String visitorCar) {
        this.visitorID = visitor.getVisitorID() + "";
        this.visitorName = visitor.getVisitorName();
        this.visitorBirth = visitor.getVisitorBirth();
        this.visitorMobile = visitor.getMobile();
        if (!visitorCarType.equals("??????"))
            this.carNo = visitorCar;
    }

    // ????????? ???????????? ????????? (??????)
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
     * ????????? ????????? ?????? ??????
     * 
     * @param _visitor
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void setVisitorByVisiter(Visitor _visitor) {
        // ?????????
        this.visitorType = 2;
        this.visitorID = _visitor.getVisitorID()+"";
        this.visitorCompany = _visitor.getCompany();
        this.visitorGender = _visitor.getVisitorGender();
        this.visitorLocation = _visitor.getVisitorLocation();
        // try {
        //     this.visitorName = AES256Util.encrypt(_visitor.getVisitorName());
        //     this.visitorBirth = AES256Util.encrypt(_visitor.getVisitorBirth());
        //     this.visitorMobile = AES256Util.encrypt(_visitor.getMobile());
        // } catch (UnsupportedEncodingException | GeneralSecurityException e) {
        //     e.printStackTrace();
        // }
        this.visitorName = _visitor.getVisitorName();
        this.visitorBirth = _visitor.getVisitorBirth();
        this.visitorMobile = _visitor.getMobile();
    }

    /**
     * ????????? ????????? ?????? ??????
     * 
     * @param _host
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void setHostByVisiter(Host _host) {
        // ?????????
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
     * ???????????? ????????? ??????????????? ??????
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
     * ????????? ???????????? ?????? ??????
     * @param visitorHistory
     * @return true(??????), false(?????????)
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
     * ????????? ???????????? ??????
     * @return (South : S, North : N) + yyMMdd + seq%10000
     */
    public void makeReservationNumber() {
        String strReserveNo = this.visitorPosition2.equals("South")  ? "S" :"N";
        strReserveNo += LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        strReserveNo += String.format("%04d", (this.visitorHistorySeq)%10000);
        this.visitorReservationNumber = strReserveNo;
    }

    /**
     * ????????? ??? ???????????? ??? ?????? ?????? ???????????? ??????
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
            throw new IllegalArgumentException("?????? ?????? ????????? ??????????????????.");
        }
    }

    public void addVisitorInout(VisitorInoutTime visitorInoutTime) {
        this.visitorInoutTimes.add(visitorInoutTime);
    }

    public void isVaildVisitCard() {
        if(this.cardID.length()==0 || this.cardID.equals("null") || this.cardID==null || this.cardID.equals("undefined"))
            throw new IllegalArgumentException("????????? ?????????????????????.");
    }

}