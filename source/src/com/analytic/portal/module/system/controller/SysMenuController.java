/**
 * 系统菜单
 */
package com.analytic.portal.module.system.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DESEncode;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.service.I18NService;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.service.interfaces.BIService;
import com.analytic.portal.module.report.service.interfaces.ReportParamService;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;

/** 
 * 系统菜单控制器
 * @author Boger
 */
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private ReportToolService reportToolService;
	@Resource
	private ReportParamService reportParamService;
	@Resource
	private I18NService i18NService;
	@Resource
	private BIService bIService;
	
	/**
	 * 新增菜单
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月21日下午12:53:10
	 */
	@RequestMapping(value="saveSysMenu", method=RequestMethod.POST)
	public void saveSysMenu(SysMenu sysMenu,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> saveSysMenuMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				//以下为数据信息
				sysMenu=getSysMenu(request,sysMenu);
				sysMenu.setCreateTime(DateUtil.getNowTime());
				sysMenu.setCreateUser(user.getLoginName());
				String strReportMenuParam=sysMenu.getReportMenuParam();
				if (StringUtil.isEmpty(strReportMenuParam)) strReportMenuParam="";
				sysMenuService.saveMenu(sysMenu,strReportMenuParam);
				//以下为状态信息
				saveSysMenuMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_ADD);
				LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
						GlobalConstants.lOGGER_LEVEL_INFO_END);
				
				writerJSON(saveSysMenuMap, response);
			}else{
				noLogin(response);
			}

		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 删除菜单信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月21日下午5:22:56
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="deleteSysMenu", method=RequestMethod.POST)
	public void deleteSysMenu(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> deleteSysMenuMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String menuId=request.getParameter("deletemenuId");
			if(user!=null){
				//以下为数据信息
				SysMenu sysMenu=sysMenuService.getMenuById(menuId);
				deleteSysMenuMap=sysMenuService.deleteMenu(menuId);
				//更新报表临时暂存地址
				/*if (sysMenu.getMenuType().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_MENU_TYPE).
						get(GlobalConstants.SYS_MENU_TYPE_REPORT))){
					sysMenuService.updateReportMenuUrl(request);
				}*/
				
				//以下为状态信息
				deleteSysMenuMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);

				writerJSON(deleteSysMenuMap, response);
			}
			noLogin(response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 更新菜单信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月22日下午1:10:00
	 */
	@RequestMapping(value="updateSysMenu", method=RequestMethod.POST)
	public void updateSysMenu(SysMenu sysMenu,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> updateSysMenuMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String reportMenutParam = sysMenu.getReportMenuParam();
			if (StringUtil.isEmpty(reportMenutParam)) reportMenutParam="";
			if(user!=null){
				//以下为数据信息
				if (sysMenu!=null){
					BeanUtils.copyProperties(sysMenu, getSysMenu(request,sysMenu));
					sysMenu.setUpdateTime(DateUtil.getNowTime());
					sysMenu.setUpdateUser(user.getLoginName());
					sysMenu.setReportMenuParam(reportMenutParam);
				}
				sysMenuService.updateMenu(sysMenu);
				//更新报表临时暂存地址
				/*if (sysMenu.getMenuType().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_MENU_TYPE).
						get(GlobalConstants.SYS_MENU_TYPE_REPORT))){
					sysMenuService.updateReportMenuUrl(request);
				}*/
				//以下为状态信息
				updateSysMenuMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(updateSysMenuMap, response);
			}
			noLogin(response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_UPDATE);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 验证数据是否存在
	 * @param request
	 * @param response
	 * pengbo
	 * 2016年4月15日上午10:39:21
	 */
	@RequestMapping(value="/getOnlySysMenu",method=RequestMethod.GET)
	public void getOnlySysMenu(HttpServletRequest request ,HttpServletResponse response){
		String menuNameZh = getParameter(request, "menuNameZh");
		
		try {
			SysMenu sysMenu=sysMenuService.getSysMenu(menuNameZh);
			if(null != sysMenu){
				this.writeJSONResult(0, null,null, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}
			
		} catch (Exception e) {
			this.writeJSONResult(-1, null,null, response);
		}
	}
	
	/**
	 * 分页查询系统菜单列表
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月21日下午5:27:23
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getSysMenuListByPage", method=RequestMethod.GET)
	public void getSysMenuListByPage(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> sysMenuListMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				//以下为数据信息
				sysMenuListMap=sysMenuService.getMenuListByPage(getReqParam(request));
				//以下为表单信息
				//菜单类型
				sysMenuListMap.put("sysMenuType", GlobalCache.getDic(GlobalConstants.DIC_SYS_MENU_TYPE));
				//以下为状态信息
				sysMenuListMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				//当前国际化
				sysMenuListMap.put(GlobalConstants.I18N_LOCAL_LANGUAGE, i18NService.getCurrentLanguage());
				
				writerJSON(sysMenuListMap, response);
			}
			noLogin(response);
			String isLoged=request.getParameter("isLoged");
			if ("yes".equals(isLoged)) {
				sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
			}
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}

	/**
	 * 获取全部菜单列表信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月30日下午3:48:30
	 */
	@RequestMapping(value="getSysMenuList", method=RequestMethod.GET)
	public void getSysMenuList(HttpServletRequest request,HttpServletResponse response){
		List<SysMenu> list=new ArrayList<SysMenu>();
		Map<String,Object> map=new HashMap<String, Object>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				//以下为数据信息
				list=sysMenuService.getEnableMenuList(getReqParam(request));
				map.put("sysMenuList", list);
				//以下为状态信息
				map.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(map, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取菜单明细
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月22日下午1:10:30
	 */
	@RequestMapping(value="getMenuDetailByMenuId", method=RequestMethod.GET)
	public void getMenuDetailByMenuId(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> sysMenuDetailMap=new HashMap<>();
		Map<String, String> formMap=new HashMap<>();
		SysUser user = getLoginUser(request);

		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String menuId=request.getParameter("menuId");
			formMap.put("menuId", menuId);
			if(user!=null){
				List<SysMenu> sysMenus = sysMenuService.getMenuList(formMap);
				sysMenuDetailMap.put("menuDetail", sysMenus);
				if (StringUtil.isNotEmpty(sysMenus.get(0).getMenuReportTool())) {
					sysMenuDetailMap.put("reportTool", reportToolService.getReportToolById(sysMenus.get(0).getMenuReportTool()));
				}
				List<ReportMenuParam> reportMenuParams = sysMenuService.getReportMenuParamList4MenuId(menuId);
				sysMenuDetailMap.put("menuReportParam", reportMenuParams);
				List<ReportParam> reportParams = new ArrayList<>();
				for (ReportMenuParam reportMenuParam : reportMenuParams) {
					ReportParam reportParam =reportParamService.getReportParamById(reportMenuParam.getParamId());
					reportParams.add(reportParam);
				}
				sysMenuDetailMap.put("reportParams", reportParams);
				writerJSON(sysMenuDetailMap, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取全部的菜单层级关系
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日下午3:12:27
	 */
	@RequestMapping(value="getMenuHierarchyList",method=RequestMethod.GET)
	public void getMenuHierarchyList(HttpServletRequest request, 
			HttpServletResponse response){
		List<SysMenu> list=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				list=sysMenuService.getMenuHierarchyList();
				map.put("menuHierarchy", list);
				writerJSON(map, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 根据菜单类别获取菜单层级关系信息，仅包括上级菜单
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月22日下午1:14:16
	 */
	@RequestMapping(value="getMenuHierarchyByMenuType", method=RequestMethod.GET)
	public void getMenuHierarchyDirectoryListByMenuType(HttpServletRequest request, 
			HttpServletResponse response){
		List<SysMenu> list=new ArrayList<>();
		Map<String, Object> menuHierarchyMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String menuType=request.getParameter("menuType");
			if (StringUtil.isEmpty(menuType)) menuType="";
			if(user!=null){
				//以下为表单信息
				//上级菜单
				list=sysMenuService.getMenuHierarchyDirectoryListByMenuType(menuType);
				menuHierarchyMap.put("menuParentHierarchy", list);
				//以下为状态信息
				menuHierarchyMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(menuHierarchyMap, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 根据用户的功能角色显示菜单层级关系
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日下午3:13:48
	 */
	@RequestMapping(value="/getMenuHierarchyListByUserId",method=RequestMethod.GET)
	public void getMenuHierarchyListByUserId(HttpServletRequest request, 
			HttpServletResponse response){
		List<SysMenu> list=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			if(user!=null){
				String userId=user.getId();
				list=sysMenuService.getMenuHierarchyListByUserId(userId);
				map.put("userMenu", list);
				writerJSON(map, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}

	@RequestMapping(value="/getDefaultReportMenuByUserId",method=RequestMethod.GET)
	public void getDefaultReportMenuByUserId(HttpServletRequest request,
											 HttpServletResponse response){
		List<SysMenu> list=new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);

		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);

			if(user!=null){
				String userId=user.getId();
				list=sysMenuService.getDefaultReportMenuByUserId(userId);
				map.put("userDefaultReportMenu", list);
				writerJSON(map, response);
			}
			noLogin(response);

			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}


	
	/**
	 * 根据父级菜单ID获取报表展示目录列表
	 * 前端根据报表的类型访问具体的页面信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日下午3:15:37
	 */
	public void getReportHierarchyDirectoryByParentId(HttpServletRequest request, 
			HttpServletResponse response){
		List<SysMenu> list=new ArrayList<SysMenu>();
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			String menuParentId=request.getParameter("menuParentId");
			if(user!=null){
				String userId=user.getId();
				list=sysMenuService.getReportMenuListByParentMenuId(menuParentId,userId);
				map.put("reportDirectory", list);
				writerJSON(map, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取报表集成页面信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日下午3:17:05
	 */
	@RequestMapping(value="/getReportMenuPage",method=RequestMethod.GET)
	public void getReportMenuPage(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			//根据报表菜单ID生成报表所需的相关信息（必要项）
			String menuId=request.getParameter("menuId");
			if (StringUtil.isEmpty(menuId)) menuId="";
			if(user!=null){
				//获取报表路径信息
				String userId=user.getId();
				map=bIService.getReportSSO(menuId, userId, request);
                String str =map.get("reqLocation")+user.getLoginName();
                map.put("reqLocation",str);
                writerJSON(map, response);
				System.out.println("reqLocation:"+map.get("reqLocation"));
			}
			noLogin(response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取其它系统集成页面信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日下午3:18:04
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/getOtherMenuPage",method=RequestMethod.GET)
	public void getOtherMenuPage(HttpServletRequest request, 
			HttpServletResponse response){
		String menuUrl="";
		SysMenu sysMenu=new SysMenu();
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			String menuId=request.getParameter("menuId");
			if (StringUtil.isEmpty(menuId)) menuId="";
			if(user!=null){
				//获取菜单的地址信息
				sysMenu=sysMenuService.getMenuById(menuId);
				if (menuId!=null){
					menuUrl=sysMenu.getMenuUrl();
				}
				//生成加密信息
				String loginUser=user.getLoginName();
				String loginPass=request.getSession().getAttribute(GlobalConstants.SESSION_USER_PASS).toString();
				String token=loginUser+"&"+UUID.randomUUID().toString().substring(0,10)+"&"+loginPass;
				menuUrl=menuUrl+"?token="+DESEncode.getInstance().Encrypt(token);
				map.put(GlobalConstants.REPORT_REQ_LOCATION, menuUrl);
				writerJSON(map, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 根据菜单ID获取报表菜单参数
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月23日上午10:46:55
	 */
	@RequestMapping(value="getReportMenuParamListByMenuId", method=RequestMethod.GET)
	public void getReportMenuParamListByMenuId(HttpServletRequest request, 
			HttpServletResponse response){
		Map<String, Object> reportMenuMap=new HashMap<>();
		SysUser user = getLoginUser(request);
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String menuId=request.getParameter("menuId");
			if (StringUtil.isEmpty(menuId)) menuId="";
			if(user!=null){
				//以下为表单信息
				//报表菜单参数
				reportMenuMap.put("reportParamList",reportParamService.getReportParamList());
				reportMenuMap.put("reportMenuParamList",sysMenuService.getReportMenuParamListByMenuId(menuId));
				//以下为状态信息
				reportMenuMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(reportMenuMap, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 通过父级菜单获取菜单
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月10日下午2:48:20
	 */
	@RequestMapping(value="getSysMenu4MenuParentId", method=RequestMethod.GET)
	public void getSysMenu4MenuParentId(HttpServletRequest request, 
			HttpServletResponse response){
		Map<String, Object> map=new HashMap<>();
		SysUser user = getLoginUser(request);
		Map<String, Object> reportMenuMap=new HashMap<>();
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			String menuParentId=request.getParameter("menuParentId");
			if(user!=null){
				//以下为表单信息
				map.put("menuId", menuParentId);
				reportMenuMap.put("sysMenuList",sysMenuService.getMenuList(map));
				//以下为状态信息
				reportMenuMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
				
				writerJSON(reportMenuMap, response);
			}
			noLogin(response);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取报表目录列表数据
	 * @param request
	 * @param response
	 * likezhuang
	 * 2016年5月10日下午2:45:59
	 */
	@RequestMapping(value="reportFolders",method=RequestMethod.GET)
	public void getReportFolders(HttpServletRequest request,HttpServletResponse response){
		String menuParentId = getParameter(request, "menuId");
		SysUser user = getLoginUser(request);
		
		try {
			if(user==null){
				writerJSON("[]", response);
				return;
			}else {
				List<Map<String, Object>> list = sysMenuService.getReportFolders(menuParentId, user.getId());
				writerJSON(list, response);
			}
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}

	}
	
	/**
	 * 获取当前请求的参数信息
	 * @param request
	 * @return
	 * Boger
	 * 2016年3月21日下午2:13:56
	 */
	private Map<String, Object> getReqParam(HttpServletRequest request) throws Exception {
		Map<String, Object> formMap=new HashMap<>();
		
		//菜单id
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)){
			formMap.put("id", id);
		}
		//菜单代码
		String code=request.getParameter("code");
		if (StringUtil.isNotEmpty(code)){
			formMap.put("code", code);
		}
		//菜单名称
		String menuName=request.getParameter("menuName");
		if (StringUtil.isNotEmpty(menuName)){
			//中文乱码转换
			menuName = new String(menuName.getBytes("iso8859-1"),"UTF-8");
			formMap.put("menuName", menuName);
		}
		//中文名称
		String menuNameZh=request.getParameter("menuNameZh");
		if (StringUtil.isNotEmpty(menuNameZh)){
			formMap.put("menuNameZh", menuNameZh);
		}
		//英文名称
		String menuNameEn=request.getParameter("menuNameEn");
		if (StringUtil.isNotEmpty(menuNameEn)){
			formMap.put("menuNameEn", menuNameEn);
		}
		//上级菜单
		String menuParentId=request.getParameter("menuParentId");
		if (StringUtil.isNotEmpty(menuParentId)){
			formMap.put("parentMenuId", menuParentId);
		}
		//菜单状态
		String menuStatus=request.getParameter("menuStatus");
		if (StringUtil.isNotEmpty(menuStatus)){
			formMap.put("menuStatus", menuStatus);
		}
		//菜单类型
		String menuType=request.getParameter("menuType");
		if (StringUtil.isNotEmpty(menuType)){
			formMap.put("menuType", menuType);
		}
		//菜单类型
		String notMenuType=request.getParameter("notMenuType");
		if (StringUtil.isNotEmpty(notMenuType)){
			formMap.put("notMenuType", notMenuType);
		}
		//菜单属性
		String menuAttribute=request.getParameter("menuAttribute");
		if (StringUtil.isNotEmpty(menuAttribute)){
			formMap.put("menuAttribute", menuAttribute);
		}
		//创建日期
		String beginCreateTime=request.getParameter("beginCreateTime");
		if (StringUtil.isNotEmpty(beginCreateTime)){
			formMap.put("beginCreateTime", beginCreateTime);
		}
		String endCreateTime=request.getParameter("endCreateTime");
		if (StringUtil.isNotEmpty(endCreateTime)){
			formMap.put("endCreateTime", endCreateTime);
		}
		//当前页
		String currentPage = getParameter(request, GlobalConstants.PAGE_CURRENT_PAGE);
		if (StringUtil.isNotEmpty(currentPage)){
			formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, currentPage);
		}else {
			formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, GlobalConstants.PAGE_START);
		}
		//页面数据限制
		String pageSize = getParameter(request, GlobalConstants.PAGE_PAGE_SIZE);
		if (StringUtil.isNotEmpty(pageSize)){
			formMap.put(GlobalConstants.PAGE_PAGE_SIZE, pageSize);
		}else {
			formMap.put(GlobalConstants.PAGE_PAGE_SIZE, GlobalConstants.PAGE_SIZE);
		}
		
		return formMap;
	}
	
	/**
	 * 根据请求获取菜单信息
	 * @param request
	 * @return
	 * Boger
	 * 2016年3月21日下午6:21:07
	 */
	private SysMenu getSysMenu(HttpServletRequest request,SysMenu sysMenu) {
		//菜单名称
		String menuName=request.getParameter("menuName");
		sysMenu.setMenuName(menuName);
		//菜单顺序
		String menuOrder=request.getParameter("menuOrder");
		sysMenu.setMenuOrder(Integer.valueOf(menuOrder));
		//中文名称
		String menuNameZh=request.getParameter("menuNameZh");
		sysMenu.setMenuNameZh(menuNameZh);
		//英文名称
		String menuNameEn=request.getParameter("menuNameEn");
		sysMenu.setMenuNameEn(menuNameEn);

		//Jet for 当菜单为顶级菜单时添加菜单图标
		if(request.getParameter("menuParentId")==null){
			String menuIcon="fa fa-area-chart";
			sysMenu.setMenuIcon(menuIcon);
		}
		//上级菜单
		String menuParentId=request.getParameter("menuParentId");
		sysMenu.setMenuParentId(menuParentId);
		//菜单状态
		String menuStatus=request.getParameter("menuStatus");
		sysMenu.setMenuStatus(menuStatus);
		//菜单类型
		String menuType=request.getParameter("menuType");
		sysMenu.setMenuType(menuType);
		//菜单地址
		String menuUrl=request.getParameter("menuUrl");
		sysMenu.setMenuUrl(menuUrl);
		//菜单属性
		String menuAttribute=request.getParameter("menuAttribute");
		sysMenu.setMenuAttribute(menuAttribute);
		//关联报表工具
		String menuReportTool=request.getParameter("menuReportTool");
		sysMenu.setMenuReportTool(menuReportTool);
		
		return sysMenu;
	}

}
