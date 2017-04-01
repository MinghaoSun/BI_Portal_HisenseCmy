package com.analytic.portal.module.report.model;

import java.io.Serializable;

/**
 * 报表参数表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportParam  implements Serializable{
	
	//主键
	private String id;
	//参数代码
	private String code;
	//参数名称
	private String paramName;
	//参数键
	private String paramKey;
	//参数排序
	private Integer paramOrder;
	//备注
	private String paramRemark;
	//删除标识
	private String isDelete;
	//创建时间
	private String createTime;
	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;

	/*新增*/
	private String relateDataType;
	private String paramOperatType;
	private String paramValueType;
	private String paramValueRelate;

	public ReportParam(){
	}

	public ReportParam(String id, String code, String paramName, String paramKey, Integer paramOrder,
			String paramRemark, String isDelete, String createTime, String createUser, String updateTime,
			String updateUser,String relateDataType) {
		this.id = id;
		this.code = code;
		this.paramName = paramName;
		this.paramKey = paramKey;
		this.paramOrder = paramOrder;
		this.paramRemark = paramRemark;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.relateDataType = relateDataType;
	}

	public String getParamValueRelate() {
		return paramValueRelate;
	}

	public void setParamValueRelate(String paramValueRelate) {
		this.paramValueRelate = paramValueRelate;
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

	public String getRelateDataType() {
		return relateDataType;
	}

	public void setRelateDataType(String relateDataType) {
		this.relateDataType = relateDataType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamRemark() {
		return paramRemark;
	}
	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark;
	}
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getParamOrder() {
		return paramOrder;
	}
	public void setParamOrder(Integer paramOrder) {
		this.paramOrder = paramOrder;
	}

}
