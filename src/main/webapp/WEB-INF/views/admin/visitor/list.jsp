<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
        $('#datepicker2').datepicker('setDate', '+1D');
    });
</script>

<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문 승인 관리</h4>
		</div>
		<div class="btn_left">
			<p class="m_tit nv_bold pc_skip tpc_skip">엑셀 다운로드</p>
			<button type="button" class="nv_green_button down_icon_btn">엑셀
				다운로드</button>
		</div>
		<div class="nv_contents_search nv_contents_search_type2">
			<p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
			<div class="nv_date_box">
				<span class="icon_date">달력 아이콘</span> <input type="text"
					class="nv_input" id="datepicker"> <span>~</span> <input
					type="text" class="nv_input" id="datepicker2">
			</div>
			<p class="m_tit nv_bold pc_skip tpc_skip">검색 조건</p>
			<div class="nv_select_box">
				<p>방문자</p>
				<ul>
					<li>방문자</li>
					<li>방문자</li>
				</ul>
			</div>
			<div class="nv_search_box">
				<input type="text" class="nv_input">
                <input type="submit" class="nv_search_icon">
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
					<tr class="nv_view_nexttable">
						<td>홍길동</td>
						<td>두산인프라코어</td>
						<td class="tpc_skip m_skip">010-4212-2622</td>
						<td class="tpc_skip m_skip">업무협의</td>
						<td>Noth,24층</td>
						<td class="tpc_skip m_skip">2020-11-11</td>
						<td class="tpc_skip m_skip">2020-11-11</td>
						<td class="tpc_skip m_skip">00가0000</td>
						<td class="tpc_skip m_skip">테스트</td>
                        <td class="tpc_skip m_skip">디지털이노베이션</td>
                        <td class="tpc_skip m_skip">인프라기획팀</td>
						<td><button type="button"
                                class="nv_blue_button nv_modal2_open">승인</button>
                                <button type="button" class="nv_red_button width_50">반려</button></td>
					</tr>
					<tr class="pc_skip">
						<td colspan="4" class="nv_hidden_table_area">
							<table class="nv_hidden_table">
								<colgroup>
									<col width="20%">
									<col width="30%">
									<col width="20%">
									<col width="30%">
								</colgroup>
								<tbody>
									<tr>
										<td class="nv_bold">방문기간</td>
										<td colspan="3">2020-04-01 ~ 2020-04-31</td>
									</tr>
									<tr>
										<td class="nv_bold">생년월일</td>
										<td>1991-01-01</td>
										<td class="nv_bold">연락처</td>
										<td>010-1111-2222</td>
									</tr>
									<tr>
										<td class="nv_bold">임직원</td>
										<td>임직원명</td>
										<td class="nv_bold">임직원부서</td>
										<td>임직원부서명</td>
									</tr>
									<tr>
										<td class="nv_bold">방문목적</td>
										<td>내방</td>
										<td class="nv_bold">반입물품</td>
										<td>PC</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					
				</tbody>
			</table>
			<div class="nv_table_pagenum">
				<ul>
					<li class="first">처음으로</li>
					<li class="prev">이전으로</li>
					<li class="on">1</li>
					<li class="next">다음으로</li>
					<li class="last">마지막으로</li>
				</ul>
			</div>
		</div>
	</div>
</div>
