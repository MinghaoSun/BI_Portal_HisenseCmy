package com.analytic.portal.module.system.service.interfaces;

import java.util.List;

import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.model.SysUser;

public interface SysRoleParamService {

	List<SysRoleParam> getSysRoleParam4SysRoleId(String sysRoleId) throws Exception;

	boolean updateSysRoleParam4RoleId(SysRole sysRole) throws Exception;

}
