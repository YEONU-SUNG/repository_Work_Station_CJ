package com.neo.visitor.domain.education.service;

import java.util.List;

import com.neo.visitor.domain.education.entity.Contents;
import com.neo.visitor.domain.education.repository.ContentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentsService {
    
    @Autowired ContentsRepository contentsRepository;
    
    public List<Contents> findAll() {
        return contentsRepository.findAll();
    }

    public List<Contents> findByType(Contents contents) {
        return contentsRepository.findByType(contents);
    }
    public void deleteContents(int id) {
        contentsRepository.deleteContents(id);
    }
    public void updateContentsSort(int[] ids) {
        for(int i=0; i<ids.length; i++) {
            contentsRepository.updateContentsSort(new Contents().updateSort(ids[i], i));
        }
    }
    public void save(Contents contents) {
        contentsRepository.save(contents);
    }
}