<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                                            '<span class="con_name">'+this.tableData[i].fileInfo.fileName+'</span>' +
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
                return this.tableHTML;
            } else {
                this.tableHTML = '';
                for(var i=0; i<this.tableData.length;i++) {
                    this.tableHTML +=   '<li id="'+this.tableData[i].id+'">' +
                                            '<span class="con_name">'+this.tableData[i].fileInfo.fileName+'</span>' +
                                        '</li>';   
                    }
                return this.tableHTML;
            }
        }
    }
    
    function init() {
        callApi.getData('/edumanage/safe-list', function (result) {
            module.tableData = result;
            $('#safeList').html(module.makeTable());  
        });
        callApi.getData('/edumanage/security-list', function (result) {
            module2.tableData = result;
            $('#securityList').html(module2.makeTable());  
        });
    }

    $(document).ready(function() {
        init();
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
					<h4>안전교육</h4>
					<button type="button" class="nv_blue_button" onclick="javascript:location.href='edumanage/view/safe'">수정</button>
				</div>
				<ul id="safeList"></ul>
			</div>
			<div class="nv_edu_container">
				<div class="nv_edu_tit">
					<h4>보안교육</h4>
					<button type="button" class="nv_blue_button" onclick="javascript:location.href='edumanage/view/security'">수정</button>
				</div>
				<ul id="securityList"></ul>
			</div>
		</div>
	</div>
</div>
