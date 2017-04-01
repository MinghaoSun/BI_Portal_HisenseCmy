/**
 * BI集成
 */
package com.analytic.portal.module.report.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.report.model.ReportTool;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.report.service.interfaces.BOService;
import com.analytic.portal.module.report.service.interfaces.QlikViewService;
import com.analytic.portal.module.report.service.interfaces.BIService;
import com.analytic.portal.module.report.service.interfaces.TableauService;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysUserService;

/**
 * BI集成
 * @author Boger
 */
@Service
public class BIServiceImpl implements BIService {

	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private ReportToolService reportToolService;
	@Resource
	private TableauService tableauService;
	@Resource
	private BOService bOService;
	@Resource
	private QlikViewService qlikViewService;
	
	/**
	 * 根据当前菜单ID获取对应的报表权限
	 * @param menuId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午2:13:01
	 */
	@Override
	public Map<String, String> getReportSSO(String menuId, String userId, HttpServletRequest request) throws Exception {
		String location="";
		Map<String, String> reportMap=new HashMap<String, String>();
		
		try {
			if (StringUtil.isNotEmpty(menuId)&&StringUtil.isNotEmpty(userId)){
				//1.根据菜单ID获取当前报表工具ID
				SysMenu sysMenu=sysMenuService.getMenuById(menuId);
				String reportToolId=sysMenu.getMenuReportTool();
				//2.根据用户ID获取用户的BI账户映射信息
				ReportUserMapping reportUser=sysUserService.getReportUserMapping(reportToolId, userId);
				//3.构建BI工具预处理数据
				/*ReportMenuUrl reportMenuUrl=sysMenuService.getReportMenuUrl(userId, menuId);
				//防止可能操作的失误，导致无法获取到完整的地址，而重新获取生成
				if (reportMenuUrl==null){
					sysMenuService.updateReportMenuUrl(request);
					reportMenuUrl=sysMenuService.getReportMenuUrl(userId, menuId);
				}*/
				/*todo 特别注意：此处逻辑*/
				/*用户每次点击报表都是去拼接最新访问地址*/
				sysMenuService.updateReportMenuUrl(request);
				ReportMenuUrl reportMenuUrl=sysMenuService.getReportMenuUrl(userId, menuId);

				String address=reportMenuUrl.getReportUrl().split("\\?")[0];
				String param=reportMenuUrl.getReportUrl().split("\\?")[1];
				String url=sysMenu.getMenuUrl();
				ReportTool reportTool=reportToolService.getReportToolById(reportToolId);
				String version=reportTool.getReportToolVersion();
				String authAddress=reportTool.getReportToolAuthUrl();
				Map<String, String> map=GlobalCache.getDic(GlobalConstants.DIC_REPORT_TOOL_CATEGORY);
				//Tableau工具地址
				if (map.get(GlobalConstants.REPORT_TOOL_CATEGORY_TABLEAU).toString().
						equals(reportTool.getReportToolType())){
					//报表请求凭证
					String client=request.getRemoteAddr();
//					String client="192.168.1.103";
					String ticket=tableauService.getTableauTicket(reportUser, authAddress, client);
					//请求地址
/**/				if (ticket.equals(GlobalConstants.REPORT_TICKET_FAIL)){
						reportMap.put(GlobalConstants.REPORT_REQ_STATUS, String.valueOf(GlobalConstants.ERROR_CODE));
					}else {
						location+=tableauService.getTableauLocation(address, url, param, ticket, version);
						//参数.UserID=
						location+="&%E5%8F%82%E6%95%B0.UserID=";
						reportMap.put(GlobalConstants.REPORT_REQ_STATUS, String.valueOf(GlobalConstants.SUCCESS_CODE));
					}
					reportMap.put(GlobalConstants.REPORT_TOOL_TYPE, GlobalCache.getDic(GlobalConstants.DIC_REPORT_TOOL_CATEGORY).
							get(GlobalConstants.REPORT_TOOL_CATEGORY_TABLEAU));
				}
				//BO工具地址
				if (map.get(GlobalConstants.REPORT_TOOL_CATEGORY_BO).toString().
						equals(reportTool.getReportToolType())){
					//报表请求凭证
					String client=request.getRemoteAddr();
					String token=bOService.getBOToken(reportUser, authAddress, client, request);
					//请求地址
					if (StringUtil.isEmpty(token)){
						reportMap.put(GlobalConstants.REPORT_REQ_STATUS, String.valueOf(GlobalConstants.ERROR_CODE));
					}else {
						location+=bOService.getBOLocation(address, url, param, token, version);
						location+="&lsSUSER=";
						reportMap.put(GlobalConstants.REPORT_REQ_STATUS, String.valueOf(GlobalConstants.SUCCESS_CODE));
					}
					reportMap.put(GlobalConstants.REPORT_TOOL_TYPE, GlobalCache.getDic(GlobalConstants.DIC_REPORT_TOOL_CATEGORY).
							get(GlobalConstants.REPORT_TOOL_CATEGORY_BO));
				}
				//QlikView工具地址
				if (map.get(GlobalConstants.REPORT_TOOL_CATEGORY_QV).toString().
						equals(reportTool.getReportToolType())){
					String tokenUrl=qlikViewService.getQVTokenUrl(reportUser, authAddress);
					location+=qlikViewService.getQVLocation(tokenUrl, address, url, param, version);
					reportMap.put(GlobalConstants.REPORT_TOOL_TYPE, GlobalCache.getDic(GlobalConstants.DIC_REPORT_TOOL_CATEGORY).
							get(GlobalConstants.REPORT_TOOL_CATEGORY_BO));
					reportMap.put(GlobalConstants.REPORT_REQ_STATUS, String.valueOf(GlobalConstants.SUCCESS_CODE));
				}
				
				reportMap.put(GlobalConstants.REPORT_REQ_LOCATION, location);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return reportMap;
	}

}
