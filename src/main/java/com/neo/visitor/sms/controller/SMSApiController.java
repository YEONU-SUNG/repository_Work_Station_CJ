package com.neo.visitor.sms.controller;

import com.neo.visitor.sms.repository.SMSRepository;
import com.neo.visitor.util.MailSenderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.neo.visitor.sms.entity.SMSMsgQue;

@RestController
public class SMSApiController {
    
    @Autowired SMSRepository smsRepository;

    @PostMapping(path = "smssend")
    public void smssend(@RequestParam(name = "dstaddr") String dstaddr, @RequestParam(name = "callback") String callback, @RequestParam(name = "text") String text) 
    {
        SMSMsgQue smsMsgque = new SMSMsgQue();
        smsMsgque.setDstaddr(dstaddr);
        smsMsgque.setCallback(callback);
        smsMsgque.setText(text);
        smsRepository.sendSMS(smsMsgque);
    }
    
}