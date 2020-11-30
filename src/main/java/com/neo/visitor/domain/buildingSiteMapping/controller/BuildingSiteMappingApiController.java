package com.neo.visitor.domain.buildingSiteMapping.controller;

import java.util.List;

import com.neo.visitor.domain.buildingSiteMapping.entity.Building;
import com.neo.visitor.domain.buildingSiteMapping.entity.BuildingSiteMapping;
import com.neo.visitor.domain.buildingSiteMapping.entity.Site;
import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingRepository;
import com.neo.visitor.domain.buildingSiteMapping.repository.BuildingSiteMappingRepository;
import com.neo.visitor.domain.buildingSiteMapping.repository.SiteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingSiteMappingApiController {

    @Autowired SiteRepository siteRepository;
    @Autowired BuildingRepository buildingRepository;
    @Autowired BuildingSiteMappingRepository buildingSiteMappingRepository;
    
    @GetMapping(value = "site")
	public List<Site> getSite() {
		return siteRepository.findAll();
    }

    @GetMapping(value = "site/{code}")
	public List<Building> getSiteMappingBuilding(@PathVariable String code) {
        return new Building().distinctBuildingNameAndFloorAddComma(buildingSiteMappingRepository.findBySiteCode(code));
    }

    @PostMapping(value = "site/{code}")
    public void setSiteMappingBuilding(@PathVariable String code, @RequestParam("buildingName") String buildingName, @RequestParam("buildingFloor") String[] floor) {
        Site site = siteRepository.findByCode(code);
        if(site==null) throw new IllegalArgumentException("존재하지않는 계열사 정보입니다.");

        for (String _floor : floor) {
            buildingSiteMappingRepository.save(new BuildingSiteMapping().save(site.getCode(), site.getName(), buildingName, _floor));
        }
    }

    @PostMapping(value = "site/{code}/building-floor")
    public void updateSiteMappingBuildingFloor(@PathVariable String code, @RequestParam("buildingName") String buildingName, @RequestParam("buildingFloor") String[] floor) {
        Site site = siteRepository.findByCode(code);
        if(site==null) throw new IllegalArgumentException("존재하지않는 계열사 정보입니다.");
        buildingSiteMappingRepository.delete(new BuildingSiteMapping().delete(site.getCode(), buildingName));

        for (String _floor : floor) {
            buildingSiteMappingRepository.save(new BuildingSiteMapping().save(site.getCode(), site.getName(), buildingName, _floor));
        }
    }

    @PostMapping(value = "site/{code}/delete-mapping")
    public void deleteSiteMappingBuilding(@PathVariable String code, @RequestParam("buildingName") String buildingName) {
        buildingSiteMappingRepository.delete(new BuildingSiteMapping().deleteMapping(code, buildingName));
    }

    @GetMapping(value = "building")
    public List<Building> getBuilding(@RequestParam(name = "buildingName", defaultValue = "") String buildingName) {
        if(buildingName.equals("")) {
            return new Building().distinctBuildingNameAndFloorAddComma2(buildingRepository.findAll());
        }
        return buildingRepository.findByName(buildingName);
    }
}