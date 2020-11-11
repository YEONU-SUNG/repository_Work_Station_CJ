package com.neo.visitor.domain.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.repository.HostRepository;

@Service
public class HostService {

    @Autowired HostRepository hostRepository;

    public Host findByHostID(String hostID) {
        return hostRepository.findByHostID(hostID);
    }
    
    public List<Host> findByHostName(String hostName) {
        return hostRepository.findByHostName(hostName);
    }

    public Host findByHostDeptAndHostName(String deptCD, String hostName) {
        Host host = hostRepository.findByHostDeptAndHostName(new Host().findHost(deptCD, hostName));
        if(host==null) throw new IllegalArgumentException("존재하지않는 호스트아이디");

        return host;
    }

    public Host findByHostDeptHead(Host host) {
        Host hosthead = hostRepository.findByHostDeptHead(host);
        return hosthead;
    }

    public Host updateHostAuth(String hostID, String auth) {
        Host host = findByHostID(hostID);
        if(host==null) throw new IllegalArgumentException("존재하지않는 호스트아이디");
        
        host.updateHostAuth(auth);
        hostRepository.updateHostAuth(host);
        return host;
    }   

    public Host updateActiveFlag(String hostID) {
        Host host = findByHostID(hostID);
        host.updateHostActiveFlag();
        hostRepository.updateActiveFlag(host);
        return host;
    }
}