package com.neo.visitor.domain.personalinfo.service;

import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.domain.personalinfo.entity.Personalinfo;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.personalinfo.repository.PersonalinfoRepository;
import com.neo.visitor.domain.file.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalinfoService {

    @Autowired PersonalinfoRepository PersonalinfoRepository;
    @Autowired FileService fileService;

    public PagenationResponse<Personalinfo> findAll(Pagenation pagenation) {
        PagenationResponse<Personalinfo> pagenationResponse = new PagenationResponse<>();
        pagenationResponse.setResponse(PersonalinfoRepository.findAll(pagenation));
        pagenationResponse.setPagenation(pagenation.makePagenation(PersonalinfoRepository.countByDeleteFlag(pagenation), PagenationType.PERSONALINFO));
        return pagenationResponse;
    }

    public Personalinfo findById(int PersonalinfoID) {
        return PersonalinfoRepository.findById(PersonalinfoID);
    }

    public void updatePersonalinfo(Personalinfo Personalinfo) {
        PersonalinfoRepository.updatePersonalinfo(Personalinfo);
    }

    public Personalinfo updatePersonalinfoActiveFlag(int PersonalinfoID) {
        Personalinfo Personalinfo = findById(PersonalinfoID);
        Personalinfo.updatePersonalinfoActiveFlag();
        PersonalinfoRepository.updateActiveFlag(Personalinfo);
        return Personalinfo;
    }

    public void updatePersonalinfosDeleteFlag(int[] PersonalinfoIDs) {
        List<Personalinfo> Personalinfos = new ArrayList<>();
        for (int PersonalinfoID : PersonalinfoIDs) {
            Personalinfos.add(new Personalinfo().deleteFlagPersonalinfo(PersonalinfoID, "Y"));
        }
        PersonalinfoRepository.updateDeleteFlag(Personalinfos);
    }

    public Personalinfo save(Personalinfo Personalinfo) {
        PersonalinfoRepository.save(Personalinfo);
        return Personalinfo;
    }
}