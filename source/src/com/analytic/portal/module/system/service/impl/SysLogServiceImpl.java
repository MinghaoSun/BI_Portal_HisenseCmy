/**
 * 系统访问日志
 */
package com.analytic.portal.module.system.service.impl;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.dao.interfaces.SysLogDao;
import com.analytic.portal.module.system.model.SysLog;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;

/**
 * 系统访问日志定义
 * @author Boger
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private IBaseDao iBaseDao;
	@Resource
	private SysLogDao sysLogDao;
	@Resource
	private SysMenuService sysMenuService;

	/**
	 * 获取日志信息列表（分页查询）
	 * @param formMap
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月16日下午3:19:23
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getLogListByPage(Map formMap) throws Exception {
		return sysLogDao.getLogListByPage(formMap);
	}
	
	/**
	 * 按方式保存系统操作日志
	 * @param logMap
	 * @param request
	 * @param response
	 * @param operType
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月18日上午10:38:54
	 */
	@Override
	public boolean saveSysLog(HttpServletRequest request,
			HttpServletResponse response, String operType) throws Exception {
		boolean result = false;
		SysLog sysLog = new SysLog();

		try {
			//日志信息
			sysLog=getSysLog(request, response);
			//菜单信息
			Map<String, String> menuMap=getSysMenu(request);
			sysLog.setMenuCode(menuMap.get(GlobalConstants.MENU_OPER_MENU_CODE));
			sysLog.setMenuName(menuMap.get(GlobalConstants.MENU_OPER_MENU_NAME));
			//操作方式
			sysLog.setOperType(operType);
			//最近访问报表地址（保存访问的具体报表的ID,为最近访问报表提供依据）
			if (menuMap.get(GlobalConstants.MENU_OPER_MENU_REPORT).
					equals(GlobalCache.getDic(GlobalConstants.DIC_REPORT_MENU_TYPE).
							get(GlobalConstants.REPORT_MENU_TYPE_REPORT))){
				sysLog.setMenuReportId(menuMap.get(GlobalConstants.MENU_OPER_MENU_ID));
			}else {
				sysLog.setMenuReportId("");
			}
			//操作方式
			sysLog.setOperType(getOperType(operType));
			
			//reqData中去除某些敏感字符
			String reqData=sysLog.getReqData();
			if(StringUtils.isNotBlank(reqData)){
				JSONObject json=JSONObject.fromObject(reqData);
				JSONArray value=(JSONArray)json.get("password");
				if(value!=null&&value.size()>0){
					json.remove("password");
				}
				value=(JSONArray)json.get("loginPassword");
				if(value!=null&&value.size()>0){
					json.remove("loginPassword");
				}
				reqData=json.toString();
				sysLog.setReqData(reqData);
			}
			
			result = iBaseDao.save(sysLog);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取最近访问报表菜单ID
	 * @param LoginName
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月21日下午4:00:33
	 */
	@SuppressWarnings("rawtypes")
	public List getSysLogListByUser(String LoginName) throws Exception{
		return sysLogDao.getSysLogListByUser(LoginName);
	}

	/**
	 * 获取当前的操作日志信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月18日上午11:33:50
	 */
	private SysLog getSysLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysLog sysLog = new SysLog();
		HttpSession session = request.getSession();

			SysUser user = (SysUser)session.getAttribute(GlobalConstants.SESSION_USER);
			String reqData = JSONObject.fromObject(request.getParameterMap()).toString();
			String reqIP = getIpAddress(request);
			
			sysLog.setId(DataOperation.getSequenseStr());
			//用户信息
			sysLog.setLoginName(user.getLoginName());
			sysLog.setUserName(user.getUserFullName());
			//请求地址
			sysLog.setReqPath(reqIP);
			//请求数据
			sysLog.setReqData(reqData);
			//操作状态
			sysLog.setStatusCode(response.getStatus());
			//日志描述
			sysLog.setLogDesc(request.getRequestURL().toString());
			sysLog.setCreateTime(DateUtil.getNowTime());

		return sysLog;
	}
	
	/**
	 * 获取IP地址信息
	 * @param request
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月16日下午4:04:59
	 */
	private String getIpAddress(HttpServletRequest request) throws Exception {
		String ipAddress = null;
		InetAddress inet = null;
		
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
			ipAddress = request.getHeader("HTTP_CLIENT_IP"); 
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) { 
			ipAddress = request.getHeader("x-real-ip"); 
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
			// 根据网卡取本机配置的IP
			inet = InetAddress.getLocalHost();
			ipAddress = inet.getHostAddress();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}

		return ipAddress;
	}
	
	/**
	 * 获取当前的操作类型
	 * @return
	 * Boger
	 * 2016年3月16日下午4:55:37
	 */
	private String getOperType(String operType) throws Exception{
		//新增
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_ADD)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_ADD).toString();
		}
		//删除
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_DELETE)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_DELETE).toString();
		}
		//修改
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_UPDATE)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_UPDATE).toString();
		}
		//查询
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_QUERY)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_QUERY).toString();
		}
		//登录
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_LOGIN)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_LOGIN).toString();
		}
		//登出
		if (operType.equals(GlobalConstants.SYS_OPER_TYPE_LOGOUT)){
			operType=GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE).
					get(GlobalConstants.SYS_OPER_TYPE_LOGOUT).toString();
		}
		
		return operType;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * Boger
	 * 2016年3月16日下午5:00:19
	 */
	private Map<String, String> getSysMenu(HttpServletRequest request) throws Exception{
		SysMenu sysMenu=null;
		Map<String, String> map = new HashMap<String, String>();
		
		String menuId=request.getParameter(GlobalConstants.MENU_OPER_MENU_ID);
		if (StringUtil.isNotEmpty(menuId)){
			//获取菜单代码和菜单名称
			sysMenu=sysMenuService.getMenuById(menuId);
			map.put(GlobalConstants.MENU_OPER_MENU_CODE, sysMenu.getCode());
			map.put(GlobalConstants.MENU_OPER_MENU_NAME, sysMenu.getMenuName());
			map.put(GlobalConstants.MENU_OPER_MENU_REPORT, sysMenu.getMenuAttribute());
			map.put(GlobalConstants.MENU_OPER_MENU_ID, menuId);
		}else {
			map.put(GlobalConstants.MENU_OPER_MENU_CODE, "");
			map.put(GlobalConstants.MENU_OPER_MENU_NAME, "");
			map.put(GlobalConstants.MENU_OPER_MENU_REPORT, "");
			map.put(GlobalConstants.MENU_OPER_MENU_ID, "");
		}
		
		return map;
	}
	
}
