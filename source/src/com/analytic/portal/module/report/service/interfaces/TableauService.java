package com.analytic.portal.module.report.service.interfaces;

import com.analytic.portal.module.report.model.ReportUserMapping;

public interface TableauService {

	public String getTableauLocation(String address, String url, String param, String ticket, String version) 
			throws Exception;
	
	public String getTableauTicket(ReportUserMapping reportUser, String authAddress, String client) throws Exception;
	
}
