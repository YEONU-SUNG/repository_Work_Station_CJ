package com.neo.visitor.insa.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.neo.visitor.insa.repository.InsaRepository;
import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.domain.user.repository.HostInsaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InsaScheduler {

    @Autowired HostInsaRepository hostRepository;
    @Autowired InsaRepository insaRepository;

    //@Scheduled(cron = "10 0 0,12 * * *")
    //@Scheduled(cron = "30 * * * * *")
    //@Scheduled(cron = "0 54 23 * * *")
    public void contentsStateSchedulCheckRun() {
        //List<Host> hostList = new ArrayList<>();
        List<Map<String, Object>> insaList = insaRepository.findAll();
        for (Map<String, Object> _data : insaList) {
            hostRepository.insaSave(new Host().insaInterface(_data));
            hostRepository.insaAdminSave(new AdminUser().insaInterface(_data));
            //hostList.add(new Host().insaInterface(_data));
        }

        //List<Map<String, Object>> positionList = insaRepository.getInsa();
        // 
        // 부서정보 가공하고
        /*List<Map<String, Object>> test = new ArrayList<>();

        hostRepository.save(new Host().insaInterface(_test));

        for (Map<String, Object> _test : test) {
            hostRepository.save(new Host().insaInterface(_test));
        }      */ 
    }
}
