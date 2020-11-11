package com.neo.visitor.domain.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.neo.visitor.domain.user.entity.AdminUser;
import com.neo.visitor.domain.user.entity.Host;

@Repository
public class HostInsaRepository {

    @Autowired
    @Qualifier("sqlSession")
    private SqlSession primarySqlSession;

    public void insaSave(Host host) {
        primarySqlSession.insert("com.neo.visitor.domain.user.repository.HostRepository.insaSave", host);
    }

    public void insaAdminSave(AdminUser adminUser) {
        primarySqlSession.insert("com.neo.visitor.domain.user.repository.AdminUserRepository.insaAdminSave", adminUser);
    }
}
