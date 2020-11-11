package com.neo.visitor.domain.visitor.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VisitorInoutTime {

    private int vSeq;
    private int visitorHistorySeq;
    private String visitFromDateTime;
    private String visitToDateTime;
    private String cardID;

    private VisitorHistory visitorHistory;

    public VisitorInoutTime makeVisitorIn(int visitorHistorySeq) {
        this.visitorHistorySeq = visitorHistorySeq;
        return this;
    }
    
}