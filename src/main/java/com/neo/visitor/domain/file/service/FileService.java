package com.neo.visitor.domain.file.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neo.visitor.domain.file.entity.FileInfo;
import com.neo.visitor.domain.file.entity.FileType;
import com.neo.visitor.domain.file.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileService {

    @Autowired FileRepository fileRepository;

    @Value("${image.save-path}")
    private String SAVE_PATH;

    public void editorImageUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
        OutputStream out = null;
        PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try{
            String dir = "editor";
            byte[] bytes = upload.getBytes();
            String time = System.currentTimeMillis()+"";
            String tarnsFileName = convertFileName(dir, time ,upload.getOriginalFilename());
        
            out = new FileOutputStream(isDestDir(dir)+"/"+tarnsFileName);
            out.write(bytes);
            
            printWriter = response.getWriter();
            String fileUrl = "/upload/editor/" + tarnsFileName; //url경로
            String data = "{\"filename\" : \""+tarnsFileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}";

            printWriter.write(data);
            printWriter.flush();
 
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (printWriter != null) printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public void fileUpload(int targetId, FileType fileType, HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartRequest.getFileNames();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            for(MultipartFile upload : multipartRequest.getFiles(key)){
                String time = System.currentTimeMillis()+"";
                try {
                    byte[] bytes = upload.getBytes();
                    String tarnsFileName = convertFileName(fileType.getValue(), time, upload.getOriginalFilename());
                    String saveFilePath = isDestDir(fileType.getValue())+"/"+tarnsFileName;
                    OutputStream out = new FileOutputStream(saveFilePath);
                    out.write(bytes);
                    out.close();
                    fileRepository.save(new FileInfo().makeFileSaveInfo(tarnsFileName, saveFilePath, upload.getOriginalFilename(), targetId, fileType.getValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File isDestDir(String dir) {
        File destdir = new File(SAVE_PATH + dir); //디렉토리 가져오기    
        if(!destdir.exists()) destdir.mkdirs(); //디렉토리가 존재하지 않는다면 생성
        return destdir;
    }

    private String convertFileName(String dir, String time, String fileName) {
        int extensionPosition = fileName.lastIndexOf(".");
		String extention = fileName.substring(extensionPosition+1).toLowerCase();
		return dir+"_"+time+"."+extention; 
	}
}