package com.analytic.portal.module.system.model;

import java.io.Serializable;


/**
 * 系统用户表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysUser implements Serializable{

	//主键
	private String id;
	//登录账户
	private String loginName;
	//登录密码
	private String loginPassword;
	//用户全名
	private String userFullName;
	//名
	private String firstName;
	//姓
	private String lastName;
	//性别
	private String userSex;
	//生日
	private String userBirthday;
	//手机号码
	private String userMobile;
	//电子邮件
	private String userEmail;
	//是否在线
	private String isOnline;
	//是否启用
	private String userEnabled;
	//删除标识
	private String isDelete;
	//最后登录时间
	private String lastLoginTime;
	//数据角色ID
	private String dataRoleId;
	//用户主题
	private String userThemes;
	//备注
	private String userRemark;
	//创建时间
	private String createTime;
	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;
	//功能角色
	private String sysFunctionRole;
	//用户映射
	private String sysUserReportTool;
    //用户部门
    private String userDepartment;
	//用户编辑权限
    private String userFlag;

	public SysUser() {
	}
	
	public SysUser(String id, String loginName, String loginPassword, String userFullName, String firstName,
			String lastName, String userSex, String userBirthday, String userMobile, String userEmail,
			String isOnline, String userEnabled, String isDelete, String lastLoginTime,String userThemes,String userRemark,
			String dataRoleId, String createTime, String createUser, String updateTime, String updateUser, String sysFunctionRole,
			String sysUserReportTool, String userDepartment,String userFlag) {
		this.id = id;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.userFullName = userFullName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userSex = userSex;
		this.userBirthday = userBirthday;
		this.userMobile = userMobile;
		this.userEmail = userEmail;
		this.isOnline = isOnline;
		this.userEnabled = userEnabled;
		this.isDelete = isDelete;
		this.lastLoginTime = lastLoginTime;
		this.dataRoleId = dataRoleId;
		this.userThemes = userThemes;
		this.userRemark = userRemark;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.sysFunctionRole = sysFunctionRole;
		this.sysUserReportTool = sysUserReportTool;
        this.userDepartment = userDepartment;
        this.userFlag = userFlag;
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

	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getUserEnabled() {
		return userEnabled;
	}
	public void setUserEnabled(String userEnabled) {
		this.userEnabled = userEnabled;
	}

	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getDataRoleId() {
		return dataRoleId;
	}
	public void setDataRoleId(String dataRoleId) {
		this.dataRoleId = dataRoleId;
	}

	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
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
	public String getSysFunctionRole() {
		return sysFunctionRole;
	}

	public void setSysFunctionRole(String sysFunctionRole) {
		this.sysFunctionRole = sysFunctionRole;
	}

	public String getSysUserReportTool() {
		return sysUserReportTool;
	}

	public void setSysUserReportTool(String sysUserReportTool) {
		this.sysUserReportTool = sysUserReportTool;
	}

	public String getUserThemes() {
		return userThemes;
	}
	public void setUserThemes(String userThemes) {
		this.userThemes = userThemes;
	}

    public String getUserDepartment() {
        return userDepartment;
    }
    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserFlag() {
        return userFlag;
    }
    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }
}