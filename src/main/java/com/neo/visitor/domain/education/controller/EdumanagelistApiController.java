package com.neo.visitor.domain.education.controller;

import java.util.List;

import com.neo.visitor.domain.education.application.ContentsApplication;
import com.neo.visitor.domain.education.entity.Contents;
import com.neo.visitor.domain.education.entity.ContentsType;
import com.neo.visitor.domain.education.service.ContentsService;
import com.neo.visitor.domain.file.entity.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EdumanagelistApiController {
    
    @Autowired ContentsService contentsService;
    @Autowired ContentsApplication contentsApplication;

    @GetMapping(path = "edumanage/contents-list")
    public List<FileInfo> edumanageContentsList() {
        return contentsApplication.getContentsUpload("", "");
    }

    @GetMapping(path = "edumanage/{type}")
	public List<Contents> edumanagesafty(@PathVariable String type, Model model) {
        if(type.equals("safe-list")) {
            return contentsService.findByType(new Contents().addContentsType(ContentsType.SAFE));
        } else {
            return contentsService.findByType(new Contents().addContentsType(ContentsType.SECURITY));
        }
	}

    @PostMapping(path = "edumanage/{type}/add/{fileId}")
	public void updateEdumanageContents(@PathVariable String type, @PathVariable int fileId) {
        if(type.equals("safe")) {
            contentsApplication.setContents(ContentsType.SAFE, fileId);
        } else {
            contentsApplication.setContents(ContentsType.SECURITY, fileId);
        }
    }
    
    @PostMapping(path = "edumanage/contents/{id}")
	public void deleteEdumanageContents(@PathVariable int id) {
        contentsService.deleteContents(id);
    }
    
    @PostMapping(path = "edumanage/contents/sort")
	public void updateSortEdumanageContents(@RequestParam(name = "ids[]") int[] ids) {
        contentsService.updateContentsSort(ids);
	}
}