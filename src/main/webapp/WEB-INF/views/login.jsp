<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <%-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> --%>
    <script src="/admin/static/js/jquery.min.js"></script>
    <script src="/admin/static/js/jquery.ajax-cross-origin.min"></script>
    <%-- <script src="https://code.jquery.com/jquery-1.12.4.js"
  integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
  crossorigin="anonymous"></script> --%>
<%-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> --%>

    <!---<script src="js/jquery.mCustomScrollbar.js"></script>--->
    <script src="/admin/static/js/common.js"></script>
    <script src="/admin/static/js/select.js"></script>
    <script src="/admin/static/js/crypto-js.js"></script>
    <link rel="stylesheet" href="/admin/static/css/common.css" />
    <link rel="stylesheet" href="/admin/static/css/jquery.mCustomScrollbar.css" />
    <title>DOOSAN - 방문관리시스템</title>
</head>
<body>
<form action="login" id="loginForm">
    <div class="nv_login_wrap">
        <div class="nv_login_box">
            <h1 class="login_logo">DOOSAN 두산</h1>
            <input type="text" onkeyup="enterkey();" id="AdminID" name="AdminID" class="nv_login_input" placeholder="ID" required />
            <input type="password" onkeyup="enterkey();" id="AdminPW" name="AdminPW" class="nv_login_input" placeholder="PASSWORD" required />
            <%-- <div class="nv_login_check">
                <input type="checkbox" id="loginsave"/>
                <label for="loginsave">로그인 정보 저장</label>
            </div> --%>
            <input type="button" onclick="ssologin();" class="nv_login_button" value="LOGIN"/>

            <li class="m_skip" style="margin-top: 20px;"><a href="javascript:void(0);" onclick="javascript:location.href='/upload/sample/방문예약시스템 사용자가이드-임직원.pdf'" style="margin-bottom: 15px; font-size: 15px;padding-right: 10px; color: coral; float: right;">운영가이드(상세) 다운로드</a></li>
            <li class="m_skip" style=" float: right; padding-left: 10px; padding-right: 10px; font-size: 14px;">|</li>
			<li class="m_skip" ><a href="javascript:void(0);" onclick="javascript:location.href='/upload/sample/방문예약시스템-임직원가이드(간략본).pdf'" style="margin-bottom: 15px; font-size: 15px;padding-left: 10px; color: coral; float: right;">운영가이드(간략) 다운로드</a></li>
            <%-- <p style="position: fixed;bottom: 3%;" >(주)DOOSAN 방문관리시스템</p> --%>
        </div>
    </div>
</form>
<script>
    // $(document).ready(function() {
    //     $.ajax({
    //         url: '/login',
    //         type: 'POST',
    //         // data: $(this).serialize(),
    //         data: {
    //             AdminID : 'test',
    //             AdminPW : 'test'
    //         },
    //         success: function (result) {
    //             if (result=='Y'){
    //                 location.href = '/dashboard';
    //             } else {
                    
    //             }
    //         }
    //     });
    // });

    function enterkey() {
        if (window.event.keyCode == 13) {
             ssologin();
        }
    }

    function locallogin(Type)
    {
        var encryptedId = CryptoJS.AES.encrypt($('#AdminID').val(), "passwordQ1W2E3R!").toString();
        var encryptedPwd = CryptoJS.AES.encrypt($('#AdminPW').val(), "passwordQ1W2E3R!").toString();
        console.log(encryptedId, encryptedPwd);
        //var encryptedId = $('#AdminID').val();
        //var encryptedPwd = $('#AdminPW').val();
        $.ajax({
            url: '/admin/login',
            type: 'POST',
            //data: $(this).serialize(),
            data: {
                AdminID : encryptedId,
                AdminPW : encryptedPwd,
                LoginType : Type
            },
            success: function (result) {
                if (result=='Y'){
                    location.href = '/admin/dashboard';
                } else {

                    var encryptedId = $('#AdminID').val();
                    var encryptedPwd = $('#AdminPW').val();

                    $.ajax({
                        url: '/admin/login',
                        type: 'POST',
                        //data: $(this).serialize(),
                        data: {
                            AdminID : encryptedId,
                            AdminPW : encryptedPwd,
                            LoginType : Type
                        },
                        success: function (result) {
                            if (result=='Y'){
                                location.href = '/admin/dashboard';
                            } else {
                                alert("아이디/패스워드를 확인 하세요.")
                            }
                        }
                    });

                    
                }
            }
        });
    }

    function ssologin() {

        if($('#AdminID').val().trim() =="")
        {
            alert("아이디를 입력하세요.");
            return;
        }
        if($('#AdminPW').val().trim() =="")
        {
            alert("패스워드를 입력하세요.");
            return;
        }
        locallogin('L');
    }
</script>
</body>
</html>