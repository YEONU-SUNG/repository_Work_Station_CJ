package com.neo.visitor.domain.visitor.service;

import com.neo.visitor.domain.visitor.entity.VisitorHistory;
import com.neo.visitor.domain.visitor.repository.VisitorHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorHistoryService {
    
    @Autowired VisitorHistoryRepository visitorHistoryRepository;

    public VisitorHistory findById(int visitorHistorySeq) {
        VisitorHistory visitorHistory = visitorHistoryRepository.findById(visitorHistorySeq);
        if(visitorHistory==null) throw new IllegalArgumentException("존재하지않는 방문이력");
        return visitorHistory;
    }
}