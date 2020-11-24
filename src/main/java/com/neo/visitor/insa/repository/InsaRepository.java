package com.neo.visitor.insa.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.SecondaryMapperScan;

@SecondaryMapperScan
public interface InsaRepository {
    public List<Map<String, Object>> findAll();
    public Map<String, Object> findByEmail(String email);
    public List<Map<String, Object>> findByName(String name);
}   
