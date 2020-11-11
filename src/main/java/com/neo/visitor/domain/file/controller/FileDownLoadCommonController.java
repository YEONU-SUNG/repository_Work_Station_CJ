package com.neo.visitor.domain.file.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDownLoadCommonController {
	
	@Value("${image.save-path}")
    private String SAVE_PATH;
	
	@RequestMapping("/file/{saveFilePath}/{saveFileName}")
    public void fileDownload(HttpServletRequest req, HttpServletResponse res
        , @PathVariable String saveFilePath
        , @PathVariable String saveFileName) {
		// 다운로드할 파일이 저장되어 있는 곳 정의
		try {
            // 다운로드할 파일의 완전한 경로
            String fname = req.getParameter("name");
		    String dname = SAVE_PATH + saveFilePath + "/" + saveFileName;
		    
            // 다운로드할 파일에 대해 한글처리
            fname = setDisposition(fname, getBrowser(req));
		    //fname = new String(fname.getBytes("UTF-8"), "ISO-8859-1");
		      
		    // 다운로드할 파일 내용을 디스크로 부터 읽어오기.
		    File fp = null;
		    FileInputStream fis = null;
		    
		    fp = new File(dname);
		    fis = new FileInputStream(fp);
		    String fsize = String.valueOf(fp.length());
		    byte b[] = new byte[Integer.parseInt(fsize)];
		    
		    // 무조건 다운로드창을 띠우기 위해 HTTP header 설정
		    res.setHeader("Content-type","application/unknowon");
		    res.setHeader("Content-Disposition","attachment; filename=\"" + fname +"\"");    
		    res.setHeader("Content-Length",fsize+";");
		    res.setHeader("Content-Transfer-Encoding","binary");
		    res.setHeader("Pragma","no-cache");    
		    res.setHeader("Expires","0");    
		    if(b.length > 0){  //size가 0 이면 무한루프에 빠짐
		        if(fp.isFile()){
		            BufferedInputStream fin  = new BufferedInputStream(fis);
		            BufferedOutputStream fon =  new BufferedOutputStream(res.getOutputStream());
		                
		            int read = 0;
		            try{
		                while((read = fin.read(b)) != -1){
		                    fon.write(b,0,read );
		                }                                
		            }catch(Exception ex){
		                ex.printStackTrace();
		            }finally{
		                if(fon!=null)
		                    fon.close();
		                if(fin!=null)
		                    fin.close();                        
		            }
		    	}
		    }
		} catch (FileNotFoundException fe) {
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			try {
				out = res.getWriter();
				out.print("<script>"
						+ "alert(\"다운로드실패 사유 : 지정 된 파일경로를 찾을수 없습니다.\\n확인버튼을 누르시면 이전 페이지로 이동됩니다.\");"
						+ "history.back();"
						+ "</script>");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    }
    
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Trident") > -1) {   // IE11 문자열 깨짐 방지
           return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
           return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
           return "Opera";
        } else if (header.indexOf("Safari") > -1) {
           return "Safari";
        }
        return "Firefox";
    }

    private String setDisposition(String filename, String browser) throws Exception {
        String encodedFilename = null;
        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        }
        // else if (browser.equals("Firefox")) {
        //        encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        //        encodedFilename = URLDecoder.decode(encodedFilename);
        // } else if (browser.equals("Opera")) {
        //        encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        // } 
        // else if (browser.equals("Safari")){
        //        encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1")+ "\"";
        //        encodedFilename = URLDecoder.decode(encodedFilename);
        // }
        return encodedFilename;
    }
}
