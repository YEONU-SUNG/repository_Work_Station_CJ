package com.neo.visitor.domain.buildingSiteMapping.entity;

import lombok.Getter;

@Getter
public class BuildingSiteMapping {
    
    private String siteCode;
    private String siteName;
    private String buildingName;
    private String buildingFloor;

    public BuildingSiteMapping deleteMapping(String siteCode, String buildingName) {
        this.siteCode = siteCode;
        this.buildingName = buildingName;
        return this;
    }

    public BuildingSiteMapping save(String siteCode, String siteName, String buildingName, String buildingFloor) {
        this.siteCode = siteCode;
        this.siteName = siteName;
        this.buildingName = buildingName;
        this.buildingFloor = buildingFloor;
        return this;
    }

    public BuildingSiteMapping delete(String siteCode, String buildingName) {
        this.siteCode = siteCode;
        this.buildingName = buildingName;
        return this;
    }
}