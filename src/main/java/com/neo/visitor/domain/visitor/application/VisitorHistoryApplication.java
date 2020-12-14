package com.neo.visitor.domain.visitor.application;

import com.neo.visitor.domain.user.service.HostService;
import com.neo.visitor.domain.visitor.entity.Visiter;
import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;
import com.neo.visitor.domain.visitor.service.VisitorBlackListService;
import com.neo.visitor.domain.visitor.service.VisitorDashboardService;
import com.neo.visitor.domain.visitor.service.VisitorService;
import com.neo.visitor.insa.repository.InsaRepository;
import com.neo.visitor.sms.entity.SMSMsgQue;
import com.neo.visitor.sms.repository.SMSRepository;

import java.util.List;

import com.neo.visitor.domain.user.entity.Host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.neo.visitor.util.MailSenderUtil;

@Service
public class VisitorHistoryApplication {
    
    @Autowired VisitorDashboardService visitorDashBoardService;
    @Autowired VisitorService visitorService;
    @Autowired HostService hostService;
    @Autowired VisitorHistoryRepository visitorHistoryRepository;
    @Autowired SMSRepository smsRepository;
    @Autowired MailSenderUtil mailSenderUtil;
    @Autowired VisitorBlackListService visitorBlackListService;
    @Autowired InsaRepository insaRepository;

    /*
    @Transactional
    public void saveHistoryVisitPurposeCar(VisitorHistory visitorHistory, String[] visitorNames, String[] visitorBirths, String[] visitorPhones, String[] visitorCarTypes, String[] visitorCars) {
        // 호스트정보 바인딩
        Host host = hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName());
        //visitorHistory.addHost(hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName()));
        visitorHistory.addHost(host);
        // 신청 방문객 수 만큼 등록
        String strSMSSendText = "SFA방문예약 - ";
        //String strHostSendText = "SFA방문예약 - ";
        String strHostSendText = "SFA방문예약이 요청되었습니다.";
        int smsEduSendCnt = 0;
        String strRepresentativeVisitorHistorySeq ="";
        SMSMsgQue smsMsgque = new SMSMsgQue();
        for(int i=0; i<visitorNames.length; i++) {
            // 방문객 정보가 있는지 확인
            Visitor visitor = visitorService.save(new Visitor().makeVisitor(visitorNames[i], visitorBirths[i].replace("-", ""), visitorPhones[i], visitorHistory.getVisitorCompany()));
            visitorHistory.addVisitPurposeCar(visitor, visitorCarTypes[i], visitorCars[i]);
            //overlapVisitorIsVaild(visitorHistory);
            visitorHistory.educationCheck(visitorHistoryRepository.findByVisitorIDAndEduCompleteDateTime(visitorHistory));
            visitorHistory.setRepresentativeVisitorHistorySeq(strRepresentativeVisitorHistorySeq);
            visitorHistoryRepository.save(visitorHistory);
            visitorHistory.makeReservationNumber();
            visitorHistoryRepository.updatevisitorReservationNumber(visitorHistory);
            visitorDashBoardService.updateVisitApplicationCount(visitorHistory);

            if(i == 0)
                strRepresentativeVisitorHistorySeq = String.format("%d", visitorHistory.getVisitorHistorySeq());

            //smsMsgque.setCallback("041-520-1004");
    
            smsMsgque.setCallback("041-539-6741");
            if(visitorHistory.getEduCompleteDateTime() == null) //방문교육 이수전에는 방문객에게 방문교육 이수 알림 보냄
            {
                smsEduSendCnt++;
                //strSMSSendText += "정보 입력이 완료되었습니다. 방문교육 이수 후 방문 신청이 완료됩니다.\r\nhttp://visit.sfa.co.kr";
                strSMSSendText = "SFA방문예약 - ";
                //strSMSSendText += "정보 입력이 완료되었습니다. 방문예약 시스템에서 방문신청 진행사항 바로가기를 통해 방문교육을 이수 하셔야 방문 신청이 완료됩니다.\r\nhttp://visit.sfa.co.kr";
                strSMSSendText += "정보 입력이 완료되었습니다. 방문예약 시스템에서 방문신청 진행사항 바로가기를 통해 방문교육을 이수 하셔야 방문 신청이 완료됩니다.\r\nhttps://visit.sfa.co.kr";
                smsMsgque.setDstaddr(visitorHistory.getVisitorMobile());
                smsMsgque.setText(strSMSSendText);
                smsRepository.sendSMS(smsMsgque);
            }
            else //방문교육 이수된 신청에 대해서는 담당자에게 승인요청 알림 보냄
            {
                visitorDashBoardService.updateApprovalStandbyCount(visitorHistory);
                if (i == 0) {
                    //strHostSendText += visitorHistory.getVisitorName() + "님(" + visitorHistory.getVisitorCompany() +") " ;
                    strHostSendText +=  "\r\n- 성명 : " + visitorHistory.getVisitorName();
                }
            }
          
        }

        if(strHostSendText.length() >11 && smsEduSendCnt < 1)
        {
            //strHostSendText += "님이 방문 신청하였습니다.\r\n방문 기간 : " + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime()
            if(visitorNames.length >1)
                strHostSendText += " 외 "+ (visitorNames.length-1) +"명이 방문 신청하였습니다.\r\n방문 기간 : ";
            else
                strHostSendText += "이 방문 신청하였습니다.\r\n방문 기간 : ";
                
            strHostSendText += visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() 
            + "\r\n방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
			+ "\r\n방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
            + "\r\n승인사이트 : http://a.visit.sfa.co.kr";
            smsMsgque.setDstaddr(visitorHistory.getHostMobile());
            smsMsgque.setText(strHostSendText);
            mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문신청",strHostSendText,"");

            smsRepository.sendSMS(smsMsgque);
        }
        if(strHostSendText.length() >18 && smsEduSendCnt < 1)
        {
            if(visitorNames.length >1)
                strHostSendText += " 외 "+ (visitorNames.length-1) +"명";

            strHostSendText +=  "\r\n- 회사 : " + visitorHistory.getVisitorCompany();
                
            strHostSendText += "\r\n- 방문기간 : " + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() 
            + "\r\n- 방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
			+ "\r\n- 방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
            //+ "\r\n- 승인사이트 : http://a.visit.sfa.co.kr";
            + "\r\n- 승인사이트 : https://avisit.sfa.co.kr";
            smsMsgque.setDstaddr(visitorHistory.getHostMobile());
            smsMsgque.setText(strHostSendText);
            mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문신청",strHostSendText,"");

            smsRepository.sendSMS(smsMsgque);
        }
    }

    @Transactional
    public void saveHistoryVisitPurposeWork(VisitorHistory visitorHistory, String[] visitorNames, String[] visitorBirths, String[] visitorPhones, String[] visitorSerials,  String[] visitorPurpose2s, String[] visitorUseds) {
        Host host = hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName());
        //visitorHistory.addHost(hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName()));
        visitorHistory.addHost(host);
        String strSMSSendText = "SFA방문예약 - ";
        //String strHostSendText = "SFA방문예약 - ";
        String strHostSendText = "SFA방문예약이 요청되었습니다.";
        int smsEduSendCnt = 0;
        String strRepresentativeVisitorHistorySeq ="";
        SMSMsgQue smsMsgque = new SMSMsgQue();
        for(int i=0; i<visitorNames.length; i++) {
            Visitor visitor = visitorService.save(new Visitor().makeVisitor(visitorNames[i], visitorBirths[i].replace("-", ""), visitorPhones[i], visitorHistory.getVisitorCompany()));
            visitorHistory.addVisitPurposeWork(visitor, visitorSerials[i], visitorPurpose2s[i], visitorUseds[i]);
            //overlapVisitorIsVaild(visitorHistory);
            visitorHistory.educationCheck(visitorHistoryRepository.findByVisitorIDAndEduCompleteDateTime(visitorHistory));
            visitorHistory.setRepresentativeVisitorHistorySeq(strRepresentativeVisitorHistorySeq);
            visitorHistoryRepository.save(visitorHistory);
            visitorHistory.makeReservationNumber();
            visitorHistoryRepository.updatevisitorReservationNumber(visitorHistory);
            visitorDashBoardService.updateVisitApplicationCount(visitorHistory);

            if(i == 0)
                strRepresentativeVisitorHistorySeq = String.format("%d", visitorHistory.getVisitorHistorySeq());
            
            //smsMsgque.setCallback("041-520-1004");
            smsMsgque.setCallback("041-539-6741");
            if(visitorHistory.getEduCompleteDateTime() == null) //방문교육 이수전에는 방문객에게 방문교육 이수 알림 보냄
            {
                smsEduSendCnt++;
                strSMSSendText = "SFA방문예약 - ";
                //strSMSSendText += "정보 입력이 완료되었습니다. 방문예약 시스템에서 방문신청 진행사항 바로가기를 통해 방문교육을 이수 하셔야 방문 신청이 완료됩니다.\r\nhttp://visit.sfa.co.kr";
                strSMSSendText += "정보 입력이 완료되었습니다. 방문예약 시스템에서 방문신청 진행사항 바로가기를 통해 방문교육을 이수 하셔야 방문 신청이 완료됩니다.\r\nhttps://visit.sfa.co.kr";
                smsMsgque.setDstaddr(visitorHistory.getVisitorMobile());
                smsMsgque.setText(strSMSSendText);
                smsRepository.sendSMS(smsMsgque);
            }
            else //방문교육 이수된 신청에 대해서는 담당자에게 승인요청 알림 보냄
            {
                visitorDashBoardService.updateApprovalStandbyCount(visitorHistory);
                if (i == 0) {
                    //strHostSendText += visitorHistory.getVisitorName() + "님(" + visitorHistory.getVisitorCompany() +") " ;
                    strHostSendText +=  "\r\n- 성명 : " + visitorHistory.getVisitorName();
                }
            }
            else //방문교육 이수된 신청에 대해서는 담당자에게 승인요청 알림 보냄
            {
                visitorDashBoardService.updateApprovalStandbyCount(visitorHistory);
                strSMSSendText = "SFA방문예약 - " + visitorHistory.getVisitorName() + "(" + visitorHistory.getVisitorCompany() +")님이 방문 신청하였습니다.\r\n 방문 기간 : " 
                + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime();
                smsMsgque.setDstaddr(visitorHistory.getHostMobile());
                smsMsgque.setText(strSMSSendText);

                mailSenderUtil.sendMail("visit@sfa.co.kr","shkim_neo@naver.com","방문예약시스템 방문신청",strSMSSendText,"kshzz012@gamil.com");
            }
        }
        if(strHostSendText.length() >11 && smsEduSendCnt < 1)
        {
            if(visitorNames.length >1)
                strHostSendText += " 외 "+ (visitorNames.length-1) +"명이 방문 신청하였습니다.\r\n방문 기간 : ";
            else
                strHostSendText += "이 방문 신청하였습니다.\r\n방문 기간 : ";
            
            strHostSendText += visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() 
            + "\r\n방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
			+ "\r\n방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
            + "\r\n승인사이트 : http://a.visit.sfa.co.kr";
            smsMsgque.setDstaddr(visitorHistory.getHostMobile());
            smsMsgque.setText(strHostSendText);
            //mailSenderUtil.sendMail("visit@sfa.co.kr","shkim_neo@naver.com","방문예약시스템 방문신청",strHostSendText,"kshzz012@gamil.com");
            mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문신청",strHostSendText,"");

            smsRepository.sendSMS(smsMsgque);
        }
        if(strHostSendText.length() >18 && smsEduSendCnt < 1)
        {
            if(visitorNames.length >1)
                strHostSendText += " 외 "+ (visitorNames.length-1) +"명";

            strHostSendText +=  "\r\n- 회사 : " + visitorHistory.getVisitorCompany();
                
            strHostSendText += "\r\n- 방문기간 : " + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() 
            + "\r\n- 방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
			+ "\r\n- 방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
            //+ "\r\n- 승인사이트 : http://a.visit.sfa.co.kr";
            + "\r\n- 승인사이트 : https://avisit.sfa.co.kr";
            smsMsgque.setDstaddr(visitorHistory.getHostMobile());
            smsMsgque.setText(strHostSendText);
            mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문신청",strHostSendText,"");

            smsRepository.sendSMS(smsMsgque);
        }
    }
    */

    @Transactional
    public void saveHistoryVisit(VisitorHistory visitorHistory, List<Visiter> visiters) {
        //Host host = hostService.findByHostID(visitorHistory.getHostID());
        Host host = insaRepository.findByHostId(visitorHistory.getHostID());
        visitorHistory.addHost(host);
        
        for (Visiter _visiter : visiters) {
            if(_visiter instanceof Visitor) {
                Visitor visitor = (Visitor) _visiter;
                visitorBlackListService.isBlackList(visitor, visitorHistory.getPlanFromDateTime(), visitorHistory.getPlanToDateTime());
                visitorHistory.setVisitorByVisiter(visitorService.save(visitor));
            } else {
                Host _host = (Host) _visiter;
                //_host = hostService.findByHostID(_host.getHostID());
                _host = insaRepository.findByHostId(_host.getHostID());
                if(_host==null) 
                    throw new IllegalArgumentException("임직원정보가 존재하지 않습니다.");
                visitorHistory.setHostByVisiter(_host);
            }
            visitorHistory.setCarryinWareByVisitor(_visiter);
            if(visitorHistory.isEducationCheck(visitorHistoryRepository.findByVisitorIDAndEduCompleteDateTime(visitorHistory))) {
                //방문교육 이수된 신청에 대해서는 담당자에게 승인요청 알림 보냄
            } else {
                // 방문교육 이수전에는 방문객에게 방문교육 이수 알림 보냄
            }
            visitorHistory.makeReservationNumber();
            visitorHistoryRepository.save(visitorHistory);
            visitorDashBoardService.updateVisitApplicationCount(visitorHistory);
            visitorDashBoardService.updateApprovalStandbyCount(visitorHistory);
        }
    }

    private void overlapVisitorIsVaild(VisitorHistory visitorHistory) {
        if(visitorHistoryRepository.findByVisitorIDAndPlanDate(visitorHistory)>0)
            throw new IllegalArgumentException(visitorHistory.getVisitorName()+"님은 중복 신청이력이 존재합니다.");
    }
}