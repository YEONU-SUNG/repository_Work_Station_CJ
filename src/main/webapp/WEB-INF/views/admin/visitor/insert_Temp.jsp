<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
	$(function() {
		$.datepicker.setDefaults({
			dateFormat : 'yy-mm-dd',
			showOtherMonths : true,
			showMonthAfterYear : true,
			buttonImageOnly : true,
			buttonText : "선택",
			yearSuffix : "년",
			monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'10', '11', '12' ],
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ]
		});

		$("#datepicker").datepicker();
		$("#datepicker2").datepicker();
		$("#datepicker3").datepicker();
		$("#datepicker4").datepicker();

		$('#datepicker').datepicker('setDate', 'today');
		$('#datepicker2').datepicker('setDate', 'today');
		$('#datepicker3').datepicker('setDate', '+1D');
		$('#datepicker4').datepicker('setDate', 'today');
	});
</script>
<script>
    $(document).on('click', '#vistorPurpose > ul li', function() {
        $('#pc_import_info').css('display', 'none');
        $('#pc_import_info').next().css('display', 'none');
        $('#carNumber').css('display', 'none');
        $('#carNumber').next().css('display', 'none');
        switch ($(this).html()) {
            case '내방' : 
                $('#pc_import_info').css('display', '');
                $('#pc_import_info').next().css('display', '');
                break;
            case '작업' : 
                $('#pc_import_info').css('display', '');
                $('#pc_import_info').next().css('display', '');
                break;
            case '납품' : 
                $('#carNumber').css('display', '');
                $('#carNumber').next().css('display', '');
                break;
        }
    });

</script>

<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문등록</h4>
		</div>
		<div class="nv_table_tit">
			<h4>방문 신청 정보</h4>
		</div>
		<div class="nv_table_box">
			<dl class="nv_dl_table">
				<dt>방문부서</dt>
				<dd>
					<input type="text" class="nv_input" placeholder="부서명을 입력해주세요.">
				</dd>
				<dt>피방문자</dt>
				<dd>
					<input type="text" class="nv_input max_200">
					<button type="button" class="nv_blue_button">확인</button>
				</dd>
				<dt>이름</dt>
				<dd>
					<input type="text" class="nv_input" placeholder="이름을 입력해주세요.">
				</dd>
				<dt>생년월일</dt>
				<dd>
					<input type="text" class="nv_input" id="datepicker">
				</dd>
				<dt>소속회사</dt>
				<dd>
					<input type="text" class="nv_input" placeholder="회사명을 입력해주세요.">
				</dd>
				<dt>연락처</dt>
				<dd class="nv_phone_area">
					<div class="nv_select_box">
						<p>010</p>
						<ul>
							<li>010</li>
							<li>011</li>
							<li>070</li>
						</ul>
					</div>
					<span>-</span> <input type="text" class="nv_input"> <span>-</span>
					<input type="text" class="nv_input">
				</dd>
                <dt>방문목적</dt>
				<dd>
					<div class="nv_select_box" id="vistorPurpose">
						<p>내방</p>
						<ul>
							<li>내방</li>
                            <li>작업</li>
							<li>납품</li>
						</ul>
					</div>
				</dd>
				<dt>방문기간</dt>
				<dd class="nv_date">
					<input type="text" class="nv_input max_200" id="datepicker2">
					<span>~</span>
                    <input type="text" class="nv_input max_200" id="datepicker3">
				</dd>
                <dt>방문위치</dt>
                <dd class="nv_date nv_double_select">
                    <div class="nv_select_box">
                        <p>화성</p>
                        <ul>
                            <li>화성</li>
                            <li>아산</li>
                        </ul>
                    </div>
                    <div class="nv_select_box">
                        <p>분류2</p>
                        <ul>
                            <li>옵션1</li>
                            <li>옵션2</li>
                        </ul>
                    </div>
                </dd>
                <dt id="carNumber" style="display:none;">방문차량번호</dt>
				<dd style="display:none;">
					<input type="text" class="nv_input" placeholder="차량번호를 입력해주세요.">
				</dd>
			</dl>
		</div>
		<div class="nv_table_tit" id="pc_import_info">
			<h4>PC 반입 정보</h4>
		</div>
		<div class="nv_table_box">
			<dl class="nv_dl_table">
				<dt>시리얼번호</dt>
				<dd>
					<input type="text" class="nv_input" placeholder="시리얼번호를 입력해주세요.">
				</dd>
				<dt>사용목적</dt>
				<dd>
					<div class="nv_select_box">
						<p>시스템 A/S</p>
						<ul>
							<li>시스템 A/S</li>
							<li>시스템 A/S</li>
						</ul>
					</div>
				</dd>
				<dt>사용유무</dt>
				<dd class="nv_dd_full nv_check_area">
                    <input type="checkbox" id="n1" class="nv_checkbox">
                    <label for="n1">LAN 사용</label>
                    <input type="checkbox" id="n2" class="nv_checkbox">
                    <label for="n2">USB 사용</label>
                    <input type="checkbox" id="n3" class="nv_checkbox">
                    <label for="n3">USB 사용 (마우스)</label>
				</dd>
			</dl>
		</div>
		<div class="nv_table_tit nv_btn_area_tit">
			<h4>방문객 정보</h4>
			<div class="btn_area">
                <button type="button" class="nv_green_button down_icon_btn">엑셀 다운로드</button>
                <button type="button" class="nv_green_button up_icon_btn">엑셀 일괄 등록</button>
                <button type="button" class="nv_blue_button add_icon_btn">방문객 추가</button>
			</div>
		</div>
		<div class="nv_table_box">
			<table class="nv_table textcenter tpc_skip m_skip" cellspacing="0"
				cellpadding="0">
				<thead>
					<tr>
						<th>내/외국인</th>
						<th>방문자명</th>
						<th>생년월일</th>
						<th>연락처</th>
						<th>반입물품</th>
						<th>차량정보</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="nv_select_box">
								<p>내국인</p>
								<ul>
									<li>내국인</li>
									<li>외국인</li>
								</ul>
							</div>
						</td>
						<td><input type="text" class="nv_input"
							placeholder="이름을 입력해주세요."></td>
						<td><input type="text" class="nv_input" id="datepicker4">
						</td>
						<td class="nv_phone_area">
							<div class="nv_select_box">
								<p>010</p>
								<ul>
									<li>010</li>
									<li>011</li>
									<li>070</li>
								</ul>
							</div> <span>-</span> <input type="text" class="nv_input"> <span>-</span>
							<input type="text" class="nv_input">
						</td>
						<td>
							<button type="button" class="nv_blue_button">등록</button>
						</td>
						<td>
							<button type="button" class="nv_blue_button">등록</button>
						</td>
						<td>
							<button type="button" class="nv_red_button">삭제</button>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="nv_table nv_hidden_table_type2 nv_m_table pc_skip"
				cellspacing="0" cellpadding="0">
				<colgorup>
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				</colgorup>
				<thead>
					<tr>
						<th class="textleft">1</th>
						<th class="textright" colspan="3">
							<p class="table_delete_btn">삭제</p>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="nv_bold textcenter">내/외국인</td>
						<td colspan="3">
							<div class="nv_select_box">
								<p>내국인</p>
								<ul>
									<li>내국인</li>
									<li>외국인</li>
								</ul>
							</div>
						</td>
					</tr>
					<tr>
						<td class="nv_bold textcenter">방문자명</td>
						<td colspan="3"><input type="text" class="nv_input"
							placeholder="이름을 입력해주세요."></td>
					</tr>
					<tr>
						<td class="nv_bold textcenter">생년월일</td>
						<td colspan="3"><input type="text" class="nv_input">
						</td>
					</tr>
					<tr>
						<td class="nv_bold textcenter">연락처</td>
						<td class="nv_phone_area" colspan="3">
							<div class="nv_select_box">
								<p>010</p>
								<ul>
									<li>010</li>
									<li>011</li>
									<li>070</li>
								</ul>
							</div> <span>-</span> <input type="text" class="nv_input"> <span>-</span>
							<input type="text" class="nv_input">
						</td>
					</tr>
					<tr>
						<td class="nv_bold textcenter">반입물품</td>
						<td>
							<button type="button" class="nv_blue_button">등록</button>
						</td>
						<td class="nv_bold textcenter">차량정보</td>
						<td>
							<button type="button" class="nv_blue_button">등록</button>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btn_area nv_page_bottomarea">
				<button type="button" class="nv_blue_button m_full_btn">입력
					완료</button>
			</div>
		</div>
	</div>
</div>
