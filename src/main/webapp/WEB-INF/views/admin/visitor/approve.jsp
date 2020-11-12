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

    const date = {
        today : new Date(),
        compare : function(compareDate) {
            let _today = this.today.getFullYear()+'-'+this.today.getMonth()+'-'+this.today.getDay();
            let _compareDate = compareDate.getFullYear()+'-'+compareDate.getMonth()+'-'+compareDate.getDay();
            //return (_today==_compareDate);
            return (_today>=_compareDate);
        }
    }
</script>
<c:if test="${sessionScope.login.host.auth eq '1'}">
    <script>

// 원본
 /*        var approveActionCommponet = {
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
                        return '승인완료';
                } else {
                    return '<button type="button" class="nv_blue_button nv_modal2_open" value="approve">승인/거부</button>';
                }
            }
        }; */
// 수정본
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
                    return '<button type="button" class="nv_blue_button" value="approve">승인</button><button type="button" class="nv_red_button nv_modal5_open" onclick="$(\'.nv_modal5\').addClass(\'on\');" value="reject">반려</button>';
                }
            }
        };

        var reasonRejection = {
            visitorRejection : function(row) {
                if(row.rejectFlag==='Y'){
                    if(row.rejectType==='1') return '규칙위반';
                    if(row.rejectType==='2') return '보안위반';
                    else return '기타';
                }
                else return '';
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
                        //return '<button type="button" class="nv_blue_button">방문</button>';
                        return '<button type="button" class="nv_blue_button nv_green_button">입문</button>';
                    }
                    else if(row.toDayYN==='N')
                        return '승인완료';
                } else {
                    return '승인대기';
                }
            }
        };
    </script>
</c:if>
<c:if test="${sessionScope.login.host.auth eq '3' or sessionScope.login.host.auth eq '4'}">
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
                    return '<button type="button" class="nv_blue_button nv_modal2_open" value="approve">승인/거부</button>';
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
        makeTable : function(pagenation) {
            if(this.tableData.length==0 && this.pagenation.conditionKey!='' && !inoutMode) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                inoutMode = false;
                this.tableHTML = '';
                /* for(var i=0; i<this.tableData.length;i++) {
                    var strcarryStuffUsed = this.tableData[i].carryStuffUsed.trim() == "" ? "사용안함":this.tableData[i].carryStuffUsed;
                    this.tableHTML +=   '<tr class="nv_view_nexttable" id="'+this.tableData[i].visitorHistorySeq+'">' +
                                            '<td class="nv_gray_check">' +
                                            '<input type="checkbox" id="n1_'+(this.tableData[i].visitorHistorySeq)+'" value="'+(this.tableData[i].visitorHistorySeq)+'">' +
                                            '<label for="n1_'+(this.tableData[i].visitorHistorySeq)+'">선택</label></td>' +
                                            //'<td>' +this.tableData[i].visitorHistorySeq+ '</td>' +
                                            // '<td>' +this.tableData[i].visitorReservationNumber+ '</td>' +
                                            '<td>' +this.tableData[i].visitorName+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitorMobile+ '</td>' +
                                            '<td>' +this.tableData[i].visitorCompany+ '</td>';// +
                                            
                                            this.tableHTML += '<td class="tpc_skip m_skip">' +this.tableData[i].planFromDateTime+ '~' + this.tableData[i].planToDateTime + '</td>';
                                            //'<td>' +this.tableData[i].cardID+ '</td>' +
                                            if( ${sessionScope.login.host.auth eq '1'} || ${sessionScope.login.host.auth eq '2'})
                                            {
                                                // if((approveActionCommponet.visitorButton(this.tableData[i]).indexOf("방문") != -1 || approveActionCommponet.visitorButton(this.tableData[i]).indexOf("퇴실") != -1 )
                                                if((approveActionCommponet.visitorButton(this.tableData[i]).indexOf("입문") != -1 || approveActionCommponet.visitorButton(this.tableData[i]).indexOf("출문") != -1 )
                                                    && approveActionCommponet.visitorButton(this.tableData[i]).indexOf("button") != -1)
                                                    this.tableHTML += '<td class="card"> <input type="text" class="nv_input max_100" value="'+this.tableData[i].cardID+'" /></td>';
                                                else
                                                    this.tableHTML += '<td class="card">' +this.tableData[i].cardID+ '</td>';
                                            }
                    this.tableHTML +=       '<td class="tpc_skip m_skip">' +this.tableData[i].carNo+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].hostName+ '</td>' +
                                            //'<td class="tpc_skip m_skip">' +this.tableData[i].visitPurpose+'</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitPurpose+"_"+ this.tableData[i].visitorPosition1 +"_"+ this.tableData[i].visitorPosition2+'</td>' +
                                            '<td class="tpc_skip m_skip">' +
                                                '<a class="nv_blue nv_bold nv_link nv_modal1_open" style="cursor: pointer">' +this.tableData[i].carryStuff+ 
                                                '<span style="display:none;">' +
                                                    '시리얼번호 : '+this.tableData[i].carryStuffSerialNo+'\n\n' +
                                                    '사용목적 : '+this.tableData[i].carryStuffPurpose+'\n\n' +
                                                    '사용유무 : '+strcarryStuffUsed+
                                                '</span>' +
                                                '</a>' +
                                            '</td>' +
                                            '<td class="approvechidren">'+approveActionCommponet.visitorButton(this.tableData[i])+'</td>';
                                        '<tr>'; 
                    this.tableHTML +=   '<tr class="pc_skip">' +
                                            '<td colspan="6" class="nv_hidden_table_area">' +
                                                '<table class="nv_hidden_table">' +
                                                    '<colgroup>' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                    '</colgroup>' +
                                                    '<tbody>' +
                                                        '<tr>' +
                                                            '<td class="nv_bold">연락처</td>' +
                                                            '<td>'+this.tableData[i].visitorMobile+'</td>' +
                                                            '<td class="nv_bold">차량번호</td>' +
                                                            '<td>' +this.tableData[i].carNo+ '</td>' +
                                                            //'<td class="nv_bold">회사</td>' +
                                                            //'<td>' +this.tableData[i].visitorCompany+ '</td>' +
                                                        '</tr>' +
                                                        // '</tr>' +
                                                        //     '<td class="nv_bold">차량번호</td>' +
                                                        //     '<td>' +this.tableData[i].carNo+ '</td>' +
                                                        //     '<td class="nv_bold">임직원</td>' +
                                                        //     '<td>' +this.tableData[i].hostName+ '</td>' +
                                                        // '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">방문목적</td>' +
                                                            '<td>'+this.tableData[i].visitPurpose+"_"+ this.tableData[i].visitorPosition1 +"_"+ this.tableData[i].visitorPosition2+'</td>' +
                                                            '<td class="nv_bold">반입물품</td>' +
                                                            '<td>' +
                                                                '<a class="nv_blue nv_bold nv_link nv_modal1_open" style="cursor: pointer">' +this.tableData[i].carryStuff+ 
                                                                '<span style="display:none;">' +
                                                                    '시리얼번호 : '+this.tableData[i].carryStuffSerialNo+'\n\n' +
                                                                    '사용목적 : '+this.tableData[i].carryStuffPurpose+'\n\n' +
                                                                    '사용유무 : '+strcarryStuffUsed +
                                                                '</span>' +
                                                                '</a>' +
                                                            '</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">방문기간</td>' +
                                                            '<td>' +this.tableData[i].planFromDateTime+ '~' + this.tableData[i].planToDateTime + '</td>'+
                                                            '<td class="nv_bold">임직원</td>' +
                                                            '<td>' +this.tableData[i].hostName+ '</td>' +
                                                        '</tr>' +
                                                    '</tbody>' +
                                                '</table>' +
                                            '</td>' +
                                        '</tr>';
                    } */
                    for(var i=0; i<this.tableData.length;i++) {
                    var strcarryStuffUsed = this.tableData[i].carryStuffUsed.trim() == "" ? "사용안함":this.tableData[i].carryStuffUsed;
                    this.tableHTML +=   '<tr class="nv_view_nexttable" id="'+this.tableData[i].visitorHistorySeq+'">' +
                                            '<td>' +this.tableData[i].visitorName+ '</td>' +
                                            '<td>' +this.tableData[i].visitorCompany+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitorMobile+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitPurpose + 
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].visitorPosition1 +", "+ this.tableData[i].visitorPosition2 +", "+ this.tableData[i].visitorPosition3 +'</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].planFromDateTime+ '</td>' +
                                            '<td class="tpc_skip m_skip">' +this.tableData[i].planToDateTime + '</td>';

                    this.tableHTML +=   '<td class="tpc_skip m_skip">' +this.tableData[i].carNo+ '</td>' +
                                        '<td class="tpc_skip m_skip">' +this.tableData[i].hostName+ '</td>' +
                                        '<td class="tpc_skip m_skip">' +this.tableData[i].hostCompany+ '</td>' +
                                        '<td class="tpc_skip m_skip">' +this.tableData[i].hostDept+ '</td>';
                                        if( ${sessionScope.login.host.auth eq '1'} || ${sessionScope.login.host.auth eq '2'})
                                        {
                                            // if((approveActionCommponet.visitorButton(this.tableData[i]).indexOf("방문") != -1 || approveActionCommponet.visitorButton(this.tableData[i]).indexOf("퇴실") != -1 )
                                            if((approveActionCommponet.visitorButton(this.tableData[i]).indexOf("입문") != -1 || approveActionCommponet.visitorButton(this.tableData[i]).indexOf("출문") != -1 )
                                                && approveActionCommponet.visitorButton(this.tableData[i]).indexOf("button") != -1)
                                                this.tableHTML += '<td class="card"> <input type="text" class="nv_input max_100" value="'+this.tableData[i].cardID+'" /></td>';
                                            else
                                                this.tableHTML += '<td class="card">' +this.tableData[i].cardID+ '</td>';
                                        }
                    this.tableHTML +=   '<td class="approvechidren">'+approveProcessStatus.visitorApprove(this.tableData[i])+'</td>' +
                                        '<td class="tpc_skip m_skip" onclick="javascript:reasonRejectRead">'+reasonRejection.visitorRejection(this.tableData[i])+'</td>' +
                                        '<td class="approvechidren">'+approveActionCommponet.visitorButton(this.tableData[i])+'</td>' +
                                        '<tr>'; 

                    this.tableHTML +=   '<tr class="pc_skip">' +
                                            '<td colspan="6" class="nv_hidden_table_area">' +
                                                '<table class="nv_hidden_table">' +
                                                    '<colgroup>' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                        '<col width="20%">' +
                                                        '<col width="30%">' +
                                                    '</colgroup>' +
                                                    '<tbody>' +
                                                        '<tr>' +
                                                            '<td class="nv_bold">연락처</td>' +
                                                            '<td>'+this.tableData[i].visitorMobile+'</td>' +
                                                            '<td class="nv_bold">차량번호</td>' +
                                                            '<td>' +this.tableData[i].carNo+ '</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">방문목적</td>' +
                                                            '<td>'+this.tableData[i].visitPurpose+","+ this.tableData[i].visitorPosition1 +","+ this.tableData[i].visitorPosition2+'</td>' +
                                                            '<td class="nv_bold">반입물품</td>' +
                                                            '<td>' +
                                                                '<a class="nv_blue nv_bold nv_link nv_modal1_open" style="cursor: pointer">' +this.tableData[i].carryStuff+ 
                                                                '<span style="display:none;">' +
                                                                    '시리얼번호 : '+this.tableData[i].carryStuffSerialNo+'\n\n' +
                                                                    '사용목적 : '+this.tableData[i].carryStuffPurpose+'\n\n' +
                                                                    '사용유무 : '+strcarryStuffUsed +
                                                                '</span>' +
                                                                '</a>' +
                                                            '</td>' +
                                                        '</tr>' +
                                                        '</tr>' +
                                                            '<td class="nv_bold">방문기간</td>' +
                                                            '<td>' +this.tableData[i].planFromDateTime+ '~' + this.tableData[i].planToDateTime + '</td>'+
                                                            '<td class="nv_bold">임직원</td>' +
                                                            '<td>' +this.tableData[i].hostName+ '</td>' +
                                                        '</tr>' +
                                                    '</tbody>' +
                                                '</table>' +
                                            '</td>' +
                                        '</tr>';
                    }
                pagenation.html(page.makePagenation(module.pagenation));
                return this.tableHTML;
            }
        }
    }

    function buttonTypeChange(buttonType) {
        buttonType.removeClass('on');
        if(buttonType.attr('id')=='selectorModify') buttonType.next().addClass('on');
        else buttonType.prev().addClass('on');
    }

    function checkedEach(actionFunction, buttonType) {
        var checked = $('#approveTable > tbody input[type="checkbox"]:checked');
        if(checked.length>0) {
            buttonTypeChange(buttonType);
            $(checked).each(function(){
                var targetParentTrId = $(this).parent().parent().attr('id');
                var target = $(this).parent().parent().children('td:eq(5)');
                actionFunction(targetParentTrId, target);
            });
        } else {
            alert('선택 된 행이 없습니다.');
        }
    }

    function checkedEachapproval(actionFunction, buttonType) {
        var checked = $('#approveTable > tbody input[type="checkbox"]:checked');
        if(checked.length>0) {
            //buttonTypeChange(buttonType);
            $(checked).each(function(){
                //var targetParentTrId = $(this).parent().parent().attr('id');
                //var target = $(this).parent().parent().children('td:eq(5)');

                /* if($(this).parent().parent().children('td:eq(9)').children().attr("type") == "button" && $(this).parent().parent().children('td:eq(9)').children().text() == "승인") */
                if($(this).parent().parent().children('td:eq(15)').children().attr("type") == "button" && $(this).parent().parent().children('td:eq(15)').children().text() == "승인")
                {
                    $('#visitApprovalForm').attr('action', '/visitor-approval/'+$(this).parent().parent().attr("id"));

                    var form = $('#visitApprovalForm');
                    var formData = new FormData();
                    formData.append('carryStuff', $('#carryStuff').val());
                    formData.append('visitApprovalComment', $('#visitApprovalComment').val());
                    callApi.setFormData(form.attr('action'), formData, function(result) {
                        //alert('승인처리되었습니다.');
                        form.children().removeClass('on');
                        init(module.pagenation.params);     // 금일방문객 리랜더링
                        dashBoardInit();                    // 대시보드 리렌더링
                    });
                }

                //actionFunction(targetParentTrId, target);
            });
        } else {
            alert('선택 된 행이 없습니다.');
        }
    }

    function actionClean() {
        if($('#allcheck').is(":checked")) $("#allcheck").click();
        $('#selectorModify').addClass('on');
        $('#selectorSave').removeClass('on');
    }

    function init(url) {
        actionClean();
        callApi.getData(url, function (result) {
            module.pagenation = result.pagenation;
            module.tableData = result.response;
            $('#approveTable > tbody').html(module.makeTable($('#pagenation')));
        });
    }

    function search() {
        var visitorFromDateTime = $('input[name="visitorFromDateTime"]').val();
        var visitorToDateTime = $('input[name="visitorToDateTime"]').val();
        var conditionKey = $('#conditionKey').html();
        var conditionValue = $('#conditionValue').val();
        init('/visitor/history-list?page=1&size=10&conditionKey='+conditionKey+"&conditionValue="+conditionValue+'&visitorFromDateTime='+visitorFromDateTime+'&visitorToDateTime='+visitorToDateTime);
    }

    function reasonRejectRead() {
        $(".nv_modal5").addClass("on");
        $(".nv_modal5").attr("readonly", true);
        $(".nv_modal5").attr("disabled", true);
    }

    $(document).ready(function() {
        var visitorFromDateTime = $('input[name="visitorFromDateTime"]').val();
        var visitorToDateTime = $('input[name="visitorToDateTime"]').val();
        init('/visitor/approve-list?page=1&size=10');
        $("#conditionValue").focus();

        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
    });

    $(document).on('click', '.nv_modal2_open', function() {
        $('.nv_modal2 #carryStuff').html($(this).parent().parent().find('.nv_modal1_open > span').html());
    });
    
    // 원본
    /* $(document).on('click', '#approveTable button', function() {
        var target = $(this);
        var targetId = target.parent().parent().attr('id');
        if(target.val()==='approve') {
            $('#visitApprovalForm').attr('action', '/visitor-approval/'+targetId);
        } else {
            ////////////////////
            // var formData = new FormData();
            // formData.append('visitorHistorySeq[]', targetId);
            // formData.append('cardID[]', target.parent().parent().children('td:eq(4)').children().val());
       
            // if(formData.has('visitorHistorySeq[]')) {
            //     callApi.setFormData('/visitor/card-no', formData, function(result) {
            //         //alert('변경되었습니다.');
            //         init(module.pagenation.params);
            //     });
            // }
            ///////////////

            inoutMode = true;
            //callApi.setData("/visitor-inout/"+targetId+"/" +target.parent().parent().children('td:eq(4)').children().val(), {}, function (result) {
            callApi.setData("/visitor-inout/"+targetId+"/" +target.parent().parent().children('[class^=card]').children().val(), {}, function (result) {
                target.parent().html(approveActionCommponet.visitorButton(result));
                init(module.pagenation.params);     // 금일방문객 리랜더링
                dashBoardInit();
            })
        }
    }); */
    // 수정본
    $(document).on('click', '#approveTable button', function() {
        var target = $(this);
        var targetId = target.parent().parent().attr('id');
        if(target.val()==='approve') {
            //$('#visitApprovalForm').attr('action', '/visitor-approval/'+targetId);
            callApi.setData('/visitor-approval/'+targetId, {}, function (result) {
                target.parent().html(approveActionCommponet.visitorButton(result));
                init(module.pagenation.params);     // 금일방문객 리랜더링
                dashBoardInit();
            })
        }
         else if(target.val()==='reject') {
            $('#visitRejectForm').attr('action', '/visitor-reject/'+targetId);

        } 
        else {
            ////////////////////
            // var formData = new FormData();
            // formData.append('visitorHistorySeq[]', targetId);
            // formData.append('cardID[]', target.parent().parent().children('td:eq(4)').children().val());
       
            // if(formData.has('visitorHistorySeq[]')) {
            //     callApi.setFormData('/visitor/card-no', formData, function(result) {
            //         //alert('변경되었습니다.');
            //         init(module.pagenation.params);
            //     });
            // }
            ///////////////

            inoutMode = true;
            //callApi.setData("/visitor-inout/"+targetId+"/" +target.parent().parent().children('td:eq(4)').children().val(), {}, function (result) {
            callApi.setData("/visitor-inout/"+targetId+"/" +target.parent().parent().find('.card').children().val(), {}, function (result) {
                target.parent().html(approveActionCommponet.visitorButton(result));
                init(module.pagenation.params);     // 금일방문객 리랜더링
                dashBoardInit();
            })
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
        // if(target.val()==='approve') {
        //     $('#visitApprovalForm').attr('action', '/visitor-approval/'+targetId);
        // } else {
        //     callApi.setData("/visitor-inout/"+targetId+"/" +target.parent().parent().children('[class^=card]').children().val(), {}, function (result) {
        //         target.parent().html(approveActionCommponet.visitorButton(result));
        //         init(module.pagenation.params);     // 금일방문객 리랜더링
        //         dashBoardInit();
        //     })
        // }
    });

    $(document).on('submit', '#visitApprovalForm', function(event) {
        event.preventDefault();
        var form = $(this);
        var formData = new FormData();
        formData.append('carryStuff', $('#carryStuff').val());
        formData.append('visitApprovalComment', $('#visitApprovalComment').val());
        callApi.setFormData(form.attr('action'), formData, function(result) {
            alert('승인처리되었습니다.');
            form.children().removeClass('on');
            init(module.pagenation.params);     // 금일방문객 리랜더링
            dashBoardInit();                    // 대시보드 리렌더링
        });
    });

    $(document).on('submit', '#visitApprovalFormChecked', function(event) {
        if(_varApprovalchecked.length > 0)
        {
            event.preventDefault();
            var form = $(this);
            var formData = new FormData();
            formData.append('carryStuff', "");
            formData.append('visitApprovalComment', $('#visitApprovalCheckedComment').val());
            _varApprovalchecked.forEach(function(currentValue, index, array){
                formData.append('visitorHistorySeq[]', currentValue);
            });
            // _varApprovalchecked.forEach(element => {
            //     formData.append('visitorHistorySeq[]', element);    
            // });
            
            callApi.setFormData(form.attr('action'), formData, function(result) {
                alert('승인처리되었습니다.');
                form.children().removeClass('on');
                init(module.pagenation.params);     // 금일방문객 리랜더링
                dashBoardInit();                    // 대시보드 리렌더링
            });
        }
    });

//    $(document).on('click', '.nv_modal2 button.nv_green_button', function() {
    $(document).on('click', '.nv_modal5 button.nv_green_button', function() {
        var form = $(this).parents().parents().parents().parents().parents();
        var url =  form.attr('action');
        url = url.replace(/approval/,"reject");
        var formData = new FormData();
//        formData.append('carryStuff', $('#carryStuff').val());
        formData.append('rejectComment', $('#rejectComment').val());
        callApi.setFormData(url, formData, function(result) {
            alert('반려처리되었습니다.');
            form.children().removeClass('on');
            init(module.pagenation.params);     // 금일방문객 리랜더링
            dashBoardInit();                    // 대시보드 리렌더링
        });
    });

    // 원본
    /* $(document).on('click', '.nv_modal4 button.nv_green_button', function() { */
    // 수정본

    $(document).on('click', '.nv_modal4 button.nv_green_button', function() {
        if(_varApprovalchecked.length > 0)
        {
            var form = $('#visitRejectForm');
            var url =  form.attr('action');
            var targetId = url.replace('/visitor-reject/', '');
            var formData = new FormData();
            formData.append('visitRejectType', $('#nv5_rejectCmbBox').find('p').text().trim());
            formData.append('visitRejectComment', $('#nv5_rejectComment').val());
            formData.append('visitorHistorySeq', targetId);
            // _varApprovalchecked.forEach(function(currentValue, index, array){
            //     formData.append('visitorHistorySeq[]', currentValue);
            // });
            callApi.setFormData(url, formData, function(result) {
                alert('반려처리되었습니다.');
                form.children().removeClass('on');
                init(module.pagenation.params);     // 금일방문객 리랜더링
                dashBoardInit();                    // 대시보드 리렌더링
            });
        }
    });
    
    $(document).on('click', '#selectorModify', function() {
        checkedEach(function(targetParentTrId, target) {
            if(module.tableData.filter(function(row) {
                return row.visitorHistorySeq==targetParentTrId;
            })) {
                target.html('<input type="text" class="nv_input max_100" value="'+target.html()+'" />');
            }
        }, $(this));
    });

    var _varApprovalchecked;
    $(document).on('click', '#selectorModifyapproval', function() {
        var nCnt = 0;
        _varApprovalchecked = [];
        var checked = $('#approveTable > tbody input[type="checkbox"]:checked');
        if(checked.length>0) {
            $('#visitApprovalFormChecked').attr('action', '/visitor-approval/all');
            $(".nv_modal4").addClass("on");
            //buttonTypeChange(buttonType);
            $(checked).each(function(){
                //var targetParentTrId = $(this).parent().parent().attr('id');
                //var target = $(this).parent().parent().children('td:eq(5)');

                //if($(this).parent().parent().children('td:eq(9)').children().attr("type") == "button" && $(this).parent().parent().children('td:eq(9)').children().text() == "승인")
                //if($(this).parent().parent().children('[class^=approvechidren]').children().attr("type") == "button" && $(this).parent().parent().children('[class^=approvechidren]').children().text() == "승인/반려")
                if($(this).parent().parent().children('[class^=approvechidren]').children().attr("type") == "button" && $(this).parent().parent().children('[class^=approvechidren]').children().text() == "승인")
                {
                    _varApprovalchecked[nCnt++] = $(this).parent().parent().attr("id");
                }
                // if(_varApprovalchecked.length > 0)
                // {
                //     var form = $('#visitApprovalFormChecked');
                //     var formData = new FormData();
                //     formData.append('carryStuff', "");
                //     formData.append('visitApprovalComment', $('#visitApprovalCheckedComment').val());
                //     formData.append('visitorHistorySeq[]', _varApprovalchecked);
                //     callApi.setFormData(form.attr('action'), formData, function(result) {
                //         alert('승인처리되었습니다.');
                //         form.children().removeClass('on');
                //         init(module.pagenation.params);     // 금일방문객 리랜더링
                //         dashBoardInit();                    // 대시보드 리렌더링
                //     });
                // }

                //actionFunction(targetParentTrId, target);
            });
        } else {
            alert('선택 된 행이 없습니다.');
        }
        // checkedEachapproval(function(targetParentTrId, target) {
        //     if(module.tableData.filter(function(row) {
        //         return row.visitorHistorySeq==targetParentTrId;
        //     })) {
        //         //target.html('<input type="text" class="nv_input max_100" value="'+target.html()+'" />');
        //     }
        // }, $(this));
    });

    $(document).on('click', '#selectorSave', function() {
        var formData = new FormData();
        checkedEach(function(targetParentTrId, target) {
            formData.append('visitorHistorySeq[]', targetParentTrId);
            formData.append('cardID[]', target.children('input').val());
        }, $(this));
       
        if(formData.has('visitorHistorySeq[]')) {
            callApi.setFormData('/visitor/card-no', formData, function(result) {
                alert('변경되었습니다.');
                init(module.pagenation.params);
            });
        }
        $("#conditionValue").focus();
    });
</script>
<div class="nv_contents_header"><h4>방문현황</h4></div>
<%-- <c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}"> --%>
    <%--<c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '3'}">
<div class="btn_left toggle_btn">
     <button type="button" class="nv_blue_button on" id="selectorModify">선택 수정</button>
    <button type="button" class="nv_green_button" id="selectorSave">선택 저장</button>
    <button type="button" class="nv_blue_button on" id="selectorModifyapproval">선택 승인/반려</button>
</div>
</c:if> --%>
<div class="nv_contents_search">
    <p class="m_tit nv_bold pc_skip tpc_skip">기간 설정</p>
        <div class="nv_date_box">
            <span class="icon_date">달력 아이콘</span>
            <input type="text" class="nv_input" id="datepicker" name="visitorFromDateTime">
            <span>~</span>
            <input type="text" class="nv_input" id="datepicker2" name="visitorToDateTime">
        </div>
    <p class="m_tit nv_bold pc_skip tpc_skip">검색 설정</p>
    <div class="nv_select_box">
        <p id="conditionKey">성명</p>
        <ul>
            <%-- <li>예약번호</li> --%>
            <%-- <li>성명</li>
            <li>연락처</li>
            <li>회사</li>
            <li>차량번호</li>
            <li>임직원</li>
            <li>방문목적</li>
            <c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}">
            <li>방문증번호</li> --%>

            <c:if test="${sessionScope.login.host.auth eq '1'}">
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
        <input type="submit" class="nv_search_icon" onclick="javascript:search();">
    </div>
</div>
<div class="nv_table_box">
    <table class="nv_table textcenter" cellspacing="0" cellpadding="0" id="approveTable">
        <thead>
<!--            <tr>
                <th class="nv_gray_check">
                    <input type="checkbox" id="allcheck">
                    <label for="allcheck">전체 선택</label>
                </th>
                <%-- <th style="width:7%">예약번호</th> --%>
                <th>성명</th>
                <th class="tpc_skip m_skip">연락처</th>
                <th>회사</th>
                <th class="tpc_skip m_skip">방문기간</th>
                <c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}">
                <th>방문증번호</th>
                </c:if>
                <th class="tpc_skip m_skip">차량번호</th>
                <th class="tpc_skip m_skip">임직원</th>
                <th class="tpc_skip m_skip">방문목적</th>
                <th class="tpc_skip m_skip">반입물품</th>
                <th>방문관리</th>
            </tr> -->
            <tr>
                <th>방문자</th>
                <th>업체명</th>
                <th class="tpc_skip m_skip">연락처</th>
                <th class="tpc_skip m_skip">방문목적</th>
                <th class="tpc_skip m_skip">방문위치</th>
                <th class="tpc_skip m_skip">방문시작일</th>
                <th class="tpc_skip m_skip">방문종료일</th>
                <th class="tpc_skip m_skip">차량번호</th>
                <c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}">
                    <th class="tpc_skip m_skip">접견인</th>
                    <th class="tpc_skip m_skip">접견인 회사</th>
                    <th class="tpc_skip m_skip">접견인 팀</th>
                </c:if>
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
<form id="visitApprovalForm">
    <div class="nv_modal nv_modal2" id="nv_modal_2">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>방문 승인</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div>
                    <h4 class="textarea_name">반입물품</h4>
                    <textarea name="carryStuff" id="carryStuff" cols="30" rows="10" class="nv_textarea" placeholder="반입물품 표시" readonly></textarea>
                </div>
                <div>
                    <h4 class="textarea_name">승인 코멘트</h4>
                    <textarea name="visitApprovalComment" id="visitApprovalComment" cols="30" rows="10" class="nv_textarea" placeholder="승인전달사항 입력"></textarea>
                </div>
                <div>
                    <h4 class="textarea_name">반려사유
                        <div class="nv_select_box" id="rejectCmbBox" style="float:right; margin:10px 0;">
                            <p>선택</p>
                            <ul> 
                                <li>규칙위반</li>
                                <li>보안위반</li>
                                <li>기타</li>
                            </ul>
                        </div>
                    </h4>
                    <textarea name="rejectComment" id="rejectComment" cols="30" rows="10" class="nv_textarea" placeholder="반려사유 입력"></textarea>
                </div>
                <div class="btn_area">
                    <button type="submit" class="nv_blue_button" onclick="javascript:$('#nv_modal_2').removeClass('on');">승인</button>
                    <button type="button" class="nv_green_button"onclick="javascript:$('#nv_modal_2').removeClass('on');">반려</button>
                    <button type="button" class="nv_red_button">취소</button>
                </div>
            </div>
        </div>
    </div>
</form>
<form id="visitApprovalFormChecked">
    <div class="nv_modal nv_modal4">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>방문 승인</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div>
                    <h4 class="textarea_name">승인 코멘트</h4>
                    <textarea name="visitApprovalCheckedComment" id="visitApprovalCheckedComment" cols="30" rows="10" class="nv_textarea" placeholder="승인전달사항 입력"></textarea>
                </div>
                <div class="btn_area">
                    <button type="submit" class="nv_blue_button">승인</button>
                    <button type="button" class="nv_green_button">반려</button>
                    <button type="button" class="nv_red_button">취소</button>
                </div>
            </div>
        </div>
    </div>
</form>
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