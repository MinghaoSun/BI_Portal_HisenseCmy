package com.analytic.portal.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLfilter implements Filter{
	

	
	private static String[] keys = {"select","insert","update","delete","like","execute","exec","count","*","chr","mid","master","truncate","char","declare"};
	 
	

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String url = request.getQueryString();
		if(null != url){
			for(String key : keys){
				if(url.indexOf(key) != -1){
					request.getRequestDispatcher("/").forward(request, response);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if(req instanceof HttpServletRequest) {
			doFilter((HttpServletRequest)req, (HttpServletResponse)res, chain);
		}else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	
		
	}
}
