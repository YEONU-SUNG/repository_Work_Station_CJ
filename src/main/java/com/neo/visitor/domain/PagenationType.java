package com.neo.visitor.domain;

import lombok.Getter;

@Getter
public enum PagenationType {

    PERSONALINFO("/personalinfo-list"),
    NOTICE("/notice-list"),
    FAQ("/faq-list"),
    VISITOR("/visitor-list"),
    VISITOR_HISTORY("/visitor/history-list"),
    VISITOR_APPROVE("/visitor/approve-list"),
    VISITOR_CONFIRM("/visitor/confirm-list"),
    VISITOR_STANDBY("/visitor/standby-list"),
    VISITOR_BLACKLIST("/visitor/black-list"),
    AUTH("/auth-list");

    private String value;

    PagenationType(String value) {
        this.value = value;
    }
}