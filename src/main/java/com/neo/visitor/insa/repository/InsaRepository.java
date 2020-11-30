package com.neo.visitor.insa.repository;

import java.util.List;

import com.neo.visitor.config.SecondaryMapperScan;
import com.neo.visitor.domain.user.entity.Host;

@SecondaryMapperScan
public interface InsaRepository {
    List<Host> findAll();
    Host findByEmail(String email);
    List<Host> findByLikeName(String name);
    Host findByHostId(String hostId);
}   
