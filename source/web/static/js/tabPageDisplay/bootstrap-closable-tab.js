
function add(obj){
	var id = obj.id;
	var name = obj.innerHTML;
	var uri = obj.href;
//				var item2 = {'id':'2','name':'首页22','url':'#/reportTool/20160407141758754','closable':true};
	var item2 = {'id':id,'name':name,'url':uri,'closable':true};
	closableTab.addTab(item2);
}

function addForIndex(obj){
	var id = $(obj).attr('reportOfIndexId');
	//alert(id)
	var name = obj.innerHTML;
	var uri = obj.href;
//				var item2 = {'id':'2','name':'首页22','url':'#/reportTool/20160407141758754','closable':true};
	var item2 = {'id':id,'name':name,'url':uri,'closable':true};
	closableTab.addTab(item2);
}

function  controlTabClick(menuIdPara) {
	menuIdPara=String(menuIdPara);
	var menuIdTemp;
	$(".sidebar-menu li").find("a").each(function () {
		//var currentName = $(this).text();
		menuIdTemp = String($(this).attr("id"));
		if (menuIdPara == menuIdTemp) {
			if (!$("#"+menuIdTemp).parent().parent().parent().hasClass("active")) {
				$("#" + menuIdTemp).parent().parent().prev('a').click();
			}
			$("#"+menuIdTemp).click();
			if (!$("#"+menuIdTemp).parent().hasClass("active")){
				$("#"+menuIdTemp).parent().addClass("active");
			}
			//alert("dfafasfasfdasf%%")
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
			var li_tab = '<li style="margin-top: 1px" role="presentation" class="" id="'+id+'"><span onclick="controlTabClick('+'\''+tabItem.id+'\''+')"  role="tab" data-toggle="tab" style="position: relative;padding:2px 20px 0px 15px">'+tabItem.name;
			if(tabItem.closable){
				li_tab = li_tab + '</span><i title="全屏显示" class="glyphicon glyphicon-fullscreen small" tabenlarge="enlarge'+id+'" style="position: relative;left: -12%;"  onclick="closableTab.enlargeTab(this)"></i><i title="恢复正常显示" class="glyphicon glyphicon-resize-small small" tabenlarge="enlargeResize'+id+'" style="position: relative;left: -12%;display: none" onclick="closableTab.resizeTab(this)"></i><i title="关闭当前窗口" class="glyphicon glyphicon-remove small" tabclose="'+id+'" style="position: relative;left: -9%;"  onclick="closableTab.closeTab(this)"></i></li> ';
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
		$('#'+val).prev().find("span").click();
		$("#"+val).remove();
	},

	//放大tab
	enlargeTab:function(item){
		var val = $(item).attr('tabclose');

		$('#main-header').hide();
		$('#main-sidebar').hide();
		$('#pageLoader').removeClass("content-wrapper");
		$('#pageLoader').addClass("wrapper");
		$('.glyphicon-fullscreen').hide();
		$('.glyphicon-resize-small').show();
		//$('#navTabsId').css('background-color','#40d2b6');


		/*$(this).css('display','none');
		$('#'+val).prev().addClass('active');
		$('#'+val).prev().find("span").click();
		$("#"+val).remove();*/
	},

	//缩小tab
	resizeTab:function(item){
		$('#main-header').show();
		$('#main-sidebar').show();
		$('#pageLoader').removeClass("wrapper");
		$('#pageLoader').addClass("content-wrapper");
		$('.glyphicon-fullscreen').show();
		$('.glyphicon-resize-small').hide();

		//$('#navTabsId').css('background-color',null);
		//$('#navTabsId').removeAttr('style');
	}
}