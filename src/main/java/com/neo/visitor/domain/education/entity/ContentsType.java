package com.neo.visitor.domain.education.entity;

import lombok.Getter;

@Getter
public enum ContentsType {
    
    SAFE("safe"),
    SECURITY("security");

    private String value;

    ContentsType(String value) {
        this.value = value;
    }
}