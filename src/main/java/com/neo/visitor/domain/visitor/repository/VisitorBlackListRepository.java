package com.neo.visitor.domain.visitor.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.visitor.entity.VisitorBlackList;

@PrimaryMapperScan
public interface VisitorBlackListRepository {
    
    void save(VisitorBlackList visitorBlackList);
    void update(VisitorBlackList visitorBlackList);
    VisitorBlackList findByVisitorIdAndDate(Map<String, Object> map);
    List<VisitorBlackList> findAll();
    List<VisitorBlackList> findSearch(Map<String, Object> map);
}