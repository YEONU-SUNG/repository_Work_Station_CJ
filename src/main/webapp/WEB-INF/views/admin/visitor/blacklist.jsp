<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/admin/static/js/jquery-ui-1.12.1.js"></script>
<link rel="stylesheet" href="/admin/static/css/jquery-ui-1.12.1.css">
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

        $(document).find(".birth").removeClass('hasDatepicker').datepicker();
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
                pagenation.html(page.makePagenation(module.pagenation));
                return this.tableHTML;
            }
        }
    }

    function init(url) {
        $('#historyTable > tbody').empty();
        callApi.getData(url, function (result) {
            module.pagenation = result.pagenation;
            module.tableData = result.response;
            var auth = '${sessionScope.login.host.auth}';
            $('#historyTable > tbody').html(module.makeTable($('#pagenation')));
            $.each(result.response, function(i, e) {
                var tr = $('<tr class="nv_view_nexttable">');
                tr.append('<input type="hidden" name="visitorId" value="'+e.visitorId+'"></input>');
                tr.append('<td>'+e.visitor.visitorName+'</td>');
                tr.append('<td class="tpc_skip m_skip">'+e.visitor.company+'</td>');
                tr.append('<td class="tpc_skip m_skip">'+e.visitor.mobile+'</td>');
                tr.append('<td class="tpc_skip m_skip">'+e.planFromDate+'</td>');
                tr.append('<td class="tpc_skip m_skip">'+e.planToDate+'</td>');
                tr.append('<td>'+e.blacklistState+'</td>');
                tr.append('<td>'+e.blacklistReason+'</td>');
                if(auth!='4')
                {
                var btn = $('<td><button class="nv_blue_button nv_modal2_open">편집</button></td>');
                
                btn.on('click', function() {
                    $('#visitorId').text(e.visitorId);
                    $('#name').text('성명 : '+e.visitor.visitorName);
                    $('#company').text('업체명 : '+e.visitor.company);
                    $('#phone').text('연락처 : '+e.visitor.mobile);
                    $('#visitorId').val(e.visitorId);
                    $('#name').val(e.visitor.visitorName);
                    $('#company').val(e.visitor.company);
                    $('#phone').val(e.visitor.mobile);
                    $('#plan_from_date').val(e.planFromDate);
                    $('#plan_to_date').val(e.planToDate);
                    $('#blacklistState > p').text(e.blacklistState);
                    $('#blacklistReason > p').text(e.blacklistReason);
                    $('#blacklistReasonComment').val(e.comment);
                })
                tr.append(btn);
                }
                $('#historyTable > tbody:eq(0)').append(tr);

                //모바일 상세
                var mtr = $('<tr class="pc_skip"></tr>');
                var mtd = $('<td colspan="4" class="nv_hidden_table_area"></td>');
                var mtable = $('<table class="nv_hidden_table" id="detail_"'+i+'></table>');
                var mcolgroup = $('<colgroup><col width="20%"><col width="30%"><col width="20%"><col width="30%"></colgroup>');
                var mtbody = $('<tbody></tbody>');

                mtable.append(mcolgroup);
                mtable.append(mtbody);
                mtd.append(mtable);
                mtr.append(mtd);
                $('#historyTable tbody:eq(0)').append(mtr);

                var msubtr1 = $('<tr></tr>'); mtbody.append(msubtr1);
                var msubtr2 = $('<tr></tr>'); mtbody.append(msubtr2);

                msubtr1.append('<td class="nv_bold">업체명</td>');
                msubtr1.append('<td>'+e.visitor.company+'</td>');
                msubtr1.append('<td class="nv_bold">연락처</td>');
                msubtr1.append('<td>'+e.visitor.mobile+'</td>');
                msubtr2.append('<td class="nv_bold">시작일</td>');
                msubtr2.append('<td>'+e.planFromDate+'</td>');
                msubtr2.append('<td class="nv_bold">종료일</td>');
                msubtr2.append('<td>'+e.planToDate+'</td>');
            });
        });
    }

    function search() {
        var visitorFromDateTime = $('input[name="visitorFromDateTime"]').val();
        var visitorToDateTime = $('input[name="visitorToDateTime"]').val();
      
        var conditionKey = $('#conditionKey').html();
        var conditionValue = $('#conditionValue').val();
        init('/visitor/black-list?page=1&size=10&conditionKey='+conditionKey+"&conditionValue="+conditionValue+'&visitorFromDateTime='+visitorFromDateTime+'&visitorToDateTime='+visitorToDateTime);
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
        init('/visitor/black-list?page=1&size=10');

        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
    });

    $(document).on('click', '.nv_modal1_open', function() {
        $('.nv_modal1 textarea').html($(this).find('span').html());
    });

    $(document).on('submit', '#visitBlackListForm', function(e) {
        e.preventDefault();
        var visitorName = $('#name').val();
        var visitorCompany = $('#company').val();
        var visitorPhone = $('#phone').val();
        var visitorId = $('#visitorId').val();
        //var visitorId = $('input[name="visitorId"]').val();
        var blacklistState = $('#blacklistState > p').text();
        var blacklistReason = $('#blacklistReason > p').text();
        var blacklistReasonComment = $('#blacklistReasonComment').val();
        if(blacklistReason=='기타') {
            if(blacklistReasonComment.trim().length==0) {
                alert('상세내용을 입력해주세요.');
                return;
            }
        } else {
            blacklistReasonComment = '';
        }
        var planFromDate = $('input[name="plan_from_date"]').val();
        var planToDate = $('input[name="plan_to_date"]').val();
        var visitorForm = new FormData();
        visitorForm.append('visitorName', visitorName);
        visitorForm.append('visitorCompany', visitorCompany);
        visitorForm.append('visitorPhone', visitorPhone);
        visitorForm.append('visitorId', visitorId);
        visitorForm.append('blacklistState', blacklistState);
        visitorForm.append('blacklistReason', blacklistReason);
        visitorForm.append('blacklistReasonComment', blacklistReasonComment);
        visitorForm.append('planFromDate', planFromDate);
        visitorForm.append('planToDate', planToDate);

        callApi.setFormData('/visitor/blacklist', visitorForm, function(result) {
            alert('정상적으로 등록되었습니다.');
            location.reload();
        });
    })
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
            <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2' or sessionScope.login.host.auth eq '3'}">
			<h4>방문 제한자 관리</h4>
            </c:if>
            <c:if test="${sessionScope.login.host.auth eq '4'}">
			<h4>방문 제한자 리스트</h4>
            </c:if>
		</div>
		<div class="btn_left">
			<p class="m_tit nv_bold pc_skip tpc_skip m_skip" onclick="javascript:excel();">엑셀 다운로드</p>
			<button type="button" class="nv_green_button down_icon_btn m_skip" onclick="javascript:excel();">엑셀 다운로드</button>
		</div>
		<div class="nv_contents_search nv_contents_search_type2">
			<%-- <p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
			<div class="nv_date_box">
				<span class="icon_date">달력 아이콘</span>
                <input type="text" class="nv_input" id="datepicker" name="visitorFromDateTime" title="datapicker">
                <span>~</span>
                <input type="text" class="nv_input" id="datepicker2" name="visitorToDateTime" title="datapicker">
			</div> --%>
			<p class="m_tit nv_bold pc_skip tpc_skip">검색 설정</p>
			<div class="nv_select_box">
				<%-- <p id="conditionKey">선택</p> --%>
                <p id="conditionKey">이름</p>
				<ul>
					<%-- <li>예약번호</li> --%>
                    <li>이름</li>
                    <li>회사명</li>
                    <%-- <li>방문목적</li>
                    <li>방문증번호</li> --%>
				</ul>
			</div>
			<div class="nv_search_box">
				<input type="text" class="nv_input" id="conditionValue" title="conditionValue">
                <input type="submit" class="nv_search_icon tpc_skip m_skip" onclick="javascript:search();" title="Search">
			</div>
            <div class="nv_search_box pc_skip tpc_skip" style="width: 32px;">
				<input type="submit" class="nv_search_icon" onclick="javascript:search();" title="Search" >
			</div>
		</div>
		<div class="nv_table_box">
            <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2' or sessionScope.login.host.auth eq '3'}">
            <div class="nv_m_btn_area nv_bord_btn_area">
                <button type="button" class="nv_blue_button add_icon_btn right" onclick="javascript:location.href='add-blacklist'" style="margin-right: 30px;">방문 제한자 추가</button>
            </div>
            </c:if>
			<table class="nv_table textcenter" id="historyTable" summary="blacklist">
                <caption style="display: none;">blacklist</caption>
				<thead>
					<tr>
                        <th>성명</th>
                        <th class="tpc_skip m_skip">업체명</th>
						<th class="tpc_skip m_skip">연락처</th>
						<th class="tpc_skip m_skip">시작일</th>
                        <th class="tpc_skip m_skip">종료일</th>
                        <th>상태</th>
                        <th>제한사유</th>
                        <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2' or sessionScope.login.host.auth eq '3'}">
                        <th>제한 관리</th>
                        </c:if>
					</tr>
				</thead>
				<tbody>
					
                </tbody>
                <tfoot></tfoot>
			</table>
			<div class="nv_table_pagenum" id="pagenation"></div>
		</div>
	</div>
</div>
<form id="visitBlackListForm">
<div class="nv_modal nv_modal2" id="nv_modal_2">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2>출입 제한 편집</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <h4 class="textarea_name" id="visitorId" style="display: none;" ></h4>
            <div>
                <h4 class="textarea_name" id="name">성명</h4>
            </div>
            <div>
                <h4 class="textarea_name" id="company">업체명</h4>
            </div>
            <div>
                <h4 class="textarea_name" id="phone">연락처</h4>
            </div>
            <div>
                <h4 class="textarea_name">제한기간</h4>
                <input type="text" class="nv_input max_200 birth" id="plan_from_date" name="plan_from_date" title="Plan" />
                <span>~</span>
                <input type="text" class="nv_input max_200 birth" id="plan_to_date" name="plan_to_date"  title="Plan" />
            </div>
            <div>
                <!-- <h4 class="textarea_name">제한상태
                    <div class="nv_select_box" id="blacklistState" style="float:right; margin:10px 0;">
                        <p>선택</p>
                        <ul> 
                            <li>출입제한</li>
                            <li>출입허용</li>
                        </ul>
                    </div>
                </h4> -->
                <div class="textarea_name">제한상태
                    <div class="nv_select_box" id="blacklistState" style="float:right; margin:10px 0;">
                        <p>선택</p>
                        <ul> 
                            <li>출입제한</li>
                            <li>출입허용</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div>
                <!-- <h4 class="textarea_name">제한사유
                    <div class="nv_select_box" id="blacklistReason" style="float:right; margin:10px 0;">
                        <p>선택</p>
                        <ul> 
                            <li>보안위반</li>
                            <li>경쟁업체/직원</li>
                            <li>방문규정 위반</li>
                            <li>기타</li>
                        </ul>
                    </div>
                </h4> -->
                <div class="textarea_name">제한사유
                    <div class="nv_select_box" id="blacklistReason" style="float:right; margin:10px 0;">
                        <p>선택</p>
                        <ul> 
                            <li>보안위반</li>
                            <li>경쟁업체/직원</li>
                            <li>방문규정 위반</li>
                            <li>기타</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div>
                <h4 class="textarea_name">상세내용</h4>
                <textarea name="blacklistReasonComment" id="blacklistReasonComment" cols="30" rows="10" class="nv_textarea" placeholder="제한 상세 내용 입력"></textarea>
            </div>
            <div class="btn_area">
                <button type="submit" class="nv_blue_button" onclick="javascript:$('#nv_modal_2').removeClass('on');">확인</button>
                <button type="button" class="nv_red_button">취소</button>
            </div>
        </div>
    </div>
</div>
</form>