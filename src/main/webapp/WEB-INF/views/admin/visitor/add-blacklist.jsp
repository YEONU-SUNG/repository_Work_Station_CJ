<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="/admin/static/js/Inputmask/dist/jquery.inputmask.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<script src="/admin/static/js/xlsx.full.min.js"></script>
<script>
    $(function() {
		$.datepicker.setDefaults({
			dateFormat : 'yy-mm-dd',
			showOtherMonths : true,
			showMonthAfterYear : true,
			buttonImageOnly : true,
			buttonText : "선택",
			yearSuffix : "년",
			monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9','10', '11', '12' ],
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월','9월', '10월', '11월', '12월' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ]
		});		

		$('#plan_from_date').datepicker();
		$('#plan_to_date').datepicker();
		
		$('#plan_from_date').datepicker('setDate', 'today');
		$('#plan_to_date').datepicker('setDate', 'today');

        var today = new Date();
        var maxDate = new Date();
        var dd = maxDate.getDate() + 179;
        maxDate.setDate(dd);

        jQuery("#plan_from_date").datepicker("option", "minDate", today);
        jQuery("#plan_to_date").datepicker("option", "minDate", today);
        jQuery("#plan_to_date").datepicker("option", "maxDate", maxDate);
    });

    $(document).on('change', '#plan_from_date', function(dateText) {
        $("#plan_to_date").val(this.value);

        var temp = this.value.split('-');
        var minDate = new Date(temp[0],temp[1]-1,temp[2]);
        var maxDate = new Date(temp[0],temp[1]-1,temp[2]);
        var dd = maxDate.getDate() + 179;
        maxDate.setDate(dd);

        jQuery("#plan_to_date").datepicker("option", "minDate", minDate);
        jQuery("#plan_to_date").datepicker("option", "maxDate", maxDate);
    }); 
   
    $(document).on('click', '#visitorInfoSave', function(event) {
        var visitorName = $('input[name="visitor_name"]').val();
        var visitorCompany = $('input[name="visitor_company"]').val();
        var visitorPhone = $('input[name="visitor_phone"]').val();
        var blacklistState = $('#blacklistState > p').text();
        var blacklistReason = $('#blacklistReason > p').text();
        var blacklistReasonComment = $('#blacklistReasonComment').val();
        if(blacklistReason=='기타') {
            if(blacklistReasonComment.trim().length()==0) {
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
        visitorForm.append('blacklistState', blacklistState);
        visitorForm.append('blacklistReason', blacklistReason);
        visitorForm.append('blacklistReasonComment', blacklistReasonComment);
        visitorForm.append('planFromDate', planFromDate);
        visitorForm.append('planToDate', planToDate);

        callApi.setFormData('/visitor/blacklist', visitorForm, function(result) {
            alert('정상적으로 등록되었습니다.');
            location.reload();
        });
    });
</script>

<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문 제한 추가</h4>
        </div>
		<div class="nv_table_tit regist_tit">
			<h4>방문자 정보</h4>
        </div>
        
        <div class="nv_table_box">
           
            <dl class="nv_dl_table">
                <dt>성명</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input" style="width: 30%;" id="visitor_name" name="visitor_name">
                </dd>
                <dt>업체명</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input" style="width: 30%;" id="visitor_company" name="visitor_company">
                </dd>
                <dt>연락처</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input pone phone" style="width: 30%;" id="visitor_phone" name="visitor_phone">
                </dd>
                <dt>제한기간</dt>
                <dd class="nv_dd_full nv_date">
                    <input type="text" class="nv_input max_200 birth" id="plan_from_date" name="plan_from_date" />
                    <span>~</span>
                    <input type="text" class="nv_input max_200 birth" id="plan_to_date" name="plan_to_date" />
                </dd>
                <dt>제한상태</dt>
                <dd class="nv_dd_full">
                    <div class="nv_select_box" id="blacklistState">
                        <p>출입제한</p>
                        <ul>
                            <li>출입제한</li>
                            <li>출입허용</li>
                        </ul>
                    </div>
                </dd>
                <dt colspan="2">제한사유</dt>
                <dd class="nv_dd_full">
                    <div class="nv_select_box" id="blacklistReason">
                        <p>보안위반</p>
                        <ul>
                            <li>보안위반</li>
                            <li>경쟁업체/직원</li>
                            <li>방문규정 위반</li>
                            <li>기타</li>
                        </ul>
                    </div>
                </dd>
                <dt>상세내용</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input" id="blacklistReasonComment" name="blacklistReasonComment" placeholder="제한 상세 내용 입력">
                </dd>
            </dl>
        </div>
        <div class="nv_table_box"> 
            <div class="btn_area nv_page_bottomarea">
                <button type="button" class="nv_red_button m_full_btn">취소</button>
                <button type="button" id="visitorInfoSave" class="nv_blue_button m_full_btn">입력 완료</button>
            </div>
        </div>   
	</div>
</div>