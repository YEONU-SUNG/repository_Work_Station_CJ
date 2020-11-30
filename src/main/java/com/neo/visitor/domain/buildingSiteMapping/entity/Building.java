package com.neo.visitor.domain.buildingSiteMapping.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class Building {
    
    private String name;
    private String floor;

    public Building makeBuilding(String name, String floor) {
        this.name = name;
        this.floor = floor;
        return this;
    }

    
    
    /**
     * 건물명 중복제거 및 층에 , 추가
     * @param buildingSiteMappings
     * @return
     */
    public List<Building> distinctBuildingNameAndFloorAddComma(List<BuildingSiteMapping> buildingSiteMappings) {
        List<Building> buildings = new ArrayList<>();
        List<String> _names = buildingSiteMappings.stream().map(BuildingSiteMapping::getBuildingName).distinct().collect(Collectors.toList());
        for (String _name : _names) {
            String commaFloor = "";
            for(int i=0; i<buildingSiteMappings.size(); i++) {
                if(_name.equals(buildingSiteMappings.get(i).getBuildingName())) {
                    commaFloor += buildingSiteMappings.get(i).getBuildingFloor()+", ";
                }
            }
            buildings.add(new Building().makeBuilding(_name, commaFloor.substring(0, commaFloor.length()-2)));
        }
        return buildings;
    }

    public List<Building> distinctBuildingNameAndFloorAddComma2(List<Building> buildings) {
        List<Building> _buildings = new ArrayList<>();
        List<String> _names = buildings.stream().map(Building::getName).distinct().collect(Collectors.toList());
        for (String _name : _names) {
            String commaFloor = "";
            for(int i=0; i<buildings.size(); i++) {
                if(_name.equals(buildings.get(i).getName())) {
                    commaFloor += buildings.get(i).getFloor()+", ";
                }
            }
            _buildings.add(new Building().makeBuilding(_name, commaFloor.substring(0, commaFloor.length()-2)));
        }
        return _buildings;
    }


}