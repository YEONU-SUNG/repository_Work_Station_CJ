package com.neo.visitor.domain.user.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.user.entity.Host;

@PrimaryMapperScan
public interface HostRepository {
    
    int count(Map<String, Object> map);
    List<Host> findAll(Map<String, Object> map);
    Host findByHostID(String hostId);
    List<Host> findByHostName(String hostName);
    Host findByHostDeptAndHostName(Host host);
    Host findByHostDeptHead(Host host);
    void updateHostAuth(Host host);
    void insaSave(Host host);
    void updateActiveFlag(Host host);
}
