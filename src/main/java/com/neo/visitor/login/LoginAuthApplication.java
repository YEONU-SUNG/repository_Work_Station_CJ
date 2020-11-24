package com.neo.visitor.login;

import javax.servlet.http.HttpSession;

import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;

public abstract class LoginAuthApplication {
    
    // static final String MASTER_ID = "master";
    // static final String MASTER_PASSWORD = "master";
    static final String MASTER_ID = "master";
    static final String MASTER_PASSWORD = "master";

    private Host host = new Host();

    /**
     * 권한종류 (0~4)
     * 마스터 0
     * 계열사관리자 1
     * 보안관리자 2
     * 안내데스크 3
     * 임직원 4
     */
    private enum ROLE {
        MASTER,         // 마스터
        BRANCH_ADMIN,   // 계열사관리자
        SECURITY_ADMIN, // 보안관리자
        DESK,           // 안내데스크
        NONE;           // 임직원
    }

    public final void authorize(HttpSession session, String id, String password) {
        if(isMaster(id, password)) {
            this.host.setHostName("MASTER");
            this.host.setAuth("0");
            setSession(session);    
        } else {
            this.host =
                isEmptyOrganizationChart(
                    checkAuthorityAD(id, password)
                );
            checkAuthority();
            setSession(session);
        }
    }

    /**
     * 마스터 접근인지 체크
     * @param id
     * @param password
     * @return true if authorized
     */
    private boolean isMaster(String id, String password) {
        return (id.equals(MASTER_ID) && password.equals(MASTER_PASSWORD));
    }

    /**
     * AD인증체크
     * @param id
     * @param password
     * @return key id
     */
    public abstract String checkAuthorityAD(String id, String password);

    /**
     * 조직도에 정보가 있는 AD인증정보인지 체크
     * @return Host or null
     */
    public abstract Host isEmptyOrganizationChart(String id);

    /**
     * 
     * @return
     */
    private ROLE checkAuthority() {
        try {
            switch(this.host.getAuth()) {
                case "1" : return ROLE.BRANCH_ADMIN;
                case "2" : return ROLE.SECURITY_ADMIN;
                case "4" : return ROLE.DESK;
                default :
                    this.host.setAuth("3"); 
                    return ROLE.NONE;
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("접근권한이 없습니다.");
        }
    }
    
    /**
     * 세션에 로그인 정보 셋팅
     * @param session
     */
    private void setSession(HttpSession session) {
        AdminUser adminUser = new AdminUser();
        adminUser.setHost(this.host);
        session.setAttribute("login", adminUser);
        session.setMaxInactiveInterval(60*60*12);
    }

}