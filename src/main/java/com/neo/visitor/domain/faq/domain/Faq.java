package com.neo.visitor.domain.faq.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Faq {
    
    private int FaqID;
    private String Question;
    private String Answer;
    private String AdminID;
    private String HostName;
    private String ActiveFlag;
    private LocalDateTime RegDate;
    private String DeleteFlag;

    public void setFaqID(int FaqID) {
        if(FaqID==0) new NumberFormatException();
        this.FaqID = FaqID;
    }

    public Faq makeFaq(String Question, String Answer) {
        if(Question==null || Question.length()==0) new IllegalAccessException("제목 항목 미입력");
        if(Answer==null || Answer.length()==0) new IllegalAccessException("내용 항목 미입력");
        this.Question = Question;
        this.Answer = Answer;
        this.ActiveFlag= "Y";
        this.RegDate = LocalDateTime.now();
        this.DeleteFlag = "N";
        return this;
    }

    public Faq deleteFlag(int FaqID, String DeleFlag) {
        this.FaqID = FaqID;
        this.DeleteFlag = DeleFlag;
        return this;
    }
    public void updateFaqActiveFlag() {
        this.ActiveFlag = this.ActiveFlag.equals("Y") ? "N" : "Y";
    }

}