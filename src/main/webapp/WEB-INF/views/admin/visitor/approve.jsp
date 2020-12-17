<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
    .rejectColor {
        color: red;
    }
</style>
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

    const date = {
        today : new Date(),
        compare : function(compareDate) {
            let _today = this.today.getFullYear()+'-'+this.today.getMonth()+'-'+this.today.getDay();
            let _compareDate = compareDate.getFullYear()+'-'+compareDate.getMonth()+'-'+compareDate.getDay();
            //return (_today==_compareDate);
            return (_today>=_compareDate);
        }
    }

    var approveProcessStatus = {
        visitorApprove : function(row) {
            if(row.eduCompleteDateTime==null || row.eduCompleteDateTime.length==0) {
                return '교육미수료';
            }
            if(row.rejectFlag==='Y'){
                return '';
            }
            if(row.visitApprovalYN==='Y') {
                return '승인완료';
            } else {
                return '승인대기';
            }
        }
    };
</script>
<c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1'}">
    <script>
        var approveActionCommponet = {
            visitorButton : function(row) {
                if(row.eduCompleteDateTime==null || row.eduCompleteDateTime.length==0) {
                    return '';
                }
                if(row.rejectFlag==='Y'){
                    return '';
                }
                if(row.visitApprovalYN==='Y') {
                    if(row.toDayYN==='Y')
                    {
                        for(var i in row.visitorInoutTimes) {
                            //var visitDate = new Date(row.visitorInoutTimes[i].visitFromDateTime);
                            var ds = row.visitorInoutTimes[i].visitFromDateTime;
                            var arr = ds.split("-");  // 2018,01,01 00:10:11
                            var visitDate = new Date(arr[0] + "/" + arr[1] + "/" + arr[2]);
                            if(date.compare(visitDate)) {
                                if(row.visitorInoutTimes[i].visitToDateTime=='')
                                    // return '<button type="button" class="nv_red_button">퇴실</button>';
                                    return '<button type="button" class="nv_red_button">출문</button>';
                                else 
                                    $('#'+row.visitorHistorySeq).remove();
                            }
                        }
                        // return '<button type="button" class="nv_blue_button">방문</button>';
                        return '<button type="button" class="nv_blue_button nv_green_button">입문</button>';
                    }
                    else if(row.toDayYN==='N')
                        return '';
                } else {
                    if(row.visitorType == 1) {
                        return '<button type="button" class="nv_blue_button" value="approve">승인</button>' +
                           '<button type="button" class="nv_red_button" value="reject">반려</button>';
                    } else {
                        return '<button type="button" class="nv_blue_button" value="approve2">승인</button>' +
                           '<button type="button" class="nv_red_button" value="reject">반려</button>';
                    }
                }
            }
        };
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '2'}">
    <script>
        var approveActionCommponet = {
            visitorButton : function(row) {
                if(row.eduCompleteDateTime==null || row.eduCompleteDateTime.length==0) {
                    return '미수료';
                }
                if(row.visitApprovalYN==='Y') {
                    if(row.toDayYN==='Y') {
                        for(var i in row.visitorInoutTimes) {
                            var ds = row.visitorInoutTimes[i].visitFromDateTime;
                            var arr = ds.split("-");  // 2018,01,01 00:10:11
                            var visitDate = new Date(arr[0] + "/" + arr[1] + "/" + arr[2]);
                            if(date.compare(visitDate)) {
                                if(row.visitorInoutTimes[i].visitToDateTime=='')
                                    return '<button type="button" class="nv_red_button">출문</button>';
                                else 
                                    $('#'+row.visitorHistorySeq).remove();
                            }
                        }
                        return '<button type="button" class="nv_blue_button nv_green_button">입문</button>';
                    }
                    else if(row.toDayYN==='N') return '승인완료';
                } else {
                    return '승인대기';
                }
            }
        };
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '3'}">
    <script>
        var approveActionCommponet = {
            visitorButton : function(row) {
                if(row.eduCompleteDateTime==null || row.eduCompleteDateTime.length==0) {
                    return '미수료';
                }
                if(row.visitApprovalYN==='Y') {
                    for(var i in row.visitorInoutTimes) {
                        //var visitDate = new Date(row.visitorInoutTimes[i].visitFromDateTime);
                        var ds = row.visitorInoutTimes[i].visitFromDateTime;
                            var arr = ds.split("-");  // 2018,01,01 00:10:11
                            var visitDate = new Date(arr[0] + "/" + arr[1] + "/" + arr[2]);
                        if(date.compare(visitDate)) {
                            if(row.visitorInoutTimes[i].visitToDateTime=='')
                                // return '방문';
                                return '입문';
                            else 
                                $('#'+row.visitorHistorySeq).remove();
                        }
                    }
                    //return '방문';
                    return '승인완료';
                } else {
                    return '<button type="button" class="nv_blue_button" value="approve">승인</button>' +
                           '<button type="button" class="nv_red_button" value="reject">반려</button>';
                }
            }
        };
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '4'}">
    <script>
        var approveActionCommponet = {
            visitorButton : function(row) {
                if(row.eduCompleteDateTime==null || row.eduCompleteDateTime.length==0) {
                    return '미수료';
                }
                if(row.visitApprovalYN==='Y') {
                    if(row.toDayYN==='Y')
                    {
                        for(var i in row.visitorInoutTimes) {
                            //var visitDate = new Date(row.visitorInoutTimes[i].visitFromDateTime);
                            var ds = row.visitorInoutTimes[i].visitFromDateTime;
                            var arr = ds.split("-");  // 2018,01,01 00:10:11
                            var visitDate = new Date(arr[0] + "/" + arr[1] + "/" + arr[2]);
                            if(date.compare(visitDate)) {
                                if(row.visitorInoutTimes[i].visitToDateTime=='')
                                    // return '<button type="button" class="nv_red_button">퇴실</button>';
                                    return '<button type="button" class="nv_red_button">출문</button>';
                                else 
                                    $('#'+row.visitorHistorySeq).remove();
                            }
                        }
                        // return '<button type="button" class="nv_blue_button">방문</button>';
                        return '<button type="button" class="nv_blue_button nv_green_button">입문</button>';
                    }
                    else if(row.toDayYN==='N')
                        return '';
                }
                else {
                    return '';
                }
            }
        };
    </script>
</c:if>
<script>
    var inoutMode = false;
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        search : function() {
            var searchFromDateTime = $('input[name="searchFromDateTime"]').val();
            var searchToDateTime = $('input[name="searchToDateTime"]').val();
            var conditionKey = $('#conditionKey').html();
            var conditionValue = $('#conditionValue').val();
            init('/visitor/approve-list?page=1&size=10&conditionKey='+conditionKey+"&conditionValue="+conditionValue+"&searchFromDateTime"+searchFromDateTime+"&searchToDateTime"+searchToDateTime);
        },
        makeTd : function(className, innerHTML) {
            return $('<td class="'+className+'">'+innerHTML+'</td>');
        },
        makeTable : function(pagenation) {
            if(this.tableData.length==0 && this.pagenation.conditionKey!='' && !inoutMode) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            }
        },
        getFloorCheckBox : function(checked, floor) {
            return '<input type="checkbox" name="floor" id="'+floor+'" value="'+floor+'" '+checked+'/><label style="padding-right:2%;" for="'+floor+'">'+floor+'</label>';
        },
        getBuildingSelectBox : function() {
            return '<h4 class="textarea_name" style="padding-bottom: 10px;">건물명' +
                    '<div class="nv_select_box" id="accessBox" style="float:right; margin:10px 0;">' +
                        '<p>건물선택</p>' +
                        '</div>' +
                '</h4>' +
                '<div id="floorBox"></div>';
        }
    }

    function buttonTypeChange(buttonType) {
        buttonType.removeClass('on');
        if(buttonType.attr('id')=='selectorModify') buttonType.next().addClass('on');
        else buttonType.prev().addClass('on');
    }

    function init(url) {
        callApi.getData(url, function (result) {
                
            module.pagenation = result.pagenation;
            module.tableData = result.response;
            var auth = '${sessionScope.login.host.auth}';
            $('#pagenation').html(page.makePagenation(module.pagenation));
            $.each(result.response, function(i, e) {
                var tr = $('<tr class="nv_view_next table" id="'+e.visitorHistorySeq+'">');
                    tr.append(module.makeTd('', e.visitorName));
                    tr.append(module.makeTd('', e.visitorCompany));
                    tr.append(module.makeTd('tpc_skip m_skip', e.visitorMobile));
                    tr.append(module.makeTd('tpc_skip m_skip', e.visitPurpose));
                    tr.append(module.makeTd('tpc_skip m_skip', e.visitorPosition1+','+e.visitorPosition2+','+e.visitorPosition3));
                    tr.append(module.makeTd('tpc_skip m_skip', e.planFromDateTime));
                    tr.append(module.makeTd('tpc_skip m_skip', e.planToDateTime));
                    tr.append(module.makeTd('tpc_skip m_skip', e.carNo));
                    tr.append(module.makeTd('tpc_skip m_skip', e.hostName));
                    tr.append(module.makeTd('tpc_skip m_skip', e.hostCompany));
                    tr.append(module.makeTd('tpc_skip m_skip', e.hostDept));

                    // 방문증번호 로직
                    var innerHTML_CardID = module.makeTd('card', e.cardID);
                    var visitorStatus = approveActionCommponet.visitorButton(e);
                    // 임직원인 경우 승인되었을때 승인관리 비활성화
                    if(e.visitorType == 1 && e.visitApprovalYN == 'Y') visitorStatus = '';

                    var approveManagerBtn = module.makeTd('approvechidren', visitorStatus);
                    if(auth!='3') {
                        // 임직원이 아닐때
                        if(e.visitorType == 2) {
                            // 외부인인 경우 방문증 입력 폼 활성화
                            if(visitorStatus.indexOf('입문')!=-1 || visitorStatus.indexOf('출문')!=-1) {
                                var cardInput = $('<input type="text" class="nv_input max_100" value="'+e.cardID+'"/>');
                                innerHTML_CardID.html(cardInput);

                                approveManagerBtn.on('click', function() {
                                    callApi.setFormData('/visitor-inout/'+e.visitorHistorySeq+'/'+cardInput.val(), {}, function(result) {
                                        location.reload();
                                    })
                                });

                            }
                            tr.append(innerHTML_CardID);
                        } else tr.append(module.makeTd('tpc_skip m_skip', ''));
                    } else if (auth == '4') {
                        tr.append(module.makeTd('tpc_skip m_skip', innerHTML_CardID));
                    }

                    tr.append(module.makeTd('approvechidren', approveProcessStatus.visitorApprove(e)));

                    var rejectName = '';
                    if(e.rejectFlag==='Y'){
                        switch (e.rejectType) {
                            case 1 : rejectName = '보안위반'; break;
                            case 2 : rejectName = '경쟁업체/직원'; break;
                            case 3 : rejectName = '방문규정 위반'; break;
                            case 4 : rejectName = '기타'; break;
                        }
                    }

                    // 반려사유가있는 경우 클릭 시 상세보기 팝업 활성화
                    var rejectPopup = module.makeTd('tpc_skip m_skip rejectColor', rejectName);
                    rejectPopup.on('click', function() {
                        $('.nv_modal5').addClass('on');
                        $('#rejectCmbBox > p').html(rejectName);
                        $('#rejectComment').val(e.rejectComment);
                        $('.nv_modal5 .btn_area').html('<button type="button" class="nv_red_button">닫기</button>');
                    })
                    tr.append(rejectPopup);
                    tr.append(approveManagerBtn);
                    $('#approveTable tbody').append(tr);
            });
        });
    }

    // 반려 처리
    $(document).on('submit', '#visitRejectForm', function(e) {
        e.preventDefault();
        var formData = new FormData();
        formData.append('visitRejectType', $('#rejectCmbBox > p').text());
        formData.append('visitRejectComment', $('#rejectComment').val());
        callApi.setFormData('/visitor-reject/'+$('#visitRejectFormId').val(), formData, function(result) {
            alert('반려처리되었습니다.');
            location.reload();
            // form.children().removeClass('on');
            // init(module.pagenation.params);     // 금일방문객 리랜더링
            // dashBoardInit();                    // 대시보드 리렌더링
        });
    });

    function excel() {
        location.href = convertEncoding(module.pagenation.excelParams);
    }

    $(document).ready(function() {
        var searchFromDateTime = $('input[name="searchFromDateTime"]').val();
        var searchToDateTime = $('input[name="searchToDateTime"]').val();
        init('/visitor/approve-list?page=1&size=10');
        $("#conditionValue").focus();
        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) module.search();
        });
    });

    $(document).on('click', '.nv_modal2_open', function() {
        $('.nv_modal2 #carryStuff').html($(this).parent().parent().find('.nv_modal1_open > span').html());
    });
    
    // 승인 시 접근권한 층 선택
    $(document).on('submit', '#buildingSiteMapping', function(event) {
        event.preventDefault();

        var form = new FormData();
        form.append('buildingName', $('#buildingInfo > h4 p').text());

        var buildingFloors = $('input[name="floor"]');
        $.each(buildingFloors, function(i, e) {
            if(e.checked) form.append('buildingFloor', e.value);
        })
        if(form.get('buildingFloor')==null || form.get('buildingFloor')== undefined) { alert('한개 층 이상 선택해주세요.'); return;}

        callApi.setFormData($(this).attr('action'), form, function(result) {
            location.reload();
        })
    });

    $(document).on('click', '#approveTable button', function() {
        var target = $(this);
        var targetId = target.parent().parent().attr('id');
        if(target.val()==='approve') {
            $('.nv_modal4').addClass('on');
            $('#buildingInfo').html(module.getBuildingSelectBox());
            $('#buildingSiteMapping').attr('action', '/visitor-approval/staffe/'+targetId);
            callApi.getData('/visitor-approval/site', function (result) {
                var ul = $('<ul>');
                $.each(result, function(i, e) {
                    // 계열사에 맵핑 된 건물 및 층 정보에 대한 li 생성
                    var li = $('<li>'+e.name+'</li>');
                    li.on('click', function() {
                        var tmpHtml = '';
                        if(e.floor.indexOf(',')!=-1) {
                            var _floorArr = e.floor.split(',');
                            for(var i=0; i<_floorArr.length; i++) {
                                var _floor = _floorArr[i].trim();
                                tmpHtml += module.getFloorCheckBox('', _floor);
                            }
                        } else {
                            tmpHtml += module.getFloorCheckBox('', e.floor);
                        }
                        $('#buildingInfo #floorBox').html(tmpHtml);
                    });
                    ul.append(li);
                });
                $('#accessBox').append(ul);
            });
        } else if (target.val()==='approve2') {
            callApi.setFormData('/visitor-approval/'+targetId, {}, function(result) {
                location.reload();
            })
        } else if(target.val()==='reject') {
            $('.nv_modal5').addClass('on');
            $('#visitRejectFormId').val(targetId);
            return;
        }
    });

    $(document).on('keypress', '#approveTable input', function(e) {
        var target = $(this);
        var targetId = target.parent().parent().attr('id');

        if(e.keyCode == 13)
        {
            if($(this).parent().hasClass('card'))
            {
                if($(this).val() !="")
                {
                    callApi.setData("/visitor-cardid/"+targetId+"/" +$(this).val(), {}, function (result) {
                        alert('변경되었습니다.');
                        init(module.pagenation.params);     // 금일방문객 리랜더링
                        dashBoardInit();
                    })
                } 
            }
        }
        
    });

    $(document).on('click', '.nv_modal2 button.nv_green_button', function() {
        var form = $(this).parents().parents().parents().parents().parents();
        var url =  form.attr('action');
        url = url.replace(/approval/,"reject");
        var formData = new FormData();
        formData.append('carryStuff', $('#carryStuff').val());
        formData.append('visitApprovalComment', $('#visitApprovalComment').val());
        callApi.setFormData(url, formData, function(result) {
            alert('반려처리되었습니다.');
            form.children().removeClass('on');
            init(module.pagenation.params);     // 금일방문객 리랜더링
            dashBoardInit();                    // 대시보드 리렌더링
        });
    });
</script>
<div class="nv_contents">
    <div class="nv_contents_main_header"><h4>방문현황</h4></div>
    <div class="nv_contents_search nv_contents_search_type2">
        <p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
            <div class="nv_date_box">
                <span class="icon_date">달력 아이콘</span>
                <input type="text" class="nv_input" id="datepicker" name="searchFromDateTime">
                <span>~</span>
                <input type="text" class="nv_input" id="datepicker2" name="searchToDateTime">
            </div>
        <p class="m_tit nv_bold pc_skip tpc_skip">검색 설정</p>
        <div class="nv_select_box">
            <p id="conditionKey">성명</p>
            <ul>
                <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1'}">
                <li>성명</li>
                <li>연락처</li>
                <li>회사</li>
                <li>차량번호</li>
                <li>임직원</li>
                <li>방문목적</li>
                <li>방문증번호</li>
                </c:if>

                <c:if test="${sessionScope.login.host.auth eq '2'}">
                <li>성명</li>
                <li>방문증번호</li>
                <li>차량번호</li>
                <li>연락처</li>
                <li>회사</li>
                <li>임직원</li>
                <li>방문목적</li>
                </c:if>

                <c:if test="${sessionScope.login.host.auth eq '3' or sessionScope.login.host.auth eq '4'}">
                <li>성명</li>
                <li>연락처</li>
                <li>회사</li>
                <li>차량번호</li>
                <li>임직원</li>
                <li>방문목적</li>
                </c:if>
            </ul>
        </div>
        <div class="nv_search_box">
            <input type="text" class="nv_input" id="conditionValue">
            <input type="submit" class="nv_search_icon tpc_skip m_skip" onclick="javascript:module.search();">
        </div>
        <div class="nv_search_box pc_skip tpc_skip" style="width: 32px;">
				<input type="submit" class="nv_search_icon" onclick="javascript:search();">
		</div>
    </div>
    <div class="nv_table_box">
        <table class="nv_table textcenter" cellspacing="0" cellpadding="0" id="approveTable">
            <thead>
                <tr>
                    <th>방문자</th>
                    <th>업체명</th>
                    <th class="tpc_skip m_skip">연락처</th>
                    <th class="tpc_skip m_skip">방문목적</th>
                    <th class="tpc_skip m_skip">방문위치</th>
                    <th class="tpc_skip m_skip">방문시작일</th>
                    <th class="tpc_skip m_skip">방문종료일</th>
                    <th class="tpc_skip m_skip">차량번호</th>
                    <%-- <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}"> --%>
                        <th class="tpc_skip m_skip">접견인</th>
                        <th class="tpc_skip m_skip">접견인 회사</th>
                        <th class="tpc_skip m_skip">접견인 팀</th>
                    <%-- </c:if> --%>
                    <th class="tpc_skip m_skip">방문증번호</th>
                    <th class="tpc_skip m_skip">승인여부</th>
                    <th class="tpc_skip m_skip">반려사유</th>
                    <th>승인관리</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div class="btn_right">
            <p class="m_tit nv_bold pc_skip tpc_skip m_skip" onclick="javascript:excel();">엑셀 다운로드</p>
            <button type="button" class="nv_green_button down_icon_btn m_skip" onclick="javascript:excel();">엑셀 다운로드</button>
        </div>
        <div class="nv_table_pagenum" id="pagenation"></div>
    </div>
</div>

<form id="visitRejectForm">
    <input type="hidden" id="visitRejectFormId"/>
    <div class="nv_modal nv_modal5">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>방문 반려사유</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div>
                    <h4 class="textarea_name">반려사유
                        <div class="nv_select_box" id="rejectCmbBox" style="float:right; margin:10px 0;">
                            <p>선택</p>
                            <ul> 
                                <li>보안위반</li>
                                <li>경쟁업체/직원</li>
                                <li>방문규정 위반</li>
                                <li>기타</li>
                            </ul>
                        </div>
                    </h4>
                    <textarea name="rejectComment" id="rejectComment" cols="30" rows="10" class="nv_textarea" placeholder="반려사유 입력"></textarea>
                </div>
                <div class="btn_area">
                    <button type="submit" class="nv_green_button">반려</button>
                    <button type="button" class="nv_red_button">취소</button>
                </div>
            </div>
        </div>
    </div>
</form>
<form id="buildingSiteMapping" action="" method="POST">
    <input type="hidden" name="targetId" value="" />
    <div class="nv_modal nv_modal4">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>건물접근권한</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div id="buildingInfo"></div>
                <div class="btn_area">
                    <button type="button" class="nv_red_button">취소</button>
                    <button type="submit" class="nv_green_button">승인</button>
                </div>
            </div>
        </div>
    </div>
</form>