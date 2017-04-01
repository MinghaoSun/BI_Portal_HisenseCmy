package com.analytic.portal.module.report.model;

import java.io.Serializable;

/**
 * 报表参数表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportParamRelateData implements Serializable{

	//代码
	private String code;
	//名称
	private String name;
	//类型
	private String type;

	public ReportParamRelateData() {
	}

	public ReportParamRelateData(String code, String name, String type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
