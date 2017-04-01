package com.analytic.portal.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录系统操作日志
 * 
 * @author kezhuang.li
 * @creation 2015年9月28日
 */
public class SystemLogFilter implements Filter {
	//private LogService logService;
	
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		long start = System.currentTimeMillis();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		chain.doFilter(req, res);
		long time = System.currentTimeMillis() - start;
		//logService.saveLog(request,response,time,notSaveReq);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String notSaveReqStr = config.getInitParameter("notSaveReq");
		if(notSaveReqStr!=null &&!"".equals(notSaveReqStr)){
			String[] strs = notSaveReqStr.split(",");
			for(int i=0;i<strs.length;i++){
				notSaveReq.put(strs[i], true);
			}
		}
		//logService = (LogService)SpringContextUtil.getBean("logService");
	}
	private static Map<String, Boolean> notSaveReq = new HashMap<String, Boolean>();
}
