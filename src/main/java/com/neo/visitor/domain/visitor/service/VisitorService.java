package com.neo.visitor.domain.visitor.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.user.service.HostService;
import com.neo.visitor.domain.user.service.LoginService;
import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorCompany;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.WorkSite;
import com.neo.visitor.domain.visitor.entity.MailSchedule;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;
import com.neo.visitor.domain.visitor.repository.VisitorRepository;
import com.neo.visitor.domain.visitor.repository.MailScheduleRepository;
import com.neo.visitor.sms.entity.SMSMsgQue;
import com.neo.visitor.util.MailSenderUtil;
import com.neo.visitor.sms.repository.SMSRepository;
import com.neo.visitor.domain.user.entity.Host;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    @Autowired VisitorRepository visitorRepository;
    @Autowired VisitorHistoryRepository visitorHistoryRepository;
    @Autowired VisitorDashboardService visitorDashboardService;
    @Autowired VisitorHistoryService visitorHistoryService;
    @Autowired MailScheduleRepository mailScheduleRepository;

    @Autowired MailSenderUtil mailSenderUtil;
    @Autowired SMSRepository smsRepository;
    @Autowired HostService hostService;
    @Autowired LoginService loginService;

    // public VisitorDashBoard getDashBoard(LocalDateTime localDateTime) {
    //     return new VisitorDashBoard().makeDashBoard(visitorHistoryRepository.dashBoard(new VisitorDashBoard().setDate(LocalDateTime.now())));
    // }

    public int countByDeleteFlag(Map<String, Object> map) {
        return visitorHistoryRepository.countByDeleteFlag(map);
    }

    public List<Visitor> findAll(Pagenation pagenation) {
        return visitorRepository.findAll(pagenation);
    }

    public List<VisitorHistory> findByPlanDateTime(Map<String, Object> map) {
        //List<VisitorHistory> temp = visitorHistoryRepository.findByPlanDateTime(map);
        return visitorHistoryRepository.findByPlanDateTime(map);
        
    }

    public void updateCardNo(int[] visitorHistorySeq, String[] cardID) {
        try {
            for(int i = 0; i < visitorHistorySeq.length; i++)
                visitorHistoryRepository.updateCardID(new VisitorHistory().updateCardID(visitorHistorySeq[i], cardID[i]));
        } catch (IndexOutOfBoundsException e) { 
            e.printStackTrace();
        }
    }

    public void updateCardNoOne(int visitorHistorySeq, String cardID) {
        try {
                visitorHistoryRepository.updateCardID(new VisitorHistory().updateCardID(visitorHistorySeq, cardID));
        } catch (IndexOutOfBoundsException e) { 
            e.printStackTrace();
        }
    }
    
    public VisitorHistory updateVisitorApproval(HttpServletRequest request, int visitorHistorySeq, String visitApprovalComment, String carryStuff) {
        VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq);
        visitorHistory.updateApproval(visitApprovalComment, carryStuff);
        visitorHistoryRepository.updateVisitorApproval(visitorHistory);
        visitorDashboardService.updateVisitStandbyCount(visitorHistory);

        String strVisitorSendText="SFA방문예약 - ";
        //String strHostHeadSendText="SFA방문예약 - ";
        String strHostHeadSendText="SFA방문예약이 승인되었습니다.";
        // SMSMsgQue smsMsgque = new SMSMsgQue();
        // //smsMsgque.setCallback("041-520-1004");
        // smsMsgque.setCallback("041-539-6741");
        // strVisitorSendText += visitorHistory.getVisitorName()+"님 방문신청이 승인되었습니다.\r\n방문기간("+ visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")";
        // smsMsgque.setDstaddr(visitorHistory.getVisitorMobile());
        // smsMsgque.setText(strVisitorSendText);
        // smsRepository.sendSMS(smsMsgque);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dayS = null;
        Date dayC = new Date();
        try {
            dayS = dateFormat.parse(visitorHistory.getPlanFromDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //가오픈시에는 담당자에게 메일 안가도록 임시 주석
        // Host host = hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName());
        // if(host!=null && !host.getPositionName().equals("팀장") && !host.getPositionName().equals("그룹장"))
        // {
        //     Host hosthead = hostService.findByHostDeptHead(host);
        //     if(hosthead!=null)
        //     {
        //         // strHostHeadSendText += visitorHistory.getVisitorName()+ "님(" + visitorHistory.getVisitorCompany() +")의 방문신청을 승인하였습니다.\r\n방문기간("
        //         // + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")" 
        //         // + "\r\n방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
        //         // + "\r\n방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
        //         // + "\r\n승인자 : " + loginService.getUserSessionInfo(request).getHost().getHostName();
        //         // mailSenderUtil.sendMail("visit@sfa.co.kr",hosthead.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
        //         // //mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");

        //         strHostHeadSendText += "\r\n- 성명 : " + visitorHistory.getVisitorName()
        //         + "\r\n- 회사 : " + visitorHistory.getVisitorCompany()
        //         + "\r\n- 방문기간 : " + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime()
        //         + "\r\n- 방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
        //         + "\r\n- 방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
        //         + "\r\n- 승인자 : " + loginService.getUserSessionInfo(request).getHost().getHostName();

        //         if(dayS.compareTo(dayC) <= 0) //방문시작시간이 현재 날짜보다 작거나 같을때만 바로 승인 알림 보냄
        //         {
        //             mailSenderUtil.sendMail("visit@sfa.co.kr",hosthead.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
        //         }
        //         else
        //         {
        //             MailSchedule schMail = new MailSchedule();
        //             LocalDateTime dt = LocalDateTime.parse(visitorHistory.getVisitApprovalDateTime());
        //             //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.forLanguageTag("ru"));
        //             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        //             //System.out.println(dt.format(formatter));
        //             strHostHeadSendText += "\r\n- 승인일시 : " + dt.format(formatter);

        //             schMail.setTargetEmail(hosthead.getEmail());
        //             schMail.setSendText(strHostHeadSendText);
        //             schMail.setVisitApprovalDateTime(dt.format(formatter));
        //             mailScheduleRepository.save(schMail);
        //         }
        //     }
        // }
        
        
        return visitorHistory;
    }

    public void updateVisitorApprovalAll(HttpServletRequest request, int[] visitorHistorySeq, String visitApprovalComment, String carryStuff) {

        try {
            for(int i = 0; i < visitorHistorySeq.length; i++)
            {
                VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq[i]);
                visitorHistory.updateApproval(visitApprovalComment, carryStuff);
                visitorHistoryRepository.updateVisitorApproval(visitorHistory);
                visitorDashboardService.updateVisitStandbyCount(visitorHistory);

                String strVisitorSendText="SFA방문예약 - ";
                //String strHostHeadSendText="SFA방문예약 - ";
                String strHostHeadSendText="SFA방문예약이 승인되었습니다.";
                // SMSMsgQue smsMsgque = new SMSMsgQue();
                // //smsMsgque.setCallback("041-520-1004");
                // smsMsgque.setCallback("041-539-6741");
                // strVisitorSendText += visitorHistory.getVisitorName()+"님 방문신청이 승인되었습니다.\r\n방문기간("+ visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")";
                // smsMsgque.setDstaddr(visitorHistory.getVisitorMobile());
                // smsMsgque.setText(strVisitorSendText);
                // smsRepository.sendSMS(smsMsgque);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dayS = null;
                Date dayC = new Date();
                try {
                    dayS = dateFormat.parse(visitorHistory.getPlanFromDateTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //가오픈시에는 담당자에게 메일 안가도록 임시 주석
                // Host host = hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName());
                // if(host!=null && !host.getPositionName().equals("팀장") && !host.getPositionName().equals("그룹장"))
                // {
                //     Host hosthead = hostService.findByHostDeptHead(host);
                //     if(hosthead!=null)
                //     {
                //         // strHostHeadSendText += visitorHistory.getVisitorName()+ "님(" + visitorHistory.getVisitorCompany() +")의 방문신청을 승인하였습니다.\r\n방문기간("
                //         // + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")" 
                //         // + "\r\n방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
                //         // + "\r\n방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
                //         // + "\r\n승인자 : " + loginService.getUserSessionInfo(request).getHost().getHostName();
                //         // mailSenderUtil.sendMail("visit@sfa.co.kr",hosthead.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
                //         strHostHeadSendText += "\r\n- 성명 : " + visitorHistory.getVisitorName()
                //         + "\r\n- 회사 : " + visitorHistory.getVisitorCompany()
                //         + "\r\n- 방문기간 : " + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime()
                //         + "\r\n- 방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
                //         + "\r\n- 방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
                //         + "\r\n- 승인자 : " + loginService.getUserSessionInfo(request).getHost().getHostName();

                //         if(dayS.compareTo(dayC) <= 0) //방문시작시간이 현재 날짜보다 작거나 같을때만 바로 승인 알림 보냄
                //         {
                //             mailSenderUtil.sendMail("visit@sfa.co.kr",hosthead.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
                //         }
                //         else
                //         {
                //             MailSchedule schMail = new MailSchedule();
                //             LocalDateTime dt = LocalDateTime.parse(visitorHistory.getVisitApprovalDateTime());
                //             //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.forLanguageTag("ru"));
                //             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
                //             //System.out.println(dt.format(formatter));
                //             strHostHeadSendText += "\r\n- 승인일시 : " + dt.format(formatter);

                //             schMail.setTargetEmail(hosthead.getEmail());
                //             schMail.setSendText(strHostHeadSendText);
                //             schMail.setVisitApprovalDateTime(dt.format(formatter));
                //             mailScheduleRepository.save(schMail);
                //         }
                //     }
                // }
                
            }
        } catch (IndexOutOfBoundsException e) { 
            e.printStackTrace();
        }
        
        //return visitorHistory;
    }

    public VisitorHistory updateVisitorApprovalReject(HttpServletRequest request, int visitorHistorySeq, String rejectComment) {
        VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq);
        visitorHistory.updateReject(rejectComment);
        visitorHistoryRepository.updateVisitorApprovalReject(visitorHistory);
        // visitorDashboardService.updateVisitStandbyCount(visitorHistory);

        // String strVisitorSendText="SFA방문예약 - ";
        // String strHostHeadSendText="SFA방문예약 - ";
        // SMSMsgQue smsMsgque = new SMSMsgQue();
        // smsMsgque.setCallback("041-520-1004");
        // strVisitorSendText += visitorHistory.getVisitorName()+"님 방문신청이 승인되었습니다.\r\n방문기간("+ visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")";
        // smsMsgque.setDstaddr(visitorHistory.getVisitorMobile());
        // smsMsgque.setText(strVisitorSendText);
        // //smsRepository.sendSMS(smsMsgque);

        // //가오픈시에는 담당자에게 메일 안가도록 임시 주석
        // Host host = hostService.findByHostDeptAndHostName(visitorHistory.getHostDept(), visitorHistory.getHostName());
        // if(host!=null && !host.getPositionName().equals("팀장") && !host.getPositionName().equals("그룹장"))
        // {
        //     Host hosthead = hostService.findByHostDeptHead(host);
        //     if(hosthead!=null)
        //     {
        //         strHostHeadSendText += visitorHistory.getVisitorName()+ "님(" + visitorHistory.getVisitorCompany() +")의 방문신청을 승인하였습니다.\r\n방문기간("
        //         + visitorHistory.getPlanFromDateTime() +" ~ "+ visitorHistory.getPlanToDateTime() +")" 
        //         + "\r\n방문목적 : " + visitorHistory.getVisitPurpose() + "("+ visitorHistory.getVisitPurposeDetail() +")"
		// 	    + "\r\n방문위치 : " + visitorHistory.getVisitorPosition1() + "_" + visitorHistory.getVisitorPosition2()
        //         + "\r\n승인자 : " + loginService.getUserSessionInfo(request).getHost().getHostName();
        //         mailSenderUtil.sendMail("visit@sfa.co.kr",hosthead.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
        //         //mailSenderUtil.sendMail("visit@sfa.co.kr",host.getEmail(),"방문예약시스템 방문승인",strHostHeadSendText,"");
        //     }
        // }
        
        return visitorHistory;
    }

    public void updateVisitorApprovalAllReject(HttpServletRequest request, int[] visitorHistorySeq, String visitApprovalComment, String carryStuff) {

        try {
            for(int i = 0; i < visitorHistorySeq.length; i++)
            {
                VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq[i]);
                visitorHistoryRepository.updateVisitorApprovalReject(visitorHistory);
                //visitorHistory.updateApproval(visitApprovalComment, carryStuff);
                // visitorHistoryRepository.updateVisitorApproval(visitorHistory);
                // visitorDashboardService.updateVisitStandbyCount(visitorHistory);
            }
        } catch (IndexOutOfBoundsException e) { 
            e.printStackTrace();
        }
        
        //return visitorHistory;
    }

    public List<WorkSite> getWorkSite() {
    	return visitorRepository.getWorkSite();
    }

    public List<VisitorCompany> getVisitorCompany() {
    	return visitorRepository.getVisitorCompany();
    }

    public Visitor findByVisitorNameAndVisitorBirthAndVisitorMobile(Visitor visitor) {
        return visitorRepository.findByVisitorNameAndVisitorBirthAndVisitorMobile(visitor);
    }

    public Visitor save(Visitor visitor) {
        Visitor resultVisitor = findByVisitorNameAndVisitorBirthAndVisitorMobile(visitor);
        if(resultVisitor==null) {
            visitorRepository.save(visitor);
            return visitor;
        } else {
            return resultVisitor;
        }
    }
}