package com.neo.visitor.domain.notice.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.domain.file.entity.FileInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Notice {

    private int NoticeID;
    private String Title;
    private String Contents;
    private String AdminID;
    private String HostName;
    private LocalDateTime VisibleStartDate;
    private LocalDateTime VisibleEndDate;
    private String ActiveFlag;
    private LocalDateTime RegDate;
    private String DeleteFlag;
    private List<FileInfo> files = new ArrayList<>();

    public void setNoticeID(int NoticeID) {
        if(NoticeID==0) new NumberFormatException();
        this.NoticeID = NoticeID;
    }

    public Notice makeNotice(String Title, String Contents) {
        if(Title==null || Title.length()==0) throw new IllegalArgumentException("제목 항목 미입력");
        if(Contents==null || Contents.length()==0) throw new IllegalArgumentException("내용 항목 미입력");
        this.Title = Title;
        this.Contents = Contents;
        this.ActiveFlag= "Y";
        this.RegDate = LocalDateTime.now();
        this.DeleteFlag = "N";
        return this;
    }

    public Notice deleteFlagNotice(int NoticeID, String DeleFlag) {
        this.NoticeID = NoticeID;
        this.DeleteFlag = DeleFlag;
        return this;
    }
    public void updateNoticeActiveFlag() {
        this.ActiveFlag = this.ActiveFlag.equals("Y") ? "N" : "Y";
    }

    public void addFileInfos(List<FileInfo> files) {
        this.files = files;
    }
}