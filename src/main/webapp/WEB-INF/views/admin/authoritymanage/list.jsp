<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    function getStrAuth(auth) {
        switch (auth) {
            case '0' : return '시스템담당자';
            case '1' : return '계열사담당자';
            case '2' : return '보안담당자';
            case '4' : return '안내데스크';
            default : return '임직원';
        }
    }
</script>
<c:if test="${sessionScope.login.host.auth eq '0'}">
    <script>
    function authSelect(auth) {
        return  '<div class="nv_select_box">' +
                    '<p>' + getStrAuth(auth) + '</p>' +
                    '<ul>' + 
                        '<li>시스템담당자</li>' +
                        '<li>계열사담당자</li>' +
                        '<li>보안담당자</li>' +
                        '<li>안내데스크</li>' +
                    '</ul>' +
                '</div>';
    }    
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '1'}">
    <script>
    function authSelect(auth) {
        return
             '<div class="nv_select_box">' +
                '<p>' + getStrAuth(auth) + '</p>' +
                '<ul>' + 
                    '<li>계열사담당자</li>' +
                    '<li>보안담당자</li>' +
                    '<li>안내데스크</li>' +
                '</ul>' +
            '</div>';
    }    
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '2'}">
    <script>
    function authSelect(auth) {
        return
             '<div class="nv_select_box">' +
                '<p>' + getStrAuth(auth) + '</p>' +
                '<ul>' + 
                    '<li>보안담당자</li>' +
                    '<li>안내데스크</li>' +
                '</ul>' +
            '</div>';
    }    
    </script>
</c:if>
<script>
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        auth : [],
        makeTable : function(pagenation, type) {
            if(this.tableData.length==0 && type) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i = 0, len = this.tableData.length; i < len; i++){
                    this.tableHTML += 
                                    '<tr class="nv_view_nexttable" id="'+ this.tableData[i].hostID + '">' +
                                        '<td>' + this.tableData[i].hostName + '</td>' +
                                        '<td>' + this.tableData[i].company + '</td>' +
                                        '<td>' + this.tableData[i].deptCD + '</td>' +
                                        '<td>' + authSelect(this.tableData[i].auth) + '</td>' +
                                        '<td><button type="button" class="nv_blue_button" name="modify">변경</button> <button type="button" class="nv_red_button" name="delete">삭제</button></td>' +
                                    '</tr>';
                }
                
                pagenation.html(page.makePagenation(module.pagenation));
                return this.tableHTML;
            }
        }
    }
    
    function init(url, type) {
        type = type || false;
        callApi.getData(url, function (result) {
            module.tableData = result.response;
            module.pagenation = result.pagenation;
            $('#authTable > tbody').html(module.makeTable($('#pagenation'), type));
        });
    }

    function search() {
        init('/auth-list?page=1&size=10&conditionKey='+$('#conditionKey').html()+"&conditionValue="+$('#conditionValue').val(), true);
    }

    $(document).on('click', '#authTable > tbody > tr button', function() {
        var target = $(this);
        var tr = target.parents('.nv_view_nexttable');
        var HostID = tr.attr('id');
        //target.parent().prev().prev().html(module.authSelectBox());
        //target.parent().prev().html(module.authSelectBox());

        if(target.attr('name')=='active'){
            callApi.setData("/active-list/"+HostID, {}, function (result) {
                alert('정상적으로 삭제되었습니다.');
                init(module.pagenation.params);
            })
        }
        else if(target.attr('name')=='delete'){
            callApi.setData("/delete-list/"+HostID, {}, function (result) {
                alert('정상적으로 삭제되었습니다.');
                init(module.pagenation.params);
            })
        }
        else if(target.attr('name')=='modify') {
            callApi.setData("/auth-list/"+HostID, {auth: tr.find('.nv_select_box p').text()}, function (result) {
                alert('정상적으로 변경되었습니다.');
                init(module.pagenation.params);
            })
        }
    });

    $(document).on('click', '.nv_select_box li', function() {
        var hostID = $(this).parent().parent().parent().parent().attr('id');
        $('#auth_'+hostID).val($(this).text());
    });

    //관리자 추가
    function saveAdmin(){
        if(confirm('저장하시겠습니까?')){
            callApi.setData("/auth-list/"+$('#find_id').val(), {auth: $('#authCmbBox p').text()}, function (result) {
                alert('정상적으로 저장되었습니다.');
                init(module.pagenation.params);
                $(".nv_modal2").removeClass("on");
                // $(".nv_modal2").addClass("off");
            })
        }
    }

    $(document).on('keydown', '#host_name', function(event) {
        if (event.keyCode == 13) event.target.nextElementSibling.click();
    });

    $(document).on('click', '.find_modal', function() {
        var target = $(this);
        var targetId = target.next();
        var targetName = target.prev();
        var _targetName = targetName.val().split(']').length==3 ? targetName.val().split(']')[2] : targetName.val();
        callApi.getData('/hostpartner-list?hostName=' + _targetName, function (result) {
            if(result.length == 0 ) {
                alert('이름을 정확히 입력해주세요.');
                return;
            }
            $(".nv_modal1").addClass("on");
            var tableTbody = $('#host_table_tbody');
            tableTbody.empty();
            $.each(result, function(i, e) {
                var _tr = $('<tr>');
                _tr.on('click', function() {
                    targetId.val(e.hostID);
                    targetName.val('['+e.company+']['+e.deptCD+']'+e.hostName);
                    $('.nv_modal1').removeClass('on');
                });
                _tr.append('<td>'+e.company+'</td>');
                _tr.append('<td>'+e.deptCD+'</td>');
                _tr.append('<td>'+e.hostName+'</td>');
                tableTbody.append(_tr);
            });
            
        });
    });

    $(document).ready(function() {
        init('/auth-list?page=1&size=10', true);
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>관리자권한관리</h4>
		</div>
		<div class="nv_m_btn_area nv_bord_btn_area">
            <button type="button" class="nv_blue_button add_icon_btn right" style="margin-right: 30px;" onclick="javascript:$('.nv_modal2').addClass('on')">관리자 추가</button>
        </div>
		<div class="nv_table_box">
			<table class="nv_table textcenter" cellspacing="0" cellpadding="0" id="authTable">
				<thead>
					<tr>
						<th>성명</th>
						<th>회사명</th>
						<th>팀명</th>
						<th>권한</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<div class="nv_table_pagenum" id="pagenation"></div>
		</div>
	</div>
</div>
<div class="nv_modal nv_modal2">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2>관리자 추가 등록</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <div class="nv_table_box">
                <dl class="nv_dl_table">
                    <dt>성명</dt>
                    <dd class="nv_dd_full">
                        <input type="text" class="nv_input" style="width: 80%;" id="host_name" name="host_name" placeholder="임직원 검색">
                        <button type="button" class="nv_blue_button find_modal">찾기</button>
                        <input type="hidden" id="find_id" name="host_id" />
                    </dd>
                    <dt>권한</dt>
                    <dd class="nv_dd_full">
                        <div class="nv_select_box" id="authCmbBox">
                            <p>시스템담당자</p>
                            <ul>
                                <li>시스템담당자</li>
                                <li>보안담당자</li>
                                <li>계열사담당자</li>
                                <li>안내데스크</li>
                            </ul>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn_area nv_page_bottomarea">
                <button type="button" class="nv_blue_button m_full_btn" onclick="saveAdmin();">저장</button>
            </div>
        </div>
    </div>
</div>
<div class="nv_modal nv_modal1">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2 id="find-host-title">임직원 선택</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            
            <table class="nv_table textcenter">
				<thead>
					<tr>
						<th>이름</th>
                        <th>회사</th>
						<th>부서</th>
					</tr>
				</thead>
				<tbody id="host_table_tbody"></tbody>
			</table>
        </div>
    </div>
</div>
