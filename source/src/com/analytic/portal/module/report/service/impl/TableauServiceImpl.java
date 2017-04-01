/**
 * TableauBI工具
 */
package com.analytic.portal.module.report.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import org.springframework.stereotype.Service;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.report.service.interfaces.TableauService;

/**
 * TableauBI工具集成
 * @author Boger
 */
@Service
public class TableauServiceImpl implements TableauService {

	/**
	 * 获取Tableau报表地址信息
	 * @param address
	 * @param url
	 * @param param
	 * @param ticket
	 * @param version
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午5:58:07
	 */
	@Override
	public String getTableauLocation(String address, String url, String param,
			String ticket, String version) throws Exception {
		String location="";
		//Tableau9.2.0版本
		if (version.equals(GlobalConstants.REPORT_VERSION_TABLEAU_920)){
			location=getLocation920(address, url, param, ticket);
		}
		return location;
	}

	/**
	 * 获取Tableau报表凭证信息
	 * @param reportUser
	 * @param authAddress
	 * @param client
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午5:58:14
	 */
	@Override
	public String getTableauTicket(ReportUserMapping reportUser, String authAddress, String client) throws Exception {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try {
			StringBuffer data = new StringBuffer();
			data.append(URLEncoder.encode("username", "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(reportUser.getReportSysName(), "UTF-8"));
			data.append("&");
			data.append(URLEncoder.encode("client_ip", "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(client, "UTF-8"));
			URL url = new URL("http://" + authAddress + "/trusted");
			URLConnection conn = url.openConnection();
			/*
			* 超时时间，单位毫秒。一旦达到超时时间，会抛出java.net.SocketTimeoutException异常
			* */
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.setDoOutput(true);
			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(data.toString());
			out.flush();
			String line;
			StringBuffer rsp = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = in.readLine()) != null) {
				rsp.append(line);
			}
			return rsp.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取Tableau9.2.0报表地址
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午6:43:24
	 */
	private String getLocation920(String address, String url, String param, String ticket) throws Exception{
		String location="";
		location+="http://";
		location+=address+"/trusted";
		location+="/"+ticket;
		location+="/"+url;
		location+="?"+param;
		return location;
	}
}

