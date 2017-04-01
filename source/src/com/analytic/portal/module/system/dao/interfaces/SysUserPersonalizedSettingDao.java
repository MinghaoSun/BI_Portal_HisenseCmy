package com.analytic.portal.module.system.dao.interfaces;

import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUserParamSetting;
import com.analytic.portal.module.system.model.SysUserReportSetting;

import java.util.List;
import java.util.Map;

public interface SysUserPersonalizedSettingDao {

	public void deleteUserReportSetting(String userId, String menuId, String settingType) throws Exception;
	public void deleteUserParamSetting(String userId, String paramId) throws Exception;
	@SuppressWarnings("rawtypes")
	public List<SysUserReportSetting> getUserReportSettingList(Map paraMap) throws Exception;
	public List<SysUserParamSetting> getUserParamSettingList(String userId, String paramId) throws Exception;
}
	

