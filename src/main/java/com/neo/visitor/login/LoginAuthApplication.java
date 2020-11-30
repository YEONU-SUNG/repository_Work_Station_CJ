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
     * 임직원 3
     * 안내데스크 4
     */
    public enum ROLE {
        MASTER("0", "시스템담당자"),            // 시스템담당자
        BRANCH_ADMIN("1", "계열사담당자"),      // 계열사담당자
        SECURITY_ADMIN("2", "보안담당자"),      // 보안담당자
        NONE("3", "임직원"),                    // 임직원
        DESK("4", "안내데스크");                // 안내데스크

        private String roleNumber;
        private String roleName;

        ROLE(String roleNumber, String roleName) {
            this.roleNumber = roleNumber;
            this.roleName = roleName;
        }

        public static String findByRoleNumber(String roleName) {
            for (ROLE role : ROLE.values()) {
                if(role.roleName.equals(roleName)) {
                    return role.roleNumber;
                }
            }
            throw new IllegalArgumentException("잘못된 권한 요청입니다.");
        }

        public static String findByRoleName(String roleNumber) {
            for (ROLE role : ROLE.values()) {
                if(role.roleNumber.equals(roleNumber)) {
                    return role.roleName;
                }
            }
            throw new IllegalArgumentException("잘못된 권한 요청입니다.");
        }
    }

    public final void authorize(HttpSession session, String id, String password) {
        if(isMaster(id, password)) {
            this.host.setHostName("MASTER");
            this.host.setAuth("0");
        } else {
            this.host =
                isEmptyOrganizationChart(
                    checkAuthorityAD(id, password)
                );

            //checkAuthority();
            host.setAuth(checkAdminAuthority(host.getHostID()));
        }
        if(checkAuthority()!=ROLE.DESK) setMappingSite(this.host);
        setSession(session);
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
     * 관리자 권한 관리에 등록되어있는지 체크
     * @return Host or null
     */
    public abstract String checkAdminAuthority(String id);

    /**
     * 
     * @return
     */
    private ROLE checkAuthority() {
        try {
            switch(this.host.getAuth()) {
                case "0" : return ROLE.MASTER;
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
        adminUser.setAdminID(MASTER_ID);
        adminUser.setHost(this.host);
        session.setAttribute("login", adminUser);
        session.setMaxInactiveInterval(60*60*12);
    }

    /**
     * 건물 및 층별 권한 셋팅
     * @param host
     */
    public abstract void setMappingSite(Host host);

}