/**
 * 系统菜单
 */
package com.analytic.portal.module.system.controller;

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
import com.analytic.portal.module.report.model.ReportRefreshParam;
import com.analytic.portal.module.report.service.interfaces.BIService;
import com.analytic.portal.module.report.service.interfaces.ReportParamService;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.model.SysUserParamSetting;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysUserPersonalizedSettingService;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户个性化设置控制器
 * @author Boger
 */
@Controller
@RequestMapping("/sysUserPersonalizedSetting")
public class SysUserPersonalizedSettingController extends BaseController {
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysUserPersonalizedSettingService sysUserPersonalizedSettingService;
	@Resource
	private ReportToolService reportToolService;
	@Resource
	private ReportParamService reportParamService;
	@Resource
	private I18NService i18NService;
	@Resource
	private BIService bIService;

    /**
     * 分页查询用户角色相关报表列表
     * @param request
     * @param response
     * Boger
     * 2016年3月21日下午5:27:23
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="getReportSettingListByUserId", method=RequestMethod.GET)
    public void getReportSettingListByUserId(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> sysMenuListMap=new HashMap<>();
        SysUser user = getLoginUser(request);

        try {
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
            String settingType=request.getParameter("settingType");
            if(user!=null){
                //以下为数据信息
                sysMenuListMap=sysUserPersonalizedSettingService.getMenuSettingListByUserId(getReportSettingReqParam(request),user.getId(),settingType);
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
            /*String isLoged=request.getParameter("isLoged");
            if ("yes".equals(isLoged)) {
                sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
            }
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_END);*/
        } catch (Exception e) {
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
    }


    /**
     * 确认设置
     * @param request
     * @param response
     * Boger
     * 2016年3月21日下午5:22:56
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="confirmSettingReport", method=RequestMethod.POST)
    public void confirmSettingReport(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> confirmSettingReportMap=new HashMap<>();
        SysUser user = getLoginUser(request);
        try {
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
            String menuId=request.getParameter("confirmmenuId");
            String settingType=request.getParameter("settingType");
            if(user!=null){
                //以下为数据信息
                confirmSettingReportMap=sysUserPersonalizedSettingService.addSetting(user.getId(),menuId,settingType);
                //以下为状态信息
                confirmSettingReportMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
                writerJSON(confirmSettingReportMap, response);
            }
            noLogin(response);

            /*sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_END);*/
        } catch (Exception e) {
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
    }

    /**
     * 取消设置
     * @param request
     * @param response
     * Boger
     * 2016年3月21日下午5:22:56
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="cancelSettingReport", method=RequestMethod.POST)
    public void cancelSettingReport(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> confirmSettingReportMap=new HashMap<>();
        SysUser user = getLoginUser(request);
        try {
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
            String menuId=request.getParameter("cancelmenuId");
            String settingType=request.getParameter("settingType");
            if(user!=null){
                //以下为数据信息
                confirmSettingReportMap=sysUserPersonalizedSettingService.deleteSetting(user.getId(),menuId,settingType);
                //以下为状态信息
                confirmSettingReportMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
                writerJSON(confirmSettingReportMap, response);
            }
            noLogin(response);

            /*sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_END);*/
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
    private Map<String, Object> getReportSettingReqParam(HttpServletRequest request) throws Exception {
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
     * 获取分页的的报表参数集合
     * @throws Exception
     * pengbo
     * 2016年3月11日下午1:58:59
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/getReportParamListByPage",method=RequestMethod.GET)
    public void getReportParamListByPage(HttpServletRequest request ,HttpServletResponse response) throws Exception{
        int status=GlobalConstants.SUCCESS_STATE;
        Map map = new  HashMap();
        SysUser user = getLoginUser(request);
        try {
//            map=reportParamService.getReportParamListByPage(getParamterSettingReqParam(request));
            Map<String, Object> formMap=getParamterSettingReqParam(request);
            formMap.put("userId",user.getId());
            map=sysUserPersonalizedSettingService.getUserReportParamSettingListByPage(formMap);
            /*String isLoged=request.getParameter("isLoged");
            if ("yes".equals(isLoged)) {
                sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
            }*/
        } catch (Exception e) {
            status=GlobalConstants.FAILURE_STATE;
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
        response.setStatus(status);
        writerJSON(map, response);
    }

    /*
	 * 获取当前请求的参数信息
	 * @param request
	 * @return
	 * pengbo
	 * 2016年3月21日下午2:13:56
	 */
    private Map<String, Object> getParamterSettingReqParam(HttpServletRequest request) throws Exception {
        Map<String, Object> formMap=new HashMap<>();

        //参数ID
        String id=request.getParameter("id");
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(id)){
            formMap.put("id", id);
        }
        //参数CODE
        String code=request.getParameter("code");
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(code)){
            formMap.put("code", code);
        }
        //参数名称
        String paramName=request.getParameter("reportParamName");
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(paramName)){
            //中文乱码转换
            paramName = new String(paramName.getBytes("iso8859-1"),"UTF-8");
            formMap.put("paramName", paramName);
        }
        //参数key
        String paramKey=request.getParameter("reportParamKey");
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(paramKey)){
            formMap.put("paramKey", paramKey);
        }
        //参数排序
        String paramOrder=request.getParameter("paramOrder");
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(paramOrder)){
            formMap.put("paramOrder", paramOrder);
        }
        //当前页
        String currentPage = getParameter(request, GlobalConstants.PAGE_CURRENT_PAGE);
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(currentPage)){
            formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, currentPage);
        }else {
            formMap.put(GlobalConstants.PAGE_CURRENT_PAGE, GlobalConstants.PAGE_START);
        }
        //页面数据限制
        String pageSize = getParameter(request, GlobalConstants.PAGE_PAGE_SIZE);
        if (com.yzd.plat.common.util.StringUtil.isNotEmpty(pageSize)){
            formMap.put(GlobalConstants.PAGE_PAGE_SIZE, pageSize);
        }else {
            formMap.put(GlobalConstants.PAGE_PAGE_SIZE, GlobalConstants.PAGE_SIZE);
        }

        return formMap;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/getGlobalParamOfUserList",method=RequestMethod.GET)
    public void getGlobalParamOfUserList(HttpServletRequest request ,HttpServletResponse response) throws Exception{
        int status=GlobalConstants.SUCCESS_STATE;
        Map map = new  HashMap();
        SysUser user = getLoginUser(request);
        try {
//            map=reportParamService.getReportParamListByPage(getParamterSettingReqParam(request));
            Map<String, Object> formMap=getParamterSettingReqParam(request);
            formMap.put("userId",user.getId());

            String menuId=request.getParameter("menuId");
            List<ReportRefreshParam> resultReportRefreshParamList=sysUserPersonalizedSettingService.getGlobalParamOfUserList(formMap,menuId);
            map.put("result",resultReportRefreshParamList);
            /*String isLoged=request.getParameter("isLoged");
            if ("yes".equals(isLoged)) {
                sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
            }*/
        } catch (Exception e) {
            status=GlobalConstants.FAILURE_STATE;
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
        response.setStatus(status);
        writerJSON(map, response);
    }

    @RequestMapping(value="/confirmRefreshParamSetting",method=RequestMethod.POST)
    public void confirmRefreshParamSetting(ReportRefreshParam reportRefreshParam, HttpServletRequest request ,HttpServletResponse response) throws Exception {
        Map<String, Object> confirmSettingReportMap=new HashMap<>();
        SysUser user = getLoginUser(request);
        try {
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
            String menuId=request.getParameter("confirmmenuId");
            if(user!=null){
                //以下为数据信息

                confirmSettingReportMap=sysUserPersonalizedSettingService.updateRefreshParamSetting(user.getId(),reportRefreshParam);
                //以下为状态信息
                confirmSettingReportMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
                writerJSON(confirmSettingReportMap, response);
            }
            noLogin(response);

            /*sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_END);*/
        } catch (Exception e) {
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
    }

    @RequestMapping(value="/confirmGlobalRefreshParamSetting",method=RequestMethod.POST)
    public void confirmGlobalRefreshParamSetting( HttpServletRequest request ,HttpServletResponse response) throws Exception {
        Map<String, Object> confirmSettingReportMap=new HashMap<>();
        SysUser user = getLoginUser(request);
        try {
            String strSelectRefreshParamMap=request.getParameter("selectRefreshParamMap");
            JSONObject jb = JSONObject.fromObject(strSelectRefreshParamMap);
            Map<String, Object> selectRefreshParamMap=(Map)jb;
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
            if(user!=null){
                //以下为数据信息
                confirmSettingReportMap=sysUserPersonalizedSettingService.updateGlobalRefreshParamSetting(user.getId(),selectRefreshParamMap);
                //以下为状态信息
                confirmSettingReportMap.put(GlobalConstants.RESPONSE_CODE, GlobalConstants.SUCCESS_STATE);
                writerJSON(confirmSettingReportMap, response);
            }
            noLogin(response);

            /*sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
            LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO,
                    GlobalConstants.lOGGER_LEVEL_INFO_END);*/
        } catch (Exception e) {
            LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
        }
    }
}
