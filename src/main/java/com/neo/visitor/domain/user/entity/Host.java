package com.neo.visitor.domain.user.entity;

import java.util.List;
import java.util.Map;

import com.neo.visitor.domain.buildingSiteMapping.entity.Building;
import com.neo.visitor.domain.visitor.entity.Visiter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Host extends Visiter {

	private String hostID;              //임직원 ID
    private String hostName;            //임직원 이름
    private String mobile;              //임직원 핸드폰 번호
    private String tel;                 //임직원 내선번호
    private String company;             //임직원 회사
    private String gradeName;           //임직원 직급
    private String positionName;
    private String insertDate;          //임직원 등록 일자
    private String deleteFlag;          //삭제여부
    private String adminID ;            //임직원 아이디
    private String email;               //임직원 이메일
    private String deptCD;              //임직원 부서
    private String auth;                //임직원 권한
    private String upperDeptCD;         //임직원 상위 부서
    private String activeFlag;          //임직원 상위 부서

    private List<Building> mappingBuildings;    // 접근 건물 및 층정보

    public Host makeHost(String hostID) {
        this.hostID = hostID;
        return this;
    }

    public void updateHostAuth(String auth) {
        switch (auth) {
            case "관리자" : this.auth = "1"; break;
            case "보안실" : this.auth = "2"; break;
            case "임직원" : this.auth = "3"; break;
            default : throw new IllegalArgumentException("잘못된 권한 요청");
        }
    }

    public void updateHostActiveFlag() {
        this.activeFlag = this.activeFlag.equals("Y") ? "N" : "Y";
    }

    public Host findHost(String deptCD, String hostName) {
        this.deptCD = deptCD;
        this.hostName = hostName;
        return this;
    }

    public Host insaInterface(Map<String, Object> map) {
        System.out.println(map);
        Host host = new Host();
        host.setHostID(map.get("HostID")!=null ? map.get("HostID").toString() : "");
        host.setHostName(map.get("HostName")!=null ? map.get("HostName").toString() : "");
        host.setDeptCD(map.get("DeptCD")!=null ? map.get("DeptCD").toString() : "");
        host.setCompany(map.get("Company")!=null ? map.get("Company").toString() : "");
        // host.setSiteCD(response.get("SiteCD")!=null ? response.get("SiteCD").toString() : "");
        // host.setSiteName(response.get("SiteName")!=null ? response.get("SiteName").toString() : "");
        host.setTel(map.get("Tel")!=null ? map.get("Tel").toString() : "");
        if(map.get("RET_DATE") == null)
            host.deleteFlag = "N";
        else
            host.deleteFlag = "Y";
        
        host.gradeName = map.get("GradeName").toString();
        host.email = String.valueOf(map.get("Email"));
        host.adminID  = String.valueOf(map.get("UserID"));
        host.auth = "3";
        return host;

        // this.hostID = map.get("USER_ID").toString();
        // this.hostName = map.get("USER_NM").toString();
        // this.mobile = map.get("MOBILE").toString();
        // this.tel = map.get("COMP_TEL").toString();
        // this.company = map.get("WP_NM").toString();
        // this.gradeName = map.get("TITLE_NM").toString();
        // this.positionName = map.get("DUTY_NM").toString();
        // this.insertDate = map.get("INS_DATE").toString();

        // //String.valueOf(map.get("RET_DATE"));
        // //if(map.get("RET_DATE").toString() == "")
        // //if(String.valueOf(map.get("RET_DATE")) != "null")
        // if(map.get("RET_DATE") == null)
        //     this.deleteFlag = "N";
        // else
        //     this.deleteFlag = "Y";
        // this.adminID  = map.get("USER_ID").toString();
        // this.email = map.get("MAIL").toString();
        // this.deptCD = map.get("DEPT_NM").toString();
        // this.upperDeptCD = map.get("DEPT_NM_PATH").toString();

        // this.hostID = String.valueOf(map.get("USER_ID"));
        // this.hostName = String.valueOf(map.get("USER_NM"));
        // this.mobile = String.valueOf(map.get("MOBILE"));
        // this.tel = String.valueOf(map.get("COMP_TEL"));
        // //this.company = String.valueOf(map.get("WP_NM"));
        // //if(String.valueOf(map.get("COM_CD")) =="KO031")
        // //System.out.println(String.valueOf(map.get("COM_CD")));
        // if(String.valueOf(map.get("COM_CD")).equals("KO031")){
        //     this.company = String.valueOf(map.get("WP_NM"));
        //     //System.out.println("AA");
        // }
        // else{
        //     this.company = String.valueOf(map.get("COM_NM"));
        //     //System.out.println("BB");
        // }

        // this.gradeName = String.valueOf(map.get("TITLE_NM"));
        // this.positionName = String.valueOf(map.get("DUTY_NM"));
        // this.insertDate = String.valueOf(map.get("INS_DATE"));

        // if(map.get("RET_DATE") == null)
        //     this.deleteFlag = "N";
        // else
        //     this.deleteFlag = "Y";
        // this.adminID  = String.valueOf(map.get("USER_ID"));
        // this.email = String.valueOf(map.get("MAIL"));
        // this.deptCD = String.valueOf(map.get("DEPT_NM"));
        // this.upperDeptCD = String.valueOf(map.get("DEPT_NM_PATH"));

        //return this;
    }

    public void addPositionName(String positionName) {
        this.positionName = positionName;
    }
}
