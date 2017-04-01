package com.analytic.portal.module.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关系表
 * @author pengbo
 * 2016-09-09 
 */
@SuppressWarnings("serial")
public class SysUserRole implements Serializable {

	//主键
	private String id;
	//用户ID
	private String userId;
	//角色ID
	private String roleId;
	//创建时间
	private String createTime;

	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;
	
	public SysUserRole() {
	}
	
	public SysUserRole(String id, String userId, String roleId,
			String createTime, String createUser, String updateTime,
			String updateUser) {
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
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
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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