package com.analytic.portal.module.system.service.interfaces;

import java.util.List;

import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleMenu;

public interface SysRoleMenuService {

	List<SysRoleMenu> getSysRoleMenu4SysRoleId(String sysRoleId)
			throws Exception;

	boolean updateSysRoleMenu4RoleId(SysRole sysRole) throws Exception;

}
