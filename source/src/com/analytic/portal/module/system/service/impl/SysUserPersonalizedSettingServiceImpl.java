/**
 * 系统菜单
 */
package com.analytic.portal.module.system.service.impl;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.service.IEncodingService;
import com.analytic.portal.module.report.dao.interfaces.ReportParamDao;
import com.analytic.portal.module.report.model.*;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;
import com.analytic.portal.module.system.dao.interfaces.SysMenuDao;
import com.analytic.portal.module.system.dao.interfaces.SysUserPersonalizedSettingDao;
import com.analytic.portal.module.system.model.*;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleService;
import com.analytic.portal.module.system.service.interfaces.SysUserPersonalizedSettingService;
import com.analytic.portal.module.system.service.interfaces.SysUserService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户个性化设置
 *
 * @author Boger
 */
@Service
public class SysUserPersonalizedSettingServiceImpl implements SysUserPersonalizedSettingService {

    @Resource
    private IBaseDao iBaseDao;
    @Resource
    private SysUserPersonalizedSettingDao sysUserPersonalizedSettingDao;
    @Resource
    private SysMenuDao sysMenuDao;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ReportParamDao reportParamDao;
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 分页查询获取用户角色相关所有报表列表
     *
     * @param formMap
     * @return
     * @throws Exception Boger
     *                   2016年3月21日上午11:37:25
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getMenuSettingListByUserId(Map formMap, String userId,String settingType) throws Exception {
        Map resultMap = sysMenuDao.getMenuSettingListByUserId(formMap, userId);
        List<SysUserReportSetting> userReportSettingList = getUserReportSettingList(userId, settingType);

        for (SysUserReportSetting settingItem : userReportSettingList) {
            for (SysMenu menuItem : (List<SysMenu>) resultMap.get("result")) {
                if (menuItem.getId().equals(settingItem.getMenuId())) {
                    menuItem.setStatus("是");
                    break;
                }
            }
        }

        return resultMap;
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
    public Map deleteSetting(String userId,String menuId, String settingType) throws Exception {
        Map map = new HashMap<>();
        SysUserReportSetting sysUserReportSetting = new SysUserReportSetting();
        boolean deleteFlag = true;

        try {
            sysUserPersonalizedSettingDao.deleteUserReportSetting(userId,menuId,settingType);
            map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
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
    public Map addSetting(String userId,String menuId, String settingType) throws Exception {
        Map map = new HashMap<>();
        try {
            /*注:默认报表只允许设置一张*/
            if(GlobalConstants.SYS_SETTING_TYPE_DEFAULT_REPORT.equals(settingType)){
                deleteSetting(userId,null,settingType);
            }
            SysUserReportSetting sysUserReportSetting = new SysUserReportSetting();
            sysUserReportSetting.setId(DataOperation.getSequenseStr());
            sysUserReportSetting.setUserId(userId);
            sysUserReportSetting.setMenuId(menuId);
            sysUserReportSetting.setSettingType(settingType);
            sysUserReportSetting.setDeletedFlag("0");
            iBaseDao.save(sysUserReportSetting);
            map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map updateRefreshParamSetting(String userId,ReportRefreshParam reportRefreshParam) throws Exception{
        Map map = new HashMap<>();
        try {
            sysUserPersonalizedSettingDao.deleteUserParamSetting(userId,reportRefreshParam.getId());
            SysUserParamSetting sysUserParamSetting=new SysUserParamSetting();
            sysUserParamSetting.setUserId(userId);
            sysUserParamSetting.setParamId(reportRefreshParam.getId());
            sysUserParamSetting.setRefreshValue(reportRefreshParam.getRefreshValue());
            sysUserParamSetting.setId(DataOperation.getSequenseStr());
            sysUserParamSetting.setIsDelete("0");
            iBaseDao.save(sysUserParamSetting);
            map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map updateGlobalRefreshParamSetting(String userId,Map<String,Object> selectRefreshParamMap) throws Exception{
        Map map = new HashMap<>();
        try {
            for (Map.Entry<String,Object> entry : selectRefreshParamMap.entrySet()) {
                /*if ("menuid".equals(entry.getKey())){
                    continue;
                }*/
                if (entry.getValue()!=null&&!"".equals(entry.getValue())){
                    /*设置刷新值*/
                    String refreshValue="";
                    if (entry.getValue() instanceof JSONArray){
                        JSONArray jsonArray= (JSONArray) entry.getValue();
                        List<String> strings= (List<String>)JSONArray.toList(jsonArray);
                        for(int i=0;i<strings.size();i++){
                            refreshValue+=String.valueOf(strings.get(i));
                            if (i+1<strings.size()){
                                refreshValue+=',';
                            }
                        }
                    }else{
                        refreshValue= String.valueOf(entry.getValue());
                    }
                    System.out.println(refreshValue);
                    /*查询数据库中是否存在记录*/
                    /*List<SysUserParamSetting> paramSettingList= sysUserPersonalizedSettingDao.getUserParamSettingList(userId, (String) entry.getKey());
                    if (paramSettingList!=null&&paramSettingList.size()>0){

                    }*/
                    sysUserPersonalizedSettingDao.deleteUserParamSetting(userId,String.valueOf(entry.getKey()));
                    SysUserParamSetting sysUserParamSetting=new SysUserParamSetting();
                    sysUserParamSetting.setUserId(userId);
                    sysUserParamSetting.setParamId(String.valueOf(entry.getKey()));
                    sysUserParamSetting.setRefreshValue(refreshValue);
                    sysUserParamSetting.setId(DataOperation.getSequenseStr());
                    sysUserParamSetting.setIsDelete("0");
                    iBaseDao.save(sysUserParamSetting);
                }else{
                	 //根据key删除
                	 sysUserPersonalizedSettingDao.deleteUserParamSetting(userId,String.valueOf(entry.getKey()));
                }
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()+",ClassType:"+entry.getValue().getClass());
            }
            /*SysUserParamSetting sysUserParamSetting=new SysUserParamSetting();
            sysUserParamSetting.setUserId(userId);
            sysUserParamSetting.setParamId(reportRefreshParam.getId());
            sysUserParamSetting.setRefreshValue(reportRefreshParam.getRefreshValue());
            sysUserParamSetting.setId(DataOperation.getSequenseStr());
            sysUserParamSetting.setIsDelete("0");
            iBaseDao.save(sysUserParamSetting);*/
            map.put(GlobalConstants.DELETE_OPER_RESULT, GlobalConstants.DELETE_OPER_DATA_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 获取用户个性化设置报表列表
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:29:37
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<SysUserReportSetting> getUserReportSettingList(String userId, String settingType) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("settingType", settingType);
        return sysUserPersonalizedSettingDao.getUserReportSettingList(map);
    }

    /**
     * 获取分页的报表参数集合
     * @param formMap
     * @return
     * @throws Exception
     * pengbo
     * 2016年3月11日下午1:50:03
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getUserReportParamSettingListByPage(Map formMap) throws Exception {
        return reportParamDao.getUserReportParamSettingListByPage(formMap);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ReportRefreshParam> getGlobalParamOfUserList(Map formMap,String menuId) throws Exception {
        /*1.根据用户ID获取用户相关的数据角色*/
        List<ReportRefreshParam> resultReportRefreshParamList= new ArrayList<>();
        List<ReportRefreshParam> reportRefreshParamList= reportParamDao.getUserReportParamSettingList(formMap);
        /*2.根据菜单ID获取报表相关的参数*/
        List<ReportMenuParam> reportMenuParamList = sysMenuService.getReportMenuParamListByMenuId(menuId);
        for (ReportMenuParam reportMenuParam : reportMenuParamList) {
            for (ReportRefreshParam reportRefreshParam: reportRefreshParamList){
                if (reportMenuParam.getParamId().equals(reportRefreshParam.getId())){
                    resultReportRefreshParamList.add(reportRefreshParam);
                }
            }
        }
        return resultReportRefreshParamList;
    }


}
