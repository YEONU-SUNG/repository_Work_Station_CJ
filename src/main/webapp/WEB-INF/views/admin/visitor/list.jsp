<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script>
    $(function() {
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd'
            ,showOtherMonths: true
            ,showMonthAfterYear:true
            ,buttonImageOnly: true
            ,buttonText: "선택"       
            ,yearSuffix: "년"
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            ,dayNamesMin: ['일','월','화','수','목','금','토']
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
        });
        
        $("#searchFromDateTime").datepicker();                    
        $("#searchToDateTime").datepicker();
        
        $('#searchFromDateTime').datepicker('setDate', 'today');
        $('#searchToDateTime').datepicker('setDate', '+1D');
    });
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        auth : [],
        authSelectBox : function(auth) {
            var str = auth ? auth : '관리자';

            return '<div class="nv_select_box">' +
                        '<p>' + str + '</p>' +
                        '<ul>' + 
                            '<li>관리자</li>' +
                            '<li>보안실</li>' + 
                            '<li>임직원</li>' + 
                        '</ul>' +
                    '</div>';
					
        },
        makeTable : function(pagenation, type) {
            if(this.tableData.length==0 && type) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                var oData;
                for(var i = 0, len = this.tableData.length; i < len; i++){
                    oData = this.tableData[i];
                    this.tableHTML += 
                                    '<tr class="nv_view_nexttable" id="' + oData.visitorHistorySeq + '">' +
                                        '<td>' + oData.visitorName + '</td>' +
                                        '<td>' + oData.visitorCompany + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.visitorMobile + '</td>' + 
                                        '<td class="tpc_skip m_skip">' + oData.visitPurpose + '</td>' +
                                        '<td>' + oData.visitorPosition1 + ' ' + oData.visitorPosition2 + ' ' + oData.visitorPosition3 + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.planFromDateTime + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.planToDateTime + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.carNo + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.hostName + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.hostCompany + '</td>' +
                                        '<td class="tpc_skip m_skip">' + oData.hostDept + '</td>' +
                                        '<td>' +
                                            '<button type="button" class="nv_blue_button" name="approve">승인</button>' +
                                            '<button type="button" class="nv_red_button width_50 nv_modal5_open" name="reject">반려</button>' +
                                        '</td>' +
                                    '</tr>'
                }
                
                pagenation.html(makePagenationCustom(module.pagenation));
                return this.tableHTML;
            }
        }
    }

    function makePagenationCustom(result){
        var searchFromDateTime = $('#searchFromDateTime').val();
        var searchToDateTime = $('#searchToDateTime').val();

        let init = 'init';
        let limitIndex = 0, breakIndex =0;
        if(result.url==='/visitor/standby-list') init = 'standby_init';
        let pagenationHTML = '';
        pagenationHTML =   '<ul>' +
                                '<li class="first" onclick="javascript:'+init+'(\''+result.firstURL + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime+'\')">처음으로</li>' +
                                '<li class="prev" onclick="javascript:'+init+'(\''+result.prevURL + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime+'\')">이전으로</li>';
        
        if(result.page <= 5) {
            breakIndex = 1;
        } else if (result.page > 5) {
            breakIndex = result.page-5;
        }
        limitIndex = breakIndex + 9;
        for(var i=1; i<=result.totalPage; i++) {
            if(breakIndex <= i && limitIndex>= i) 
                pagenationHTML += (result.page===i)
                                ? '<li class="on" onclick="javascript:'+init+'(\''+(result.url+'?page='+result.page+'&size='+result.size + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime)+result.conditionURL+'\')">'+(i)+'</li>'
                                : '<li onclick="javascript:'+init+'(\''+(result.url+'?page='+i+'&size='+result.size + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime)+result.conditionURL+'\')">'+(i)+'</li>';
        }
        pagenationHTML += 	    '<li class="next" onclick="javascript:'+init+'(\''+result.nextURL + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime+'\')">다음으로</li>' +
                                '<li class="last" onclick="javascript:'+init+'(\''+result.lastURL + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime+'\')">마지막으로</li>' +
                            '</ul>';
        return pagenationHTML;
    }
    
    function init(url, type) {
        type = type || false;
        callApi.getData(url, function (result) {
            module.tableData = result.response;
            module.pagenation = result.pagenation;
            $('table > tbody').html(module.makeTable($('#pagenation'), type));
        });
    }

    function search() {

        var searchFromDateTime = $('#searchFromDateTime').val();
        var searchToDateTime = $('#searchToDateTime').val();
        var conditionKey = $('#conditionKey').html();
        var conditionValue = $('#conditionValue').val();
        init('/visitor/confirm-list?page=1&size=10&conditionKey='+conditionKey+"&conditionValue="+conditionValue+"&searchFromDateTime="+searchFromDateTime+"&searchToDateTime="+searchToDateTime);
    }

    $(document).ready(function() {
        var searchFromDateTime = $('#searchFromDateTime').val();
        var searchToDateTime = $('#searchToDateTime').val();

        init('/visitor/confirm-list?page=1&size=10&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime);
        
        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
    });

    $(document).on('click', 'table tbody tr button', function() {
        var target = $(this);
        var targetId = target.parents('tr').attr('id');
        //target.parent().prev().prev().html(module.authSelectBox());
        //target.parent().prev().html(module.authSelectBox());

        if(target.attr('name')=='approve'){
            var searchFromDateTime = $('#searchFromDateTime').val();
            var searchToDateTime = $('#searchToDateTime').val();

            callApi.setData('/visitor-approval/'+targetId, {}, function (result) {
                init(module.pagenation.params + '&searchFromDateTime='+searchFromDateTime+'&searchToDateTime='+searchToDateTime);     // 금일방문객 리랜더링
                dashBoardInit();
            })
        }else if(target.attr('name')=='reject') {
            $('#visitRejectForm').attr('action', '/visitor-reject/'+targetId);
        }
    });

    $(document).on('click', '.nv_modal5 button.nv_green_button', function() {
        var form = $('#visitRejectForm');
        var url =  form.attr('action');
        var targetId = url.replace('/visitor-reject/', '');
        var formData = new FormData();
        formData.append('visitRejectType', $('#nv5_rejectCmbBox').find('p').text().trim());
        formData.append('visitRejectComment', $('#nv5_rejectComment').val());
        formData.append('visitorHistorySeq', targetId);
        callApi.setFormData(url, formData, function(result) {
            alert('승인거부처리되었습니다.');
            form.children().removeClass('on');
            init(module.pagenation.params);     // 금일방문객 리랜더링
            dashBoardInit();                    // 대시보드 리렌더링
        });
    });
</script>

<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문 승인 관리</h4>
		</div>
		<div class="nv_contents_search nv_contents_search_type2">
			<p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
			<div class="nv_date_box">
                <span class="icon_date">달력 아이콘</span> 
                <input type="text" class="nv_input" id="searchFromDateTime"> 
                <span>~</span> 
                <input type="text" class="nv_input" id="searchToDateTime">
			</div>
			<p class="m_tit nv_bold pc_skip tpc_skip">검색 조건</p>
			<div class="nv_select_box">
                <p id="conditionKey">방문자</p>
                <ul>
                    <li>방문자</li>
                    <li>연락처</li>
                    <li>업체명</li>
                </ul>
            </div>
			<div class="nv_search_box">
				<input type="text" class="nv_input" id="conditionValue">
                <input type="submit" class="nv_search_icon" onclick="search();">
			</div>
		</div>
		<div class="nv_table_box">
			<table class="nv_table textcenter" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>방문자</th>
						<th>업체명</th>
						<th class="tpc_skip m_skip">연락처</th>
						<th class="tpc_skip m_skip">방문목적</th>
						<th>방문위치</th>
						<th class="tpc_skip m_skip">방문시작일</th>
						<th class="tpc_skip m_skip">방문종료일</th>
						<th class="tpc_skip m_skip">차량번호</th>
						<th class="tpc_skip m_skip">접견인</th>
                        <th class="tpc_skip m_skip">접견인 회사</th>
                        <th class="tpc_skip m_skip">접견인 팀</th>
						<th>승인관리</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<div class="nv_table_pagenum" id="pagenation"></div>
		</div>
	</div>
</div>
<form id="visitRejectForm">
    <div class="nv_modal nv_modal5">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>방문 반려사유</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div>
                    <h4 class="textarea_name">반려사유
                        <div class="nv_select_box" id="nv5_rejectCmbBox" style="float:right; margin:10px 0;">
                            <p>선택</p>
                            <ul> 
                                <li>규칙위반</li>
                                <li>보안위반</li>
                                <li>기타</li>
                            </ul>
                        </div>
                    </h4>
                    <textarea name="rejectComment" id="nv5_rejectComment" cols="30" rows="10" class="nv_textarea" placeholder="반려사유 입력"></textarea>
                </div>
                <div class="btn_area">
                    <button type="button" class="nv_green_button">반려</button>
                    <button type="button" class="nv_red_button">취소</button>
                </div>
            </div>
        </div>
    </div>
</form>