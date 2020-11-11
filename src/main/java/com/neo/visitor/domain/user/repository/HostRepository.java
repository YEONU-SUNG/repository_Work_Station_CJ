package com.neo.visitor.domain.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.user.entity.Host;

@PrimaryMapperScan
public interface HostRepository {
    
    Host findByHostID(String hostId);
    List<Host> findByHostName(String hostName);
    Host findByHostDeptAndHostName(Host host);
    Host findByHostDeptHead(Host host);
    void updateHostAuth(Host host);
    void updateActiveFlag(Host host);
}
