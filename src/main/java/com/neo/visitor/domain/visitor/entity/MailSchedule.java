package com.neo.visitor.domain.visitor.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailSchedule {
    private int seq;
	private String targetEmail;
	private String sendText;
	private String visitApprovalDateTime;
}
