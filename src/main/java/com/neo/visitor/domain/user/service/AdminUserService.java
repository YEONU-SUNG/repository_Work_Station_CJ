package com.neo.visitor.domain.user.service;

import java.util.List;
import java.util.Map;

import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.repository.AdminUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService {
    
    @Autowired AdminUserRepository adminUserRepository;

    public int count(Map<String, Object> map) {
        return adminUserRepository.count(map);
    }

    public List<AdminUser> findByNotAdminId(Map<String, Object> map) {
        return adminUserRepository.findByNotAdminID(map);
    }

}