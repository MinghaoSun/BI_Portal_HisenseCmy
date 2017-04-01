package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.model.SysRole;

public interface SysRoleDao extends IBaseDao {
	
	@SuppressWarnings("rawtypes")
	public Map getSysRolesListByPage(SysRole sysRole, int page) throws Exception;
	
	public List<SysRole> getSysRoleList(SysRole SysRole) throws Exception;

	public SysRole getSysRole(String roleName) throws Exception;
}
