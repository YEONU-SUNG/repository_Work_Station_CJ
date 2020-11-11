$(function(){
    //토글 열기
    $(".nv_toggle").click(function(){
        $(".nv_lnb").addClass("on");
        $(".nv_toggle_close").addClass("on");
        $("html, body").css("overflow", "hidden");
    });

    //토글 닫기
    $(".nv_toggle_close").click(function(){
        $(".nv_lnb").removeClass("on");
        $(".nv_toggle_close").removeClass("on");
        $("html, body").css("overflow", "auto");
    });
});