/**
 * Created by Jetvan on 2016/12/16.
 */
// document.getElementById("view-fullscreen").click=function(){
//     var elem = document.getElementById("pageBody");
//     debugger;
//     requestFullScreen(elem);
// };
// function requestFullScreen(element) {
//     var requestMethod =element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
//     if (typeof requestMethod != "undefined" && rfs) {
//         requestMethod.call(element);
//         return;
//     }
//     debugger;
//     var wscript = new ActiveXObject("WScript.Shell");
//     console.log("全屏"+wscript);
//
//     wscript.sendKeys("{F11}");
// }
function fullScreen() {
    var element= document.body;//若要全屏页面中div，var element= document.getElementById("divID");
    //IE 10及以下ActiveXObject
    if (window.ActiveXObject)
    {
        var WsShell = new ActiveXObject('WScript.Shell')
        WsShell.SendKeys('{F11}');
    }
    //HTML W3C 提议
    else if(element.requestFullScreen) {
        element.requestFullScreen();
    }
    //IE11
    else if(element.msRequestFullscreen) {
        element.msRequestFullscreen();
    }
    // Webkit (works in Safari5.1 and Chrome 15)
    else if(element.webkitRequestFullScreen ) {
        document.documentElement.webkitRequestFullScreen();
    }
    // Firefox (works in nightly)
    else if(element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
    }
}
//退出全屏
function fullExit(){
    var element= document.body;//若要全屏页面中div，var element= document.getElementById("divID");
    //IE ActiveXObject
    if (window.ActiveXObject)
    {
        var WsShell = new ActiveXObject('WScript.Shell')
        WsShell.SendKeys('{F11}');
    }
    //HTML5 W3C 提议
    else if(element.requestFullScreen) {
        document.exitFullscreen();
    }
    //IE 11
    else if(element.msRequestFullscreen) {
        document.msExitFullscreen();
    }
    // Webkit (works in Safari5.1 and Chrome 15)
    else if(element.webkitRequestFullScreen ) {
        document.webkitCancelFullScreen();
    }
    // Firefox (works in nightly)
    else if(element.mozRequestFullScreen) {
        document.mozCancelFullScreen();
    }
}
var fullScreenClickFlag=true;
var menuids;
var beforeScreenSizeContainer;
var beforeScreenSizeiframe;
function fullScreenClick (){
    debugger;
    if (fullScreenClickFlag){
        fullScreen();
        var clientHeightTemp= parseInt(document.documentElement.clientHeight)-parseInt($('.page-top').height() )-parseInt($('#navTabsId').height());
        //$("#reportContent2"+menuids).height(clientHeightTemp);
		beforeScreenSizeiframe=$(".iframereport").height();
		$(".iframereport").height(clientHeightTemp+clientHeightTemp*0.22);
		var pcerheight=$('#page-container').height();
		beforeScreenSizeContainer=pcerheight;
		$('#page-container').height(pcerheight+clientHeightTemp*0.22);
        fullScreenClickFlag=false;
    }else{
        fullExit();
        var clientHeightTemp1 = parseInt(document.documentElement.clientHeight)-parseInt($('.page-top').height() )-parseInt($('#navTabsId').height());
        //$("#reportContent2"+menuids).height(clientHeightTemp1);
		//$(".iframereport").height(clientHeightTemp1-clientHeightTemp1*0.18);
		$(".iframereport").height(beforeScreenSizeiframe);
		//var pcerheight=$('#page-container').height();
		//$('#page-container').height(pcerheight-clientHeightTemp1*0.18);
		$('#page-container').height(beforeScreenSizeContainer);
        fullScreenClickFlag=true;
    }
}