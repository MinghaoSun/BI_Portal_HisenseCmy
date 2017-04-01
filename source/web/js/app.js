$(function() { 
	
	$.fn.spin = function(opts) {
		this.each(function() {
			var $this = $(this), data = $this.data();
			if (data.spinner) {
				data.spinner.stop();
				delete data.spinner;
			}
			if (opts !== false) {
				data.spinner = new Spinner($.extend({
					color : $this.css('color')
				}, opts)).spin(this);
			}
		});
		return this;
	};
	// $.loading.show() $.loading.hide()
	$.loading = {
		show : function() {
			//$('#ng-view-content').spin(true);
		},
		hide : function() {
			//$('#ng-view-content').spin(false);
		}
	}
	
	//tips
	var toast = function(msg, type) {
		var js_tips = $('#js_tips');
		if (js_tips.length < 1) {
			js_tips = '<div id="js_tips" class="page_tips success" style="display: none;"><div class="inner"></div></div>';
			$('body').append(js_tips);
		}
		$('#js_tips .inner').html(msg);
		type = type == -1 ? 'error' : 'success';
		$('#js_tips').attr('class', 'page_tips ' + type);
		$("#js_tips").fadeIn(300);
		setTimeout(function() {
			$("#js_tips").fadeOut(300);
		}, 4000);
	}
	
	window.success = function(){
    	toast(arguments[0]);
    }
	window.error = function(){
    	toast(arguments[0],-1);
    }
	localI18n(1);
	//i18n
//	$.i18n.properties({
//		name : 'strings', //file name
//		path : 'js/i18n/', //file path
//		mode : 'map', //
//		language : "zh-CN"
//	});
	
/*	//动态加载（页面跳转）
	var _baseUrl = "page/view/";
	$("body").on("click", "a[data-page]", function(e) {
		e.preventDefault();
		var pageName = $(this).data("page");
		var pageUrl = _baseUrl + pageName + ".html";
		$("#pageLoader").load(pageUrl, function(resp, status, jqxhr) {
			//console.log(status);
		});
	});*/
	
	//切换左侧菜单高亮显示
//	$(".treeview>.treeview-menu").on("click", "a", function() {
//		$(".treeview>.treeview-menu .active").removeClass("active");
//		$(this).parent().addClass("active");
//	});
//	$(".treeview-menu1").parent().click(function(){
//		$(".treeview-menu1").show();
//	});
	//切换左侧菜单高亮显示
	$(".treeview>.treeview-menu").on("click", "a", function(e) {
		
		// 使被点击的二级菜单条目高亮
		$(this).parent().addClass("active").siblings().removeClass("active");
		var $next = $(this).next(".treeview-menu1");
		// 有三级菜单，展开
		if($next.html()){
		// 阻止事件冒泡，否则点击二级菜单之后，三级菜单不会出现
			e.stopPropagation();
			$next.slideToggle();	
		}
		$("ul.treeview-menu1>li").removeClass("active");
	});
	
	$(".treeview").on("click", function(){
		var $this = $(this);
		if($this.index() == 2){
			// 使三级菜单一开始呈关闭状态
			$this.find("ul.treeview-menu1").hide();
		}
		$("ul.treeview-menu1>li").removeClass("active");
	});
	$("ul.treeview-menu1>li").on("click", function(e){
		e.stopPropagation();
		$(this).addClass("active").siblings().removeClass("active");
		var page = $(this).children("a[data-page]").data("page");
		var url = _baseUrl + page + ".html";
		$("#pageLoader").load(url);
	});
	//点击页面空白区域，隐藏右侧栏
	$(".content-wrapper").on("click", function() {
		$(".control-sidebar-open").removeClass("control-sidebar-open");
	});
	
	//全选
	$(".content-wrapper").on("click", ".check-all", function() {
		if($(this).is(":checked")) {
			$(this).parents(".table").find("input[type='checkbox']").prop("checked", true);
		}else {
			$(this).parents(".table").find("input[type='checkbox']").prop("checked", false);
		}
	});
	
	/*//换肤
	var skinArr = [
		"skin-blue", 
		"skin-red", 
		"skin-green", 
		"skin-yellow", 
		"skin-pruple",
		"skin-blue-light", 
		"skin-red-light", 
		"skin-green-light", 
		"skin-yellow-light", 
		"skin-purple-light"
	];
	$("#skinLinks").on("click", ".skin-link", function(e) {
		e.preventDefault();
		if(!$(this).hasClass("active")) {
			$(".skin-link").removeClass("active");
			$(this).addClass("active");
			
			var skinCls = $(this).data("skin");
			$.each(skinArr, function(i) {
				$("body").removeClass(skinArr[i]);
			});
			$("body").addClass(skinCls);
		}
	});*/
});