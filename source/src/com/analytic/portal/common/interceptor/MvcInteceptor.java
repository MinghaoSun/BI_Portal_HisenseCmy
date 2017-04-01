package com.analytic.portal.common.interceptor;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.analytic.portal.common.resolver.AuthorizationException;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.system.model.SysUser;

/**
 * @ClassName: MvcInteceptor
 * @Description: MVC拦截器
 * @author Minghao
 * @date 2017年3月16日13:04:45
 */
@Component
public class MvcInteceptor extends HandlerInterceptorAdapter {

	private static List<String> IGNORE_URI = new ArrayList<String>();
	static {
		IGNORE_URI.add("/sysUsers/userLogin.do");
		IGNORE_URI.add("/sysUsers/login.do");
		IGNORE_URI.add("/sysUsers/isOnline.do");
		IGNORE_URI.add("/sysUsers/close.do");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI().toString();
		//System.out.println(url);
		if (url.indexOf(";") > 0) {
			url = url.split(";")[0];
		}
		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		if (!IGNORE_URI.contains(url)) {
			if (null == user) {
				//throw new AuthorizationException("您的登录身份已过期，请重新登录！");
				throw new AuthorizationException("nologin");
			} 
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

}
