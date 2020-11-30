package com.neo.visitor.domain.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.repository.HostRepository;
import com.neo.visitor.insa.repository.InsaRepository;
import com.neo.visitor.login.LoginAuthApplication;

@Service
public class HostService {

    @Autowired HostRepository hostRepository;
    @Autowired InsaRepository insaRepository;

    public PagenationResponse<Host> findAll(HttpSession session, Pagenation pagenation) {
        PagenationResponse<Host> pagenationResponse = new PagenationResponse<>();
        Map<String, Object> map = new HashMap<>();
        map.put("pagenation", pagenation);
        hostRepository.findAll(map);
        pagenationResponse.setResponse(hostRepository.findAll(map));
        pagenationResponse.setPagenation(pagenation.makePagenation(hostRepository.count(map), PagenationType.AUTH));
        return pagenationResponse;
    }

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
        if(host==null) {
            host = insaRepository.findByHostId(hostID);
            host.setAuth(LoginAuthApplication.ROLE.findByRoleNumber(auth));
            hostRepository.insaSave(host);
        } else {
            host.setAuth(LoginAuthApplication.ROLE.findByRoleNumber(auth));
            hostRepository.updateHostAuth(host);
        }
        return host;
    }   

    public Host updateActiveFlag(String hostID) {
        Host host = findByHostID(hostID);
        host.updateHostActiveFlag();
        hostRepository.updateActiveFlag(host);
        return host;
    }
}