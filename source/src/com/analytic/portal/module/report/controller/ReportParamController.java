package com.analytic.portal.module.report.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.service.interfaces.ReportParamService;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.yzd.plat.common.util.StringUtil;

@Controller
@RequestMapping("/reportParam")
public class ReportParamController  extends BaseController{
	@Resource
	private ReportParamService reportParamService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 删除报表参数
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月11日下午1:54:41
	 */
	@RequestMapping(value="/deleteReportParamById",method=RequestMethod.POST)
	public void deleteReportParamById(HttpServletRequest request ,HttpServletResponse response){
		String id = this.getParameter(request, "id").toString();
		int code=-1;
		
		try {
			code =reportParamService.deleteReportParamById(id) ? 0 : -1;
			writeJSONResult(code, null, null, response);
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
		} catch (Exception e) {
			writeJSONResult(code, null, null, response);
			
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 新增报表参数
	 * @param reportParam
	 * @param request
	 * @param response
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:56:57
	 */
	@RequestMapping(value="/addReportParam",method=RequestMethod.POST)
	public void addReportParam(ReportParam reportParam, HttpServletRequest request ,HttpServletResponse response) throws Exception {
		SysUser user = getLoginUser(request);
		
		try{
			if(user!=null){
				reportParam.setCreateTime(DateUtil.getNowTime());
				reportParam.setCreateUser(user.getLoginName());
			}
			int code = reportParamService.addReportParam(reportParam)? SUCCESS_CODE : ERROR_CODE;
			writeJSONResult(code, null, null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_ADD);
			
		}catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 修改报表参数
	 * @param reportParam
	 * @param request
	 * @param response
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:59:16
	 */                     
	@RequestMapping(value="/updateReportParam",method=RequestMethod.POST)
	public void updateReportParam(ReportParam reportParam, HttpServletRequest request ,HttpServletResponse response) throws Exception{
		int code = ERROR_CODE;
		
		try {
			if(reportParam!=null){
				SysUser user = getLoginUser(request);
				if(user!=null){
					reportParam.setUpdateTime(DateUtil.getNowTime());
					reportParam.setUpdateUser(user.getLoginName());
				}
				code = reportParamService.updateReportParam(reportParam) ? SUCCESS_CODE : ERROR_CODE;
				//更新报表暂存地址
//				sysMenuService.updateReportMenuUrl(request);
			}
			writeJSONResult(code, null, null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_UPDATE);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 验证数据是否存在
	 * @param request
	 * @param response
	 * admin
	 * 2016年4月15日上午10:39:21
	 */
	@RequestMapping(value="/getOnlyReportParamType",method=RequestMethod.GET)
	public void getOnlyReportParamType(HttpServletRequest request ,HttpServletResponse response){
		try {
			ReportParam reportParam=reportParamService.getReportParam(getReqParam(request));
			
			if(null != reportParam){
				this.writeJSONResult(0, null,null, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}
			
		} catch (Exception e) {
			this.writeJSONResult(-1, null,null, response);
		}
	}

	/**
	 * 获取报表参数
	 * @param request
	 * @param response
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:59:10
	 */
	@RequestMapping(value="/getReportParamById",method=RequestMethod.POST)
	public void getReportParamById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = getParameter(request, "id");
		ReportParam reportParam = reportParamService.getReportParamById(id);
		writerJSON(reportParam==null ? new ReportParam(): reportParam, response);
	}
	
	/**
	 * 获取报表参数集合
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月11日下午1:59:06
	 */
	public void getReportParamList(HttpServletRequest request ,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		writerJSON(map, response);
	}

	/**
	 * 获取分页的的报表参数集合
	 * @param reportParam
	 * @param page
	 * @param response
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:58:59
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getReportParamListByPage",method=RequestMethod.GET)
	public void getReportParamListByPage(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		int status=GlobalConstants.SUCCESS_STATE;
		Map map = new  HashMap();
		
		try {
			map=reportParamService.getReportParamListByPage(getReqParam(request));
			String isLoged=request.getParameter("isLoged");
			if ("yes".equals(isLoged)) {
				sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
			}
		} catch (Exception e) {
			status=GlobalConstants.FAILURE_STATE;
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		response.setStatus(status);
		writerJSON(map, response);
	}
	
	/*
	 * 获取当前请求的参数信息
	 * @param request
	 * @return
	 * pengbo
	 * 2016年3月21日下午2:13:56
	 */
	private Map<String, Object> getReqParam(HttpServletRequest request) throws Exception {
		Map<String, Object> formMap=new HashMap<>();
		
		//参数ID
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			formMap.put("id", id);
		}
		//参数CODE
		String code=request.getParameter("code");
		if (StringUtil.isNotEmpty(code)){
			formMap.put("code", code);
		}
		//参数名称
		String paramName=request.getParameter("reportParamName");
		if (StringUtil.isNotEmpty(paramName)){
			//中文乱码转换
			paramName = new String(paramName.getBytes("iso8859-1"),"UTF-8");
			formMap.put("paramName", paramName);
		}
		//参数key
		String paramKey=request.getParameter("reportParamKey");
		if (StringUtil.isNotEmpty(paramKey)){
			formMap.put("paramKey", paramKey);
		}
		//参数排序
		String paramOrder=request.getParameter("paramOrder");
		if (StringUtil.isNotEmpty(paramOrder)){
			formMap.put("paramOrder", paramOrder);
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
