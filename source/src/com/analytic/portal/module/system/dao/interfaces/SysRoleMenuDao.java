package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.model.SysRoleMenu;

public interface SysRoleMenuDao extends IBaseDao {

	List<SysRoleMenu> getSysRoleMenuList(Map paraMap) throws Exception;

	boolean deleteSysRoleMenu4RoleId(String roleId) throws Exception;

}
