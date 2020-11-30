package com.neo.visitor.domain.visitor.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;
import com.neo.visitor.domain.visitor.repository.VisitorBlackListRepository;
import com.neo.visitor.domain.visitor.repository.VisitorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorBlackListService {
    
    @Autowired VisitorRepository visitorRepository;
    @Autowired VisitorBlackListRepository visitorBlackListRepository;

    public void addBlacklist(Visitor visitor, VisitorBlackList visitorBlackList) {
        visitorBlackList.addVisitor(visitorRepository.findByVisitornameAndVisitorCompanyAndVisitorMobile(visitor));
        visitorBlackListRepository.save(visitorBlackList);
    }

    public void updateBlackList(VisitorBlackList visitorBlackList) {
        visitorBlackListRepository.update(visitorBlackList);
    }

    public List<VisitorBlackList> findAll() {
        return visitorBlackListRepository.findAll(); 
    }


    /**
     * 블랙리스트 여부
     * 방문희망기간에 출입제안이 걸려있는지
     * @param visitor 방문자정보
     * @param planFromDate 방문시작일
     * @param planToDate 방문종료일
     */
    public void isBlackList(Visitor visitor, String planFromDate, String planToDate) {
        // 방문이력이 존재하는지 체크 
        Visitor _visitor = visitorRepository.findByVisitornameAndVisitorCompanyAndVisitorMobile(visitor);
        if(_visitor != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("visitorId", _visitor.getVisitorID());
            map.put("planFromDate", planFromDate);
            map.put("planToDate", planFromDate);
            VisitorBlackList visitorBlackList = visitorBlackListRepository.findByVisitorIdAndDate(map);
            if(visitorBlackList!=null && visitorBlackList.getBlacklistState().equals("출입제한")) {
                throw new IllegalArgumentException(
                    "\r\n"+
                    visitorBlackList.getVisitor().getVisitorName()+"님은\r\n" +
                    "다음과 같은 사유로 인하여 방문 신청이 불가 합니다.\r\n"+
                    "기간 : "+visitorBlackList.getPlanFromDate()+" ~ "+visitorBlackList.getPlanToDate()+"\r\n" +
                    "사유 : "+visitorBlackList.getBlacklistReason()+"\r\n");
            }
        }
    }
}