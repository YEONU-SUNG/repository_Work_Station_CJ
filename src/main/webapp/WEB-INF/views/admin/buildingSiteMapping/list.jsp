<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    function getFloorCheckBox(checked, floor) {
        return '<input type="checkbox" name="floor" id="'+floor+'" value="'+floor+'" '+checked+'/><label style="padding-right:2%;" for="'+floor+'">'+floor+'</label>';
    }
    function getBuildingSelectBox(name) {
        return '<div class="textarea_name" style="padding-bottom: 10px;">건물명' +
                    '<div class="nv_select_box" id="rejectCmbBox" style="float:right; margin:10px 0;">' +
                        '<p>'+name+'</p>' +
                    '</div>' +
                '</div>' +
                '<div id="floorBox"></div>';
    }
    
    function init() {
        callApi.getData('/site', function (result) {
            $.each(result, function(i, e) {
                var li = $('<li>');
                    li.append('<span class="con_name">'+e.name+'</span>');
                    li.on('click', function() {
                        $('.add').attr('id', e.code);
                        $('#securityList').empty();
                        callApi.getData('/site/'+e.code, function (result) {
                            var siteCode = e.code;
                            $.each(result, function(i, e) {
                                // 계열사에 맵핑 된 건물 및 층 정보에 대한 li 생성
                                var building_li = $('<li style="padding:0;">');
                                    building_li.append('<span class="con_name" style="width:83.6%">'+e.name+'  '+e.floor+'</span>');
                                
                                // 편집버튼 클릭시 작동 기능
                                var building_btn = $('<button type="button" class="nv_green_button" style="margin-right: 1%;">편집</button>');
                                    building_btn.on('click', function() {
                                        $('.nv_modal5').addClass('on');
                                        $('#buildingSiteMapping').attr('action', '/site/'+siteCode+'/building-floor');
                                        $('#buildingInfo').html(getBuildingSelectBox(e.name));

                                        // var form = new FormData();
                                        // form.append('buildingName', e.name);
                                        // 건물 층 정보 조회
                                        callApi.getFormData('/building', {buildingName : e.name}, function (result) {
                                            var tmpHtml = '';
                                            $.each(result, function(buildingIndex, buildingResult) {
                                                var isFloor = false;
                                                var _buildingFloor = buildingResult.floor;
                                                // 맵핑 된 층과 원래 건물이 가지고있던 층 표현
                                                if(e.floor.indexOf(',')!=-1) {
                                                    var selectedFloorArr = e.floor.split(',');
                                                    for(var i=0; i<selectedFloorArr.length; i++) {
                                                        var _selectedFloor = selectedFloorArr[i].trim();
                                                        if(_buildingFloor==_selectedFloor) isFloor = !isFloor;
                                                    }
                                                    tmpHtml += (isFloor)
                                                         ? getFloorCheckBox('checked=checked', _buildingFloor)
                                                         : getFloorCheckBox('', _buildingFloor);
                                                } else {
                                                    tmpHtml += (_buildingFloor==e.floor)
                                                         ? getFloorCheckBox('checked=checked', _buildingFloor)
                                                         : getFloorCheckBox('', _buildingFloor);
                                                }
                                                
                                            });
                                            $('#buildingInfo #floorBox').html(tmpHtml);
                                        });
                                        // if(e.floor.indexOf(',')!=-1) {
                                        //     var floorArr = e.floor.split(',');
                                        //     for(var i=0; i<floorArr.length; i++) {
                                        //         var _floor = floorArr[i].trim();
                                        //         tmpHtml += getFloorCheckBox('checked=checked', _floor);
                                        //     }
                                        // } else {
                                        //     tmpHtml += getFloorCheckBox('checked=checked', e.floor);
                                        // }
                                    });
                                building_li.append(building_btn);
                                
                                // 삭제버튼 클릭시 작동 기능
                                var building_cancel_btn = $('<button type="button" class="nv_red_button">삭제</button>');
                                building_cancel_btn.on('click', function() {
                                    if(confirm("정말 삭제하시겠습니까?")) {
                                        var form = new FormData();
                                        form.append('buildingName', e.name);
                                        callApi.setFormData('/site/'+siteCode+'/delete-mapping', form, function(result) {
                                            li.click();
                                        })
                                    }
                                });
                                building_li.append(building_cancel_btn);
                                $('#securityList').append(building_li);
                            })
                        })
                    });
                $('#safeList').append(li);
            });
        });
    }

    $(document).on('click', '.add', function(e) {
        var siteCode = $(this).attr('id');
        if(siteCode == undefined) {
            alert('계열사정보에서 계열사를 선택해주세요.');
            return;
        }
        $('#buildingSiteMapping').attr('action', '/site/'+siteCode);
        callApi.getData('/building', function (result) {
            $('.nv_modal5').addClass('on');
            $('#buildingInfo').html(getBuildingSelectBox('건물선택'));
            var ul = $('<ul>');
            $.each(result, function(i, e) {
                var li = $('<li>'+e.name+'</li>');
                li.on('click', function() {
                    var tmpHtml = '';
                    if(e.floor.indexOf(',')!=-1) {
                        var floorArr = e.floor.split(',');
                        for(var i=0; i<floorArr.length; i++) {
                            var _floor = floorArr[i].trim();
                            tmpHtml += getFloorCheckBox('', _floor);
                        }
                    } else {
                        tmpHtml = getFloorCheckBox('', e.floor);
                    }
                    $('#buildingInfo #floorBox').html(tmpHtml);
                })
                ul.append(li);
            });
            $('#buildingInfo > div > div').append(ul);
        }); 
    });

    // 등록 및 수정
    $(document).on('submit', '#buildingSiteMapping', function(event) {
        event.preventDefault();

        var form = new FormData();
        form.append('buildingName', $('#buildingInfo > div p').text());

        var buildingFloors = $('input[name="floor"]');
        var floorCnt=0;
        $.each(buildingFloors, function(i, e) {
            //if(e.checked) form.append('buildingFloor', e.value);
            if(e.checked) 
            {
                form.append('buildingFloor', e.value);
                floorCnt++;
            }
        })
        //if(form.get('buildingFloor')==null || form.get('buildingFloor')== undefined) { alert('한개 층 이상 선택해주세요.'); return;}
        if(floorCnt <= 0) { alert('한개 층 이상 선택해주세요.'); return;}
        callApi.setFormData($(this).attr('action'), form, function(result) {
            $('.nv_modal5').removeClass('on');
        })
    });

    $(document).ready(function() {
        init();
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>건물관리</h4>
		</div>
		<div class="nv_edu_wrap">
			<div class="nv_edu_container">
				<div class="nv_edu_tit">
					<h4>계열사정보</h4>
				</div>
				<ul id="safeList"></ul>
			</div>
			<div class="nv_edu_container">
				<div class="nv_edu_tit">
					<h4>건물정보</h4>
					<button type="button" class="nv_blue_button add">등록</button>
				</div>
				<ul id="securityList"></ul>
			</div>
		</div>
	</div>
</div>

<form id="buildingSiteMapping" method="POST">
    <div class="nv_modal nv_modal5">
        <div class="nv_modal_container">
            <div class="nv_modal_header">
                <h2>건물관리편집</h2>
                <p class="nv_modal_close">닫기</p>
            </div>
            <div class="nv_modal_contents">
                <div id="buildingInfo"></div>
                <div class="btn_area">
                    <button type="button" class="nv_red_button">취소</button>
                    <button type="submit" class="nv_green_button">저장</button>
                </div>
            </div>
        </div>
    </div>
</form>