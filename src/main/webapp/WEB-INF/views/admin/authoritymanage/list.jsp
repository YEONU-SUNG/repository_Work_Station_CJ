<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        auth : [],
        authSelectBox : function(auth) {
            var str = auth ? auth : '시스템관리자';

            return '<div class="nv_select_box">' +
                        '<p>' + str + '</p>' +
                        '<ul>' + 
                            '<li>시스템관리자</li>' +
                            '<li>보안담당자</li>' +
                            '<li>계열사담당자</li>' +
                            '<li>안내데스크</li>' +
                        '</ul>' +
                    '</div>';
					
        },
        makeTable : function(pagenation, type) {
            if(this.tableData.length==0 && type) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i = 0, len = this.tableData.length; i < len; i++){
                    this.tableHTML += 
                                    '<tr class="nv_view_nexttable" id="'+ this.tableData[i].host.hostID + '">' +
                                        '<td>' + this.tableData[i].host.hostName + '</td>' +
                                        '<td>' + this.tableData[i].host.company + '</td>' +
                                        '<td>' + this.tableData[i].host.deptCD + '</td>' +
                                        '<td>' + this.authSelectBox(this.tableData[i].host.auth) + '</td>' +
                                        '<td><button type="button" class="nv_blue_button" name="modify">변경</button> <button type="button" class="nv_red_button" name="active">삭제</button></td>' +
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

    $(document).ready(function() {
        init('/auth-list?page=1&size=10');
        
        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
        $('#host_name').keydown(function(event) {
			if (event.keyCode == 13) {
				find_host();
			}
        });
    });

    $(document).on('click', '#authTable > tbody > tr button', function() {
        var target = $(this);
        var tr = target.parents('.nv_view_nexttable');
        var HostID = tr.attr('id');
        //target.parent().prev().prev().html(module.authSelectBox());
        //target.parent().prev().html(module.authSelectBox());

        if(target.attr('name')=='active'){
            callApi.setData("active-list/"+HostID, {}, function (result) {
                alert('정상적으로 삭제되었습니다.');
                init(module.pagenation.params);
            })
        }else if(target.attr('name')=='modify') {
            callApi.setData("auth-list/"+HostID, {auth: tr.find('.nv_select_box p').text()}, function (result) {
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
            callApi.setData("auth-insert/"+$('#host_name').data('host-data').hostID, {auth: $('#authCmbBox p').text()}, function (result) {
                alert('정상적으로 저장되었습니다.');
                init(module.pagenation.params);
                $(".nv_modal2").removeClass("on");
                // $(".nv_modal2").addClass("off");
            })
        }
    }

    function addAdmin(){
        $(".nv_modal2").addClass("on");
    }

    function setHost(data) {
		$("#host_name").val('[' + data.company + '][' + data.deptCD + ']' + data.hostName);
        $('#host_name').data('host-data', data);
	}

    function find_host(){
		if($("#host_name").val()==""){
			alert("피방문자 이름을 입력해 주세요.");
			return;
		}

        $('#host_table_tbody').empty();
		callApi.getData('/host-list?hostName=' + $("#host_name").val(), function (result) {
			var str="";
			var dataSet = {hostname : $("#host_name").val()};
			
			if(result.length > 0){
				if(result.length == 1){
					setHost(result[0]);
				} else {
                    var oData;
					for(var i=0; i < result.length; i++){
                        oData = result[i];
						str = "<tr class='nv_modal_close' id='tr_" + oData.hostID + "' style='cursor:pointer;' onmouseover='this.style.background=\"#fcecae\";' onmouseleave='this.style.background=\"#FFFFFF\";'>";
                        str += "<td>" + oData.hostName + "</td>";
                        str += "<td>" + oData.company + "</td>";
						str += "<td>" + oData.deptCD + "</td>";
						str += "</tr>";
                        $("#host_table_tbody").append(str);

                        $('#tr_' + oData.hostID).data('host-data', oData);
					}
					
                    $('#host_table_tbody').off('click').on('click', 'tr', function(){
                        setHost($(this).data('host-data'));
                    });
				}
                $(".nv_modal1").addClass("on");
                $("#guest_company").focus();
			} else {
				alert("이름을 정확히 입력해 주세요.");
				$("#host_name").focus();
			}
        });
	}
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>관리자권한관리</h4>
		</div>
		<div class="nv_m_btn_area nv_bord_btn_area">
            <button type="button" class="nv_blue_button add_icon_btn right" style="margin-right: 30px;" onclick="javascript:addAdmin();">관리자 추가</button>
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
                        <input type="text" class="nv_input max_200" id="host_name" name="host_name" placeholder="검색할 피방문자 입력">
                        <button type="button" class="nv_blue_button" onclick="find_host();">찾기</button>
                    </dd>
                    <dt>권한</dt>
                    <dd class="nv_dd_full">
                        <div class="nv_select_box" id="authCmbBox">
                            <p>시스템관리자</p>
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
