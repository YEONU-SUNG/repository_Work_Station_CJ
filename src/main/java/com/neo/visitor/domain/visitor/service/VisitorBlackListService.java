package com.neo.visitor.domain.visitor.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;
import com.neo.visitor.domain.visitor.repository.VisitorBlackListRepository;
import com.neo.visitor.domain.visitor.repository.VisitorRepository;
import com.neo.visitor.domain.visitor.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.visitor.config.AES256Util;

@Service
public class VisitorBlackListService {
    
    @Autowired VisitorRepository visitorRepository;
    @Autowired VisitorBlackListRepository visitorBlackListRepository;
    @Autowired VisitorService visitorService;

    public void addBlacklist(Visitor visitor, VisitorBlackList visitorBlackList) {
        visitorService.save(visitor); //방문객에 저장 안되있으면 저장하여 블랙리스트 등록
        visitorBlackList.addVisitor(visitorRepository.findByVisitornameAndVisitorCompanyAndVisitorMobile(visitor));
        //visitorBlackList.addVisitor(visitorRepository.findByVisitornameAndVisitorBirthAndVisitorCompanyAndVisitorMobile(visitor));
        visitorBlackListRepository.save(visitorBlackList);
    }

    public void updateBlackList(VisitorBlackList visitorBlackList) {
        visitorBlackListRepository.update(visitorBlackList);
    }

    public List<VisitorBlackList> findAll() {
        return visitorBlackListRepository.findAll(); 
    }

    public List<VisitorBlackList> findSearch(Map<String, Object> map) {
        return visitorBlackListRepository.findSearch(map); 
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
        //Visitor _visitor = visitorRepository.findByVisitornameAndVisitorBirthAndVisitorCompanyAndVisitorMobile(visitor);
        if(_visitor != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("visitorId", _visitor.getVisitorID());
            map.put("planFromDate", planFromDate);
            map.put("planToDate", planFromDate);
            VisitorBlackList visitorBlackList = visitorBlackListRepository.findByVisitorIdAndDate(map);
            if(visitorBlackList!=null && visitorBlackList.getBlacklistState().equals("출입제한")) {
                String strVisitorName = "";
                try{
                    strVisitorName = AES256Util.decrypt(visitorBlackList.getVisitor().getVisitorName());
                }catch(Exception e){
                }
                throw new IllegalArgumentException(
                    "\r\n"+
                    //visitorBlackList.getVisitor().getVisitorName()+"님은\r\n" +
                    strVisitorName+"님은\r\n" +
                    "다음과 같은 사유로 인하여 방문 신청이 불가 합니다.\r\n"+
                    "기간 : "+visitorBlackList.getPlanFromDate()+" ~ "+visitorBlackList.getPlanToDate()+"\r\n" +
                    "사유 : "+visitorBlackList.getBlacklistReason()+"\r\n");
            }
        }
    }
}