package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.system.model.SysMenu;

public interface SysMenuDao {

	public void deleteReportMenuUrlByUserId(String userId) throws Exception;
	public void deleteReportMenuUrlByUserIdAndMenuId(String userId, String menuId) throws Exception;

	public List<SysMenu> getReportMenuByUserId(String userId) throws Exception;
	public List<SysMenu> getReportMenuByMenuId(String menuId) throws Exception;

	@SuppressWarnings("rawtypes")
	public List<SysMenu> getMenuList(Map formMap) throws Exception;
	
	public List<ReportMenuParam> getReportMenuParamListByMenuId(String menuId) throws Exception;
	
	public void deleteReportMenuParamByMenuId(String menuId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map getMenuListByPage(Map formMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public Map getMenuSettingListByUserId(Map formMap, String userId) throws Exception;

	public ReportMenuUrl getReportMenuUrl(String userId, String menuId) throws Exception;
	
	public List<SysMenu> getReportMenuListByParentMenuId(String menuParentId, String userId) throws Exception;
	
	public List<SysMenu> getMenuHierarchyListByUserId(String userId) throws Exception;
	public List<SysMenu> getPersonalSettingMenuHierarchyListByUserId(String userId, String settingType) throws Exception;

	public List<SysMenu> getMenuHierarchyDirectoryListByMenuType(String menuType) throws Exception;

	public List<SysMenu> getReportFolders(String userId);

	public List<ReportMenuParam> getReportMenuParamList4MenuId(String menuId)throws Exception;
	
	public SysMenu getSysMenu(String menuName) throws Exception;
	
	public List<SysMenu> getSysMenuListByParentStr(String parentStr) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<SysMenu> getEnableSysMenuList(String parentStr, Map formMap) throws Exception;
}
