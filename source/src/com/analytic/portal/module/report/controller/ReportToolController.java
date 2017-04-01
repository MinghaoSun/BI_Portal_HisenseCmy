package com.analytic.portal.module.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.sys.SysConfig;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.report.model.ReportTool;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.yzd.plat.common.util.StringUtil;

/**
 * 报表工具
 * @author pengbo
 * 2016-03-10
 */
@Controller
@RequestMapping("/reportTool")
public class ReportToolController extends BaseController{
	@Resource
	private ReportToolService reportToolService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 删除报表工具
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月10日上午11:41:10
	 */
	@RequestMapping(value="/deleteReportToolById",method=RequestMethod.POST)
	public void deleteReportToolById(HttpServletRequest request ,HttpServletResponse response){
		String id = this.getParameter(request, "id").toString();
		int code=-1;
		
		try {
			code =reportToolService.deleteReportToolById(id) ? 0 : -1;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
			writeJSONResult(code, null, null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
		} catch (Exception e) {
			writeJSONResult(code, null, null, response);
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 添加报表工具
	 * @param reportTool
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月10日上午11:40:56
	 * @throws Exception 
	 */
	@RequestMapping(value="/addReportTool",method=RequestMethod.POST)
	public void addReportTool(ReportTool reportTool, HttpServletRequest request ,HttpServletResponse response) throws Exception {
		try {
			SysUser user = getLoginUser(request);
			
			if(user!=null){
				reportTool.setCreateTime(DateUtil.getNowTime());
				reportTool.setCreateUser(user.getLoginName());
			}
			int code = reportToolService.addReportTool(reportTool)? SUCCESS_CODE : ERROR_CODE;
			writeJSONResult(code, null, null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_ADD);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 修改报表工具
	 * @param reportTool
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月10日上午11:47:24
	 * @throws Exception 
	 */
	@RequestMapping(value="/updateReportTool",method=RequestMethod.POST)
	public void updateReportTool(ReportTool reportTool, HttpServletRequest request ,HttpServletResponse response) throws Exception{
		int code = ERROR_CODE;
		
		try {
			if(reportTool!=null){
				SysUser user = getLoginUser(request);
				if(user!=null){
					reportTool.setUpdateTime(DateUtil.getNowTime());
					reportTool.setUpdateUser(user.getLoginName());
				}
				code = reportToolService.updateReportTool(reportTool) ? SUCCESS_CODE : ERROR_CODE;
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
	 * 获取报表工具对象
	 * @param request
	 * @param response
	 * @return
	 * pengbo
	 * 2016年3月10日下午12:04:11
	 * @throws Exception 
	 */
	@RequestMapping(value="/getReportToolById",method=RequestMethod.POST)
	public void getReportToolById(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id = getParameter(request, "id");
		ReportTool reportTool = reportToolService.getReportToolById(id);
		writerJSON(reportTool==null ? new ReportTool(): reportTool, response);
	}
	
	/**
	 * 获取报表工具对象集合
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年3月10日下午1:28:31
	 */
	@RequestMapping(value="/getReportToolList",method=RequestMethod.GET)
	public void getReportToolList(HttpServletRequest request ,HttpServletResponse response){
		List<ReportTool> list=new ArrayList<ReportTool>();
		Map<String,Object> map=new HashMap<String, Object>();
		
		try {
			list = reportToolService.getReportToolList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("result", list);
		map.put("count", list.size());
		writerJSON(map,response);
	}

	/**
	 * 验证数据是否存在
	 * @param request
	 * @param response
	 * admin
	 * 2016年4月15日上午10:39:21
	 */
	@RequestMapping(value="/getOnlyReportToolType",method=RequestMethod.GET)
	public void getOnlyReportToolType(HttpServletRequest request ,HttpServletResponse response){
		try {
			ReportTool reportTool=reportToolService.getReportTool(getReqParam(request));
			if(null != reportTool){
				this.writeJSONResult(0, null,null, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}
			
		} catch (Exception e) {
			this.writeJSONResult(-1, null,null, response);
		}
	}
	
	/**
	 * 获取分页报表工具对象集合
	 * @param reportTool
	 * @param page
	 * @param response
	 * pengbo
	 * 2016年3月10日下午1:28:38
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getReportToolListByPage",method=RequestMethod.GET)
	public void getReportToolListByPage(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		int  status=GlobalConstants.SUCCESS_STATE;
		
		Map map = new  HashMap();
		try {
			
			map=reportToolService.getReportToolListByPage(getReqParam(request));
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
		//reportToolName
		String reportToolName=request.getParameter("reportToolName");
		if (StringUtil.isNotEmpty(reportToolName)){
			//中文乱码转换
			reportToolName = new String(reportToolName.getBytes("iso8859-1"),"UTF-8");
			formMap.put("reportToolName", reportToolName);
		}
		//reportToolType
		String reportToolType=request.getParameter("reportToolType");
		if (StringUtil.isNotEmpty(reportToolType)){
			formMap.put("reportToolType", reportToolType);
		}
		//reportToolVersion
		String reportToolVersion=request.getParameter("reportToolVersion");
		if (StringUtil.isNotEmpty(reportToolVersion)){
			formMap.put("reportToolVersion", reportToolVersion);
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年4月1日下午3:58:04
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getConfigByKey", method=RequestMethod.GET)
	public void GetConfigByKey(HttpServletRequest request ,HttpServletResponse response){
		Map map=new HashMap<>();
		
		try {
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String configKey = getParameter(request, "configKey");
			List<Hashtable> list = new ArrayList<Hashtable>();
			String configValues=SysConfig.getSysConfigValue(configKey);
			if(StringUtil.isNotEmpty(configValues)){
				String[] arrayValues = configValues.split(GlobalConstants.DIC_ITEM_SPLIT_SIGN);
				
				for (int i = 0; i < arrayValues.length; i++) {
					Hashtable ht=new  Hashtable();
					ht.put("key", arrayValues[i].toString());
					ht.put("value", arrayValues[i].toString());
					list.add(ht);
				}
			}
			map.put("list", list);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
			
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}	
		writerJSON(map, response);
	}
}
