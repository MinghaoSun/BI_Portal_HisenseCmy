<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String code = request.getParameter("code");
	
	String reason = "您的请求出现异常";
	boolean indexJsp = true;
	if(null != code){
		if(code.equals("500")){
			reason = "服务器内部错误";
			indexJsp = false;
		}else if(code.equals("400")){
			reason = "URL 地址请求错误";
		}else if(code.equals("403")){
			reason = "您没有访问权限";
		}else if(code.equals("404")){
			reason = "请求的网页不存在";
		}
	}
	
	if(indexJsp){
		//out.println("<meta http-equiv=refresh content=5;URL='index.jsp'>");
	}
%>
<title>服务器出错</title>
</head>
<body>
<%
	if(indexJsp){
	    out.println("<H2>" + reason + "！</H2><br>");
	}else{
		out.println("<H2>" + reason + "！显示例外堆叠跟踪：</H2><br>");
		exception.printStackTrace(response.getWriter());	
	}
%>
</body>
</html>
