package com.neo.visitor.domain.visitor.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.visitor.entity.VisitorInoutTime;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface VisitorInoutTimeRepository {
    
    void save(VisitorInoutTime visitorInoutTime);
    VisitorInoutTime findById(int vSeq);
    VisitorInoutTime findByVisitorHistorySeqAndVisitorFromDateTime(int visitorHistorySeq);
    void updateVisitorToDateTime(int visitorHistorySeq);
    void updateVisitorToDateTime(VisitorInoutTime visitorInoutTime);
    void updateVisitorCardID(VisitorInoutTime visitorInoutTime);
    
    int findAllHistoryCount(Pagenation pagenation);

    int findAllHistoryCountType(Map<String, Object> params);
    // 방문이력
    List<VisitorInoutTime> findAllHistory(Pagenation pagenation);

    List<VisitorInoutTime> findAllHistoryType(Map<String, Object> params);
    
    List<VisitorInoutTime> findAllHistoryNotLimit(Pagenation pagenation);

    List<VisitorInoutTime> findAllHistoryNotLimitType(Map<String, Object> params);
}