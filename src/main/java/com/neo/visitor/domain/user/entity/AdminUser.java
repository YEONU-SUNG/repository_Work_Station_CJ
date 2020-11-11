package com.neo.visitor.domain.user.entity;

//import com.neo.visitor.util.SHA256Util;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdminUser {
	
	private String adminID;
	private String adminPW;
	private String insertDate;
	private LocalDateTime lastLoginDate;
    private LocalDateTime lastChangePWDate;
    private String siteCode;
    private String deleteFlag;

    private Host host;
    
    // 로그인 정보
    public AdminUser login(String adminID, String adminPW) {
        this.adminID = adminID;
        //this.adminPW = SHA256Util.encryptSHA256(adminPW);
        this.adminPW = adminPW;
    	return this;
    }

    public AdminUser insaInterface(Map<String, Object> map) {
        System.out.println(map);

        this.adminID = map.get("USER_ID").toString();
        //this.adminPW = map.get("USER_ID").toString(); //패스워드 암호화방법 몰라서 일단ID값으로 넣어줌
        //this.adminPW = map.get("LOG_PWD").toString();
        this.adminPW = "";
        this.insertDate = map.get("INS_DATE").toString();
        if(map.get("RET_DATE") == null)
            this.deleteFlag = "N";
        else
            this.deleteFlag = "Y";

        return this;
    }
}
