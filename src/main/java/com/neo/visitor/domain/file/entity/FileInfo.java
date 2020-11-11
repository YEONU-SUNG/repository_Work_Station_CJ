package com.neo.visitor.domain.file.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileInfo {

    private int FileId;
    private int targetId;
    private String targetType;
    private String saveFileName;
    private String saveFilePath;
    private String fileName;
    private boolean deleteFlag;
    private LocalDateTime modifyDate;
    private LocalDateTime createDate;

    public FileInfo makeFileSaveInfo(String saveFileName, String saveFilePath, String fileName, int targetId, String targetType) {
        this.saveFileName = saveFileName;
        this.saveFilePath = saveFilePath;
        this.fileName = fileName;
        this.targetId = targetId;
        this.targetType = targetType;
        this.deleteFlag = false;
        return this;
    }

    public FileInfo targetInfo(FileType fileType, int targetId) {
        this.targetType = fileType.getValue();
        this.targetId = targetId;
        return this;
    }
}