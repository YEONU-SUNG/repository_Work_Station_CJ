package com.neo.visitor.domain.education.entity;

import com.neo.visitor.domain.file.entity.FileInfo;

import lombok.Getter;

@Getter
public class Contents {
    
    private int id;
    private String type;
    private int sort;
    private FileInfo fileInfo;

    public Contents addContentsType(ContentsType contentsType) {
        this.type = contentsType.getValue();
        return this;
    }

    public Contents makeContents(ContentsType contentsType, FileInfo fileInfo) {
        this.type = contentsType.getValue();
        this.fileInfo = fileInfo;
        return this;
    }

    public Contents updateSort(int id, int sort) {
        this.id = id;
        this.sort = sort;
        return this;
    }
}