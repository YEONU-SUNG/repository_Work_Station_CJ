package com.neo.visitor.domain.visitor.entity;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class VisitorBlackList {
    
    private int visitorId;
    private String adminId;
    private String blacklistState;
    private String blacklistReason;
    private String planFromDate;
    private String planToDate;
    private String comment;
    private LocalDateTime modifedDate;
    private LocalDateTime createDate;

    private Visitor visitor;

    public VisitorBlackList makeBlackList(
        String adminId, String blacklistState, String blacklistReason, 
        String blacklistReasonComment, String planFromDate, String planToDate) {
        this.adminId = adminId;
        this.blacklistState = blacklistState;
        this.blacklistReason = blacklistReason;
        this.comment = blacklistReasonComment;
        this.planFromDate = planFromDate;
        this.planToDate = planToDate;
        return this;
    }

    public void addVisitor(Visitor visitor) {
        if(visitor == null) throw new IllegalArgumentException("방문자 정보가 존재하지않습니다.");
        this.visitor = visitor;
        this.visitorId = visitor.getVisitorID();
    }

    public VisitorBlackList updateBlackList(int visitorId, String adminId, String blacklistState, String blacklistReason, String blacklistReasonComment, String planFromDate, String planToDate) {
        this.visitorId = visitorId;
        this.adminId = adminId;
        this.blacklistState = blacklistState;
        this.blacklistReason = blacklistReason;
        this.comment = blacklistReasonComment;
        this.planFromDate = planFromDate;
        this.planToDate = planToDate;
        return this;
    }
}