/**
 * Created by XIANGYANG on 2016/11/7.
 */
function changeBodyTheme(colorObj){
//           alert(colorObj);
    $('pageBody').addClass(colorObj);
}
$(function () {
    window.setTimeout(function () {
        $("#pageBody").css("visibility", "visible");
    }, 500);

    if($("#pageBody>#page-header>.page-header-inner>.page-logo>a>img").is(":hidden")){
        $("#sidebarToggerId").addClass("menu-toggler-right");
    }else{
        $("#sidebarToggerId").addClass("menu-toggler-left");
    }

});
function openDefaultReportTab(menuId) {
    window.location.href = "#/reportBusiness/"+menuId;
}
function cutChangeClicked(thisObj) {
    if($(thisObj).hasClass('menu-toggler-left')){
        $(thisObj).removeClass('menu-toggler-left');
        $(thisObj).addClass('menu-toggler-right');
    }else if($(thisObj).hasClass('menu-toggler-right')){
        $(thisObj).removeClass('menu-toggler-right');
        $(thisObj).addClass('menu-toggler-left');
    }
}
