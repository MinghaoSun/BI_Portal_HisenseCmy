/**
 * QlikViewBI工具
 */
package com.analytic.portal.module.report.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DESEncode;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.report.service.interfaces.QlikViewService;

/**
 * QlikViewBI工具集成
 * @author Boger
 */
@Service
public class QlikViewServiceImpl implements QlikViewService {
	
	/**
	 * 获取Token请求地址
	 * @param reportUser
	 * @param authAddress
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年5月17日下午1:57:30
	 */
	@Override
	public String getQVTokenUrl(ReportUserMapping reportUser, String authAddress)
			throws Exception {
		String tokenUrl="";
		
		tokenUrl+="http://"+authAddress;
		tokenUrl+="?reportUser="+reportUser.getReportSysName();
		tokenUrl+="&reportPass="+DESEncode.Decrypt(reportUser.getReportSysPassword());
		
		return tokenUrl;
	}
	
	/**
	 * 根据请求信息生成QV报表请求地址
	 * @param address
	 * @param url
	 * @param param
	 * @param version
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年5月17日下午1:33:15
	 */
	@Override
	public String getQVLocation(String tokenUrl, String address, String url, String param, String version) 
			throws Exception {
		String location="";
		
		if (version.equals(GlobalConstants.REPORT_VERSION_QV_110)){
			location=getLocation110(tokenUrl, address, url, param);
		}
		
		return location;
	}
	
	/**
	 * 获取QV11.0报表地址
	 * @param address
	 * @param url
	 * @param param
	 * @return
	 * Boger
	 * 2016年5月17日下午1:45:28
	 */
	private String getLocation110(String tokenUrl, String address, String url, String param){
		String location="";
		
		try {
			location+="http://"+address;
			location+="?document="+url;
			location+="&"+param;
			//直接以请求地址的形式请求QV报表
			location=tokenUrl+"&location="+URLEncoder.encode(location, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return location;
	}

}
