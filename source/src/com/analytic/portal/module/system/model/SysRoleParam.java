package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 数据角色关系表
 * @author pengbo
 * 2016-03-01
 */
@SuppressWarnings("serial")
public class SysRoleParam implements Serializable  {
	
	//主键
	private String id;
	//角色ID
	private String roleId;
	//参数ID
	private String paramId;
	//参数值
	private String paramValue;
	//参数类型
	private String paramType;
	private String paramOperatType;
	private String paramValueType;
	private String paramValueRelate;

	public SysRoleParam() {
	}

	public SysRoleParam(String id, String roleId, String paramId, String paramValue, String paramType, String paramOperatType, String paramValueType,String paramValueRelate) {
		this.id = id;
		this.roleId = roleId;
		this.paramId = paramId;
		this.paramValue = paramValue;
		this.paramType = paramType;
		this.paramOperatType = paramOperatType;
		this.paramValueType = paramValueType;
		this.paramValueRelate = paramValueRelate;
	}

	public String getParamValueRelate() {
		return paramValueRelate;
	}

	public void setParamValueRelate(String paramValueRelate) {
		this.paramValueRelate = paramValueRelate;
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

	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamOperatType() {
		return paramOperatType;
	}

	public void setParamOperatType(String paramOperatType) {
		this.paramOperatType = paramOperatType;
	}

	public String getParamValueType() {
		return paramValueType;
	}

	public void setParamValueType(String paramValueType) {
		this.paramValueType = paramValueType;
	}
}
