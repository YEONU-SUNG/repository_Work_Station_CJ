<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="//cdn.ckeditor.com/4.11.1/standard/ckeditor.js"></script>
<script>
    var module = {
        faq : {}
    }
    $(document).on('submit', '#faqForm', function(event) {
        event.preventDefault();
        var params;
        var url = '/faq-add';
        var formData = new FormData();
        if(Object.keys(module.faq).length==0) {
        } else {
            formData.append('faqId', module.faq.faqID);
        }
        formData.append('question', $('#question').val());
        formData.append('answer', CKEDITOR.instances.answer.getData());
        callApi.setMultipartData(url, formData, function(result) {
            alert('정상적으로 처리되었습니다.');
            location.href = '/faq/view/'+result.faqID;
        })
    });

    function init(faqID, form) {
        callApi.getData('/faq-view/'+faqID, function(result) {
            module.faq = result;
            //form.question.innerHTML = module.faq.question;
            form.question.value = module.faq.question;
            CKEDITOR.instances.answer.setData(module.faq.answer);
            //form.answer.innerHTML = module.faq.answer;
        });
    }
    $(document).ready(function() {
        var faqID = ${FaqID};
        if(faqID!==0) init(faqID, document.getElementById('faqForm'));
    });
</script>
<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header"><h4>FAQ 등록</h4></div>
		<div class="nv_table_tit nv_wirte_tit">
			<h4>FAQ 작성</h4>
			<p><span class="nv_red">*</span> 는 필수 입력 사항입니다.</p>
		</div>
        <form id="faqForm">
            <table class="nv_table nv_write_table" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="20%">
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <td class="nv_bold textcenter"><span class="nv_red">*</span>질문</td>
                        <td><input type="text" class="nv_input" id="question" name="question" placeholder="질문을 입력해주세요."/></td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter"><span class="nv_red">*</span>내용</td>
                        <td>
                            <textarea name="answer" id="answer" cols="30" rows="10" class="nv_textarea"></textarea>
                            <script>
                                CKEDITOR.replace('answer', {
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
                </tbody>
            </table>
            <div class="btn_area nv_page_bottomarea_type2">
                <button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/faq'">목록</button>
                <button type="submit" class="nv_blue_button m_w_100">저장</button>
            </div>
        </form>
	</div>
</div>
