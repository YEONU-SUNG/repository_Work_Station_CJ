<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    var module = {
        tableData : [],
        tableHTML : '',
        makeTable : function(type) {
            if(this.tableData.length==0 && type) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<li id="'+this.tableData[i].fileId+'">' +
                                            '<div class="tpc_skip m_skip"s style="height: 145px;background: url(/upload/contents/'+ this.tableData[i].saveFileName +') center top no-repeat;background-size: 100% 100%;"></div>'+
                                            '<input type="checkbox" id="n1_'+this.tableData[i].fileId+'" name="n">' +
                                            '<label for="n1_'+this.tableData[i].fileId+'">선택</label>' +
                                            '<p>' +
                                                '<span>'+this.tableData[i].fileName+'</span>' +
                                            '</p>' +
                                        '</li>';   
                    }
                return this.tableHTML;
            }
        }
    }
    
    function init(url, type) {
        type = type || false;
        callApi.getData(url, function (result) {
            module.tableData = result;
            $('#contentsmanageList').html(module.makeTable(type));
            
        });
    }

    function search() {
        init('/contentsmanage-list?page=1&size=10&conditionKey='+$('#conditionKey').html()+"&conditionValue="+$('#conditionValue').val(), true);
    }

    $(document).ready(function() {
        init('/contentsmanage-list?page=1&size=10');
    });

    $(document).on('click', '.delete_icon_btn', function() {
        let selectorIdArray = [];
        $('#contentsmanageList li').find('input[type="checkbox"]:checked').each(function(){
            selectorIdArray.push($(this).parent().attr('id'));
        });
        callApi.setData("/contentsmanage-list", { fileId : selectorIdArray }, function(result) {
            alert('삭제되었습니다.');
            init('/contentsmanage-list?page=1&size=10');
        });
    });
    $(document).on('click', '#allcheck2', function() {
        var checked = $(this).is(":checked");
        $('#contentsmanageList li').find('input[type="checkbox"]').each(function(){
            this.checked = checked;
        });
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>교육컨텐츠관리</h4>
		</div>
		<div class="nv_contents_select_box">
			<div class="nv_select_box">
				<p id="conditionKey">정렬기준</p>
				<ul>
					<li>파일명</li>
					<li>등록날짜</li>
				</ul>
			</div>
			<button type="button" class="nv_green_button reset_icon_btn" onclick="javascript:search();">새로고침</button>
			<button type="button" class="nv_red_button delete_icon_btn m_skip" >선택 항목 삭제</button>
		</div>
		<div class="nv_contents_manage_box">
			<div class="top_checkarea_box">
				<input type="checkbox" id="allcheck2">
                <label for="allcheck2">전체 선택</label>
			</div>
			<div class="nv_contetns_list">
				<ul id="contentsmanageList"></ul>
			</div>
			<div class="add_contents_btn_area">
				<button type="button" class="nv_red_button add_icon_btn m_skip" onclick="javascript:location.href='/contentsmanage/view'">컨텐츠 등록</button>
			</div>
			<div class="nv_m_btn_area pc_skip tpc_skip">
				<button type="button" class="nv_red_button delete_icon_btn left">선택 항목 삭제</button>
				<button type="button" class="nv_blue_button add_icon_btn right" onclick="javascript:location.href='/contentsmanage/view'">컨텐츠 등록</button>
			</div>
		</div>
	</div>
</div>
