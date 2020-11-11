package com.neo.visitor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderUtil {

    @Autowired
    public JavaMailSender javaMailSender;

    @Async
    public void sendMail(String sender, String receiver, String title, String text, String cc) {
        if(receiver == null)
            return;
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(sender);
        simpleMessage.setTo(receiver);
        simpleMessage.setSubject(title);
        simpleMessage.setText(text);
        if(!cc.equals(""))
        {
            simpleMessage.setCc(cc);
        }
        javaMailSender.send(simpleMessage);
    }
}