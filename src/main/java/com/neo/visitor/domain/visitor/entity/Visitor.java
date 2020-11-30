package com.neo.visitor.domain.visitor.entity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import com.neo.visitor.config.AES256Util;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Visitor extends Visiter {

    private int visitorID;
    private String visitorName;
    private String visitorGender;
    private String visitorLocation;
    private String visitorBirth;
    private String mobile;
    private String company;
    private String lastVisitDate;
    private String deleteFlag;

    // public Visitor makeVisitor(String visitorName, String visitorBirth, String mobile, String company,
    //         String carryWare, String carryStuff, String carryStuffSerialNo, String carryStuffPurpose, String carryStuffUsed) {
    //     addVisitPurpose(carryWare, carryStuff, carryStuffSerialNo, carryStuffPurpose, carryStuffUsed);
    //     this.visitorName = visitorName;
    //     this.visitorBirth = visitorBirth;
    //     this.mobile = mobile;
    //     this.company = company;
    //     return this;
    // }

    public Visitor makeVisitor(String visitorName, String visitorGender, String visitorLocation, String visitorBirth, String mobile, String company) {
        this.visitorName = visitorName;
        this.visitorGender = visitorGender;
        this.visitorLocation = visitorLocation;
        this.visitorBirth = visitorBirth;
        this.mobile = mobile;
        this.company = company;

        // try {
        //     this.visitorName = AES256Util.encrypt(visitorName);
        //     this.visitorBirth = AES256Util.encrypt(visitorBirth);
        //     this.mobile = AES256Util.encrypt(mobile);
        // } catch (UnsupportedEncodingException | GeneralSecurityException e) {
        //     e.printStackTrace();
        // }
        
        // this.visitorGender = visitorGender;
        // this.visitorLocation = visitorLocation;
        // this.company = company;
        return this;
    }

}