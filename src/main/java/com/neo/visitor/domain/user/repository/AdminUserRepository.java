package com.neo.visitor.domain.user.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.user.entity.AdminUser;

@PrimaryMapperScan
public interface AdminUserRepository {

    int count(Map<String, Object> map);
    AdminUser getLoginUserInfo(AdminUser adminUser);
    AdminUser getLoginUserInfosso(AdminUser adminUser);
    List<AdminUser> findByNotAdminID(Map<String, Object> map);
}
