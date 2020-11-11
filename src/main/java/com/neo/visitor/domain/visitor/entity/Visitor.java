package com.neo.visitor.domain.visitor.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Visitor {

    private int visitorID;
    private String visitorName;
    private String visitorBirth;
    private String mobile;
    private String company;
    private String lastVisitDate;
    private String deleteFlag;

    public Visitor makeVisitor(String visitorName, String visitorBirth, String mobile, String company) {
        this.visitorName = visitorName;
        this.visitorBirth = visitorBirth;
        this.mobile = mobile;
        this.company = company;
        return this;
    }

}