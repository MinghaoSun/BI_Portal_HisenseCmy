package com.analytic.portal.module.system.model;

import java.io.Serializable;

/**
 * 日志表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysLog implements Serializable {

	//主键
	private String id;
	//登录账户
	private String loginName;
	//用户姓名
	private String userName;
	//菜单名称
	private String menuName;
	//菜单代码
	private String menuCode;
	//操作类型
	private String operType;
	//请求地址
	private String reqPath;
	//请求数据
	private String reqData;
	//状态代码
	private Integer statusCode;
	//日志描述
	private String logDesc;
	//创建时间
	private String createTime;
	//最近使用报表地址
	private String menuReportId;
	
	public SysLog() {
	}
	
	public SysLog(String id, String loginName, String userName,
			String menuName, String menuCode, String operType, 
			String reqPath, String reqData, Integer statusCode,
			String logDesc, String createTime, String menuReportId) {
		this.id = id;
		this.loginName = loginName;
		this.userName = userName;
		this.menuName = menuName;
		this.menuCode = menuCode;
		this.operType = operType;
		this.reqPath = reqPath;
		this.reqData = reqData;
		this.statusCode = statusCode;
		this.logDesc = logDesc;
		this.createTime = createTime;
		this.menuReportId = menuReportId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getReqPath() {
		return reqPath;
	}
	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}
	
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getMenuReportId() {
		return menuReportId;
	}
	public void setMenuReportId(String menuReportId) {
		this.menuReportId = menuReportId;
	}

}