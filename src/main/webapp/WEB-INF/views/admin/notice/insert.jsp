<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="//cdn.ckeditor.com/4.11.1/standard/ckeditor.js"></script>
<script>
    var module = {
        notice : {}
    }
    $(document).on('submit', '#noticeForm', function(event) {
        event.preventDefault();
        var formData = new FormData();
        if(Object.keys(module.notice).length==0) {
        } else {
            formData.append('noticeId', module.notice.noticeID);
            var deleteFiles = module.notice.files.filter(function(row) {
                return row.deleteFlag;
            });
            if(deleteFiles.length>0) formData.append('deleteFiles', deleteFiles.map(function(row) {
                return row.fileId;
            }));
        }
        formData.append('title', this.title.value);
        formData.append('contents', CKEDITOR.instances.contents.getData());
        formData.append('upload', $('input[type="file"]')[0].files[0]);
        callApi.setMultipartData('/notice-add', formData, function(result) {
            alert('정상적으로 처리되었습니다.');
            location.href = '/notice/view/'+result.noticeID;
        })
    });

    function init(noticeID, form) {
        callApi.getData('/notice-view/'+noticeID, function(result) {
            module.notice = result;
            form.title.value = module.notice.title;
            CKEDITOR.instances.contents.setData(module.notice.contents);
            //form.contents.innerHTML = module.notice.contents;
            if(result.files.length>0) {
                form.uploadBtn0.dataset.fileId = module.notice.files[0].fileId;
                form.fileName0.value = module.notice.files[0].fileName;
            }
        });
    }

    $(document).on('change', 'input[type="file"]', function() {
        $(this).prev().val($(this)[0].files[0].name);
        module.notice.files.filter(function(row) {
            if(row.fileId==this.dataset.fileId) return row.deleteFlag = true;
        });
    });

    $(document).on('click', '#noticeForm button[name="uploadDelete"]', function() {
        $(this).prev().prev().val('');
        $(this).prev().prev().prev().val('');
        if(Object.keys(module.notice).length!==0) module.notice.files[0].deleteFlag = true;
    });

    $(document).ready(function() {
        var noticeID = ${NoticeID};
        if(noticeID!==0) init(noticeID, document.getElementById('noticeForm'));
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header"><h4>공지사항등록</h4></div>
		<div class="nv_table_tit nv_wirte_tit">
			<h4>공지사항 작성</h4>
			<p><span class="nv_red">*</span> 는 필수 입력 사항입니다.</p>
		</div>
        <form id="noticeForm">
            <table class="nv_table nv_write_table" cellpadding="0" cellspacing="0">
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
                <button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/notice'">목록</button>
                <button type="submit" class="nv_blue_button m_w_100">저장</button>
            </div>
        </form>
	</div>
</div>
