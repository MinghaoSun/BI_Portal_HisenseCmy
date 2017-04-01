package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 日志表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysConfig implements Serializable {

	//主键
	private String id;
	//参数名称
	private String paramName;
	//参数代码
	private String paramCode;
	//参数值
	private String paramValue;
	//参数数据类型
	private String paramType;
	//参数描述
	private String paramDesc;
	
	public SysConfig() {
	}

	public SysConfig(String id, String paramName, String paramCode,
			String paramValue, String paramType, String paramDesc) {
		super();
		this.id = id;
		this.paramName = paramName;
		this.paramCode = paramCode;
		this.paramValue = paramValue;
		this.paramType = paramType;
		this.paramDesc = paramDesc;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
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

	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	
}