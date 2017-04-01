'use strict';

/**
 * 过滤器
 * Created by admin on 2015/9/13.
 */
var platFilters = angular.module('platFilters', []);

/**
 * 状态标志位渲染
 */
platFilters.filter('enabledFlag', function() {
  return function(input) {
    if(input==0){
      return "停用";
    }else{
      return "启用";
    }
  };
});


/**
 * 登录状态标志位渲染
 */
platFilters.filter('onlineFlag', function() {
  return function(input) {
    if(input==0){
      return "离线";
    }else{
      return "在线";
    }
  };
});


/**
 * 性别标志位渲染
 */
platFilters.filter('sexFlag', function() {
  return function(input) {
    if(input==1){
      return "男";
    }else if(input==2){
      return "女";
    }else{
      return "未知";
    }
  };
});



//将长整型的日期格式转换为yyyy-MM-dd hh:MM
platFilters.filter('longDateTimeRender', function() {
  return function(input) {
    if(input==""){
      return "";
    }
    return new Date(input).format("yyyy-MM-dd hh:mm:ss");
  };
});

platFilters.filter('trustHtml', function ($sce) {

    return function (input) {

        return $sce.trustAsHtml(input);

    }

});

platFilters.filter('percentInput', function () {

    return function (input) {
    	if(input==0){
    		return "0.0"
    	}
    	input=input*100;
    	input=input.toString();
    	if(input.indexOf(".")>0){
    		 return input.substring(0,input.indexOf(".")+2)
    	}else{
    		return input;
    	}

    }

});

platFilters.filter('reservedTwoDecimal', function () {
    return function (input) {
    	if(input.indexOf(".")>0){
    		 return input.substring(0,input.indexOf(".")+2)
    	}else{
    		return input;
    	}

    }

});

platFilters.filter('toHtml_br', ['$sce', function ($sce) {
	return function (text) {
		return $sce.trustAsHtml(text.replace(/\n/g,"<br/>").replace(/\s/g,"&nbsp"));
	};
}]);


