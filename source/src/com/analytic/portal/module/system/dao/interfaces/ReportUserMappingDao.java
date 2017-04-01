package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.report.model.ReportUserMapping;

public interface ReportUserMappingDao {
			
	@SuppressWarnings("rawtypes")
	public List<ReportUserMapping> getReportUserMappingList(Map paraMap) throws Exception;
	
	
}
