package com.analytic.portal.module.system.model;

import java.io.Serializable;


/**
 * 系统用户报表设置表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysUserReportSetting implements Serializable{

	//主键
	private String id;
	//用户ID
	private String userId;
	//菜单ID
	private String menuId;
	//报表设置类型 01-常用报表；02-默认报表
	private String settingType;
	//删除标志 0-未删除；1-已删除
	private String deletedFlag;

	public SysUserReportSetting() {
	}

	public SysUserReportSetting(String id, String userId, String menuId, String settingType, String deletedFlag) {
		this.id = id;
		this.userId = userId;
		this.menuId = menuId;
		this.settingType = settingType;
		this.deletedFlag = deletedFlag;
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

	public String getSettingType() {
		return settingType;
	}

	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
}