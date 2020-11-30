package com.neo.visitor.insa.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.SecondaryMapperScan;

@SecondaryMapperScan
public interface InsaRepository {
    List<Map<String, Object>> findAll();
    Map<String, Object> findByEmail(String email);
    List<Map<String, Object>> findByName(String name);
    Map<String, Object> findByHostId(String hostId);
}   
