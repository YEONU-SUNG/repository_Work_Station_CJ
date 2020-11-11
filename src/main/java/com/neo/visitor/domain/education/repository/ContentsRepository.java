package com.neo.visitor.domain.education.repository;

import java.util.List;

import javax.annotation.Resource;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.education.entity.Contents;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface ContentsRepository {

    List<Contents> findAll();
    List<Contents> findByType(Contents contents);
    void deleteContents(int id);
    void updateContentsSort(Contents contents);
    void save(Contents contents);
    
}