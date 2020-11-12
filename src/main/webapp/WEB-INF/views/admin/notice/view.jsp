<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
function download(url , param) {
    window.location.href = url+'?name='+encodeURI(param);
}
</script>
<div class="nv_contents_wrap">
    <form method="post">
        <div class="nv_contents">
            <div class="nv_contents_main_header"><h4>공지사항</h4></div>
            <div class="nv_table_tit nv_wirte_tit">
                <h4>공지사항</h4>
            </div>
            <table class="nv_table nv_write_table" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="20%">
                    <col>
                </colgroup>
                <tbody>
                    <tr>
                        <td class="nv_bold textcenter">작성자 ID</td>
                        <td>${notice.hostName}</td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter">제목</td>
                        <td>${notice.title}</td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter">내용</td>
                        <td><c:out value ="${notice.contents}" escapeXml="false"/></td>
                    </tr>
                    <tr>
                        <td class="nv_bold textcenter">첨부파일</td>
                        <td>
                            <c:forEach items="${notice.files }" var="file">
                                <p>
                                    <span style="text-decoration: underline;">
                                        <a style="color:blue;" href="javascript:download('/file/${file.targetType}/${file.saveFileName}', '${file.fileName}');">${file.fileName}</a>
                                    </span>
                                </p>
                            </c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="btn_area nv_page_bottomarea_type2">
                <button type="button" class="nv_green_button m_w_100" onclick="javascript:location.href='/admin/notice'">목록</button>
                <button type="submit" class="nv_blue_button m_w_100">수정</button>
            </div>
        </div>
    </form>
</div>
