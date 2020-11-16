package com.neo.visitor.domain.visitor.entity;

import lombok.Getter;

/**
 * 방문자 반입물품
 * (by host or visitor)
 */
@Getter
public class Visiter {
    
    private String carryStuffWare;
    private String carryStuffSerialNo;
    private String carryStuffPurpose;
    private String carryStuffUsed;

    public void setCarryInWareByVisiter(String carryStuffWare, String carryStuffSerialNo, String carryStuffPurpose, String carryStuffUsed) {
        this.carryStuffWare = carryStuffWare;
        this.carryStuffSerialNo = carryStuffSerialNo;
        this.carryStuffPurpose = carryStuffPurpose;
        this.carryStuffUsed = carryStuffUsed;
    }
}