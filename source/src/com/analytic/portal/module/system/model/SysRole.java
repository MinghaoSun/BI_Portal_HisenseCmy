package com.analytic.portal.module.system.model;

import java.io.Serializable;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.model.I18NModel;

/**
 * 角色表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysRole extends I18NModel implements Serializable {

	//主键
	private String id;
	//角色代码
	private String code;
	//角色名称
	@SuppressWarnings("unused")
	private String roleName;
	//英文名称
	private String roleNameEn;
	//中文名称
	private String roleNameZh;
	//状态
	private String roleStatus; 
	//子角色ID
	private String subRoleId;
	//角色类型
	private String roleType;
	//删除状态
	private String isDelete;
	//备注
	private String roleRemark;
	//创建时间
	private String createTime;
	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;
	//数据角色
	private String sysRoleParams;
	//功能角色
	private String sysRoleMenuIds;

	public SysRole() {
	}
	
	public SysRole(String id, String code, String roleName, String roleNameEn,
			String roleNameZh, String roleStatus, String subRoleId,
			String roleType, String roleRemark, String isDelete, String createTime,
			String createUser, String updateTime, String updateUser) {
		this.id = id;
		this.code = code;
		this.roleName = roleName;
		this.roleNameEn = roleNameEn;
		this.roleNameZh = roleNameZh;
		this.roleStatus = roleStatus;
		this.subRoleId = subRoleId;
		this.roleType = roleType;
		this.roleRemark = roleRemark;
		this.isDelete=isDelete;
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
	
	public String getRoleName() {
		if(localLanguage!=null&&!"".equals(localLanguage)){
			return GlobalConstants.I18N_ZH_CN.equals(localLanguage) ? this.roleNameZh : this.roleNameEn;
		}
		return roleNameZh;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleNameEn() {
		return roleNameEn;
	}
	public void setRoleNameEn(String roleNameEn) {
		this.roleNameEn = roleNameEn;
	}
	
	public String getRoleNameZh() {
		return roleNameZh;
	}
	public void setRoleNameZh(String roleNameZh) {
		this.roleNameZh = roleNameZh;
	}
	
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	
	public String getSubRoleId() {
		return subRoleId;
	}
	public void setSubRoleId(String subRoleId) {
		this.subRoleId = subRoleId;
	}
	
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
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

	public String getSysRoleParams() {
		return sysRoleParams;
	}

	public void setSysRoleParams(String sysRoleParams) {
		this.sysRoleParams = sysRoleParams;
	}

	public String getSysRoleMenuIds() {
		return sysRoleMenuIds;
	}

	public void setSysRoleMenuIds(String sysRoleMenuIds) {
		this.sysRoleMenuIds = sysRoleMenuIds;
	}

}