package com.neo.visitor.domain.visitor.service;

import java.util.List;
import java.util.Map;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.entity.VisitorInoutTime;
import com.neo.visitor.domain.visitor.repository.VisitorInoutTimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorInoutTimeService {
    
    @Autowired VisitorDashboardService visitorDashboardService;
    @Autowired VisitorHistoryService visitorHistoryService;
    @Autowired VisitorInoutTimeRepository visitorInoutTimeRepository;

    public int findAllHistoryCount(Pagenation pagenation) {
        return visitorInoutTimeRepository.findAllHistoryCount(pagenation);
    }

    public int findAllHistoryCount(Map<String, Object> map) {
        return visitorInoutTimeRepository.findAllHistoryCountType(map);
    }

    public List<VisitorInoutTime> findAllHistory(Pagenation pagenation) {
        return visitorInoutTimeRepository.findAllHistory(pagenation);
    }

    public List<VisitorInoutTime> findAllHistory(Map<String, Object> map) {
        return visitorInoutTimeRepository.findAllHistoryType(map);
    }

    public List<VisitorInoutTime> findAllHistoryNotLimit(Pagenation pagenation) {
        return visitorInoutTimeRepository.findAllHistoryNotLimit(pagenation);
    }

    public List<VisitorInoutTime> findAllHistoryNotLimit(Map<String, Object> map) {
        return visitorInoutTimeRepository.findAllHistoryNotLimitType(map);
    }

    public VisitorInoutTime findById(int vSeq) {
        VisitorInoutTime visitorInoutTime = visitorInoutTimeRepository.findById(vSeq);
        if(visitorInoutTime==null) throw new IllegalArgumentException("존재하지않는 방문이력");
        return visitorInoutTime;
    }

    public VisitorHistory updateVisitorInout(int visitorHistorySeq, String cardID) {
        VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq);
        visitorHistory.setCardID(cardID);
        visitorHistory.isVaildVisitCard();
        VisitorInoutTime visitorInoutTime = visitorInoutTimeRepository.findByVisitorHistorySeqAndVisitorFromDateTime(visitorHistory.getVisitorHistorySeq());
        if(visitorInoutTime==null) {
            visitorInoutTime = new VisitorInoutTime().makeVisitorIn(visitorHistory.getVisitorHistorySeq());
            visitorInoutTime.setCardID(cardID);
            visitorInoutTimeRepository.save(visitorInoutTime);
            visitorDashboardService.updateVisitPlusCount(visitorHistory);
        } else {
            visitorInoutTime.setCardID(cardID);
            //visitorInoutTimeRepository.updateVisitorToDateTime(visitorInoutTime.getVSeq());
            visitorInoutTimeRepository.updateVisitorToDateTime(visitorInoutTime);
            visitorDashboardService.updateVisitMinusCount(visitorHistory);
        }
        visitorHistory.addVisitorInout(findById(visitorInoutTime.getVSeq()));
        return visitorHistory;
    }

    public VisitorHistory updateVisitorCardID(int visitorHistorySeq, String cardID) {
        VisitorHistory visitorHistory = visitorHistoryService.findById(visitorHistorySeq);
        visitorHistory.setCardID(cardID);
        visitorHistory.isVaildVisitCard();
        VisitorInoutTime visitorInoutTime = visitorInoutTimeRepository.findByVisitorHistorySeqAndVisitorFromDateTime(visitorHistory.getVisitorHistorySeq());
        if(visitorInoutTime==null) {
            throw new IllegalArgumentException("입문 후 수정 가능합니다.");
        } else {
            visitorInoutTime.setCardID(cardID);
            visitorInoutTimeRepository.updateVisitorCardID(visitorInoutTime);
        }
        return visitorHistory;
    }
}