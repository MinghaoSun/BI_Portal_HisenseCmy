package com.analytic.portal.module.report.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.model.ReportRefreshParam;
import com.analytic.portal.module.system.model.SysUserParamSetting;

/**
 * 报表参数Dao
 * @author pengbo
 * 2016-03-11
 */
public interface ReportParamDao {
	
	public boolean deleteReportParamById(String id) throws Exception;
	
	public boolean addReportParam(ReportParam reportParam) throws Exception; 
	
	public boolean updateReportParam(ReportParam reportParam) throws Exception; 
	
	public ReportParam getReportParamById(String id) throws Exception;
	
	public List<ReportParam> getReportParamList() throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ReportParam getReportParam(Map formMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public Map getReportParamListByPage(Map formMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public Map getUserReportParamSettingListByPage(Map formMap) throws Exception;
	public List<ReportRefreshParam> getSysRoleParamAndRefreshParamList(String userId) throws Exception;
	public List<ReportRefreshParam> getUserReportParamSettingList(Map formMap) throws Exception;

}
