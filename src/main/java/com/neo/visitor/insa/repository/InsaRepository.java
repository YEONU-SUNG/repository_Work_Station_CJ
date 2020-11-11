package com.neo.visitor.insa.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class InsaRepository {

    @Autowired
    @Qualifier("secondarySqlSession")
    private SqlSession secondarySqlSession;

    public List<Map<String, Object>> getInsa() {
        return secondarySqlSession.selectList("insa.getInsa");
    }
}   
