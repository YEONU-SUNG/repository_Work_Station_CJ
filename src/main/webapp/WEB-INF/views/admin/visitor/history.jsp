<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
        
        $("#datepicker").datepicker();                    
        $("#datepicker2").datepicker();
        
        $('#datepicker').datepicker('setDate', 'today');
        $('#datepicker2').datepicker('setDate', 'today');
    });
</script>
<script>
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        makeTable : function(pagenation) {
            if(this.tableData.length==0 && this.pagenation.conditionKey!='') {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                var oData;
                for(var i = 0, len = this.tableData.length; i < len; i++){
                    oData = this.tableData[i];
                    this.tableHTML +=
                                '<tr class="nv_view_nexttable">' +
                                    '<td>' + oData.visitorHistory.visitorName + '</td>' + 
                                    '<td>' + oData.visitorHistory.visitorCompany + '</td>' + 
                                    '<td>' + oData.visitorHistory.visitorMobile + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.visitDate.toString().substring(0, 10) + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.cardID + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.visitPurpose + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.visitorPosition1 + ', ' + oData.visitorHistory.visitorPosition2 + ', ' + oData.visitorHistory.visitorPosition3 + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.planFromDateTime + '~' + oData.visitorHistory.planToDateTime + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitFromDateTime.toString().substring(10) + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitToDateTime.toString().substring(10) + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.hostName + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.hostCompany + '</td>' + 
                                    '<td class="tpc_skip m_skip">' + oData.visitorHistory.hostDept + '</td>' + 
                                '</tr>';
                    this.tableHTML +=   
                                '<tr class="pc_skip">' +
                                    '<td colspan="3" class="nv_hidden_table_area">' +
                                        '<table class="nv_hidden_table">' +
                                            '<colgroup>' +
                                                '<col width="13%">' +
                                                '<col width="20%">' +
                                                '<col width="13%">' +
                                                '<col width="20%">' +
                                                '<col width="13%">' +
                                                '<col width="20%">' +
                                            '</colgroup>' +
                                            '<tbody>' +
                                                '<tr>' +
                                                    '<td class="nv_bold">방문증번호</td>' +
                                                    '<td>'+oData.visitorHistory.cardID+'</td>' +
                                                    '<td class="nv_bold">방문목적</td>' +
                                                    '<td>' +oData.visitorHistory.visitPurpose+ '</td>' +
                                                    '<td class="nv_bold">방문위치</td>' +
                                                    '<td>' +oData.visitorHistory.visitorPosition1 + ', ' + oData.visitorHistory.visitorPosition2 + ', ' + oData.visitorHistory.visitorPosition3+ '</td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<td class="nv_bold">방문일자</td>' +
                                                    '<td>'+oData.visitorHistory.planFromDateTime + '~' + oData.visitorHistory.planToDateTime+'</td>' +
                                                    '<td class="nv_bold">방문일시</td>' +
                                                    '<td>' +oData.visitFromDateTime.toString().substring(10)+ '</td>' +
                                                    '<td class="nv_bold">퇴실일시</td>' +
                                                    '<td>' +oData.visitToDateTime.toString().substring(10)+ '</td>' +
                                                '</tr>' +
                                                '<tr>' +
                                                    '<td class="nv_bold">접견인</td>' +
                                                    '<td>'+oData.visitorHistory.hostName+'</td>' +
                                                    '<td class="nv_bold">접견인 회사</td>' +
                                                    '<td>' +oData.visitorHistory.hostCompany+ '</td>' +
                                                    '<td class="nv_bold">접견인 팀</td>' +
                                                    '<td>' +oData.visitorHistory.hostDept+ '</td>' +
                                                '</tr>' +
                                            '</tbody>' +
                                        '</table>' +
                                    '</td>' +
                                '</tr>';
                }
                // for(var i=0; i<this.tableData.length;i++) {
                //     var strcarryStuffUsed = this.tableData[i].visitorHistory.carryStuffUsed.trim() == "" ? "사용안함":this.tableData[i].visitorHistory.carryStuffUsed;
                    
                // }
                pagenation.html(page.makePagenation(module.pagenation));
                return this.tableHTML;
            }
        }
    }

    function init(url) {
        $('#historyTable > tbody')
        callApi.getData(url, function (result) {
            module.pagenation = result.pagenation;
            module.tableData = result.response;
            $('#historyTable > tbody').html(module.makeTable($('#pagenation')));
        });
    }

    function search() {
        var visitorFromDateTime = $('input[name="visitorFromDateTime"]').val();
        var visitorToDateTime = $('input[name="visitorToDateTime"]').val();
        console.log(visitorFromDateTime);
        console.log(visitorToDateTime);
        var conditionKey = $('#conditionKey').html();
        var conditionValue = $('#conditionValue').val();
        init('/visitor/history-list?page=1&size=10&conditionKey='+conditionKey+"&conditionValue="+conditionValue+'&visitorFromDateTime='+visitorFromDateTime+'&visitorToDateTime='+visitorToDateTime);
    }

    function excel() {
        if(module.tableData.length==0) {
            if(!confirm('조회결과가 존재하지않습니다. 출력하시겠습니까?')) return false;
        }
//        location.href = '/excel'+convertEncoding(module.pagenation.params);
        location.href = convertEncoding(module.pagenation.excelParams);
    }

    $(document).ready(function() {
        var visitorFromDateTime = $('input[name="visitorFromDateTime"]').val();
        var visitorToDateTime = $('input[name="visitorToDateTime"]').val();
        //init('/visitor/history-list?page=1&size=10&visitorFromDateTime='+visitorFromDateTime+'&visitorToDateTime='+visitorToDateTime);
        init('/visitor/history-list?page=1&size=10');

        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
    });

    $(document).on('click', '.nv_modal1_open', function() {
        $('.nv_modal1 textarea').html($(this).find('span').html());
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문 이력 조회</h4>
		</div>
		<div class="btn_left">
			<p class="m_tit nv_bold pc_skip tpc_skip m_skip" onclick="javascript:excel();">엑셀 다운로드</p>
			<button type="button" class="nv_green_button down_icon_btn m_skip" onclick="javascript:excel();">엑셀 다운로드</button>
		</div>
		<div class="nv_contents_search nv_contents_search_type2">
			<p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
			<div class="nv_date_box">
				<span class="icon_date">달력 아이콘</span>
                <input type="text" class="nv_input" id="datepicker" name="visitorFromDateTime">
                <span>~</span>
                <input type="text" class="nv_input" id="datepicker2" name="visitorToDateTime">
			</div>
			<p class="m_tit nv_bold pc_skip tpc_skip">검색 설정</p>
			<div class="nv_select_box">
				<%-- <p id="conditionKey">선택</p> --%>
                <p id="conditionKey">이름</p>
				<ul>
					<%-- <li>예약번호</li> --%>
                    <li>이름</li>
                    <li>회사명</li>
                    <li>방문목적</li>
                    <li>방문증번호</li>
				</ul>
			</div>
			<div class="nv_search_box">
				<input type="text" class="nv_input" id="conditionValue">
                <input type="submit" class="nv_search_icon tpc_skip m_skip" onclick="javascript:search();">
			</div>
            <div class="nv_search_box pc_skip tpc_skip" style="width: 32px;">
				<input type="submit" class="nv_search_icon" onclick="javascript:search();">
			</div>
		</div>
		<div class="nv_table_box">
			<table class="nv_table textcenter" id="historyTable">
				<thead>
					<tr>
                        <th>방문자</th>
                        <th>업체명</th>
						<th>연락처</th>
						<th class="tpc_skip m_skip">신청일</th>
                        <th class="tpc_skip m_skip">방문증번호</th>
                        <th class="tpc_skip m_skip">방문목적</th>
                        <th class="tpc_skip m_skip">방문위치</th>
                        <th class="tpc_skip m_skip">방문일자</th>
						<th class="tpc_skip m_skip">방문일시</th>
                        <th class="tpc_skip m_skip">퇴일일시</th>
                        <th class="tpc_skip m_skip">접견인</th>
                        <th class="tpc_skip m_skip">접견인 회사</th>
                        <th class="tpc_skip m_skip">접견인 팀</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
			<div class="nv_table_pagenum" id="pagenation"></div>
		</div>
	</div>
</div>

<div class="nv_modal nv_modal1">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2>반입 물품 확인</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <div>
                <h4 class="textarea_name">반입물품</h4>
                <textarea cols="30" rows="10" class="nv_textarea" readonly></textarea>
            </div>
            <div class="btn_area">
                <button type="button" class="nv_blue_button" onclick="javascript:$('.nv_modal_close').click();">확인</button>
            </div>
        </div>
    </div>
</div>