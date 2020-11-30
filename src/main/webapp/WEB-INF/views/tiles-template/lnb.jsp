<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p class="lnb_close pc_skip">LNB 닫기</p>
<div class="nv_lnb">
    <h1 class="nv_logo tpc_skip m_skip">DOOSAN 두산</h1>
    <div class="nv_m_profile pc_skip">
        <div class="img_box"><img src="/admin/static/imgaes/profileimg.png" alt="프로필이미지"></div>
        <div class="name_box">
            <p class="username">${sessionScope.login.host.hostName}</p>
            <p class="userid">${sessionScope.login.host.hostID}</p>
        </div>
        <button type="button" class="nv_logout" onclick="javascript:location.href='/admin/logout'"></button>
    </div>
    <nav>
        <ul class="nv_lnv_level1">
            <li data-lnb="dashboard" data-url="/admin/dashboard" data-name="방문 신청 현황"><a id="dashboard" href="/admin/dashboard">방문 신청 현황</a></li>
            <%-- <c:if test="${sessionScope.login.host.auth eq '0' or sessionScope.login.host.auth eq '1'}"> --%>
            <c:if test="${sessionScope.login.host.auth eq '0'}">
                <li data-lnb="visitor" data-url="/admin/visitor" data-name="방문 승인 관리"><a id="visitor" href="/admin/visitor">방문 승인 관리 </a></li>
                <li data-lnb="visitor-view" data-url="/admin/visitor/view" data-name="방문 대리 신청"><a id="visitor-view" href="/admin/visitor/view">방문 대리 신청</a></li>
                <li data-lnb="visitor-history" data-url="/admin/visitor/history" data-name="방문 이력 조회"><a id="visitor-history" href="/admin/visitor/history">방문 이력 조회</a></li>
                <li data-lnb="visitor-blacklist" data-url="/admin/visitor/blacklist" data-name="방문 제한자 관리"><a id="visitor-blacklist" href="/admin/visitor/blacklist">방문 제한자 관리</a></li>
                <li data-lnb="authoritymanage" data-url="/admin/authoritymanage" data-name="관리자 권한관리"><a id="authoritymanage" href="/admin/authoritymanage">관리자 권한관리</a></li>
                <li data-lnb="building-site-mapping" data-url="/admin/building/site/mapping/list" data-name="건물관리"><a id="building-site-mapping" href="/admin/building/site/mapping/list">건물관리</a></li>
            </c:if>
            <%-- <c:if test="${sessionScope.login.host.auth eq '2'}"> --%>
            <c:if test="${sessionScope.login.host.auth eq '2' or sessionScope.login.host.auth eq '1'}">
                <li data-lnb="visitor" data-url="/admin/visitor" data-name="방문 승인 관리"><a id="visitor" href="/admin/visitor">방문 승인 관리 </a></li>
                <li data-lnb="visitor-view" data-url="/admin/visitor/view" data-name="방문 대리 신청"><a id="visitor-view" href="/admin/visitor/view">방문 대리 신청</a></li>
                <li data-lnb="visitor-history" data-url="/admin/visitor/history" data-name="방문 이력 조회"><a id="visitor-history" href="/admin/visitor/history">방문 이력 조회</a></li>
                <li data-lnb="visitor-blacklist" data-url="/admin/visitor/blacklist" data-name="방문 제한자 관리"><a id="visitor-blacklist" href="/admin/visitor/blacklist">방문 제한자 관리</a></li>
            </c:if>
            <c:if test="${sessionScope.login.host.auth eq '3'}">
                <li data-lnb="visitor" data-url="/admin/visitor" data-name="방문 승인 관리"><a id="visitor" href="/admin/visitor">방문 승인 관리 </a></li>
                <li data-lnb="visitor-view" data-url="/admin/visitor/view" data-name="방문 대리 신청"><a id="visitor-view" href="/admin/visitor/view">방문 대리 신청</a></li>
                <li data-lnb="visitor-history" data-url="/admin/visitor/history" data-name="방문 이력 조회"><a id="visitor-history" href="/admin/visitor/history">방문 이력 조회</a></li>
                <li data-lnb="visitor-blacklist" data-url="/admin/visitor/blacklist" data-name="방문 제한자 관리"><a id="visitor-blacklist" href="/admin/visitor/blacklist">방문 제한자 관리</a></li>
            </c:if>
            <c:if test="${sessionScope.login.host.auth eq '4'}">
                <%-- <li data-lnb="visitor" data-url="/admin/visitor" data-name="방문 승인 관리"><a id="visitor" href="/admin/visitor">방문 승인 관리 </a></li> --%>
                <li data-lnb="visitor-history" data-url="/admin/visitor/history" data-name="방문 이력 조회"><a id="visitor-history" href="/admin/visitor/history">방문 이력 조회</a></li>
                <li data-lnb="visitor-blacklist" data-url="/admin/visitor/blacklist" data-name="방문 제한자 관리"><a id="visitor-blacklist" href="/admin/visitor/blacklist">방문 제한자 관리</a></li>
            </c:if>
            
            <c:if test="${sessionScope.login.host.auth eq '1'}">
                <!-- <li data-lnb="contentsmanage" data-url="/admin/contentsmanage" data-name="교육컨텐츠관리"><a id="contentsmanage" href="/admin/contentsmanage">교육컨텐츠관리</a></li>
                <li data-lnb="edumanagelist" data-url="/admin/edumanagelist" data-name="교육관리"><a id="edumanagelist" href="/admin/edumanagelist">교육관리</a></li>
                <li data-lnb="notice" data-url="/admin/notice" data-name="공지사항"><a id="notice" href="/admin/notice">공지사항</a></li>
                <li data-lnb="faq" data-url="/admin/faq" data-name="FAQ"><a id="faq" href="/admin/faq">FAQ</a></li> -->
                <!-- <li data-lnb="personalinfo" data-url="/admin/personalinfo" data-name="개인정보처리방침"><a id="personalinfo" href="/admin/personalinfo">개인정보처리방침</a></li> -->
            </c:if>
        </ul>
    </nav>
</div>
<script>
    var menu = {
        depth : '1',
        url : '/admin/dashboard',
        name : '방문 신청 현황',
        makeRibbon : function(menu) {
            return '<li><a href="'+menu.url+'" class="nv_blue nv_bold" >'+menu.name+'</a></li>';    
        },
        saveHistory : function(lnb, url, name) {
            localStorage.setItem('lnb', lnb);
            // this.menu.url = url;
            // this.menu.name = name;
            // localStorage.setItem('menu', JSON.stringify(this.menu));
            this.url = url;
            this.name = name;
            localStorage.setItem('menu', JSON.stringify(this));
        }
    }
    
    $(document).on('click', '.nv_lnv_level1 li', function() {
        menu.saveHistory(this.dataset.lnb, this.dataset.url, this.dataset.name);
    });
    $(document).ready(function() {
        if(document.referrer.split('/')[4]=='login' || localStorage.getItem('lnb')=='undefined') {
            localStorage.setItem('lnb', 'dashboard');
            localStorage.setItem('menu', JSON.stringify(menu));
        }
        $('.nv_lnv_level1 li > #'+localStorage.getItem('lnb')).attr('class', 'on');
        //$('.nv_reborn').append(menu.makeRibbon(JSON.parse(localStorage.getItem('menu'))));
        //$('.nv_contents_header').html('<h2>제목</h2>');
    });
</script>