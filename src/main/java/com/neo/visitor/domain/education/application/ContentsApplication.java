package com.neo.visitor.domain.education.application;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.education.entity.Contents;
import com.neo.visitor.domain.education.entity.ContentsType;
import com.neo.visitor.domain.education.service.ContentsService;
import com.neo.visitor.domain.file.entity.FileInfo;
import com.neo.visitor.domain.file.entity.FileType;
import com.neo.visitor.domain.file.repository.FileRepository;
import com.neo.visitor.domain.file.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentsApplication {

    @Autowired FileService fileService;
    @Autowired FileRepository fileRepository;
    @Autowired ContentsService contentsService;

    public List<FileInfo> getContentsUpload(String conditionKey, String conditionValue) {
        return fileRepository.findByContentsID( new Pagenation(0, 0, conditionKey, conditionValue));
    }

    public void conetnsUpload(HttpServletRequest request) {
        fileService.fileUpload(0, FileType.CONTENTS, request);
    }

    public void updateContentsFileDeleteFlag(int[] fileIds) {
        for(int i=0; i< fileIds.length; i++) {
            fileRepository.updateDeleteFalg(fileIds[i]);
        }
    }

    public void setContents(ContentsType contentsType, int fileId) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileId);
        contentsService.save(new Contents().makeContents(contentsType, fileInfo));
    }
    
}