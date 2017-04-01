/**
 * BOBI工具
 */
package com.analytic.portal.module.report.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DESEncode;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.report.service.interfaces.BOService;
import com.crystaldecisions.celib.properties.URLEncoder;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;

/**
 * BOBI工具集成
 * @author Boger
 */
@Service
public class BOServiceImpl implements BOService {

	/**
	 * 获取BO报表地址信息
	 * @param address
	 * @param url
	 * @param param
	 * @param ticket
	 * @param version
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月20日下午6:26:22
	 */
	@Override
	public String getBOLocation(String address, String url, String param,
			String token, String version) throws Exception {
		String location="";
		
		if (version.equals(GlobalConstants.REPORT_VERSION_BO_41SP5)){
			location=getLocation41SP5Patch3(address, url, param, token);
		}
		
		return location;
	}

	/**
	 * 获取BO令牌
	 * @param reportUser
	 * @param address
	 * @param client
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月20日下午6:27:55
	 */
	@Override
	public String getBOToken(ReportUserMapping reportUser, String authAddress, String client, HttpServletRequest request) 
			throws Exception  {
		String token="";
		
		try {
			ISessionMgr sessionMgr=CrystalEnterprise.getSessionMgr();
			//登录验证
			String boUserName=reportUser.getReportSysName();
			String boPassword=reportUser.getReportSysPassword();
			boPassword=DESEncode.Decrypt(boPassword);
			IEnterpriseSession enterpriseSession = sessionMgr.
					logon(boUserName, boPassword, authAddress, GlobalConstants.REPORT_TOKEN_AUTH_TYPE);

	        //获取Token
			int tokenTime=Integer.valueOf(GlobalCache.getConfig(GlobalConstants.CONFIG_BO_SESSION));
			token = enterpriseSession.getLogonTokenMgr().
					createLogonToken("", tokenTime, GlobalConstants.REPORT_TOKEN_COUNT);
	        token= URLEncoder.encode(token);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	/**
	 * 获取BO4.1 sp5 patch3报表地址
	 * @return
	 * Boger
	 * 2016年4月26日下午4:49:01
	 */
	private String getLocation41SP5Patch3(String address, String url, String param, String token){
		String location="";
		
		location+="http://"+address;
		location+="?sIDType=CUID&iDocID="+url;
		location+="&"+param;
		location+="&token="+token;
		location+="&wmode=opaque";

		return location;
	}
	
}
