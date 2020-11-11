package com.neo.visitor.domain.file.entity;

import lombok.Getter;

@Getter
public enum FileType {
    PERSONALINFO("personalinfo"),
    NOTICE("notice"),
    CONTENTS("contents");

    private String value;

    FileType(String value) {
        this.value = value;
    }
}