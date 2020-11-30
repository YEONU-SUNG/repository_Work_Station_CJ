package com.neo.visitor.domain.buildingSiteMapping.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.buildingSiteMapping.entity.BuildingSiteMapping;

@PrimaryMapperScan
public interface BuildingSiteMappingRepository {

    List<BuildingSiteMapping> findBySiteCode(String siteCode);
    void save(BuildingSiteMapping buildingSiteMapping);
    void delete(BuildingSiteMapping buildingSiteMapping);
}