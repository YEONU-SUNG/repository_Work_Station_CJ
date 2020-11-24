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
     * @param visitor
     * @return visitorBlackList
     */
    public VisitorBlackList isBlackList(Visitor visitor) {
        Visitor _visitor = visitorRepository.findByVisitornameAndVisitorCompanyAndVisitorMobile(visitor);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("visitorId", _visitor.getVisitorID());
        map.put("date", LocalDate.now().toString());
        return visitorBlackListRepository.findByVisitorIdAndDate(map);
    }
}