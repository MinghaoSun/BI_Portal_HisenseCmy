package com.analytic.portal.module.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleMenu;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleMenuService;

@Controller
@RequestMapping("/sysRoleMenu")
public class SysRoleMenuController extends BaseController{
	@Resource
	private SysRoleMenuService sysRoleMenuService;
	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月12日上午11:58:39
	 */
	@RequestMapping(value="/getSysRoleMenu4RoleId",method=RequestMethod.GET)
	public void getSysRoleMenuList(HttpServletRequest request ,HttpServletResponse response){
		int  status = GlobalConstants.SUCCESS_STATE;
		List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
		try {
			String sysRoleId=this.getParameter(request, "sysRoleId");
			sysRoleMenus = sysRoleMenuService.getSysRoleMenu4SysRoleId(sysRoleId);
			
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writerJSON(sysRoleMenus, response);
	}
	
	/**
	 * 更新系统角色功能菜单
	 * @param sysRole
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月12日上午11:58:49
	 */
	@RequestMapping(value="/updateSysRoleMenu4RoleId",method=RequestMethod.POST)
	public void updateSysRoleMenu4RoleId(SysRole sysRole,HttpServletRequest request ,HttpServletResponse response){
		int  status = SUCCESS_CODE;
		
		try {
			status = sysRoleMenuService.updateSysRoleMenu4RoleId(sysRole)?SUCCESS_CODE : ERROR_CODE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		
		writerJSON(status, response);
	}
	
}
