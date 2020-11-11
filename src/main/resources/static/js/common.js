$(function(){
    $(".nv_lnv_level1 > li").click(function(){
        $(".nv_lnv_level2").css("max-height", 0);
        $(this).siblings("li").children("a").removeClass("on");
        $(this).children("a").addClass("on");
        
        var $height = $(this).children(".nv_lnv_level2").children("li").length;
        $height = $height*55;
        $(this).children(".nv_lnv_level2").css("max-height", $height);
        console.log($height);
    });
    $(".nv_toggle").click(function(){
        $(".nv_lnb").addClass("on");
        $(".lnb_close").addClass("on");
    });
    
    $(".lnb_close").click(function(){
        $(".nv_lnb").removeClass("on");
        $(this).removeClass("on");
    });
});

$(function(){
    $(window).on("load",function(){
        $(".nv_lnb").mCustomScrollbar();
    });
});

$(document).on('click', '.nv_modal1_open', function() {
    $(".nv_modal1").addClass("on");
});
$(document).on('click', '.nv_modal2_open', function() {
    $(".nv_modal2").addClass("on");
});
$(document).on('click', '.nv_modal4_open', function() {
    $(".nv_modal4").addClass("on");
});
$(document).on('click', '.nv_modal5_open', function() {
    $(".nv_modal5").addClass("on");
});
$(document).on('click', '.nv_modal_close', function() {
    $(this).parents(".nv_modal").removeClass("on");
});
$(document).on('click', '.nv_modal button.nv_red_button', function() {
    $(this).parents().parents().parents().parents(".nv_modal").removeClass("on");
});
$(document).on('click', '.nv_modal_com', function() {
    $(this).parents(".nv_modal").removeClass("on");
});
$(document).on('click', '.nv_active_table tr', function() {
    $(this).toggleClass("active"); 
});
$(document).on('click', '.nv_view_nexttable', function() {
    $(this).next("tr").children(".nv_hidden_table_area").toggleClass("on");
});
