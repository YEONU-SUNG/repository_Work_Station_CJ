package com.neo.visitor.domain.buildingSiteMapping.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.buildingSiteMapping.entity.Site;

@PrimaryMapperScan
public interface SiteRepository {
    
    List<Site> findAll();
    Site findByCode(String code);
}