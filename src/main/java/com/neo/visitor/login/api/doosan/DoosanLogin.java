package com.neo.visitor.login.api.doosan;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.neo.visitor.login.LoginAuthApplication;
import com.neo.visitor.domain.buildingSiteMapping.entity.Building;
import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingRepository;
import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingSiteMappingRepository;
import com.neo.visitor.domain.user.entity.Host;
import com.neo.visitor.insa.repository.InsaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoosanLogin extends LoginAuthApplication {

    @Autowired
    private InsaRepository insaRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingSiteMappingRepository buildingSiteMappingRepository;
    
    @Override
    public String checkAuthorityAD(String id, String password) {
        try {
            DoosanADResponse doosanADResponse = DoosanLoginApiConnect.postRequest(id, password);
            if(doosanADResponse==null)
                throw new IllegalArgumentException("로그인정보가 존재하지않습니다.");
            
            return doosanADResponse.getUser().getEmail();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("관리자에게 문의해주세요.");
        }
    }

    @Override
    public Host isEmptyOrganizationChart(String id) {
        Host host = insaRepository.findByEmail("singhal.anju@doosan.com");
        if(host == null) throw new IllegalArgumentException("로그인정보가 존재하지않습니다.");
        return host;
    }

    @Override
    public void setMappingSite(Host host) {
        List<Building> buildings = null;
        if(host.getAuth().equals("0")) {
            // 마스터인경우 모든 건물의 층 권한 부여 가능
            buildings = new Building().distinctBuildingNameAndFloorAddComma2(buildingRepository.findAll());
        } else {
            Host _host = insaRepository.findByHostId(host.getHostID());
            if(_host == null || _host.getSiteCode()==null) throw new IllegalArgumentException("로그인정보가 존재하지않습니다.");
            buildings = new Building().distinctBuildingNameAndFloorAddComma(buildingSiteMappingRepository.findBySiteCode(_host.getSiteCode()));
        }
        host.setMappingBuildings(buildings);
    }
    
}