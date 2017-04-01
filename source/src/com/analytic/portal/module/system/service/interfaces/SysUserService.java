package com.analytic.portal.module.system.service.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.model.SysUserReportSetting;
import com.analytic.portal.module.system.model.SysUserRole;

public interface SysUserService {
	public SysUser getSysUser(String loginName, String password) throws Exception;
	
	public boolean updatePassword(SysUser user, String password) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map getSysUsersListByPage(SysUser sysUser, int page);
	
	public boolean deleteSysUsersById(String id) throws Exception;
	
	public boolean saveSysUsers(SysUser sysUsers) throws Exception;
	
	public boolean saveSysUsersRole(SysUserRole sysUserRoles) throws Exception;
	
    public List<SysUserRole> getSysUserRoleUserId(String userId) throws Exception;
	
	boolean updateSysUser(SysUser sysUser) throws Exception;

	boolean updateEditSysUser(SysUser sysUser) throws Exception;

	public boolean updateSysUser4Role(SysUser sysUsers) throws Exception;
	
	public SysUser getSysUserById(String userId);
	
	public List<SysUser> getDataRoleId(String userId) throws Exception;
		
	@SuppressWarnings("rawtypes")
	public  List<HashMap> getVistReportListByUser(String LoginName) throws Exception;

	public boolean updateSysUserInfo(SysUser user, boolean updatePassword) throws Exception;
	
	public boolean checkAdUser(String adName, String password) throws NamingException;
	
	public SysUser getSysUsers(String loginName);
	
	public ReportUserMapping getUserReportMappingByReportToolId(String reportToolId) throws Exception;
	
	public boolean saveReportUserMapping(ReportUserMapping ReportUserMappings) throws Exception;
	
	public List<ReportUserMapping> getUserReportMappingByReportUserId(String userId) throws Exception;
	
	public ReportUserMapping getReportUserMapping(String reportToolId, String userId) throws Exception;

	public void doSynLdapUserInfo() throws Exception;


}
