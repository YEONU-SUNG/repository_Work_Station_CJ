package com.neo.visitor.domain.visitor.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.visitor.entity.Visitor;
import com.neo.visitor.domain.visitor.entity.VisitorCompany;
import com.neo.visitor.domain.visitor.entity.WorkSite;

@PrimaryMapperScan
public interface VisitorRepository {
    
    // 리스트 카운트
    int countByDeleteFlag(String DeleteFlag);

    // 리스트
    List<Visitor> findAll(Pagenation pagenation);
    
    List<WorkSite> getWorkSite();
    List<VisitorCompany> getVisitorCompany();

    Visitor findByVisitorNameAndVisitorBirthAndVisitorMobile(Visitor visitor);
    Visitor findByVisitornameAndVisitorCompanyAndVisitorMobile(Visitor visitor);
    void save(Visitor visitor);
}