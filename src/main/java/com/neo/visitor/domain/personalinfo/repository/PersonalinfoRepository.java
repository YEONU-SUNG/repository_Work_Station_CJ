package com.neo.visitor.domain.personalinfo.repository;

import java.util.List;

import com.neo.visitor.domain.personalinfo.entity.Personalinfo;
import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface PersonalinfoRepository {

    // 리스트 카운트
    int countByDeleteFlag(Pagenation pagenation);

    // 리스트
    List<Personalinfo> findAll(Pagenation pagenation);

    // 상세보기
    Personalinfo findById(int PersonalinfoID);

    // 삭제
    int updateDeleteFlag(List<Personalinfo> personalinfos);

    // 상태변경
    int updateActiveFlag(Personalinfo personalinfo);

    // 등록
    void save(Personalinfo personalinfo);

    void updatePersonalinfo(Personalinfo personalinfo);
}