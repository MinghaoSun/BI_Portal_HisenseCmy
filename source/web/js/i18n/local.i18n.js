function localI18n(source){
	var localLanguage="zh-CN";
	//cookie方式
	if (source=='0'){
		if ($.cookie("localLanguage")!=null){
			localLanguage=$.cookie("localLanguage");
		}
	}
	//session方式
	if (source=='1'){
		//优先获取cookie值
		if ($.cookie("localLanguage")!=null){
			localLanguage=$.cookie("localLanguage");
		}else{
			//获取session值
			$.ajax({
				url:"sysUsers/getLocalI18n.do",
				async:true,
				type:"GET",
				dataType:"json",
				success:function(result){
					localLanguage=result.localLanguage;
				}
			});
		}
	}
	//设置i18n
	$.i18n.properties({
		name : 'strings',
		path : 'js/i18n/',
		mode : 'map', 
		language : localLanguage
	});
}