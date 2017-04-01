package com.analytic.portal.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzd.plat.common.util.StringUtil;

public class SecurityFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<String> notCheckURLList = new ArrayList<String>();
	protected FilterConfig filterConfig = null;
	
	public void destroy() {
		this.notCheckURLList.clear();
	}

	@SuppressWarnings("unused")
	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath()
				+ ((request.getPathInfo() == null) ? "" : request.getPathInfo());

		boolean ckr = false;
		for (int i = 0; i < this.notCheckURLList.size(); ++i) {
			String noCkUri = this.notCheckURLList.get(i).toString();
			int n = noCkUri.indexOf("*");
			if (n > -1)
				noCkUri = noCkUri.substring(0, n);
			if (uri.startsWith(noCkUri))
				return true;
		}
		return ckr;
	}
	
	@SuppressWarnings("deprecation")
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try{
			req.setCharacterEncoding("UTF-8");
			if(req instanceof HttpServletRequest) {
				HttpServletRequest request=(HttpServletRequest)req;      
		        HttpServletResponse response  =(HttpServletResponse) res;
		        /*if (checkRequestURIIntNotFilterList(request)==false){
		        	response.setStatus(403);
		        	return;
				}*/
		        //反射型跨站脚本漏洞的检查
				String queryStr=request.getQueryString();
				if(StringUtil.isNotEmpty(queryStr)){
					if(hasDanger(queryStr)){
						request.setAttribute("errorMsgInfo","非法请求!!");
						response.setStatus(403);
						return;//检测出含有特殊字符引起的漏洞
					}
					//解码后再次判断
					String decodeQueryStr=java.net.URLDecoder.decode(queryStr);
					if(decodeQueryStr!=null&&hasDanger(decodeQueryStr)){
						request.setAttribute("errorMsgInfo","非法请求!!");
						response.setStatus(403);
						return;//检测出含有特殊字符引起的漏洞
					}
				}
				
				chain.doFilter(req, res);
			}else {
				chain.doFilter(req, res);
			}
		}catch(Exception e){
			logger.error("doFilter error:",e);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
		String notCheckURLListStr = this.filterConfig
				.getInitParameter("notCheckURLList");  
                                        
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");        
			this.notCheckURLList.clear();
			while (st.hasMoreTokens())                 
				this.notCheckURLList.add(st.nextToken());
		}
		
	}

	/**
	 * 获取去除空格后的参数字符串
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private String getParamS(HttpServletRequest request) throws UnsupportedEncodingException{
		String paramStr = "&";
		Map paramMap = request.getParameterMap();
		String method=request.getMethod();
		Iterator it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String paramKey = it.next().toString();
			if (StringUtil.isEmpty(paramKey)) {
				continue;
			}
			String paramValue = request.getParameter(paramKey);
			if (StringUtil.isEmpty(paramValue)) {
				continue;
			}
			if(method.equals("GET")){
				paramValue=new String(paramValue.getBytes("ISO-8859-1"),"UTF-8");
			}
			String param = paramKey + "=" + paramValue;
			paramStr = paramStr + param + "&";
		}
		return paramStr;
	}
	
	/**
	 * 是否含有特殊字符引起的漏洞
	 * @param queryStr
	 * @return
	 */
	private static boolean hasDanger(String queryStr){
		boolean has=false;
		String[] dangerChar=new String[]{";","'","\"","<>","<",">","()","(",")"};
		for(String danger:dangerChar){
			if(queryStr.contains(danger)){
				has=true;
				break;
			}
		}
		return has;
	}
}
