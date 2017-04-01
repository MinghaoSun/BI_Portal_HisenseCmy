package com.analytic.portal.module.common.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.sys.JsonValueProcessorImpl;
import com.analytic.portal.module.system.model.SysUser;
import com.yzd.plat.common.util.StringUtil;

/**
 * @author Louis
 *
 */
public class BaseController{
	public static final int SUCCESS_CODE = 200;
	public static final int ERROR_CODE = 500;
	public final static int FORBIDDEN_CODE = 403;// 禁止访问
	
	
	public static boolean isNotEmpty(String... str){
		for(int i=0;i<str.length;i++){
			if(str[i] == null || str[i].length() == 0){
				return false;
			}
		}
		return true;
	}
	/**
	 * 通过参数名称获取页面传递过来的值
	 * @param paramName	参数名称
	 * @return
	 */
	protected String getParameter(HttpServletRequest request,String paramName) {
		String method=request.getMethod();
		String paramValue=request.getParameter(paramName);
		if(method.equals("GET")&&StringUtil.isNotEmpty(paramValue)){
			try {
				paramValue=new String(paramValue.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return paramValue;
	}
	
	/**
	 * 获取Get请求时的中文参数
	 * @param paramName	参数名称
	 * @return
	 */
	protected String getParameter_CN(HttpServletRequest request,String paramName) {
		String paramValue=request.getParameter(paramName);
		try {
			if(StringUtil.isNotEmpty(paramValue)){
				paramValue=new String(paramValue.getBytes("ISO-8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paramValue;
	}
	
	/**
	 * 通过参数名称获取页面传递过来的值
	 * @param paramName	参数名称
	 * @return
	 */
	protected String[] getParameterValues(HttpServletRequest request,String paramName) {
		return request.getParameterValues(paramName);
	}

	/**
	 * 通过rquest对象中属性名称获取对象
	 * @param attrName	属性名称
	 * @return
	 */
	protected Object getAttribute(HttpServletRequest request,String attrName) {
		return request.getAttribute(attrName);
	}
	
	/**
	 * 将对象封装到request对象中
	 * @param attrName	属性名
	 * @param obj	对象
	 */
	protected void setAttribute(HttpServletRequest request,String attrName,Object obj) {
		request.setAttribute(attrName,obj);
	}

	
	static JsonConfig cfg=new JsonConfig();
	public static final String SUCCESS = "{success:true}";
	public static final String FAIL = "{success:false}";
	
	static{
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(Long.class, new JsonValueProcessorImpl());
	}
	
	/**
	 * 以JSON格式返回给页面时,头部流设置
	 * @param response
	 */
	public void setJSONHeader(HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cahce-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/json; charset=utf-8");
	}
	
	/**
	 * 将对象以JSON格式输出到客户端
	 * @param object
	 * @param response
	 * @return
	 */
	public String writerJSON(Object object,HttpServletResponse response){
		String jsonStr = null;
		try {
			Object jSon;
			if(object instanceof List){
				jSon = JSONArray.fromObject(object,cfg);
			}else{
				jSon = JSONObject.fromObject(object,cfg);
			}
			jsonStr = jSon.toString();
			if(null != response){
				setJSONHeader(response);
				PrintWriter out = response.getWriter();
				out.write(jsonStr);
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 将字符串以JSON格式输出到客户端
	 * @param strInfo	字符串
	 * @param response
	 */
	public void writerJSON(String strInfo,HttpServletResponse response){
		setJSONHeader(response);
		try {
			PrintWriter out = response.getWriter();
			out.write(strInfo);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 以JSON格式输出错误信息
	 * @param e
	 * @param response
	 */
	public void writeExceptionInfo(Exception e,HttpServletResponse response){
		writerJSON("{success:false,info:'服务器出错,请稍候再试!'}",response);
	}
	
	/**
	 * 返回结果给调用接口者
	 * @param code	状态码
	 * @param message	提示信息
	 * @param data	返回的JSON字符串数据
	 */
	public void writeJSONResult(int code,String message,Object data,HttpServletResponse response){
		JSONObject json=new JSONObject();
		json.put("code", code);
		json.put("message", message);
		json.put("data", data);
		this.writerJSON(json,response);
	}
	public SysUser getLoginUser(HttpServletRequest request){
		Object user = request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		if(user!=null){
			return (SysUser) user;
		}
		return null;
	}
	
	public void noLogin(HttpServletResponse response) {
		response.setStatus(FORBIDDEN_CODE);
		writeJSONResult(FORBIDDEN_CODE, "no login", null, response);
	}
}
