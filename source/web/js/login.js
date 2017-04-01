!function() {
	/*alert(11);
	
	function $(a) {
		return document.getElementById(a)
	}*/
/*	function v(o) {
		if (!o.value) {
			o.focus();
			return false;
		}
		return true;
	}*/
	/*function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	$("loginForm").onsubmit = function(t) {
		if (v(this.loginName) && v(this.password) && v(this.code)) {
			if (this.code.value.length != 4) {
				this.code.focus();
				return false;
			}
			return true;
		}
		return false;
	}*/
	
	/*//重新获取验证码
	$('imgcode').onclick=function() {
		alert(11);
		this.src = 'sysUsers/checkCode.do?_=' + +new Date;
		//$('imgcode').src = 'sysUsers/checkCode.do?_=' + +new Date;
	}
	
	//重新获取验证码
	$('aRefreshCode').onclick=function() {
		$('imgcode').src = 'sysUsers/checkCode.do?_=' + +new Date;
	}*/
}();