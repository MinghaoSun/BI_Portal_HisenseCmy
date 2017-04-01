package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 日志表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysDic implements Serializable {

	//主键
	private String id;
	//字典项名称
	private String dicName;
	//字典项类别
	private String dicCategory;
	//字典项代码
	private String dicCode;
	//字典项值
	private String dicValue;
	//字典项描述
	private String dicDesc;
	
	public SysDic() {
	}

	public SysDic(String id, String dicName, String dicCategory,
			String dicCode, String dicValue, String dicDesc) {
		this.id = id;
		this.dicName = dicName;
		this.dicCategory = dicCategory;
		this.dicCode = dicCode;
		this.dicValue = dicValue;
		this.dicDesc = dicDesc;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicCategory() {
		return dicCategory;
	}
	public void setDicCategory(String dicCategory) {
		this.dicCategory = dicCategory;
	}

	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public String getDicDesc() {
		return dicDesc;
	}
	public void setDicDesc(String dicDesc) {
		this.dicDesc = dicDesc;
	}
	
}