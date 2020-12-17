<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    var module = {
        tableData : [],
        pagenation : {},
        pagenationHTML : '',
        tableHTML : '',
        activeButton : function(row) {
            return (row.activeFlag=='Y')
                 ? '<button type="button" class="nv_blue_button">활성</button>' : '<button type="button" class="nv_red_button">비활성</button>'; 
        },
        makeTable : function(pagenation, type) {
            if(this.tableData.length==0 && type) {
                alert('조회결과가 없습니다.');
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<tr id="'+(this.tableData[i].personalinfoID)+'">' +
                                            '<td class="nv_gray_check">' +
                                            '<input type="checkbox" id="n1_'+(this.tableData[i].personalinfoID)+'" value="'+(this.tableData[i].personalinfoID)+'">' +
                                            '<label for="n1_'+(this.tableData[i].personalinfoID)+'">선택</label></td>' +
                                            '<td>'+this.activeButton(this.tableData[i])+'</td>' +
                                            '<td class="tit"><a href="/personalinfo/view/'+(this.tableData[i].personalinfoID)+'">'+(this.tableData[i].title)+'</a></td>' +
                                            '<td class="name">'+(this.tableData[i].hostName)+'</td>' + 
                                        '<tr>';   
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

            $('#personalinfoTable > tbody').html(module.makeTable($('#pagenation'), type));
        });
    }

    function search() {
        init('/personalinfo-list?page=1&size=10&conditionKey='+$('#conditionKey').html()+"&conditionValue="+$('#conditionValue').val(), true);
    }

    $(document).ready(function() {
        init('/personalinfo-list?page=1&size=10');

        $('#conditionValue').keydown(function(event) {
			if (event.keyCode == 13) {
				search();
			}
        });
    });

    $(document).on('click', '#personalinfoTable > tbody > tr button', function() {
        var target = $(this);
        var PersonalinfoID = target.parent().parent().attr('id');
        callApi.setData("personalinfo-list/"+PersonalinfoID, {}, function (result) {
            target.parent().html(module.activeButton(result));
        })
    });
    
    $(document).on('click', '#selectorDelete', function() {
        let selectorIdArray = [];
        $('#personalinfoTable > tbody input[type="checkbox"]:checked').each(function(){
            selectorIdArray.push($(this).val());
        });
        callApi.setData("/personalinfo-list", { PersonalinfoID : selectorIdArray }, function(result) {
            alert('삭제되었습니다.');
            init('/personalinfo-list?page=1&size=10');
        });
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>개인정보처리방침</h4>
		</div>
		<div class="personalinfo_left m_skip">
			<p>※ 활성 상태의 게시글만 사용자에게 표시됩니다.</p>
		</div>
		<div class="nv_contents_search">
			<div class="nv_select_box">
				<p id="conditionKey">작성자</p>
				<ul>
					<li>작성자</li>
					<li>제목</li>
					<li>상태</li>
				</ul>
			</div>
			<div class="nv_search_box">
				<input type="text" class="nv_input" id="conditionValue">
                <input type="submit" class="nv_search_icon" onclick="javascript:search();">
			</div>
		</div>
		<div class="nv_table_box">
			<table class="nv_table textcenter nv_board_table" id="personalinfoTable">
				<colgroup>
					<col width="50">
					<col width="90">
					<col>
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th class="nv_gray_check">
                        <input type="checkbox" id="allcheck">
                        <label for="allcheck">전체 선택</label></th>
						<th>상태</th>
						<th>제목</th>
						<th>작성자</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			<div class="nv_m_btn_area nv_bord_btn_area">
				<button type="button" class="nv_red_button delete_icon_btn left" id="selectorDelete">선택 항목 삭제</button>
				<button type="button" class="nv_blue_button add_icon_btn right" onclick="javascript:location.href='/personalinfo/view'">개인정보처리방침 등록</button>
			</div>
			<div class="nv_table_pagenum" id="pagenation"></div>
		</div>
	</div>
</div>
