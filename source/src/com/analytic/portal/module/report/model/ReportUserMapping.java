package com.analytic.portal.module.report.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表用户映射表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class ReportUserMapping  implements Serializable{
	
	//主键
	private String id;
	//用户ID
	private String userId;
	//报表工具ID
	private String reportToolId;
	//报表系统用户名
	private String reportSysName;
	//报表系统密码
	private String reportSysPassword;
	//创建时间
	private String createTime;
	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;
	
	public ReportUserMapping(){
	}
	
	public ReportUserMapping(String id, String userId, String reportToolId,
			String reportSysName, String reportSysPassword, String createTime,
			String createUser, String updateTime, String updateUser) {
		this.id = id;
		this.userId = userId;
		this.reportToolId = reportToolId;
		this.reportSysName = reportSysName;
		this.reportSysPassword = reportSysPassword;
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

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReportToolId() {
		return reportToolId;
	}
	public void setReportToolId(String reportToolId) {
		this.reportToolId = reportToolId;
	}

	public String getReportSysName() {
		return reportSysName;
	}
	public void setReportSysName(String reportSysName) {
		this.reportSysName = reportSysName;
	}

	public String getReportSysPassword() {
		return reportSysPassword;
	}
	public void setReportSysPassword(String reportSysPassword) {
		this.reportSysPassword = reportSysPassword;
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
	
}
