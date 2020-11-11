package com.neo.visitor.domain.user.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class LoginApiConnect {

    private static final String SSOURL = "https://smart.sfa.co.kr/gswfimage/legacyAuth.aspx";
    
    public String httpURLConnection(String adminId, String adminPw)
            throws IOException {

        StringBuffer retStr = new StringBuffer();
        OutputStream os = null;
        BufferedWriter writer = null;
        BufferedReader br = null;
        
        try {
          URL url = new URL(SSOURL+"?&userId="+adminId+"&userpwd="+adminPw+"&pwdEncType=");
          System.out.println(url);
          HttpURLConnection  huc = (HttpURLConnection) url.openConnection();
          huc.setRequestMethod("GET");
          huc.setDoInput(true);
          huc.setDoOutput(true);
          huc.setRequestProperty("Content-Type", "application/json");
          huc.setConnectTimeout(2000); //상대방 서버 통신 오류로 인해 접속 지연시 강제로 timeout 처리;
          huc.setReadTimeout(2000); //상대방 서버 통신 오류로 인해 접속 지연시 강제로 timeout 처리;
      
          os = huc.getOutputStream();
          writer = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
          //writer.write(parameterInfo);
          writer.flush(); // 버퍼를 비워준다.
          
          // 응답받은 메시지의 길이만큼 버퍼를 생성하여 읽어들임
          br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "utf-8"));
          String data;
          // 표준출력으로 한 라인씩 출력
          data = br.readLine();
          while(data != null ) {
            if(retStr.length() > 0) {
              retStr.append("\n");
            }
            retStr.append(data);
            data = br.readLine();
          }
        } catch (Exception e) {
            e.printStackTrace();
           //throw new IoComException("요청URL을 받아 통신 처리하는 도중 예기치 않은 오류 발생", e, request);
        } finally{
          // 스트림을 닫는다.
          try {
            if(os != null){
              os.close();
            }
          }catch(IOException e) {
            e.printStackTrace();
            // new IoComException("OutputStream close 처리 도중 예기치 않은 오류 발생", e);
         }
      
          try {
            if(writer != null){
              writer.close();
            }
          }catch(IOException e) {
            e.printStackTrace();
            // new IoComException("BufferedWriter close 처리 도중 예기치 않은 오류 발생", e);
          }
          
          try {
            if(br != null){
              br.close();
            }
          }catch(IOException e) {
            e.printStackTrace();
            // new IoComException("BufferedReader close 처리 도중 예기치 않은 오류 발생", e);
          }
        }
        
        return retStr.toString();
      }
}