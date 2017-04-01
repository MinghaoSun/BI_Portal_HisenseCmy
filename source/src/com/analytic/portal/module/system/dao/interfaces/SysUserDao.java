package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.model.SysUser;

public interface SysUserDao extends IBaseDao {
	
	@SuppressWarnings("rawtypes")
	public List<SysUser> getSysUserList(Map paraMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public Map getSysUsersListByPage(SysUser sysUser, int page);

	public boolean updateSysUser4Role(SysUser sysUsers);
		
	public SysUser getSysUsers(String loginName);
	
	public ReportUserMapping getReportUserMapping(String reportToolId, String userId) throws Exception;

}
