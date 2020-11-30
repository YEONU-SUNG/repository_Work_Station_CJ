<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="utf-8">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />
    <meta http-equiv='cache-control' content='no-cache'/>
    <meta http-equiv='expires' content='0'/>
    <meta http-equiv='pragma' content='no-cache'/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <%-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> --%>
    <script src="/admin/static/js/jquery.min.js"></script>
    <script src="/admin/static/js/jquery.mCustomScrollbar.js"></script>
    <script src="/admin/static/js/common.js"></script>
    <script src="/admin/static/js/select.js"></script>
    <link rel="stylesheet" href="/admin/static/css/common.css" />
    <link rel="stylesheet" href="/admin/static/css/jquery.mCustomScrollbar.css" />
    <title>DOOSAN - 방문관리시스템</title>
    <script>
        function convertEncoding(data) {
            var ua = window.navigator.userAgent;
            return (ua.indexOf('MSIE') > 0 || ua.indexOf('Trident') > 0) ? encodeURI('/admin'+data) : '/admin'+data;
        }
        var callApi = {
            getData : function(url, callbackFunc) {
                $.ajax({
                    url: convertEncoding(url),
                    type: 'get',
                    cache: false,
                    headers: { "cache-control": "no-cache", "custom": "1" },
                    success: function (result) {
                        callbackFunc(result);
                    },
                    error : function (err) {
                        alert(err.responseText);
                    }
                });
            },
            getFormData : function(url, params, callbackFunc) {
                $.ajax({
                    url: convertEncoding(url),
                    type: 'get',
                    cache: false,
                    data: params,
                    headers: { "cache-control": "no-cache", "custom": "1" },
                    success: function (result) {
                        callbackFunc(result);
                    },
                    error : function (err) {
                        alert(err.responseText);
                    }
                });
            },
            setData : function(url, params, callbackFunc) {
                $.ajax({
                    url: convertEncoding(url),
                    type: 'post',
                    data: params,
                    headers: { "custom": "1" },
                    success: function (result) {
                        callbackFunc(result);
                    },
                    error : function (err) {
                        alert(err.responseText);
                    }
                });
            },
            setFormData : function(url, params, callbackFunc) {
                $.ajax({
                    url: convertEncoding(url),
                    type: 'post',
                    processData: false,
                    contentType: false,
                    data: params,
                    headers: { "custom": "1" },
                    success: function (result) {
                        callbackFunc(result);
                    },
                    error : function (err) {
                        alert(err.responseText);
                    }
                });
            },
            setMultipartData : function(url, params, callbackFunc) {
                $.ajax({
                    url: convertEncoding(url),
                    type: 'post',
                    data: params,
                    enctype : 'multipart/form-data',
                    headers: { "custom": "1" },
                    contentType : false,
                    cache : false,
                    processData : false,
                    success: function (result) {
                        callbackFunc(result);
                    },
                    error : function (err) {
                        alert(err.responseText);
                    }
                });
            }
        };
        var page = {
            makePagenation : function(result) {
                let init = 'init';
                let limitIndex = 0, breakIndex =0;
                if(result.url==='/visitor/standby-list') init = 'standby_init';
                let pagenationHTML = '';
                pagenationHTML =   '<ul>' +
                                        '<li class="first" onclick="javascript:'+init+'(\''+result.firstURL+'\')">처음으로</li>' +
                                        '<li class="prev" onclick="javascript:'+init+'(\''+result.prevURL+'\')">이전으로</li>';
                
                if(result.page <= 5) {
                    breakIndex = 1;
                } else if (result.page > 5) {
                    breakIndex = result.page-5;
                }
                limitIndex = breakIndex + 9;
                for(var i=1; i<=result.totalPage; i++) {
                    if(breakIndex <= i && limitIndex>= i) 
                        pagenationHTML += (result.page===i)
                                        ? '<li class="on" onclick="javascript:'+init+'(\''+(result.url+'?page='+result.page+'&size='+result.size)+result.conditionURL+'\')">'+(i)+'</li>'
                                        : '<li onclick="javascript:'+init+'(\''+(result.url+'?page='+i+'&size='+result.size)+result.conditionURL+'\')">'+(i)+'</li>';
                }
                pagenationHTML += 	    '<li class="next" onclick="javascript:'+init+'(\''+result.nextURL+'\')">다음으로</li>' +
                                        '<li class="last" onclick="javascript:'+init+'(\''+result.lastURL+'\')">마지막으로</li>' +
                                    '</ul>';
                return pagenationHTML;
            }
        }
        $(document).on('click', '#allcheck', function() {
            var checked = $(this).is(":checked");
            $($(this).parent().parent().parent().next().find('input[type="checkbox"]')).each(function(){
                this.checked = checked;
            });
        });
    </script>
</head>
<body>
	<div class="nv_wrap">
		<tiles:insertAttribute name="lnb" />
		<div class="nv_container">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="body" />
		</div>
		<tiles:insertAttribute name="footer" />
	</div>
</body>
<script>
    
$(function(){
    $(window).on("load",function(){
        $(".nv_lnb").mCustomScrollbar();
    });
});
</script>
</html>
