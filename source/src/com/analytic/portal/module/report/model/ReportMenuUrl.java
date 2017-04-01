package com.analytic.portal.module.report.model;

import java.io.Serializable;

/**
 * 用户报表地址访问表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportMenuUrl implements Serializable{

	//主键
	private String id;
	//用户ID
	private String userId;
	//菜单ID
	private String menuId;
	//报表地址
	private String reportUrl;
	
	public ReportMenuUrl() {
	}

	public ReportMenuUrl(String id, String userId, String menuId, String reportUrl) {
		this.id = id;
		this.userId = userId;
		this.menuId = menuId;
		this.reportUrl = reportUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getReportUrl() {
		return reportUrl;
	}
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

}
