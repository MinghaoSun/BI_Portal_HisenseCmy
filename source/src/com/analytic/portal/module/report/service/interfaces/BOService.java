package com.analytic.portal.module.report.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.analytic.portal.module.report.model.ReportUserMapping;

public interface BOService {

	public String getBOLocation(String address, String url, String param, String token, String version) 
			throws Exception;
	
	public String getBOToken(ReportUserMapping reportUser, String authAddress, String client,
                             HttpServletRequest request) throws Exception;
	
}
