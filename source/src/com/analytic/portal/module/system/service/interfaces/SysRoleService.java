package com.analytic.portal.module.system.service.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.report.model.ReportParamRelateData;
import com.analytic.portal.module.report.model.ReportRefreshParam;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleMenu;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.model.SysUser;

public interface SysRoleService {

	public List<SysRoleMenu> getSysRoleMenuByMenuId(String menuId) throws Exception;

	@SuppressWarnings("rawtypes")
	public Map getSysRolesListByPage(SysRole sysRole, int page) throws Exception;

	boolean saveSysRole(SysRole sysRole) throws Exception;

	boolean updateSysRole(SysRole sysRole) throws Exception;

	boolean deleteSysRole(String id, SysUser sysUser) throws Exception;
	
	public List<SysRoleParam> getSysRoleParamByRoleId(String roleId) throws Exception;
	public List<ReportRefreshParam> getSysRoleParamAndRefreshParamByUserId(String userId) throws Exception;

	public List<SysRole> getSysRoleList(SysRole SysRole) throws Exception;

	public SysRole getSysRole(String roleName) throws Exception;

	public List<ReportParamRelateData> getRelateDataByType(String type) throws Exception;

}
