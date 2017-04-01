package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 角色菜单表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysRoleMenu implements Serializable {
	
	//主键
	private String id;
	//角色ID
	private String roleId;
	//菜单Id
	private String menuId;
	
	public SysRoleMenu() {
	}
	
	public SysRoleMenu(String id, String roleId, String menuId) {
		this.id = id;
		this.roleId = roleId;
		this.menuId = menuId;
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

}