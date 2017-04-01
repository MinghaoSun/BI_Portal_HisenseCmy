package com.analytic.portal.module.report.service.interfaces;

import java.util.List;
import java.util.Map;

import com.analytic.portal.module.report.model.ReportParam;

public interface ReportParamService{
	
	public boolean deleteReportParamById(String id) throws Exception;
	
	public boolean addReportParam(ReportParam reportParam) throws Exception; 
	
	public boolean updateReportParam(ReportParam reportParam) throws Exception; 
	
	public ReportParam getReportParamById(String id) throws Exception;
	
	public List<ReportParam> getReportParamList() throws Exception;

	@SuppressWarnings("rawtypes")
	public ReportParam getReportParam(Map formMap) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map getReportParamListByPage(Map formMap) throws Exception;
}
