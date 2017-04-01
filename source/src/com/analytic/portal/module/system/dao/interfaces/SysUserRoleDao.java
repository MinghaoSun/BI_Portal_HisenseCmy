package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.model.SysUserRole;

public interface SysUserRoleDao extends IBaseDao {

	public boolean deleteSysUserRoleUserId(String userId) throws Exception;
	public void deleteSysUserRoleByRoleType(String userId, String roleType) throws Exception;

	public boolean deleteRepeortUserMapping(String userId) throws Exception;

	public List<SysUserRole> getSysUserRoleList(Map map);
	
	List<SysUserRole> getSysUserRoleByUseridAndType(String userid, String type);
	
	public boolean deleteSysUserParamSettingByUserId(String id) throws Exception;
	
}
