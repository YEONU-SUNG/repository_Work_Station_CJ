<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2' or sessionScope.login.host.auth eq '3'}">
    <script>
        var standbyActionCommponet = {
            approveButton : function(row) {
                if(row.visitApprovalYN==='N') {
                    return '<button type="button" class="nv_blue_button nv_modal2_open">승인</button>';
                }
            }
        };
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '4'}">
    <script>
        var standbyActionCommponet = {
            approveButton : function(row) {
                if(row.visitApprovalYN==='N') {
                    return '승인대기';
                }
            }
        };
    </script>
</c:if>
<script>
    var standby_module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        makeTable : function() {
            if(this.tableData.length==0 && this.pagenation.conditionKey!='') {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<tr class="nv_view_nexttable" id="'+this.tableData[i].visitorHistorySeq+'">' +
                                            '<td>' +this.tableData[i].visitorHistorySeq+ '</td>' +
                                            '<td>' +this.tableData[i].visitor.visitorName+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitor.visitorBirth+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitor.mobile+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitor.company+ '</td>' +
                                            '<td>' +this.tableData[i].planFromDateTime+ ' ~ '+this.tableData[i].planToDateTime+'</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].host.hostName+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].host.tel+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitorPurpose+'</td>' +
                                            '<td class="tpc_skip m_skip"><a class="nv_blue nv_bold nv_link nv_modal1_open">'+this.tableData[i].carryStuff+'</a></td>' +
                                            '<td>'+standbyActionCommponet.approveButton(this.tableData[i])+'</td>';
                                        '<tr>'; 
                    this.tableHTML +=   '<tr class="pc_skip">' +
                                            '<td colspan="5" class="nv_hidden_table_area">' +
                                                '<table class="nv_hidden_table">' +
                                                    '<colgroup>' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                    '</colgroup>' +
                                                    '<tbody>' +
                                                        '<tr>' +
                                                            '<td class="nv_bold">방문기간</td>' +
                                                            '<td colspan="3">'+this.tableData[i].planFromDateTime+ ' ~ '+this.tableData[i].planToDateTime+'</td>' +
                                                        '</tr>' +
                                                        '<tr>' +
                                                            '<td class="nv_bold">생년월일</td>' +
                                                            '<td>'+this.tableData[i].visitor.visitorBirth+'</td>' +
                                                            '<td class="nv_bold">연락처</td>' +
                                                            '<td>'+this.tableData[i].visitor.mobile+'</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">회사</td>' +
                                                            '<td>' +this.tableData[i].visitor.company+ '</td>' +
                                                            '<td class="nv_bold">차량번호</td>' +
                                                            '<td>' +this.tableData[i].carNo+ '</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">임직원</td>' +
                                                            '<td>' +this.tableData[i].host.hostName+ '</td>' +
                                                            '<td class="nv_bold">내선번호</td>' +
                                                            '<td>' +this.tableData[i].host.tel+ '</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">방문목적</td>' +
                                                            '<td>' +this.tableData[i].visitorPurpose+'</td>' +
                                                            '<td class="nv_bold">반입물품</td>' +
                                                            '<td><a class="nv_blue nv_bold nv_link nv_modal1_open">'+this.tableData[i].carryStuff+'</a></td>' +
                                                        '</tr>' +
                                                    '</tbody>' +
                                                '</table>' +
                                            '</td>' +
                                        '</tr>';
                    }
                return this.tableHTML;
            }
        }
    }
    
    function standby_init(url) {
        callApi.getData(url, function (result) {
            standby_module.pagenation = result.pagenation;
            standby_module.tableData = result.response;
            
            $('#standbyTable > tbody').html(standby_module.makeTable());
            $('#standbyTable_pagenation').html(page.makePagenation(standby_module.pagenation));
        });
    }

    function standby_search() {
        standby_init('/visitor/standby-list?page=1&size=10&conditionKey='+$('#standby_conditionKey').html()+"&conditionValue="+$('#standby_conditionValue').val());
    }

    $(document).ready(function() {
        standby_init('/visitor/standby-list?page=1&size=10');
    });

    $(document).on('click', '#standbyTable > tbody > tr button', function() {
        var targetId = $(this).parent().parent().attr('id');
        $('#visitApprovalForm').attr('action', '/visitor-approval/'+targetId);
    });

    $(document).on('submit', '#visitApprovalForm', function(event) {
        event.preventDefault();
        var form = $(this);
        var formData = new FormData();
        formData.append('carryStuff', $('#carryStuff').val());
        formData.append('visitApprovalComment', $('#visitApprovalComment').val());
        callApi.setFormData(form.attr('action'), formData, function(result) {
            alert('승인처리되었습니다.');
            $('#'+result.visitorHistorySeq).remove();
            $('#nv_modal_close').click();
            approveHandler();   // 금일방문객 리랜더링
            dashBoardInit();    // 대시보드 리렌더링
        });
    });
</script>
<div class="nv_contents_header">
    <h4>금일 승인 대기 목록</h4>
</div>
<div class="nv_contents_search">
    <div class="nv_select_box">
        <p id="standby_conditionKey">예약번호</p>
        <ul>
            <li>예약번호</li>
            <li>이름</li>
            <li>방문증번호</li>
        </ul>
    </div>
    <div class="nv_search_box">
        <input type="text" class="nv_input" id="standby_conditionValue">
        <input type="submit" class="nv_search_icon" onclick="javascript:standby_search();">
    </div>
</div>
<div class="nv_table_box">
    <table class="nv_table textcenter" cellspacing="0" cellpadding="0" id="standbyTable">
        <thead>
            <tr>
                <th>예약번호</th>
                <th>성명</th>
                <th class="tpc_skip m_skip">생년월일</th>
                <th class="tpc_skip m_skip">연락처</th>
                <th>회사</th>
                <th class="tpc_skip m_skip">방문기간</th>
                <th class="tpc_skip m_skip">임직원</th>
                <th class="tpc_skip m_skip">내선번호</th>
                <th class="tpc_skip m_skip">방문목적</th>
                <th class="tpc_skip m_skip">반입물품</th>
                <th>방문승인</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
    <div class="nv_table_pagenum" id="standbyTable_pagenation"></div>
</div>
<form id="visitApprovalForm">
    <div class="nv_modal nv_modal2">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>방문 승인</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div>
                    <h4 class="textarea_name">반입물품</h4>
                    <textarea name="carryStuff" id="carryStuff" cols="30" rows="10" class="nv_textarea" placeholder="반입물품 표시"></textarea>
                </div>
                <div>
                    <h4 class="textarea_name">승인 코멘트</h4>
                    <textarea name="visitApprovalComment" id="visitApprovalComment" cols="30" rows="10" class="nv_textarea" placeholder="승인전달사항 입력"></textarea>
                </div>
                <div class="btn_area">
                    <button type="button" class="nv_red_button">취소</button>
                    <button type="submit" class="nv_blue_button">승인</button>
                </div>
            </div>
        </div>
    </div>
</form>