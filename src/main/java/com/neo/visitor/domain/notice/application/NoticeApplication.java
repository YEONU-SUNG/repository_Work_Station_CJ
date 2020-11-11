package com.neo.visitor.domain.notice.application;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.file.entity.FileInfo;
import com.neo.visitor.domain.file.entity.FileType;
import com.neo.visitor.domain.file.repository.FileRepository;
import com.neo.visitor.domain.file.service.FileService;
import com.neo.visitor.domain.notice.entity.Notice;
import com.neo.visitor.domain.notice.service.NoticeService;
import com.neo.visitor.domain.user.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeApplication {

    @Autowired LoginService loginService;
    @Autowired NoticeService noticeService;
    @Autowired FileService fileService;
    @Autowired FileRepository fileRepository;

    public Notice noticeIntert(Notice notice, HttpServletRequest request) {
        notice.setAdminID(loginService.getUserSessionInfo(request).getAdminID());
        try {
            notice.setNoticeID(Integer.parseInt(request.getParameter("noticeId")));
            noticeService.updateNotice(notice);
            if(request.getParameter("deleteFiles")!=null)
                fileRepository.updateDeleteFalg(Integer.parseInt(request.getParameter("deleteFiles")));
        } catch (NullPointerException | NumberFormatException e) {
            noticeService.save(notice);
        }
        fileService.fileUpload(notice.getNoticeID(), FileType.NOTICE, request);
        return notice;
    }
 
    public Notice noticeDetail(int NoticeID) {
        Notice notice = noticeService.findById(NoticeID);
        
        List<FileInfo> fileInfos = fileRepository.findByNoticeID(new FileInfo().targetInfo(FileType.NOTICE, notice.getNoticeID()));

        notice.addFileInfos(fileInfos);

        return notice;
    }
    
} 