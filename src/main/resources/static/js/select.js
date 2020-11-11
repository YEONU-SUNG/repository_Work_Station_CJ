$(document).on('click', '.nv_select_box p', function() {
    $(this).siblings("ul").toggleClass("on");
});
$(document).on('click', '.nv_select_box li', function() {
    var $text = $(this).text();
    $(this).parents(".nv_select_box").children("p").text($text);
    $(this).parents(".nv_select_box").children("ul").removeClass("on");
});
$(document).on('click', '.nv_select_box', function() {
    var $index = $(this).parents("tr").index();
    $(this).children("ul").css("z-index", 100-$index);
});

// $(document).click(function(e){ 
//     if(e.target.className =="nv_select_box" || e.target.className ==""){return false} 
//     $(".nv_select_box").children("ul").removeClass("on");
// });
