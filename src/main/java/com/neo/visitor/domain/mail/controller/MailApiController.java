package com.neo.visitor.domain.mail.controller;

import com.neo.visitor.util.MailSenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailApiController {
    
    @Autowired MailSenderUtil mailSenderUtil;
    
    @PostMapping(path = "mailsend")
    public void mailsend(@RequestParam(name = "sendfrom") String sendfrom, @RequestParam(name = "sendto") String sendto, 
    @RequestParam(name = "title") String title, @RequestParam(name = "text") String text, @RequestParam(name = "cc") String cc) 
    {
        mailSenderUtil.sendMail(sendfrom,sendto,title,text,cc);
    }
    
}