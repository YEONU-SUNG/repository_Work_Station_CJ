<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/admin/static/js/jquery-ui-1.12.1.js"></script>
<script>
    var module = {
        tableData : [],
        tableHTML : '',
        makeTable : function() {
            if(this.tableData.length==0) {
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<li id="'+this.tableData[i].fileId+'">' +
                                            '<span class="con_name">'+this.tableData[i].fileName+'</span>' +
                                        '</li>';   
                    }
                return this.tableHTML;
            }
        }
    };
    var module2 = {
        tableData : [],
        tableHTML : '',
        makeTable : function() {
            if(this.tableData.length==0) {
                return '';
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<li id="'+this.tableData[i].id+'" class="required">' +
                                            '<span class="con_name">'+this.tableData[i].fileInfo.fileName+'</span>' +
                                        '</li>';   
                    }
                return this.tableHTML;
            }
        }
    }
    
    function contentsInit() {
        callApi.getData('/edumanage/contents-list', function (result) {
            module.tableData = result;
            $('#contentsList').html(module.makeTable());  
        });
    }
    function eductionInit() {
        callApi.getData('/edumanage/'+$('#type').val()+'-list', function (result) {
            module2.tableData = result;
            $('#eductionList').html(module2.makeTable());  
        });
    }

    function eductionSort() {
        $("#eductionList").sortable({
            update: function (event, ui) {
                callApi.setData('/edumanage/contents/sort', {ids : $(this).sortable('toArray')}, function(result) {

                });
            }
        });
        $("#eductionList").disableSelection();
    }

    $(document).on('dblclick', '#contentsList li', function() {
        callApi.setData('/edumanage/'+$('#type').val()+'/add/'+$(this).attr('id'), {}, function(result) {
            eductionInit();
        });
    });

    $(document).on('dblclick', '#eductionList li', function() {
        callApi.setData('/edumanage/contents/'+$(this).attr('id'), {}, function(result) {
            eductionInit();
        });
    });

    $(document).ready(function() {
        contentsInit();
        eductionInit();
        eductionSort();
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>교육관리</h4>
		</div>
		<div class="nv_edu_wrap">
			<div class="nv_edu_container">
				<div class="nv_edu_tit">
					<h4>컨텐츠목록</h4>
				</div>
				<ul id="contentsList"></ul>
			</div>
			<div class="nv_edu_container">
				<div class="nv_edu_tit">
                    <input type="hidden" value="${type }" id="type" />
                    <c:if test="${type eq 'safe'}"><h4>안전교육</h4></c:if>
                    <c:if test="${type eq 'security'}"><h4>보안교육</h4></c:if>
				</div>
				<ul id="eductionList" class="eduction"></ul>
			</div>
		</div>
		<div class="btn_area nv_page_bottomarea_type2">
			<button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/admin/edumanagelist'">목록</button>
			<%-- <button type="button" class="nv_blue_button m_w_100">저장</button> --%>
		</div>
	</div>
</div>
