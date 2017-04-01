/**
 * 系统菜单
 */
package com.analytic.portal.module.system.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.analytic.portal.module.report.model.*;
import com.analytic.portal.module.system.model.SysUserReportSetting;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.service.IEncodingService;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.dao.interfaces.SysMenuDao;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleService;
import com.analytic.portal.module.system.service.interfaces.SysUserService;
import com.yzd.plat.common.util.DateUtil;

/**
 * 系统菜单定义
 *
 * @author Boger
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private IBaseDao iBaseDao;
    @Resource
    private IEncodingService iEncodingService;
    @Resource
    private SysMenuDao sysMenuDao;
    @Resource
    private ReportToolService reportToolService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 保存菜单信息
     *
     * @param sysMenu
     * @throws Exception Boger
     *                   2016年3月21日上午11:33:42
     */
    @Override
    public synchronized void saveMenu(SysMenu sysMenu, String strReportMenuParam)
            throws Exception {

        try {
            //生成ID号
            sysMenu.setId(DataOperation.getSequenseStr());
            //删除标识
            sysMenu.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
            //生成Code序列号，控制序列号的并发性
            String code = GlobalConstants.BUSINESS_ENCODING_MENU_SYSTEM;
            code += iEncodingService.getBussinessCode(SysMenu.class);
            sysMenu.setCode(code);
            if (StringUtil.isEmpty(sysMenu.getMenuParentId())) {
                sysMenu.setMenuParentId("");
            }
            //处理报表菜单对应的参数信息
//			if (StringUtil.isNotEmpty(sysMenu.getMenuAttribute())&&sysMenu.getMenuAttribute().equals(
//					GlobalCache.getDic(GlobalConstants.DIC_REPORT_MENU_TYPE).
//					get(GlobalConstants.REPORT_MENU_TYPE_REPORT))){
            if ("1".equals(sysMenu.getMenuAttribute())) {
                if (StringUtil.isNotEmpty(strReportMenuParam)) {
                    saveReportMenuParamByMenuId(sysMenu.getId(), strReportMenuParam);
                }
            }
            iBaseDao.save(sysMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据菜单ID删除菜单信息
     *
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:33:58
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Map deleteMenu(String menuId) throws Exception {
        Map map = new HashMap<>();
        SysMenu sysMenu = new SysMenu();
        boolean deleteFlag = true;

        try {
            // 1.检测该菜单是否存在
            sysMenu = iBaseDao.getObjById(SysMenu.class, menuId);
//			if (sysMenu==null){
//				deleteFlag=false;
//				map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_REMOVE);
//			}
//			// 2.检测该菜单是否被使用
//			if (deleteFlag==true){
//				//检测菜单是否被功能角色分配
//				List<SysRoleMenu> sysRoleMenuList=new ArrayList<>();
//				sysRoleMenuList=sysRoleService.getSysRoleMenuByMenuId(menuId);
//				if (sysRoleMenuList.size()>0){
//					deleteFlag=false;
//					map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_USING);
//				}
//			}
//			// 3.检测操作权限
//			if (sysMenu.getMenuType().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_MENU_TYPE).
//					get(GlobalConstants.SYS_MENU_TYPE_REPORT))){
//				deleteFlag=false;
//				map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_FORBID);
//			}
            // 4.删除菜单
            if (deleteFlag == true) {
                //4.1删除菜单的关联信息，包括菜单参数信息
                deleteReportMenuParamByMenuId(menuId);
                //4.2删除菜单信息
                sysMenu.setIsDelete(GlobalConstants.DATA_STATUS_DELETE);
                iBaseDao.update(sysMenu);
                map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_SUCCESS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenu
     * @throws Exception Boger
     *                   2016年3月21日上午11:35:41
     */
    @Override
    public void updateMenu(SysMenu sysMenu) throws Exception {
        String menuId = sysMenu.getId();

        iBaseDao.update(sysMenu);
        //更新报表菜单参数信息
        if (sysMenu.getMenuType().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_MENU_TYPE).
                get(GlobalConstants.SYS_MENU_TYPE_REPORT).toString())) {
            updateReportMenuParamByMenuId(menuId, sysMenu.getReportMenuParam());
        }
    }

    /**
     * 根据菜单ID获取菜单信息
     *
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月16日下午6:06:10
     */
    @Override
    public SysMenu getMenuById(String menuId) throws Exception {
        SysMenu sysMenu = null;
        sysMenu = iBaseDao.getObjById(SysMenu.class, menuId);
        return sysMenu;
    }

    /**
     * 分页查询获取菜单列表
     *
     * @param formMap
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:37:25
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getMenuListByPage(Map formMap) throws Exception {
        return sysMenuDao.getMenuListByPage(formMap);
    }

    /**
     * 根据条件获取菜单列表信息
     *
     * @param formMap
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:37:48
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<SysMenu> getMenuList(Map formMap) throws Exception {
        return sysMenuDao.getMenuList(formMap);
    }

    /**
     * 根据用户ID获取菜单层级关系,2层菜单信息
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:38:25
     */
    @Override
    public List<SysMenu> getMenuHierarchyListByUserId(String userId)
            throws Exception {
        List<SysMenu> list = new ArrayList<SysMenu>();
        List<SysMenu> menuTreeList = new ArrayList<>();

        list = sysMenuDao.getMenuHierarchyListByUserId(userId);
        menuTreeList = getMenuTreeList(list);
        /*新增常用，默认报表*/
        List<SysMenu> commonMenuList=sysMenuDao.getPersonalSettingMenuHierarchyListByUserId(userId,"01");
        List<SysMenu> defaultMenuList=sysMenuDao.getPersonalSettingMenuHierarchyListByUserId(userId,"02");
        for (SysMenu item:menuTreeList){
            if ("Common report".equals(item.getMenuNameEn())&&item.getMenuChild()==null){
                item.setMenuChild(commonMenuList);
            }else if ("Default report".equals(item.getMenuNameEn())&&item.getMenuChild()==null){
                item.setMenuChild(defaultMenuList);
            }
        }

        Collections.sort(menuTreeList, new Comparator<SysMenu>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(SysMenu o1, SysMenu o2) {
                if(o1.getMenuOrder() > o2.getMenuOrder()){
                    return 1;
                }
                if(o1.getMenuOrder() == o2.getMenuOrder()){
                    return 0;
                }
                return -1;
            }
        });
        return menuTreeList;
    }

    @Override
    public List<SysMenu> getDefaultReportMenuByUserId(String userId)
            throws Exception {
        List<SysMenu> defaultMenuList=sysMenuDao.getPersonalSettingMenuHierarchyListByUserId(userId,"02");
        return defaultMenuList;
    }


    /**
     * 根据菜单类别获取菜单目录层级关系
     *
     * @return
     * @throws Exception Boger
     *                   2016年3月21日下午4:48:51
     */
    @Override
    public List<SysMenu> getMenuHierarchyDirectoryListByMenuType(String menuType)
            throws Exception {
        List<SysMenu> list = new ArrayList<SysMenu>();
        List<SysMenu> menuTreeList = new ArrayList<>();

        list = sysMenuDao.getMenuHierarchyDirectoryListByMenuType(menuType);
        menuTreeList = getMenuTreeList(list);

        return menuTreeList;
    }

    /**
     * 获取全部的报表菜单层级关系
     *
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:51:18
     */
    @Override
    public List<SysMenu> getMenuHierarchyList() throws Exception {
        List<SysMenu> list = new ArrayList<SysMenu>();
        List<SysMenu> menuTreeList = new ArrayList<>();

        list = sysMenuDao.getMenuList(new HashMap<>());
        menuTreeList = getMenuTreeList(list);

        return menuTreeList;
    }

    /**
     * 根据父级菜单ID获取报表菜单列表
     *
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:50:35
     */
    @Override
    public List<SysMenu> getReportMenuListByParentMenuId(String menuParentId, String userId)
            throws Exception {
        return sysMenuDao.getReportMenuListByParentMenuId(menuParentId, userId);
    }

    /**
     * 根据菜单ID获取菜单参数列表
     *
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月22日上午11:45:45
     */
    @Override
    public List<ReportMenuParam> getReportMenuParamListByMenuId(String menuId) throws Exception {
        return sysMenuDao.getReportMenuParamListByMenuId(menuId);
    }

    /**
     * 根据菜单ID获取菜单参数列表
     *
     * @param menuId
     * @return
     * @throws Exception xufenglei
     *                   2016年4月12日
     */
    @Override
    public List<ReportMenuParam> getReportMenuParamList4MenuId(String menuId) throws Exception {
        return sysMenuDao.getReportMenuParamList4MenuId(menuId);
    }

    /**
     * 更新报表地址信息
     *
     * @param request
     * @throws Exception Boger
     *                   2016年3月18日下午3:23:00
     */
    @Override
    public void updateReportMenuUrl(HttpServletRequest request) throws Exception {
        String userId = "";
        HttpSession session = request.getSession();
        List<SysMenu> reportMenuList = new ArrayList<SysMenu>();
//        List<SysRoleParam> userParamList = new ArrayList<SysRoleParam>();
        List<ReportRefreshParam> userParamList = new ArrayList<ReportRefreshParam>();

        try {
            //获取当前用户信息,保存用户的ID号
            SysUser user = (SysUser) session.getAttribute(GlobalConstants.SESSION_USER);
            userId = user.getId();
            //根据用户选择的数据角色获取角色参数信息
//            userParamList = sysRoleService.getSysRoleParamByRoleId(user.getDataRoleId());
            userParamList = sysRoleService.getSysRoleParamAndRefreshParamByUserId(user.getId());
            //获取用户报表菜单信息
            /*modified*/
//            reportMenuList = getReportMenuListByUserId(userId);
            String menuIdTemp=request.getParameter("menuId");
            reportMenuList = getReportMenuListByMenuId(menuIdTemp);
            //清除当前用户的报表地址暂存表数据
//            deleteReportMenuUrlByUserId(userId);
            deleteReportMenuUrlByUserIdAndMenuId(userId,menuIdTemp);
            //更新用户的报表地址信息
            for (SysMenu sysMenu : reportMenuList) {
                //1.获取菜单ID
                String menuId = sysMenu.getId();
                //2.获取报表工具地址
                String reportToolId = sysMenu.getMenuReportTool();
                ReportTool reportTool = reportToolService.getReportToolById(reportToolId);
                String reportToolUrl = reportTool.getReportToolUrl();
                //3.获取用户的参数信息，根据用户选取的菜单参数信息及用户的数据角色关系获取报表菜单对应的参数列表链
                //3.1获取菜单对应的参数映射关系
                List<ReportMenuParam> paramList = new ArrayList<ReportMenuParam>();
                paramList = getReportMenuParamListByMenuId(menuId);
                Map<String, String> map = new HashMap<>();
                for (ReportMenuParam reportMenuParam : paramList) {
                    String strParamId = reportMenuParam.getParamId();
                    String strMenuId = reportMenuParam.getMenuId();
                    map.put(strParamId, strMenuId);
                }
                //3.2建立参数键值链
                String paramChain = "";
                if (!map.isEmpty()) {
//                    for (SysRoleParam sysRoleParam : userParamList) {
                    for (ReportRefreshParam sysRoleParam : userParamList) {
//                        String paramId = sysRoleParam.getParamId();
                        String paramId = sysRoleParam.getId();
                        if (map.containsKey(paramId)) {
                            /*String paramKey = iBaseDao.getObjById(ReportParam.class, paramId).
                                    getParamKey().toString();*/
                            String paramKey = sysRoleParam.getParamKey();
                            String paramValue = "";
                            //区分是使用内置对象，还是使用输入的值
                            /*if (sysRoleParam.getParamType().equals(GlobalConstants.PARAM_VALUE_INPUT)) {
                                paramValue = sysRoleParam.getParamValue();
                            }
                            if (sysRoleParam.getParamType().equals(GlobalConstants.PARAM_VALUE_SELECT)) {
                                SysUser sysUser = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
                                paramValue = sysUser.getLoginName();
                            }*/
                            if (sysRoleParam.getParamType().equals(GlobalConstants.PARAM_VALUE_SELECT)) {
                                SysUser sysUser = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
                                paramValue = sysUser.getLoginName();
                            }else{
                                /*根据不同的报表平台类型，拼接所需参数格式*/
                                Map<String, String> toolMap=GlobalCache.getDic(GlobalConstants.DIC_REPORT_TOOL_CATEGORY);
                                if (toolMap.get(GlobalConstants.REPORT_TOOL_CATEGORY_TABLEAU).toString().
                                        equals(reportTool.getReportToolType())){
                                    //Tableau工具地址
                                    /*Tableau多值之间用逗号分隔，无需畜类*/
                                    if (sysRoleParam.getRefreshValue()!=null&&!"".equals(sysRoleParam.getRefreshValue().trim())){
                                        paramValue=sysRoleParam.getRefreshValue();
                                    }else{
                                        paramValue=sysRoleParam.getParamValue();
                                        //对日期做单独处理选择最晚的一个日期，不得大于当前日期
                                    	if("YM".equals(paramKey)){
                                    		if(StringUtils.isNotBlank(paramValue)){
                                    			paramValue=this.getMaxMonth(paramValue);
                                    		}
                                    	}
                                    }
                                }else  if (toolMap.get(GlobalConstants.REPORT_TOOL_CATEGORY_BO).toString().
                                            equals(reportTool.getReportToolType())){
                                    //BO工具地址
                                    if (sysRoleParam.getRefreshValue()!=null&&!"".equals(sysRoleParam.getRefreshValue().trim())){
                                        if (sysRoleParam.getRefreshValue().split(",").length>1){
                                            paramValue=sysRoleParam.getRefreshValue().replace(",",";");
                                        }else{
                                            paramValue=sysRoleParam.getRefreshValue();
                                        }
                                    }else{
                                    	paramValue=sysRoleParam.getParamValue();
                                    	//对日期做单独处理选择最晚的一个日期，不得大于当前日期
                                    	if("YM".equals(paramKey)){
                                    		if(StringUtils.isNotBlank(paramValue)){
                                    			paramValue=this.getMaxMonth(paramValue);
                                    		}
                                    	}
                                    }
                                }else if (toolMap.get(GlobalConstants.REPORT_TOOL_CATEGORY_QV).toString().
                                        equals(reportTool.getReportToolType())){
                                    //QlikView工具地址
                                    paramValue=sysRoleParam.getParamValue();
                                }
                            }
                                if (StringUtil.isEmpty(paramChain)) {
                                paramChain += "lsS"+paramKey + "=" + paramValue;
                            } else {
                                paramChain += "&";
                                paramChain += "lsS"+paramKey + "=" + paramValue;
                            }
                        }
                    }
                }
                //报表地址组装
                //报表工具地址
                String reportUrl = "";
                reportUrl += reportToolUrl;
                //请求标识（主要用于标识当前的请求由本系统发出，无业务意义）
                reportUrl += "?reqSign=" + GlobalConstants.REPORT_REQ_SIGN;
                //请求参数
                reportUrl += StringUtil.isNotEmpty(paramChain) ? "&" + paramChain : "";
                //更新报表地址信息暂存
                ReportMenuUrl reportMenuUrl = new ReportMenuUrl();
                reportMenuUrl.setId(DataOperation.getSequenseStr());
                reportMenuUrl.setUserId(userId);
                reportMenuUrl.setMenuId(menuId);
                reportMenuUrl.setReportUrl(reportUrl);
                System.out.println("reportUrl="+reportUrl);
                iBaseDao.save(reportMenuUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @decription 获取最大的日期，不能大于当前日期
     * @param months
     * @return
     */
    private String getMaxMonth(String months){
    	String[] valueArray=months.split(",");
		String max="";
		String now=DateUtil.formatDate(new Date(), "yyyyMM");
		for(String s:valueArray){
			if(max.compareTo(s)<0&&now.compareTo(s)>=0){
				max=s;
			}
		}
		return max;
    }


    /**
     * 获取集成报表的有效访问地址
     *
     * @param userId
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月25日下午3:51:58
     */
    @Override
    public ReportMenuUrl getReportMenuUrl(String userId, String menuId) throws Exception {
        return sysMenuDao.getReportMenuUrl(userId, menuId);
    }

    /**
     * 获取树状菜单列表
     *
     * @param list
     * @return Boger
     * 2016年3月28日上午10:18:26
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> list) {
        List<SysMenu> menuTreeList = new ArrayList<SysMenu>();

        if (list.size() > 0) {
            for (SysMenu sysMenu : list) {
                //控制菜单启用禁用
                if (sysMenu.getMenuStatus().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_STAUTS).
                        get(GlobalConstants.SYS_OPER_STATUS_DISABLE))) {
                    continue;
                }
                List<SysMenu> menuChild = null;
                String menuParentId = sysMenu.getMenuParentId();
                //获取菜单条（以父级菜单空字符串为标识）
                if (StringUtil.isEmpty(menuParentId)) {
                    String id = sysMenu.getId();
                    //获取子菜单
                    menuChild = getChildMenuList(id, list);
                    if (menuChild.size() > 0) {
                        sysMenu.setMenuChild(menuChild);
                    }
                    menuTreeList.add(sysMenu);
                }
            }
        }

        return menuTreeList;
    }

    /**
     * 递归获取当前菜单ID的子菜单
     *
     * @param id
     * @param list
     * @return Boger
     * 2016年3月28日上午10:30:52
     */
    private List<SysMenu> getChildMenuList(String id, List<SysMenu> list) {
        List<SysMenu> menuTreeList = new ArrayList<SysMenu>();

        for (SysMenu sysMenu : list) {
            //控制菜单启用禁用
            if (sysMenu.getMenuStatus().equals(GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_STAUTS).
                    get(GlobalConstants.SYS_OPER_STATUS_DISABLE))) {
                continue;
            }
            String menuParentId = sysMenu.getMenuParentId();
            if (id.equals(menuParentId)) {
                String childId = sysMenu.getId();
                List<SysMenu> menuChild = getChildMenuList(childId, list);
                if (menuChild.size() > 0) {
                    sysMenu.setMenuChild(menuChild);
                }
                menuTreeList.add(sysMenu);
            }
        }

        return menuTreeList;
    }

    /**
     * 根据用户ID查询报表菜单信息
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年3月18日下午4:41:54
     */
    private List<SysMenu> getReportMenuListByUserId(String userId) throws Exception {
        return sysMenuDao.getReportMenuByUserId(userId);
    }

    private List<SysMenu> getReportMenuListByMenuId(String menuId) throws Exception {
        return sysMenuDao.getReportMenuByMenuId(menuId);
    }

    /**
     * 删除当前用户的报表地址信息
     *
     * @param userId
     * @return Boger
     * 2016年3月18日下午4:19:29
     */
    @Override
    public void deleteReportMenuUrlByUserId(String userId) throws Exception {
        sysMenuDao.deleteReportMenuUrlByUserId(userId);
    }

    private void deleteReportMenuUrlByUserIdAndMenuId(String userId,String menuId) throws Exception {
        sysMenuDao.deleteReportMenuUrlByUserIdAndMenuId(userId,menuId);
    }

    /**
     * 根据菜单ID保存菜单参数信息
     *
     * @param menuId
     * @param reportMenuParamArray
     * @throws Exception Boger
     *                   2016年3月21日上午11:51:42
     */
    private void saveReportMenuParamByMenuId(String menuId, String strReportMenuParam)
            throws Exception {
        String[] reportMenuParamArray = strReportMenuParam.split(",");

        //字符数组遍历为菜单参数关系表增加数据
        for (int i = 0; i < reportMenuParamArray.length; i++) {
            ReportMenuParam reportMenuParam = new ReportMenuParam();
            reportMenuParam.setId(DataOperation.getSequenseStr());
            reportMenuParam.setMenuId(menuId);
            reportMenuParam.setParamId(reportMenuParamArray[i]);
            iBaseDao.save(reportMenuParam);
        }
    }

    /**
     * 根据菜单ID删除报表菜单参数
     *
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:53:25
     */
    private void deleteReportMenuParamByMenuId(String menuId)
            throws Exception {
        sysMenuDao.deleteReportMenuParamByMenuId(menuId);
    }

    /**
     * 更新报表菜单参数信息
     *
     * @param menuId
     * @return
     * @throws Exception Boger
     *                   2016年3月22日下午4:36:42
     */
    private void updateReportMenuParamByMenuId(String menuId, String reportMenuParam)
            throws Exception {
        //更新，通过先删除后新增
        deleteReportMenuParamByMenuId(menuId);
        if (StringUtil.isNotEmpty(reportMenuParam)) {
            saveReportMenuParamByMenuId(menuId, reportMenuParam);
        }
    }

    @Override
    public List<Map<String, Object>> getReportFolders(String menuParentId, String userId) {
        SysMenu menu = iBaseDao.getObjById(SysMenu.class, menuParentId);
        List<SysMenu> list = null;
        if (menu != null && "1".equals(menu.getMenuStatus()) && "0".equals(menu.getIsDelete())) {
            list = sysMenuDao.getReportFolders(userId);
        }
        return getChilds(menuParentId, list);
    }

    private List<Map<String, Object>> getChilds(String id, List<SysMenu> list) {
        if (list == null) {
            return new ArrayList<Map<String, Object>>();
        }
        List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
        for (SysMenu sysMenu : list) {
            Map<String, Object> menu = null;
            String parent_id = sysMenu.getMenuParentId();
            if (id.equals(parent_id)) {
                menu = new HashMap<String, Object>();
                String childId = sysMenu.getId();
                List<Map<String, Object>> s = getChilds(childId, list);
                if (s.size() > 0) {
                    menu.put("list", s);
                }
                menu.put("id", sysMenu.getId());
                menu.put("name", sysMenu.getMenuName());
                menu.put("type", "1".equals(sysMenu.getMenuAttribute()) ? 1 : 0);
                menu.put("href", sysMenu.getMenuUrl());
                child.add(menu);
            }
        }
        return child;
    }

    @Override
    public SysMenu getSysMenu(String menuNameZh) throws Exception {
        return sysMenuDao.getSysMenu(menuNameZh);
    }

    /**
     * 获取有效的菜单列表
     *
     * @return
     * @throws Exception Boger
     *                   2016年4月29日下午5:19:59
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<SysMenu> getEnableMenuList(Map formMap) throws Exception {
        List<SysMenu> list = new ArrayList<>();
        String menuStr = "";

        //获取禁用的菜单列表
        formMap.put("menuStatus",
                GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_STAUTS).
                        get(GlobalConstants.SYS_OPER_STATUS_DISABLE));
        list = sysMenuDao.getMenuList(formMap);
        //获取禁用的子菜单列表
        for (SysMenu sysMenu : list) {
            String parentStr = sysMenu.getId();
            menuStr += parentStr;
            while (true) {
                List<SysMenu> sysMenuList = sysMenuDao.getSysMenuListByParentStr(parentStr);
                if (sysMenuList.size() > 0) {
                    parentStr = "";
                    for (SysMenu sysMenuChild : sysMenuList) {
                        if (StringUtil.isEmpty(parentStr)) {
                            parentStr += sysMenuChild.getId();
                        } else {
                            parentStr += "','" + sysMenuChild.getId();
                        }
                    }
                    menuStr += "','" + parentStr;
                } else {
                    break;
                }
            }
        }
        //获取有效的菜单列表
        formMap.put("menuStatus",
                GlobalCache.getDic(GlobalConstants.DIC_SYS_OPER_STAUTS).
                        get(GlobalConstants.SYS_OPER_STATUS_ENABLE));
        return sysMenuDao.getEnableSysMenuList(menuStr, formMap);
    }

}
