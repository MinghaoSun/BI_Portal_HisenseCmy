/**
 * 操作日志
 */
package com.analytic.portal.module.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;

/**
 * 操作日志控制器
 * @author Boger
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {
	
	@Resource
	private SysLogService sysLogService;

	/**
	 * 分页查询操作日志列表
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月17日下午2:19:44
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getSysLogListByPage",method=RequestMethod.GET)
	public void getSysLogListByPage(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> sysLogMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				//以下为数据信息
				sysLogMap=sysLogService.getLogListByPage(getReqParam(request));
				//以下为表单信息
				sysLogMap.put(GlobalConstants.DIC_SYS_OPER_TYPE, GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_TYPE));
				//以下为状态信息
				sysLogMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(sysLogMap, response);
			}
			noLogin(response);
			
			//sysLogService.saveSysLog(request, response);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取当前请求的参数信息
	 * @param request
	 * @return
	 * Boger
	 * 2016年3月17日下午1:48:13
	 */
	private Map<String, Object> getReqParam(HttpServletRequest request) throws Exception {
		Map<String, Object> formMap=new HashMap<>();
		
		//登录账户
		String loginName=request.getParameter("loginName");
		if (StringUtil.isNotEmpty(loginName)){
			loginName = new String(loginName.getBytes("iso8859-1"),"UTF-8");
			formMap.put("loginName", loginName);
		}
		//用户姓名
		String userName=request.getParameter("userName");
		if (StringUtil.isNotEmpty(userName)){
			userName = new String(userName.getBytes("iso8859-1"),"UTF-8");
			formMap.put("userName", userName);
		}
		//访问地址
		String reqPath=request.getParameter("reqPath");
		if (StringUtil.isNotEmpty(reqPath)){
			formMap.put("reqPath", reqPath);
		}
		//功能菜单
		String menuName=request.getParameter("menuName");
		if (StringUtil.isNotEmpty(menuName)){
			formMap.put("menuName", menuName);
		}
		//操作方式
		String operType=request.getParameter("operType");
		if (StringUtil.isNotEmpty(operType)){
			operType = new String(operType.getBytes("iso8859-1"),"UTF-8");
			formMap.put("operType", operType);
		}
		//创建日期
		String beginCreateTime=request.getParameter("beginCreateTime");
		if (StringUtil.isNotEmpty(beginCreateTime)){
			formMap.put("beginCreateTime", beginCreateTime);
		}
		String endCreateTime=request.getParameter("endCreateTime");
		if (StringUtil.isNotEmpty(endCreateTime)){
			formMap.put("endCreateTime", endCreateTime);
		}
		//当前页
		String currentPage = getParameter(request, GlobalConstants.PAGE_CURRENT_PAGE);
		if (StringUtil.isNotEmpty(currentPage)){
			formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, currentPage);
		}else {
			formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, GlobalConstants.PAGE_START);
		}
		
		//页面数据限制
		String pageSize = getParameter(request, GlobalConstants.PAGE_PAGE_SIZE);
		if (StringUtil.isNotEmpty(pageSize)){
			formMap.put(GlobalConstants.PAGE_PAGE_SIZE, pageSize);
		}else {
			formMap.put(GlobalConstants.PAGE_PAGE_SIZE, GlobalConstants.PAGE_SIZE);
		}
		
		return formMap;
	}
	
}
