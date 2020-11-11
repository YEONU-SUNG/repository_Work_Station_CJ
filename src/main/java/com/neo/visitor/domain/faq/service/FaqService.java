package com.neo.visitor.domain.faq.service;

import java.util.ArrayList;
import java.util.List;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.PagenationResponse;
import com.neo.visitor.domain.PagenationType;
import com.neo.visitor.domain.faq.domain.Faq;
import com.neo.visitor.domain.faq.repository.FaqRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqService {
    
    @Autowired FaqRepository faqRepository;

    public PagenationResponse<Faq> findAll(Pagenation pagenation) {
        PagenationResponse<Faq> pagenationResponse = new PagenationResponse<>();
        pagenationResponse.setResponse(faqRepository.findAll(pagenation));
        pagenationResponse.setPagenation(pagenation.makePagenation(faqRepository.countByDeleteFlag(pagenation), PagenationType.FAQ));
        return pagenationResponse;
    }

    public Faq findById(int FaqID) {
        return faqRepository.findById(FaqID);
    }

    public void update(Faq faq) {
        faqRepository.updateFaq(faq);
    }

    public Faq updateActiveFlag(int faqID) {
        Faq faq = findById(faqID);
        faq.updateFaqActiveFlag();
        faqRepository.updateActiveFlag(faq);
        return faq;
    }

    public void updateDeleteFlag(int[] FaqIDs) {
        List<Faq> faqs = new ArrayList<>();
        for (int FaqID : FaqIDs) {
            faqs.add(new Faq().deleteFlag(FaqID, "Y"));
        }
        faqRepository.updateDeleteFlag(faqs);
    }

    public Faq save(Faq faq) {
        faqRepository.save(faq);
        return faq;
    }
}