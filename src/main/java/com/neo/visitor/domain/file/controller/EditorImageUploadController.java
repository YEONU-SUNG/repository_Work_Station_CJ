package com.neo.visitor.domain.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neo.visitor.domain.file.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EditorImageUploadController {

    @Autowired FileService fileService;
	
	@PostMapping(path = "/editor/image/upload")
	@ResponseBody
	public void editorImageUpload(HttpServletRequest request, HttpServletResponse response,  @RequestParam MultipartFile upload) {
		fileService.editorImageUpload(request, response, upload);
	}
}
