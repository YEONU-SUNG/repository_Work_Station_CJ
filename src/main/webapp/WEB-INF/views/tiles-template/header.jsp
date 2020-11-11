<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="nv_top tpc_skip m_skip">
    <ul class="nv_reborn">
        <%-- <li><a href="/" class="nv_blue nv_bold">HOME</a></li> --%>
        <li><a class="nv_blue nv_bold"></a></li>
        
    </ul>
    <div class="nv_login_info_pc">
        <p class="nv_bold"><span class="nv_blue">${sessionScope.login.host.hostName}</span> 님, 반갑습니다. 좋은 하루 되세요.</p>
        <button type="button" class="nv_blue_button" onclick="javascript:location.href='/admin/logout'" style="line-height: 0px;">로그아웃</button>
    </div>
</div>
<div class="nv_m_header pc_skip">
    <p class="nv_toggle">모바일 메뉴 열기</p>
    <h1 class="nv_logo_m">DOOSAN</h1>
</div>