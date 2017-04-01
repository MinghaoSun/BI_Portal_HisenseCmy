package com.analytic.portal.module.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.analytic.portal.module.report.model.ReportParamRelateData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleService;
import com.yzd.plat.common.util.StringUtil;


/**
 * 系统角色权限
 *  
 * @author fenglei.xu 
 * @creation 2016年1月27日
 */

@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController{
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;

	/****
	 * 获取系统角色列表
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getSysRoleListByPage",method=RequestMethod.GET)
	public void getSysRoleListByPage(HttpServletRequest request ,HttpServletResponse response){
		int  status=GlobalConstants.SUCCESS_STATE;
		String searchInput = getParameter(request, "searchInput");//搜索的内容
		String roleType = getParameter(request, "roleType"); //启用状态 

		String currentPage = StringUtil.isEmpty(getParameter(request, "currentPage")) ? "1": getParameter(request, "currentPage").toString();//当前页
		SysRole sysRole=new SysRole();
		if(searchInput!=null && !"".equals(searchInput)){
			sysRole.setRoleNameZh(searchInput);
			sysRole.setRoleNameEn(searchInput);
		}
		if (StringUtil.isNotEmpty(roleType)) {
			sysRole.setRoleType(roleType);
		}

		Map map = new  HashMap();
		try {
			map=sysRoleService.getSysRolesListByPage(sysRole,Integer.parseInt(currentPage));//
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

	/**
	 * 验证数据是否存在
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年4月15日上午10:39:21
	 */
	@RequestMapping(value="/getOnlySysRole",method=RequestMethod.GET)
	public void getOnlySysRole(HttpServletRequest request ,HttpServletResponse response){
		try {
			String roleNameZh = getParameter(request, "roleNameZh");//角色名称
			
			SysRole sysRole=sysRoleService.getSysRole(roleNameZh);
			
			if(null != sysRole){
				this.writeJSONResult(0, null,null, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}
			
		} catch (Exception e) {
			this.writeJSONResult(-1, null,null, response);
		}
	}
	
	/**
	 * 获取系统角色列表集合
	 * @param request
	 * @param response
	 * 2016年3月10日下午1:28:31
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getSysRoleList",method=RequestMethod.GET)
	public void getSysRoleList(HttpServletRequest request ,HttpServletResponse response){
		List<SysRole> list=new ArrayList<SysRole>();
		Map map = new  HashMap();
		SysRole sysRole=new SysRole();
		String roleType = getParameter(request, "roleType");
		if (StringUtil.isNotEmpty(roleType)) {
			sysRole.setRoleType(roleType);
		}
		try {
			list = sysRoleService.getSysRoleList(sysRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("result", list);
		writerJSON(map,response);
	}
	
	/**
	 * 添加一个角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addSysRole",method=RequestMethod.POST)
	public void add(SysRole sysRole,HttpServletRequest request ,HttpServletResponse response){
		int code = SUCCESS_CODE;
		try {
			sysRole.setCreateUser(((SysUser)request.getSession().getAttribute(GlobalConstants.SESSION_USER)).getLoginName());
			code = sysRoleService.saveSysRole(sysRole) ? SUCCESS_CODE : ERROR_CODE;
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_ADD);
		} catch (Exception e) {
			e.printStackTrace();
			code = GlobalConstants.FAILURE_STATE; 
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		writeJSONResult(code, null, null, response);
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateSysRole",method=RequestMethod.POST)
	public void update(SysRole sysRole,HttpServletRequest request ,HttpServletResponse response){
		int code = ERROR_CODE;
		try {
			sysRole.setUpdateUser(((SysUser)request.getSession().getAttribute(GlobalConstants.SESSION_USER)).getLoginName());
			code = sysRoleService.updateSysRole(sysRole) ? SUCCESS_CODE : ERROR_CODE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			code = GlobalConstants.FAILURE_STATE; 
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		writeJSONResult(code, null, null, response);
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteSysRole",method=RequestMethod.POST)
	public void delete(HttpServletRequest request ,HttpServletResponse response){
		String id = getParameter(request, "id");
		int code;
		try {
			SysUser sysUser = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
			code = sysRoleService.deleteSysRole(id,sysUser) ? SUCCESS_CODE : ERROR_CODE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			code = GlobalConstants.FAILURE_STATE; 
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		writeJSONResult(code, null, null, response);
	}

	/*
	* 获取参数值关联数据维度
	* */
	@RequestMapping(value="/getRelateData",method=RequestMethod.GET)
	public void getRelateData(HttpServletRequest request ,HttpServletResponse response){
		try {
			String type = getParameter(request, "type");//角色名称

			List<ReportParamRelateData> reportParamRelateDataList=sysRoleService.getRelateDataByType(type);

			if(null != reportParamRelateDataList){
				this.writeJSONResult(0, null,reportParamRelateDataList, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}

		} catch (Exception e) {
			this.writeJSONResult(-1, null,null, response);
		}
	}
	
}
