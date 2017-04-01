package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.model.SysRoleParam;

public interface SysRoleParamDao extends IBaseDao {

	public List<SysRoleParam> getSysRoleParamList(Map paraMap) throws Exception;
	
	boolean deleteSysRoleParam4RoleId(String roleId) throws Exception;
}
