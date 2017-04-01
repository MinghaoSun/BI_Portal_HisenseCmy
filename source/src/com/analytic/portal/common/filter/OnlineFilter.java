package com.analytic.portal.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.system.model.SysUser;

public class OnlineFilter implements Filter{
	
	private static List<String> IGNORE_URI = new ArrayList<String>();
	static {
		IGNORE_URI.add("/sysUsers/userLogin.do");
		IGNORE_URI.add("/sysUsers/login.do");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;  
        HttpServletResponse resp = (HttpServletResponse) response;  
        SysUser user=(SysUser)req.getSession().getAttribute(GlobalConstants.SESSION_USER);
        String url = req.getRequestURI().toString();
		if (url.indexOf(";") > 0) {
			url = url.split(";")[0];
		}
		if (!IGNORE_URI.contains(url)) {
			if(user==null){
	        	resp.setHeader("Cache-Control", "no-store");  
	            resp.setDateHeader("Expires", 0);  
	            resp.setHeader("Prama", "no-cache");  
	            resp.sendRedirect("/login.html");  
	        }else{
	        	chain.doFilter(req, response);
	        }
		}else{
			chain.doFilter(req, response);
		}
        
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
