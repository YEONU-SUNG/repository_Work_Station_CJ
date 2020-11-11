package com.neo.visitor.domain;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class Pagenation {

    private int totalPage;
    private int totalSize;
    private int page;
    private int startPage = 0;
    private int size = 0;
    private String URL;
    private String params;
    private String conditionURL;
    private String firstURL;
    private String prevURL;
    private String nextURL;
    private String lastURL;
    private String conditionKey;
    private String conditionValue;
    private String visitFromDateTime = "";
    private String visitToDateTime = "";
    
    // controller request params
    public Pagenation(int page, int size, String conditionKey, String conditionValue) {
        this.page = page;
        this.startPage = page == 1 ? 0 : (page-1)*size;
        this.size = size;
        this.conditionKey = conditionKey;
        this.conditionValue = conditionValue;
    }

    public void PagenationExpansionDate(String visitFromDateTime, String visitToDateTime) {
        this.visitFromDateTime = visitFromDateTime;
        this.visitToDateTime = visitToDateTime;
    }
  
    // view response
    public Pagenation makePagenation(int totalSize, PagenationType pagenationType) {
        this.totalPage = (totalSize%this.size)==0 ? (totalSize/this.size) : (totalSize/this.size)+1;
        this.totalSize = totalSize;
        this.URL = pagenationType.getValue();
        this.conditionURL = conditionURL();
        this.params = params();
        this.firstURL = firstURL();
        this.prevURL = prevURL();
        this.nextURL = nextURL();
        this.lastURL = lastURL();
        return this;
    }

    private String params() {
        return this.URL+"?page="+this.page+"&size="+this.size+this.conditionURL;
    }

    private String firstURL() {
        if(this.page>1) return this.URL+"?page=1&size="+this.size+this.conditionURL;
        else return "";
    }

    private String prevURL() {
        if(this.page>1) return this.URL+"?page="+(this.page-1)+"&size="+this.size+this.conditionURL;
        else return "";
    }

    private String nextURL() {
        if(this.page < this.totalPage) return this.URL+"?page="+(this.page+1)+"&size="+this.size+this.conditionURL;
        else return "";
    }

    private String lastURL() {
        return this.URL+"?page="+this.totalPage+"&size="+this.size+this.conditionURL;   
    }

    private String conditionURL() {
        String conditionURL = "";
        if(this.conditionKey.length() >0 ) conditionURL += "&conditionKey="+this.conditionKey+"&conditionValue="+this.conditionValue;
        if(this.visitFromDateTime.length() >0 && this.visitToDateTime.length() > 0) conditionURL += "&visitorFromDateTime="+this.visitFromDateTime+"&visitorToDateTime="+this.visitToDateTime;
        return conditionURL;
    }
}