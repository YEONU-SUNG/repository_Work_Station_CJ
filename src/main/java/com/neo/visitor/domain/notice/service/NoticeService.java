package com.neo.visitor.domain.notice.service;

import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.domain.notice.entity.Notice;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.notice.repository.NoticeRepository;
import com.neo.visitor.domain.file.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired NoticeRepository noticeRepository;
    @Autowired FileService fileService;

    public PagenationResponse<Notice> findAll(Pagenation pagenation) {
        PagenationResponse<Notice> pagenationResponse = new PagenationResponse<>();
        pagenationResponse.setResponse(noticeRepository.findAll(pagenation));
        pagenationResponse.setPagenation(pagenation.makePagenation(noticeRepository.countByDeleteFlag(pagenation), PagenationType.NOTICE));
        return pagenationResponse;
    }

    public Notice findById(int NoticeID) {
        return noticeRepository.findById(NoticeID);
    }

    public void updateNotice(Notice notice) {
        noticeRepository.updateNotice(notice);
    }

    public Notice updateNoticeActiveFlag(int NoticeID) {
        Notice notice = findById(NoticeID);
        notice.updateNoticeActiveFlag();
        noticeRepository.updateActiveFlag(notice);
        return notice;
    }

    public void updateNoticesDeleteFlag(int[] NoticeIDs) {
        List<Notice> notices = new ArrayList<>();
        for (int NoticeID : NoticeIDs) {
            notices.add(new Notice().deleteFlagNotice(NoticeID, "Y"));
        }
        noticeRepository.updateDeleteFlag(notices);
    }

    public Notice save(Notice notice) {
        noticeRepository.save(notice);
        return notice;
    }
}