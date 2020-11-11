package com.neo.visitor.domain.notice.repository;

import java.util.List;

import com.neo.visitor.domain.notice.entity.Notice;
import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface NoticeRepository {

    // 리스트 카운트
    int countByDeleteFlag(Pagenation pagenation);

    // 리스트
    List<Notice> findAll(Pagenation pagenation);

    // 상세보기
    Notice findById(int NoticeID);

    // 삭제
    int updateDeleteFlag(List<Notice> notices);

    // 상태변경
    int updateActiveFlag(Notice notice);

    // 등록
    void save(Notice notice);

    void updateNotice(Notice notice);
}