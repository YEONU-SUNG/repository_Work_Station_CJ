package com.neo.visitor.sms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SMSMsgQue {
    private String dstaddr;
    private String callback;
    private String text;
}