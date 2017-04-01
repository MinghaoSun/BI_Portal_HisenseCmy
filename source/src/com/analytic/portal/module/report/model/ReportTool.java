package com.analytic.portal.module.report.model;

import java.io.Serializable;

/**
 * 报表工具表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportTool implements Serializable{
	
	//主键
	private String id;
	//报表代码
	private String code;
	//报表工具
	private String reportToolName;	
	//报表工具类型
	private String reportToolType;
	//报表链接
	private String reportToolUrl;
	//报表版本号
	private String reportToolVersion;
	//报表展现形式
	private String reportToolDisplay;
	//备注
	private String reportToolRemark;
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
	
	//授权信息
	private String reportToolAuthUrl;

	public ReportTool() {
	}

	public ReportTool(String id, String code, String reportToolName,String reportToolType,
			String reportToolUrl, String reportToolVersion,
			String reportToolDisplay, String reportToolRemark,String isDelete,
			String createTime, String createUser, String updateTime,
			String updateUser) {
		this.id = id;
		this.code = code;
		this.reportToolName = reportToolName;
		this.reportToolType = reportToolType;
		this.reportToolUrl = reportToolUrl;
		this.reportToolVersion = reportToolVersion;
		this.reportToolDisplay = reportToolDisplay;
		this.reportToolRemark = reportToolRemark;
		this.isDelete = isDelete;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
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
	
	public String getReportToolName() {
		return reportToolName;
	}
	public void setReportToolName(String reportToolName) {
		this.reportToolName = reportToolName;
	}
	
	public String getReportToolUrl() {
		return reportToolUrl;
	}
	public void setReportToolUrl(String reportToolUrl) {
		this.reportToolUrl = reportToolUrl;
	}
	
	public String getReportToolVersion() {
		return reportToolVersion;
	}
	public void setReportToolVersion(String reportToolVersion) {
		this.reportToolVersion = reportToolVersion;
	}
	
	public String getReportToolDisplay() {
		return reportToolDisplay;
	}
	public void setReportToolDisplay(String reportToolDisplay) {
		this.reportToolDisplay = reportToolDisplay;
	}
	
	public String getReportToolRemark() {
		return reportToolRemark;
	}
	public void setReportToolRemark(String reportToolRemark) {
		this.reportToolRemark = reportToolRemark;
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

	public String getReportToolType() {
		return reportToolType;
	}
	public void setReportToolType(String reportToolType) {
		this.reportToolType = reportToolType;
	}

	public String getReportToolAuthUrl() {
		return reportToolAuthUrl;
	}

	public void setReportToolAuthUrl(String reportToolAuthUrl) {
		this.reportToolAuthUrl = reportToolAuthUrl;
	}
}
