package com.neo.visitor.domain.file.repository;

import java.util.List;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.file.entity.FileInfo;

@PrimaryMapperScan
public interface FileRepository {

    void save(FileInfo fileInfo);
    void updateDeleteFalg(int fileId);
    List<FileInfo> findByNoticeID(FileInfo fileInfo);
    List<FileInfo> findByPersonalinfoID(FileInfo fileInfo);
    List<FileInfo> findByContentsID(Pagenation pagenation);
}