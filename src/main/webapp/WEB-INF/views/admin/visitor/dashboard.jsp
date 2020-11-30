<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
    var dashboard = {
        position1 : {
            visitApplicationCount : 0,
            approvalStandbyCount : 0,
            visitStandbyCount : 0,
            visitCount : 0
        },
        position2 : {
            visitApplicationCount : 0,
            approvalStandbyCount : 0,
            visitStandbyCount : 0,
            visitCount : 0
        }
    }
    function dashBoardInit() {
        callApi.getData('/visitor/dashboard', function (result) {
            for(var i=0; i<result.length;i++) {
                if(result[i].siteCode=='화성') {
                    dashboard.position1.visitApplicationCount = result[i].visitApplicationCount;
                    dashboard.position1.approvalStandbyCount = result[i].approvalStandbyCount;
                    dashboard.position1.visitStandbyCount = result[i].visitStandbyCount;
                    dashboard.position1.visitCount = result[i].visitCount;   
                } else {
                    dashboard.position2.visitApplicationCount = result[i].visitApplicationCount;
                    dashboard.position2.approvalStandbyCount = result[i].approvalStandbyCount;
                    dashboard.position2.visitStandbyCount = result[i].visitStandbyCount;
                    dashboard.position2.visitCount = result[i].visitCount;   
                }
            }
            if( ${sessionScope.login.host.auth eq '0'} || ${sessionScope.login.host.auth eq '1'} || ${sessionScope.login.host.auth eq '2'})
            {
                $('#visitorApplicationCount_position1').html(dashboard.position1.visitApplicationCount);
                $('#visitorApplicationCount_position2').html(dashboard.position2.visitApplicationCount);
                $('#visitorApplicationCount_total').html(dashboard.position1.visitApplicationCount + dashboard.position2.visitApplicationCount);

                $('#visitorStandbyCount_position1').html(dashboard.position1.approvalStandbyCount);
                $('#visitorStandbyCount_position2').html(dashboard.position2.approvalStandbyCount);
                $('#visitorStandbyCount_total').html(dashboard.position1.approvalStandbyCount + dashboard.position2.approvalStandbyCount);

                $('#visitorApproveCount_position1').html(dashboard.position1.visitStandbyCount);
                $('#visitorApproveCount_position2').html(dashboard.position2.visitStandbyCount);
                $('#visitorApproveCount_total').html(dashboard.position1.visitStandbyCount + dashboard.position2.visitStandbyCount);

                $('#visitorCount_position1').html(dashboard.position1.visitCount);
                $('#visitorCount_position2').html(dashboard.position2.visitCount);
                $('#visitorCount_total').html(dashboard.position1.visitCount + dashboard.position2.visitCount);
            }
        });
    }

    $(document).on('click', '.nv_modal1_open', function() {
        $('.nv_modal1 textarea').html($(this).find('span').html());
    });

    $(document).ready(function() {
        dashBoardInit();
    });
</script>
<div class="nv_contents_wrap">
    <c:if test="${sessionScope.login.host.auth eq '1' or sessionScope.login.host.auth eq '2'}">
	<ul class="nv_visitoer_info">
		<li>
			<h4>방문 신청자 수
                <!-- <span class="info_detail">
                    화성<em class="nv_blue nv_bold" id="visitorApplicationCount_position1"></em>명 /
                    아산 <em class="nv_blue nv_bold" id="visitorApplicationCount_position2"></em>명
                </span> -->
            </h4>
			<p><span class="nv_blue nv_bold" id="visitorApplicationCount_total"></span>명</p>
		</li>
		<li>
			<h4>승인 대기자 수
                <!-- <span class="info_detail" >
                    화성 <em class="nv_blue nv_bold" id="visitorStandbyCount_position1"></em>명 /
                    아산 <em class="nv_blue nv_bold" id="visitorStandbyCount_position2"></em>명
                </span> -->
            </h4>
			<p><span class="nv_blue nv_bold" id="visitorStandbyCount_total"></span>명</p>
		</li>
		<%-- <li>
			<h4>방문 대기자 수
                <span class="info_detail">
                    화성 <em class="nv_blue nv_bold" id="visitorApproveCount_position1"></em>명 /
                    아산 <em class="nv_blue nv_bold" id="visitorApproveCount_position2"></em>명
                </span>
            </h4>
			<p><span class="nv_blue nv_bold" id="visitorApproveCount_total"></span>명</p>
		</li>
		<li>
			<h4>방문객 수
                <span class="info_detail">
                    화성 <em class="nv_blue nv_bold" id="visitorCount_position1"></em>명 /
                    아산 <em class="nv_blue nv_bold" id="visitorCount_position2"></em>명
                </span>
            </h4>
			<p><span class="nv_blue nv_bold" id="visitorCount_total"></span>명</p>
		</li> --%>
	</ul>
    </c:if>
	<div class="nv_contents">
        <jsp:include page="./approve.jsp" />
        <%-- <jsp:include page="./standby.jsp" /> --%>
	</div>
</div>
<div class="nv_modal nv_modal1">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2>반입 물품 확인</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <div>
                <h4 class="textarea_name">반입물품</h4>
                <textarea name="" id="" cols="30" rows="10" class="nv_textarea" readonly></textarea>
            </div>
            <div class="btn_area">
                <button type="button" class="nv_blue_button" onclick="javascript:$('.nv_modal_close').click();">확인</button>
            </div>
        </div>
    </div>
</div>