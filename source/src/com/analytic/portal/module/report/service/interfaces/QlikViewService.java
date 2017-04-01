/**
 * QlikViewBI工具
 */
package com.analytic.portal.module.report.service.interfaces;

import com.analytic.portal.module.report.model.ReportUserMapping;

/**
 * QlikViewBI工具集成
 * @author Boger
 */
public interface QlikViewService {
	
	public String getQVLocation(String tokenUrl, String address, String url, String param, String version) 
			throws Exception;
	
	public String getQVTokenUrl(ReportUserMapping reportUser, String authAddress) throws Exception;
}
