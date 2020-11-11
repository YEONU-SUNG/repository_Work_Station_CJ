package com.neo.visitor.domain.visitor.controller;

import javax.servlet.http.HttpServletRequest;

import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.visitor.application.VisitorApplication;
import com.neo.visitor.util.ExcelDownload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

@Controller
public class VisitorHistoryController {

    @Autowired VisitorApplication visitorApplication;

    @GetMapping(value = "visitor/history")
	public String historyView(Model model) {
		// model and view , model
		return "visitor/history";
    }

    @GetMapping("excel/visitor/history-list")
	public View getAddressbookExcel(HttpServletRequest request, @RequestParam int page, @RequestParam int size
    , @RequestParam(defaultValue = "") String visitorFromDateTime
    , @RequestParam(defaultValue = "") String visitorToDateTime
    , @RequestParam(defaultValue = "") String conditionKey
    , @RequestParam(defaultValue = "") String conditionValue, Model model) {
        //String[] headers = new String[] {"예약번호", "신청일", "성명", "연락처", "회사", "방문증번호", "방문기간", "방문일시", "완료일시", "임직원"};	
        //String[] headers = new String[] {"성명", "연락처", "회사", "신청일", "방문증번호", "방문기간", "방문일시", "완료일시", "임직원"};	
        //String[] headers = new String[] {"회사", "성명", "연락처", "방문일시", "완료일시", "방문장소", "차량정보", "반입물품", "시리얼번호", "사용목적", "사용유무", "임직원", "방문증번호", "방문기간", "신청일"};	
        String[] headers = new String[] {"회사", "성명", "연락처", "방문일시", "완료일시", "방문목적", "차량정보", "반입물품", "시리얼번호", "사용목적", "사용유무", "임직원", "방문증번호", "방문기간", "신청일"};	
        model.addAttribute("excelData", visitorApplication.historyExcel(headers, request, new Pagenation(page, size, conditionKey, conditionValue), visitorFromDateTime, visitorToDateTime));
        model.addAttribute("columnLength", new int[]{5000, 5000, 5000, 5000, 5000, 10000, 6000, 6000, 4000});
        model.addAttribute("title", "방문이력");
        return new ExcelDownload();
	}
}