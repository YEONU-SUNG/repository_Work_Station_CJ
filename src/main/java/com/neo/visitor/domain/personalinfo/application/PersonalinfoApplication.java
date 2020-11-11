package com.neo.visitor.domain.personalinfo.application;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.file.entity.FileInfo;
import com.neo.visitor.domain.file.entity.FileType;
import com.neo.visitor.domain.file.repository.FileRepository;
import com.neo.visitor.domain.file.service.FileService;
import com.neo.visitor.domain.personalinfo.entity.Personalinfo;
import com.neo.visitor.domain.personalinfo.service.PersonalinfoService;
import com.neo.visitor.domain.user.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalinfoApplication {

    @Autowired LoginService loginService;
    @Autowired PersonalinfoService personalinfoService;
    @Autowired FileService fileService;
    @Autowired FileRepository fileRepository;

    public Personalinfo personalinfoIntert(Personalinfo personalinfo, HttpServletRequest request) {
        personalinfo.setAdminID(loginService.getUserSessionInfo(request).getAdminID());
        try {
            personalinfo.setPersonalinfoID(Integer.parseInt(request.getParameter("personalinfoId")));
            personalinfoService.updatePersonalinfo(personalinfo);
            if(request.getParameter("deleteFiles")!=null)
                fileRepository.updateDeleteFalg(Integer.parseInt(request.getParameter("deleteFiles")));
        } catch (NullPointerException | NumberFormatException e) {
            personalinfoService.save(personalinfo);
        }
        fileService.fileUpload(personalinfo.getPersonalinfoID(), FileType.PERSONALINFO, request);
        return personalinfo;
    }
 
    public Personalinfo personalinfoDetail(int PersonalinfoID) {
        Personalinfo personalinfo = personalinfoService.findById(PersonalinfoID);
        
        List<FileInfo> fileInfos = fileRepository.findByPersonalinfoID(new FileInfo().targetInfo(FileType.PERSONALINFO, personalinfo.getPersonalinfoID()));

        personalinfo.addFileInfos(fileInfos);

        return personalinfo;
    }
    
} 