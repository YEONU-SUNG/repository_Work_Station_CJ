package com.neo.visitor.domain.visitor.application;

import com.neo.visitor.domain.visitor.entity.MailSchedule;
import com.neo.visitor.domain.visitor.repository.MailScheduleRepository;
import com.neo.visitor.util.MailSenderUtil;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailScheduler {

    @Autowired MailScheduleRepository mailScheduleRepository;

    @Autowired MailSenderUtil mailSenderUtil;

    @Scheduled(cron = "0 0 8 * * *")
    //@Scheduled(cron = "30 * * * * *")
    public void contentsStateSchedulCheckRun() {
        
        List<MailSchedule> schMails = mailScheduleRepository.getTargetEmail();
        for(int i=0; i < schMails.size(); i++) 
        {
            MailSchedule schMail = schMails.get(i);
            List<MailSchedule> EachSendMails = mailScheduleRepository.getSendEmail(schMail);

            String SendText = "";
            for(int j=0; j < EachSendMails.size(); j++) 
            {  
                MailSchedule eachSendMail = EachSendMails.get(j);

                SendText += eachSendMail.getSendText() +"\r\n\r\n";  
            }

            if(!SendText.equals(""))
            {
                //String SendText1 = "";
                mailSenderUtil.sendMail("visit@sfa.co.kr",EachSendMails.get(0).getTargetEmail(),"방문예약시스템 방문승인",SendText,"");
            }
                
        }
        // VisitorDashboard position1 = new VisitorDashboard().makeDashBoard(POSITION1);
        // position1.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position1));
        // visitorDashboardRepository.save(position1);
        
        // VisitorDashboard position2 = new VisitorDashboard().makeDashBoard(POSITION2);
        // position2.addDefaultVisitApplicationCount(visitorHistoryRepository.countByPlanFromDateTime(position2));
        // visitorDashboardRepository.save(position2);
    }
}
