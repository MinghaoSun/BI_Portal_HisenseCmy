package com.analytic.portal.module.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleParamService;

@Controller
@RequestMapping("/sysRoleParam")
public class SysRoleParamController extends BaseController{
	@Resource
	private SysRoleParamService sysRoleParamService;
	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月12日下午12:43:31
	 */
	@RequestMapping("/getSysRoleParam4RoleId")
	public void getSysRoleParamList(HttpServletRequest request ,HttpServletResponse response){
		int  status = GlobalConstants.SUCCESS_STATE;
		List<SysRoleParam> sysRoleParams = new ArrayList<SysRoleParam>();
		try {
			String sysRoleId=this.getParameter(request, "sysRoleId");
			sysRoleParams = sysRoleParamService.getSysRoleParam4SysRoleId(sysRoleId);
			
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writerJSON(sysRoleParams, response);
	}
	
	/**
	 * 更新角色参数
	 * @param sysRole
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月12日下午12:43:07
	 */
	@RequestMapping("/updateSysRoleParam4RoleId")
	public void updateSysRoleParam4RoleId(SysRole sysRole,HttpServletRequest request ,HttpServletResponse response){
		int  status = SUCCESS_CODE;
		
		try {
			status = sysRoleParamService.updateSysRoleParam4RoleId(sysRole)?SUCCESS_CODE : ERROR_CODE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		
		writerJSON(status, response);
	}

}
