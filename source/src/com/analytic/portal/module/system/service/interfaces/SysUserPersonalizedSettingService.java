package com.analytic.portal.module.system.service.interfaces;

import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.model.ReportRefreshParam;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUserParamSetting;
import com.analytic.portal.module.system.model.SysUserReportSetting;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface SysUserPersonalizedSettingService {

	@SuppressWarnings("rawtypes")
	public Map getMenuSettingListByUserId(Map formMap, String userIs, String settingType) throws Exception;
	@SuppressWarnings("rawtypes")
	public Map deleteSetting(String userId, String menuId, String settingType) throws Exception;
	@SuppressWarnings("rawtypes")
	public Map addSetting(String userId, String menuId, String settingType) throws Exception;
	public Map updateRefreshParamSetting(String userId, ReportRefreshParam reportRefreshParam) throws Exception;
	public Map updateGlobalRefreshParamSetting(String userId, Map<String, Object> selectRefreshParamMap) throws Exception;

	public List<SysUserReportSetting> getUserReportSettingList(String userId, String settingType) throws Exception;

	public Map getUserReportParamSettingListByPage(Map formMap) throws Exception;
	public List<ReportRefreshParam> getGlobalParamOfUserList(Map formMap, String menuId) throws Exception;


}
