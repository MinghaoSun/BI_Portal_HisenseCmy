package com.analytic.portal.security;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.analytic.portal.common.util.ConfigUtil;
import com.analytic.portal.module.system.model.SysUser;

import net.sf.json.JSONObject;

/**
 * 跨域 token获取
 *  
 * @author kezhuang.li 
 * @creation 2015年11月25日
 */
public class SessionFilter implements Filter{
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//客户端token变化,从新获取user对象
		String sessionId = request.getParameter("token"); 
		if(sessionId!=null){
			Object user = request.getSession().getAttribute("user");
			if(user==null){
				getRemoteLoginUser(request,sessionId);
			}else{
				Object pubId = request.getSession().getAttribute(SESSION_NAME);
				if(pubId!=null && !sessionId.equals(pubId)){
					getRemoteLoginUser(request,sessionId);
				}
			}
		}
		//设置允许跨域 携带cookie
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin==null?"*":origin);
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(request, response);
	}
	
	//通过token获取user
	private void getRemoteLoginUser(HttpServletRequest request,String sessionId) {
		try {
			URLConnection connection = new URL(REMOTE_PATH).openConnection();
			connection.setRequestProperty("Cookie", SESSIONID_NAME + "=" + sessionId);
			connection.setRequestProperty(SECURITY_TOKEN_NAME, SECURITY_TOKEN);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(300000);
			String res = IOUtils.toString(connection.getInputStream());
			res = DES.decrypt(res);
			JSONObject obj = JSONObject.fromObject(res);
			Object id = obj.get(ID_NAME);
			Object username = obj.get(ACCOUNT_NAME);
			if(id!=null &&!"".equals(id)&&username!=null &&!"".equals(username)){
				SysUser users = new SysUser();
				users.setId(id.toString());
				users.setLoginName(username.toString());
		        request.getSession().setAttribute("user", users);
		        request.getSession().setAttribute(SESSION_NAME,sessionId);
			}else{
				request.getSession().removeAttribute("user");
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	private static final String SESSION_NAME = "COMPASS_PUB_SESSIONID";
	private static final String SECURITY_TOKEN_NAME = ConfigUtil.get("security_token_name");
	private static final String SECURITY_TOKEN = ConfigUtil.get("security_token");
	private static final String SESSIONID_NAME = ConfigUtil.get("sessionid_name");
	private static final String REMOTE_PATH = ConfigUtil.get("remote_url");
	private static final String ID_NAME = ConfigUtil.get("id_name");
	private static final String ACCOUNT_NAME = ConfigUtil.get("account_name");
	private static final DesUtils DES = new DesUtils();
}
