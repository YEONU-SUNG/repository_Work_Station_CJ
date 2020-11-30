package com.neo.visitor.domain.buildingSiteMapping.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.buildingSiteMapping.entity.Building;

@PrimaryMapperScan
public interface BuildingRepository {
    
    List<Building> findAll();
    List<Building> findByName(String name);
}