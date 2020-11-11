package com.neo.visitor.domain.visitor.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.Pagenation;
import com.neo.visitor.domain.visitor.entity.MailSchedule;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface MailScheduleRepository {
    void save(MailSchedule mailSchedule);

    List<MailSchedule> getTargetEmail();
    List<MailSchedule> getSendEmail(MailSchedule mailSchedule);
}