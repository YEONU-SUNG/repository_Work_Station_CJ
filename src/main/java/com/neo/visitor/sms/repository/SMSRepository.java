package com.neo.visitor.sms.repository;

import com.neo.visitor.sms.entity.SMSMsgQue;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SMSRepository {

    @Autowired
    @Qualifier("tertiarySqlSession")
    private SqlSession tertiarySqlSession;

    public void sendSMS(SMSMsgQue smsMsgque) {
        tertiarySqlSession.insert("sms.sendSMS",smsMsgque);
    }
}   
