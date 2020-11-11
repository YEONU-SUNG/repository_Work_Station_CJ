package com.neo.visitor.domain.visitor.repository;

import java.util.List;
import java.util.Map;

import com.neo.visitor.config.PrimaryMapperScan;
import com.neo.visitor.domain.visitor.entity.VisitorDashboard;
import com.neo.visitor.domain.visitor.entity.VisitorHistory;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@PrimaryMapperScan
public interface VisitorHistoryRepository {

    List<VisitorHistory> dashBoard(VisitorDashboard visitorDashBoard);
    
    // 리스트 카운트
    int countByDeleteFlag(Map<String, Object> params);

    VisitorHistory findById(int visitorHistorySeq);

    // 금일방문객 조회
    List<VisitorHistory> findByPlanDateTime(Map<String, Object> params);

    // 방문카드 업데이트
    void updateCardID(VisitorHistory visitorHistory);

    // 방문승인 자 방문/퇴실
    void updateVisitorInout(VisitorHistory visitorHistory);

    // 방문 승인 대기자 승인 처리
    void updateVisitorApproval(VisitorHistory visitorHistory);

    // 방문 승인 대기자 승인 거부 처리
    void updateVisitorApprovalReject(VisitorHistory visitorHistory);

    // 과거 교육이력 확인
    VisitorHistory findByVisitorIDAndEduCompleteDateTime(VisitorHistory visitorHistory);

    // 방문객 중복신청인지 아닌지 체크
    int findByVisitorIDAndPlanDate(VisitorHistory visitorHistory);

    // 방문정보등록
    void save(VisitorHistory visitorHistory);

    void updatevisitorReservationNumber(VisitorHistory visitorHistory);

    List<VisitorHistory> countByPlanFromDateTime(VisitorDashboard visitorDashboard);
}