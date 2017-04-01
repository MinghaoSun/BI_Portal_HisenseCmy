var large=false;
function add(obj){
	//var id = obj.id;
	$(".menuClickStyle").removeClass("menuClickStyle");
	$(obj).addClass("menuClickStyle");
	var id = $(obj).attr('reportId');
	globalMenuId=id;
	//var name = obj.innerHTML;
	var name = $(obj).text();
	var uri = obj.href;
	var refresh=false;
//				var item2 = {'id':'2','name':'首页22','url':'#/reportTool/20160407141758754','closable':true};
	var item2 = {'id':id,'name':name,'url':uri,'closable':true};
	if(!$('#tab_seed_'+item2.id)[0]){
		refresh=true;
	}
	closableTab.addTab(item2);
	var personalSettingItemId=$(obj).attr('personalSettingItem');
	//alert('obj.personalSettingItem:'+personalSettingItemId);
	if (personalSettingItemId!=null) {
		$('#'+personalSettingItemId).click();
	}else{
		var iframeOPen=$('#reportContent'+id)[0];
		if(refresh&&iframeOPen){
			// $scope.getApi('sysMenu/getReportMenuPage.do', {menuId:id}, function (datas){
			// 	alert(datas.reqLocation);
			// 	$('#reportContent2'+id).getAttribute('src').replace(datas.reqLocation);
			// });
			$(".menuClickStyle").removeClass("menuClickStyle");
			$(obj).addClass("menuClickStyle");
			$('#'+id+'ForShowId').click();
			$.ajax({
				url: 'sysMenu/getReportMenuPage.do',
				async: false,
				type: "GET",
				data:{menuId: id},
				dataType: "json",
				success: function (data){
					console.log(data);
					$('#reportContent2'+id).attr('src', data.reqLocation);
					$('#uiViewReportBusiness'+id).removeClass('ng-hide')
				}});

		}else{
			$('#'+id+'ForShowId').click();
		}
	}

}

function addForIndex(obj){
	var id = $(obj).attr('reportOfIndexId');
	//alert("reportOfIndexId:"+id)
	var name = obj.innerHTML;
	var uri = obj.href;
//				var item2 = {'id':'2','name':'首页22','url':'#/reportTool/20160407141758754','closable':true};
	var item2 = {'id':id,'name':name,'url':uri,'closable':true};
	closableTab.addTab(item2);
	controlTabClick(id);
}
function  mainTabClick(thisObj) {
	$("li[id^=tab_seed_]").removeClass("active");
	$(thisObj).parent().addClass("active");
}
function  controlTabClick(menuIdPara) {
	menuIdPara=String(menuIdPara);
	var menuIdTemp;
	$(".page-sidebar-menu li").find("a").each(function () {
		//var currentName = $(this).text();
		menuIdTemp = String($(this).attr("id"));
		if (menuIdPara == menuIdTemp) {
			$("#"+menuIdTemp).click();
			return false;
		}
	});
	$("#personalSettingUlId li").find("a").each(function () {
		//var currentName = $(this).text();
		menuIdTemp = String($(this).attr("id"));
		if (menuIdPara == menuIdTemp) {
			document.getElementById(menuIdTemp).click();
			//alert("dfafasfasfdasf%%")
			return false;
		}
	});
}
var closableTab = {
	//frame加载完成后设置父容器的高度，使iframe页面与父页面无缝对接
	frameLoad:function (frame){
			var mainheight = $(frame).contents().find('body').height();
			alert(mainheight);
			$(frame).parent().height(mainheight);
		},

    //添加tab
	addTab:function(tabItem){
		var id = "tab_seed_" + tabItem.id;
		var container =tabItem.url;
		$("li[id^=tab_seed_]").removeClass("active");
		if(!$('#'+id)[0]){
			var li_tab = '<li style="border-left:0.1px solid #A9A9A9;padding: 2px 0px 0px 2px; height: 25px;" role="presentation" class="" id="'+id+'"><span onclick="controlTabClick('+'\''+tabItem.id+'\''+')"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 0px 15px">'+tabItem.name;
			if(tabItem.closable){
				li_tab = li_tab + '</span><i title="放大显示" class="glyphicon glyphicon-resize-full small"  tabenlarge="enlarge'+id+'"  onclick="closableTab.enlargeTab(this)"></i><i title="恢复正常显示" class="glyphicon glyphicon-resize-small small"  tabenlarge="enlargeResize'+id+'" style="display: none;" onclick="closableTab.resizeTab(this)"></i><i title="关闭当前窗口" class="fa fa-close small" tabclose="'+id+'" style="top:1px;position: relative;"  onclick="closableTab.closeTab(this)"></i></li> ';
			}else{
				li_tab = li_tab + '</span></li>';
			}

			$('#navTabsId').append(li_tab);
		}
		$("#"+id).addClass("active");
	},

	//关闭tab
	closeTab:function(item){
		var val = $(item).attr('tabclose');

		$('#'+val).prev().addClass('active');
		//alert($('#'+val).prev().find("span").text())
		if ($('#'+val).prev().find("span").attr('mainTabClickId')=='mainTabClickId'){
			//alert($('#'+val).prev().find("span").attr('mainTabClickId'));
			$('#mainTabClickId').click();
		}else{
			$('#'+val).prev().find("span").click();
		}
		$("#"+val).remove();
	},

	//放大tab
	enlargeTab:function(item){
		var val = $(item).attr('tabclose');
		/*$('#page-header').hide();
		$('#clearfix').hide();
		$('#page-container').removeClass('page-container');
		$('#page-sidebar-wrapper').hide();
		$('#page-content-wrapper').removeClass('page-content-wrapper');
		$('.fa-search-plus').hide();
		$('.fa-search-minus').show();*/
		var phheigth=$('#page-header').height();
		var cfheigth=$('#clearfix').height();
		var pcerheight=$('#page-container').height();
		$('#page-header').hide();
		$('#clearfix').hide();
		$('#page-container').css("margin-top","0px");
		if(!large){
			$('#page-container').height(pcerheight+phheigth+cfheigth);
		}
		$('#page-content').addClass("page-content-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu>li>a').addClass("a-icon-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar').addClass("page-sidebar-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu').addClass("page-sidebar-menu-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu>li>ul').addClass("sub-menu-enlarge");
		$('#pageBody>#page-container>#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu > li').hover(function(){
			$(this).addClass('li-hover-enlarge');
		},function(){
			$(this).removeClass('li-hover-enlarge');
		});
		if($("#sidebarToggerId").hasClass('menu-toggler-left')){
			$("#sidebarToggerId").click();
		}
		$('.glyphicon-resize-full').hide();
		$('.glyphicon-resize-small').show();


        var clientHeightTemp= parseInt(document.documentElement.clientHeight)-parseInt($('.page-top').height() )-parseInt($('#navTabsId').height());
        clientHeightTemp=parseInt(clientHeightTemp+clientHeightTemp*0.095);
        $(".toggler").css("top","40%");
        //$("#reportContent2"+menuids).height(clientHeightTemp);
		$(".iframereport").height(clientHeightTemp);
		large=true;
	},

	//缩小tab
	resizeTab:function(item){
		/*$('#page-header').show();
		$('#clearfix').show();
		$('#page-container').addClass('page-container');
		$('#page-sidebar-wrapper').show();
		$('#page-content-wrapper').addClass('page-content-wrapper');
		$('.fa-search-plus').show();
		$('.fa-search-minus').hide();*/
		$('#page-header').show();
		$('#clearfix').show();
		var phheigth=$('#page-header').height();
		var cfheigth=$('#clearfix').height();
		var pcerheight=$('#page-container').height();
		//$('#page-container').addClass('page-container');
		$('#page-container').css("margin-top",'');
		$('#page-container').height(pcerheight-phheigth-cfheigth);
		//$('#page-content').css("margin-left",'');
		$('#page-content').removeClass("page-content-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu>li>a').removeClass("a-icon-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar').removeClass("page-sidebar-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu').removeClass("page-sidebar-menu-enlarge");
		$('#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu>li>ul').removeClass("sub-menu-enlarge");
		$('#pageBody>#page-container>#page-sidebar-wrapper>.page-sidebar>.page-sidebar-menu > li').hover(function(){
			$(this).removeClass('li-hover-enlarge');
		},function(){
			//$(this).removeClass('li-hover-enlarge');
		});
		/*$('#page-sidebar-wrapper').show();
		$('#page-content-wrapper').addClass('page-content-wrapper');*/
		$('.glyphicon-resize-full').show();
		$('.glyphicon-resize-small').hide();

        var clientHeightTemp1 = parseInt(document.documentElement.clientHeight)-parseInt($('.page-top').height() )-parseInt($('#navTabsId').height());
/*
        clientHeightTemp1=parseInt(clientHeightTemp1-clientHeightTemp1*0.18);
*/      $(".toggler").css("top","45%");
        //$("#reportContent2"+menuids).height(clientHeightTemp1);
		$(".iframereport").height(clientHeightTemp1);
		large=false;
	}
}