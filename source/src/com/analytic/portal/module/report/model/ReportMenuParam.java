package com.analytic.portal.module.report.model;

import java.io.Serializable;

/**
 * 角色菜单表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportMenuParam implements Serializable  {
	
	//主键
	private String id;
	//菜单ID
	private String menuId;
	//参数ID
	private String paramId;
	
	
	
	public ReportMenuParam() {
	}
	
	public ReportMenuParam(String id, String menuId, String paramId) {
		this.id = id;
		this.menuId = menuId;
		this.paramId = paramId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	
}
