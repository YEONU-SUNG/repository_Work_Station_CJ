package com.neo.visitor.domain.education.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.education.application.ContentsApplication;
import com.neo.visitor.domain.file.entity.FileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentsmanageApiController {

    @Autowired ContentsApplication contentsApplication;

    @GetMapping(path = "contentsmanage-list")
    public List<FileInfo> getContentsUpload(@RequestParam(defaultValue = "") String conditionKey, @RequestParam(defaultValue = "") String conditionValue) {
        switch (conditionKey) {
            case "파일명" : conditionKey = "fileName"; break;
            case "등록날짜" : conditionKey = "createDate"; break;
        }
        return contentsApplication.getContentsUpload(conditionKey, conditionValue);
    }

    @PostMapping(path = "contentsmanage-list")
    public void updateContentsFileDeleteFlag(@RequestParam(value = "fileId[]") int[] fileId) {
        contentsApplication.updateContentsFileDeleteFlag(fileId);
    }
    
    @PostMapping(path = "contentsmanage/view")
	public void contentsAdd(HttpServletRequest request) {
	    contentsApplication.conetnsUpload(request);
	}
}
