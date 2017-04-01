package com.analytic.portal.module.report.service.interfaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BIService {
	
	public Map<String, String> getReportSSO(String menuId, String userId, HttpServletRequest request) throws Exception;
}
