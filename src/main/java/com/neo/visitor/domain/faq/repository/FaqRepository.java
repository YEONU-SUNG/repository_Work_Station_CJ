package com.neo.visitor.domain.faq.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.faq.domain.Faq;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface FaqRepository {
    // 리스트 카운트
    int countByDeleteFlag(Pagenation pagenation);

    // 리스트
    List<Faq> findAll(Pagenation pagenation);

    // 상세보기
    Faq findById(int FaqID);

    // 삭제
    int updateDeleteFlag(List<Faq> faqs);

    // 상태변경
    int updateActiveFlag(Faq faq);

    // 등록
    void save(Faq faq);

    void updateFaq(Faq faq);
}