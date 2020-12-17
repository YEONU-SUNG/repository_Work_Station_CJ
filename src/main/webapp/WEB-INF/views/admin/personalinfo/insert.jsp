<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="//cdn.ckeditor.com/4.11.1/standard/ckeditor.js"></script>
<script>
    var module = {
        personalinfo : {}
    }
    $(document).on('submit', '#personalinfoForm', function(event) {
        event.preventDefault();
        var formData = new FormData();
        if(Object.keys(module.personalinfo).length==0) {
        } else {
            formData.append('personalinfoId', module.personalinfo.personalinfoID);
            var deleteFiles = module.personalinfo.files.filter(function(row) {
                return row.deleteFlag;
            });
            if(deleteFiles.length>0) formData.append('deleteFiles', deleteFiles.map(function(row) {
                return row.fileId;
            }));
        }
        formData.append('title', this.title.value);
        formData.append('contents', CKEDITOR.instances.contents.getData());
        formData.append('upload', $('input[type="file"]')[0].files[0]);
        callApi.setMultipartData('/personalinfo-add', formData, function(result) {
            alert('정상적으로 처리되었습니다.');
            location.href = '/personalinfo/view/'+result.personalinfoID;
        })
    });

    function init(personalinfoID, form) {
        callApi.getData('/personalinfo-view/'+personalinfoID, function(result) {
            module.personalinfo = result;
            form.title.value = module.personalinfo.title;
            CKEDITOR.instances.contents.setData(module.personalinfo.contents);
            //form.contents.innerHTML = module.personalinfo.contents;
            if(result.files.length>0) {
                form.uploadBtn0.dataset.fileId = module.personalinfo.files[0].fileId;
                form.fileName0.value = module.personalinfo.files[0].fileName;
            }
        });
    }

    $(document).on('change', 'input[type="file"]', function() {
        $(this).prev().val($(this)[0].files[0].name);
        module.personalinfo.files.filter(function(row) {
            if(row.fileId==this.dataset.fileId) return row.deleteFlag = true;
        });
    });

    $(document).on('click', '#personalinfoForm button[name="uploadDelete"]', function() {
        $(this).prev().prev().val('');
        $(this).prev().prev().prev().val('');
        if(Object.keys(module.personalinfo).length!==0) module.personalinfo.files[0].deleteFlag = true;
    });

    $(document).ready(function() {
        var personalinfoID = ${PersonalinfoID};
        if(personalinfoID!==0) init(personalinfoID, document.getElementById('personalinfoForm'));
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header"><h4>개인정보처리방침 등록</h4></div>
		<div class="nv_table_tit nv_wirte_tit">
			<h4>개인정보처리방침 작성</h4>
			<p><span class="nv_red">*</span> 는 필수 입력 사항입니다.</p>
		</div>
        <form id="personalinfoForm">
            <table class="nv_table nv_write_table">
                <colgroup><col width="20%"><col></colgroup>
                <tbody>
                    <tr>
                        <td class="nv_bold textcenter"><span class="nv_red">*</span>제목</td>
                        <td><input type="text" class="nv_input" id="title" name="title" placeholder="제목을 입력해주세요."/></td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter"><span class="nv_red">*</span>내용</td>
                        <td>
                            <textarea name="contents" id="contents" cols="30" rows="10" class="nv_textarea"></textarea>
                            <script>
                                CKEDITOR.replace('contents', {
                                    filebrowserUploadUrl : '/editor/image/upload'
                                });
                                CKEDITOR.on('dialogDefinition', function( ev ){
                                    var dialogName = ev.data.name;
                                    var dialogDefinition = ev.data.definition;
                                    switch (dialogName) {
                                        case 'image': 
                                            dialogDefinition.removeContents('Link');
                                            dialogDefinition.removeContents('advanced');
                                            break;
                                    }
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter">첨부파일</td>
                        <td>
                            <input type="hidden" id="fileId" />
                            <input type="text" id="fileName0" class="do_fileName nv_input" readonly="readonly">
                            <input type="file" name="upload" id="uploadBtn0" class="uploadBtn skip">
                            <label for="uploadBtn0" class="do_file_btn nv_blue_button">찾아보기</label>
                            <button type="button" name="uploadDelete" class="nv_blue_button m_w_100" style="margin-left: 5px;">삭제</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="btn_area nv_page_bottomarea_type2">
                <button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/personalinfo'">목록</button>
                <button type="submit" class="nv_blue_button m_w_100">저장</button>
            </div>
        </form>
	</div>
</div>
