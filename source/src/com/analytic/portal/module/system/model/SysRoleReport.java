package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 角色工具表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysRoleReport implements Serializable {
	
	//主键
	private String id;
	//角色ID
	private String roleId;
	//菜单ID
	private String menuId;
	//报表工具ID
	private String reportToolId;

	public SysRoleReport() {
	}
	
	public SysRoleReport(String id, String roleId, String menuId,
			String reportToolId) {
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
		this.reportToolId = reportToolId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getReportToolId() {
		return reportToolId;
	}
	public void setReportToolId(String reportToolId) {
		this.reportToolId = reportToolId;
	}
	
}
