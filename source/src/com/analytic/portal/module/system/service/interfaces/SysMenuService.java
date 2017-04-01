package com.analytic.portal.module.system.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUser;

public interface SysMenuService {
	
	public void saveMenu(SysMenu sysMenu, String reportMenuParam) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map deleteMenu(String menuId) throws Exception;
	
	public void updateMenu(SysMenu sysMenu) throws Exception;
	
	public SysMenu getMenuById(String menuId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map getMenuListByPage(Map formMap) throws Exception;

	
	@SuppressWarnings("rawtypes")
	public List<SysMenu> getMenuList(Map formMap) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<SysMenu> getEnableMenuList(Map formMap) throws Exception;
	
	public List<SysMenu> getMenuHierarchyListByUserId(String userId) throws Exception;
	public List<SysMenu> getDefaultReportMenuByUserId(String userId) throws Exception;

	public List<SysMenu> getMenuHierarchyDirectoryListByMenuType(String menuType) throws Exception;
	
	public List<SysMenu> getReportMenuListByParentMenuId(String menuParentId, String userId) throws Exception;
	
	public List<SysMenu> getMenuHierarchyList() throws Exception;
	
	public void updateReportMenuUrl(HttpServletRequest request) throws Exception;

	public ReportMenuUrl getReportMenuUrl(String userId, String menuId) throws Exception;
	
	public void deleteReportMenuUrlByUserId(String userId) throws Exception;

	public List<ReportMenuParam> getReportMenuParamListByMenuId(String menuId) throws Exception;

	public List<Map<String, Object>> getReportFolders(String menuParentId, String userId);

	public List<ReportMenuParam> getReportMenuParamList4MenuId(String menuId) throws Exception;
	
	public SysMenu getSysMenu(String menuName) throws Exception;
}
