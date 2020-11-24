package com.neo.visitor.login.api.doosan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
class DoosanADResponse {

    private User user;
    private String token;

    @Getter @Setter @ToString
    static class User {
        private String email;
        private String username;
        private String displayFullName;
        private String authorize;
        private String status;
        private String department;
        private String team;
        private String position;
        private String location;
        private String company;
        private String floor;
        private String isO365;
        private String locale;
        private String rootOrgUnitName;
        private String orgUnitId;
        private String workOrgUnitId;
        private String lastAccessIP;
        private String lastAccessTime;
        private String address;
        private String aboutme;
        private String _id;
        private String personId;
        private String password;
        private String created;
        private String __v;
    }
}