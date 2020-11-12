<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
var deleteIndexs = new Array();
$(document).on('click', '#save', function(event) {
    var formData = new FormData();
    var files = $('input[type="file"]')[0].files;
    if(deleteIndexs.length!=0) {
        for(var i=0; i<files.length; i++) {
            for(var j=0; j<deleteIndexs.length;j++) {
                if(i!=j) formData.append('upload', files[i]);
            }
        }
    } else {
        for(var i=0; i<files.length; i++) {
            formData.append('upload', files[i]);
        }    
    }
    callApi.setMultipartData('/contentsmanage/view', formData, function(result) {
        alert('정상적으로 처리되었습니다.');
        //location.href='/contentsmanage/list';
        location.href='/admin/contentsmanage';
    });
});
$(document).on('change', 'input[type="file"]', function() {
    var files = $(this)[0].files;
    for(var i=0; i<files.length; i++) {
        var file = files[i];
        $('#fileList').append(
            '<li id="'+i+'">'+file.name+'('+Math.ceil(file.size/1024)+'KB)' +
				'<p class="delete_contents_btn">삭제</p>' +
			'</li>');
    }
});
$(document).on('click', '.delete_contents_btn', function() {
    deleteIndexs.push($(this).parent().attr('id'));
    $(this).parent().remove();
});
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>교육컨텐츠추가</h4>
		</div>
		<div class="nv_table_tit">
			<h4>교육컨텐츠추가</h4>
		</div>
		<div class="nv_table_box">
			<table class="nv_table" cellpadding="0" cellspacing="0">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<td class="nv_bold textcenter">컨텐츠</td>
						<td>
							<ul class="nv_add_contents_area" id="fileList"></ul>
						</td>
					</tr>
                    <tr>
                        <td class="nv_bold textcenter">파일추가</td>
                        <td>
                            <input type="file" name="upload" id="uploadBtn0" class="uploadBtn skip" multiple />
                            <label for="uploadBtn0" class="do_file_btn nv_blue_button">찾아보기</label>
                        </td>
                    </tr>
				</tbody>
			</table>
			<div class="btn_area nv_page_bottomarea_type2">
				<button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/admin/contentsmanage'">목록</button>
				<button type="button" class="nv_blue_button m_w_100" id="save">저장</button>
			</div>
		</div>
	</div>
</div>
