package com.analytic.portal.module.system.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SysUserParamSetting implements Serializable{

	//主键
	private String id;
	//用户ID
	private String userId;
	//参数ID
	private String paramId;
	//刷新参数值
	private String refreshValue;
	//删除标志 0-未删除；1-已删除
	private String isDelete;

	public SysUserParamSetting() {
	}

	public SysUserParamSetting(String id, String userId, String paramId, String refreshValue, String isDelete) {
		this.id = id;
		this.userId = userId;
		this.paramId = paramId;
		this.refreshValue = refreshValue;
		this.isDelete = isDelete;
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

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getRefreshValue() {
		return refreshValue;
	}

	public void setRefreshValue(String refreshValue) {
		this.refreshValue = refreshValue;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}