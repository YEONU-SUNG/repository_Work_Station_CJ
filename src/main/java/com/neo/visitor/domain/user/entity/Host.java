package com.neo.visitor.domain.user.entity;

import java.util.List;

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
    private String deleteFlag = "N";    //삭제여부
    private String adminID ;            //임직원 아이디
    private String email;               //임직원 이메일
    private String deptCD;              //임직원 부서
    private String auth;                //임직원 권한
    private String upperDeptCD;         //임직원 상위 부서
    private String activeFlag = "Y";          //임직원 상위 부서
    private String siteCode;            //임직원 계열사 코드

    private List<Building> mappingBuildings;    // 접근 건물 및 층정보

    public Host makeHost(String hostID) {
        this.hostID = hostID;
        return this;
    }

    public void updateHostActiveFlag() {
        this.activeFlag = this.activeFlag.equals("Y") ? "N" : "Y";
    }

    public Host findHost(String deptCD, String hostName) {
        this.deptCD = deptCD;
        this.hostName = hostName;
        return this;
    }

    public void addPositionName(String positionName) {
        this.positionName = positionName;
    }
}
