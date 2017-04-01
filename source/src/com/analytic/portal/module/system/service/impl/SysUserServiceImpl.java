package com.analytic.portal.module.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.analytic.portal.module.system.model.SysUserReportSetting;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.ConfigUtil;
import com.analytic.portal.common.util.DESEncode;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.common.util.LDAPUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.ReportToolDao;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.dao.interfaces.ReportUserMappingDao;
import com.analytic.portal.module.system.dao.interfaces.SysUserDao;
import com.analytic.portal.module.system.dao.interfaces.SysUserRoleDao;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.model.SysUserRole;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysUserService;

import java.util.Hashtable;

import com.yzd.plat.common.util.StringUtil;
import com.yzd.plat.common.util.security.MD5Encryption;

@Service
public class SysUserServiceImpl implements SysUserService {
	
	private static final Logger logger=LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static final String LDAP_URL = ConfigUtil.get("ad.ip");
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private ReportToolDao reportToolDao;
    @Resource
    private ReportUserMappingDao reportUserMappingDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Resource
    private IBaseDao iBaseDao;
    @Resource
    private SysLogService sysLogService;
    @Resource
    private SysMenuService sysMenuService;

    private SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    /**
     * 获取系统用户
     *
     * @param loginName
     * @param password
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:21:58
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public SysUser getSysUser(String loginName, String password) throws Exception {
        Map map = new HashMap<>();
        SysUser sysUser = null;
        if (StringUtil.isNotEmpty(password)) {
            password = MD5Encryption.encrypt(password);
            map.put("loginPassword", password);
        }
        map.put("loginName", loginName);
        List<SysUser> list = sysUserDao.getSysUserList(map);
        if (list != null && list.size() > 0) {
            sysUser = list.get(0);
        }
        return sysUser;
    }

    /**
     * 更新系统用户
     *
     * @param sysUser
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:22:18
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean updateSysUser(SysUser sysUser) throws Exception {
        if (sysUser != null) {
            sysUser.setUpdateTime(DateUtil.getNowTime());
            String userId = sysUser.getId();
            String createUser = sysUser.getCreateUser();
            String updateUser = sysUser.getUpdateUser();
            sysUser.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
            return sysUserDao.update(sysUser);
        }
        return false;
    }

    /**
     * 更新系统用户
     *
     * @param sysUser
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:22:18
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean updateEditSysUser(SysUser sysUser) throws Exception {
        if (sysUser != null) {
            sysUser.setUpdateTime(DateUtil.getNowTime());
            String userId = sysUser.getId();
            String createUser = sysUser.getCreateUser();
            String updateUser = sysUser.getUpdateUser();
            sysUser.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);


            /*保存数据据色色*/
            if (sysUser.getDataRoleId() != null && !"".equals(sysUser.getDataRoleId())) {
            	//检查是否要删除客户设置的默认的初始化报表参数
            	List<SysUserRole> sURoleList=sysUserRoleDao.getSysUserRoleByUseridAndType(sysUser.getId(),"3");
            	if(sURoleList.size()>0){
            		String roleid=sURoleList.get(0).getRoleId();
            		if(!sysUser.getDataRoleId().equals(roleid)){
            			//删除客户已经设置的默认的初始化报表参数
            			try {
            				sysUserRoleDao.deleteSysUserParamSettingByUserId(sysUser.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
            		}
            	}
                sysUserRoleDao.deleteSysUserRoleByRoleType(sysUser.getId(),"3");
                SysUserRole sysDataRole = new SysUserRole();
                sysDataRole.setRoleId(sysUser.getDataRoleId());
                sysDataRole.setCreateTime(DateUtil.getNowTime());
                sysDataRole.setCreateUser(createUser);
                sysDataRole.setId(DataOperation.getSequenseStr());
                sysDataRole.setUserId(userId);
                sysUserRoleDao.save(sysDataRole);
            }else{
            	//数据角色未选择则删除已有的数据角色
            	//删除
            	sysUserRoleDao.deleteSysUserRoleByRoleType(sysUser.getId(),"3");
            	sysUserRoleDao.deleteSysUserParamSettingByUserId(sysUser.getId());
            }

            if (sysUser.getSysFunctionRole() != null && !"".equals(sysUser.getSysFunctionRole())) {
                sysUserRoleDao.deleteSysUserRoleByRoleType(sysUser.getId(),"1");
                String id = sysUser.getSysFunctionRole();
                String[] userroleid = id.split(",");
                for (int i = 0; i < userroleid.length; i++) {
                    SysUserRole sysRole = new SysUserRole();
                    String[] roleIdTemp = userroleid[i].split(":");
                    sysRole.setRoleId(roleIdTemp[1]);
                    sysRole.setCreateTime(DateUtil.getNowTime());
                    sysRole.setCreateUser(createUser);
                    sysRole.setUpdateTime(DateUtil.getNowTime());
                    sysRole.setUpdateUser(updateUser);
                    sysRole.setId(DataOperation.getSequenseStr());
                    sysRole.setUserId(userId);
                    sysUserRoleDao.save(sysRole);
                }
            }else{
            	//删除功能角色
            	sysUserRoleDao.deleteSysUserRoleByRoleType(sysUser.getId(),"1");
            }
            if (sysUser.getSysUserReportTool() != null && !"".equals(sysUser.getSysUserReportTool())) {
            	Map<String, String> paraMap=new HashMap<String,String>();
            	paraMap.put("userId", userId);
            	List<ReportUserMapping> old_RUserMap=reportUserMappingDao.getReportUserMappingList(paraMap);
            	Map<String, ReportUserMapping> old_RUserMaps=new HashMap<String, ReportUserMapping>();
            	if(old_RUserMap.size()>0){
            		for(ReportUserMapping map:old_RUserMap){
            			old_RUserMaps.put(map.getReportToolId(), map);
            		}
            	}
                sysUserRoleDao.deleteRepeortUserMapping(sysUser.getId());
                List<Map<String, String>> reportUserMappings = (List<Map<String, String>>) new ObjectMapper().readValue(sysUser.getSysUserReportTool(), List.class);
                for (Map<String, String> map : reportUserMappings) {
                    ReportUserMapping sysId = new ReportUserMapping();
                    sysId.setId(DataOperation.getSequenseStr());
                    sysId.setUserId(userId);
                    sysId.setReportToolId(map.get("reportToolId"));
                    sysId.setReportSysName(map.get("reportSysName"));
                    String new_reportSysPassword=map.get("reportSysPassword");
                    sysId.setReportSysPassword(DESEncode.Encrypt(new_reportSysPassword));
                    ReportUserMapping rum=old_RUserMaps.get(map.get("reportToolId"));
                    if(rum!=null){
                    	String password=rum.getReportSysPassword();
                    	if(new_reportSysPassword.equals(password)){
                    		sysId.setReportSysPassword(new_reportSysPassword);
                    	}
                    }
                    //检查一下老的密码与新密码是否一致
                    sysId.setCreateTime(DateUtil.getNowTime());
                    sysId.setCreateUser(createUser);
                    sysId.setUpdateTime(DateUtil.getNowTime());
                    sysId.setUpdateUser(updateUser);
                    sysUserDao.save(sysId);
                }
            }else{
            	sysUserRoleDao.deleteRepeortUserMapping(sysUser.getId());
            }
            return sysUserDao.update(sysUser);
        }
        return false;
    }

    /**
     * 修改密码
     *
     * @param sysUser
     * @param password
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:24:18
     */
    @Override
    public boolean updatePassword(SysUser sysUser, String password) throws Exception {
        if (sysUser != null) {
            sysUser = sysUserDao.getObjById(SysUser.class, sysUser.getId());
            password = MD5Encryption.encrypt(password);
            sysUser.setLoginPassword(password);
            return sysUserDao.update(sysUser);
        } else {
            return false;
        }
    }

    /**
     * 获取用户查询列表（分页查询）
     *
     * @param sysUser
     * @param page
     * @return Boger
     * 2016年4月21日下午1:24:40
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getSysUsersListByPage(SysUser sysUser, int page) {
        sysUser.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
        return sysUserDao.getSysUsersListByPage(sysUser, page);

    }

    /**
     * 通过ID删除用户
     *
     * @param id
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:25:01
     */
    @Override
    public boolean deleteSysUsersById(String id) throws Exception {
        SysUser sysUser = sysUserDao.getObjById(SysUser.class, id);
        sysUser.setIsDelete(GlobalConstants.DATA_STATUS_DELETE);
        //清空用户临时报表地址
        sysMenuService.deleteReportMenuUrlByUserId(sysUser.getId());
        return sysUserDao.save(sysUser);
    }

    /**
     * 新增用户
     *
     * @param sysUsers
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:25:15
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean saveSysUsers(SysUser sysUsers) throws Exception {
        String userId = DataOperation.getSequenseStr();
        String createUser = sysUsers.getCreateUser();
        sysUsers.setUserFullName(sysUsers.getLastName() + sysUsers.getFirstName());
        sysUsers.setLoginPassword(MD5Encryption.encrypt(sysUsers.getLoginPassword()));
//        sysUsers.setLoginPassword(MD5Encryption.encrypt(""));
        sysUsers.setId(userId);
        sysUsers.setCreateTime(DateUtil.getNowTime());
        sysUsers.setIsOnline("0");
        sysUsers.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
        sysUsers.setUserThemes(GlobalConstants.SYS_USER_DEFAULT_THEMES);

        /*保存数据据色色*/
        if (sysUsers.getDataRoleId() != null && !"".equals(sysUsers.getDataRoleId())) {
            SysUserRole sysDataRole = new SysUserRole();
            sysDataRole.setRoleId(sysUsers.getDataRoleId());
            sysDataRole.setCreateTime(DateUtil.getNowTime());
            sysDataRole.setCreateUser(createUser);
            sysDataRole.setId(DataOperation.getSequenseStr());
            sysDataRole.setUserId(userId);
            sysUserRoleDao.save(sysDataRole);
        }

        if (sysUsers.getSysFunctionRole() != null && !"".equals(sysUsers.getSysFunctionRole())) {
            String id = sysUsers.getSysFunctionRole();
            String[] userroleid = id.split(",");
            for (int i = 0; i < userroleid.length; i++) {
                SysUserRole sysRole = new SysUserRole();
                String[] roleIdTemp = userroleid[i].split(":");
                sysRole.setRoleId(roleIdTemp[1]);
                sysRole.setCreateTime(DateUtil.getNowTime());
                sysRole.setCreateUser(createUser);
                sysRole.setId(DataOperation.getSequenseStr());
                sysRole.setUserId(userId);
                sysUserRoleDao.save(sysRole);
            }
        }
        if (sysUsers.getSysUserReportTool() != null && !"".equals(sysUsers.getSysUserReportTool())) {
            List<Map<String, String>> reportUserMappings = (List<Map<String, String>>) new ObjectMapper().readValue(sysUsers.getSysUserReportTool(), List.class);
            for (Map<String, String> map : reportUserMappings) {
                ReportUserMapping sysId = new ReportUserMapping();
                sysId.setId(DataOperation.getSequenseStr());
                sysId.setUserId(userId);
                sysId.setReportToolId(map.get("reportToolId"));
                sysId.setReportSysName(map.get("reportSysName"));
                //des加密
                if(StringUtils.isNotBlank(map.get("reportSysPassword"))){
                	 String desPWD=DESEncode.Encrypt(map.get("reportSysPassword"));
                     sysId.setReportSysPassword(desPWD);
                }
                sysId.setCreateTime(DateUtil.getNowTime());
                sysId.setCreateUser(createUser);
                sysUserDao.save(sysId);
            }
        }
        boolean flg = true;
        boolean result = sysUserDao.save(sysUsers);
        return result && flg;
    }

    /**
     * 新增用户角色
     *
     * @param sysUserRoles
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:25:45
     */
    @Override
    public boolean saveSysUsersRole(SysUserRole sysUserRoles) throws Exception {
        String sql = "delete from sys_user_role where user_id=?";
        iBaseDao.updateBySQL(sql, sysUserRoles.getUserId());
        boolean flg = true;
        if (sysUserRoles.getRoleId() != null && !"".equals(sysUserRoles.getRoleId())) {
            String id = sysUserRoles.getRoleId();
            String[] userroleid = id.split(",");
            sysUserRoles.setId(DataOperation.getSequenseStr());
            for (int i = 0; i < userroleid.length; i++) {
                SysUserRole sysRole = new SysUserRole();
                String[] roleIdTemp = userroleid[i].split(":");
                sysRole.setRoleId(roleIdTemp[1]);
                sysRole.setUserId(sysUserRoles.getUserId());
                sysRole.setCreateTime(DateUtil.getNowTime());
                sysRole.setId(DataOperation.getSequenseStr());
                sysRole.setCreateUser(sysUserRoles.getCreateUser());
                flg = sysUserRoleDao.save(sysRole);
            }
        }
        return flg;
    }

    /**
     * 获取用户角色列表
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:26:17
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<SysUserRole> getSysUserRoleUserId(String userId) throws Exception {
        Map map = new HashMap<>();
        map.put("userId", userId);
        return sysUserRoleDao.getSysUserRoleList(map);
    }

    /**
     * 新增报表用户映射
     *
     * @param ReportUserMappings
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:26:51
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean saveReportUserMapping(ReportUserMapping ReportUserMappings) throws Exception {
        String sql = "delete from sys_report_user_mapping where user_id=?";
        iBaseDao.updateBySQL(sql, ReportUserMappings.getUserId());

        boolean flg = true;
        List<Map<String, String>> reportUserMappings = (List<Map<String, String>>) new ObjectMapper().
                readValue(ReportUserMappings.getId(), List.class);
        for (Map<String, String> map : reportUserMappings) {
            ReportUserMapping sysId = new ReportUserMapping();
            sysId.setId(DataOperation.getSequenseStr());
            sysId.setReportToolId(map.get("reportToolId"));
            sysId.setReportSysName(map.get("reportSysName"));
            sysId.setReportSysPassword(map.get("reportSysPassword"));
            sysId.setUpdateTime(map.get(DateUtil.getNowTime()));
            sysUserDao.save(sysId);
        }
        return flg;
    }

    /**
     * 通过角色更新系统用户
     *
     * @param sysUsers
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:27:33
     */
    @Override
    public boolean updateSysUser4Role(SysUser sysUsers) throws Exception {
        boolean flg = true;
        sysUsers.setUpdateTime(DateUtil.getNowTime());
        sysUsers.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
        boolean result = sysUserDao.updateSysUser4Role(sysUsers);
        return result && flg;
    }

    /**
     * 通过ID获取用户
     *
     * @param userId
     * @return Boger
     * 2016年4月21日下午1:28:00
     */
    @Override
    public SysUser getSysUserById(String userId) {
        return sysUserDao.getObjById(SysUser.class, userId);
    }

    /**
     * 获取报表用户映射
     *
     * @param reportToolId
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:28:24
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ReportUserMapping getUserReportMappingByReportToolId(
            String reportToolId) throws Exception {
        Map map = new HashMap();
        map.put("reportToolId", reportToolId);
        List list = reportUserMappingDao.getReportUserMappingList(map);
        if (list!=null&&list.size()>0) {
            return (ReportUserMapping) list.get(0);
        }
        return null;
    }

    /**
     * 查询报表用户映射列表
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:29:06
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<ReportUserMapping> getUserReportMappingByReportUserId(
            String userId) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        return reportUserMappingDao.getReportUserMappingList(map);
    }

    /**
     * 获取数据角色
     *
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:29:37
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<SysUser> getDataRoleId(
            String userId) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        return sysUserDao.getSysUserList(map);
    }

    /**
     * 获取最近访问日志
     * @return
     * @throws Exception admin
     *                   2016年4月11日下午1:29:59
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List<HashMap> getVistReportListByUser(String LoginName) throws Exception {
        List<HashMap> list = new ArrayList<>();
        List<Object[]> sysLogList = new ArrayList<>();

        try {
            sysLogList = sysLogService.getSysLogListByUser(LoginName);
            int i = 1;
            int maxItem = Integer.valueOf(GlobalCache.getConfig(GlobalConstants.CONFIG_REPORT_RECENTLY_ITEM));
            int maxDay = Integer.valueOf(GlobalCache.getConfig(GlobalConstants.CONFIG_REPORT_RECENTLY_DAY));
            for (Object[] obj : sysLogList) {
                String menuId = obj[0].toString();
                String createTime = obj[1].toString();
                //最多显示的报表条数
                if (i > maxItem) break;
                //检测日期是否符合要求
                Date beginDate = dateFormatShort.parse(createTime);
                Date endDate = dateFormatShort.parse(dateFormatShort.format(new Date()));
                int dayDiff = DateUtil.getDaysDiff(beginDate, endDate);
                if (dayDiff > maxDay) continue;
                //封装最近访问报表
                HashMap hashMap = new HashMap<>();
                SysMenu sysMenu = new SysMenu();
                sysMenu = iBaseDao.getObjById(SysMenu.class, menuId);
                //已经删除的不再显示
                if (sysMenu.getIsDelete().equals(GlobalConstants.DATA_STATUS_NORMAL)) {
                    hashMap.put("menuId", menuId);
                    hashMap.put("menuName", sysMenu.getMenuName());
                    hashMap.put("menuNameZh", sysMenu.getMenuNameZh());
                    hashMap.put("menuNameEn", sysMenu.getMenuNameEn());
                    hashMap.put("createTime", createTime);
                    list.add(hashMap);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 更新系统用户信息
     *
     * @param user
     * @param updatePassword
     * @return
     * @throws Exception Boger
     *                   2016年4月21日下午1:30:09
     */
    @Override
    public boolean updateSysUserInfo(SysUser user, boolean updatePassword) throws Exception {
        if (updatePassword) {
            String loginPassword = MD5Encryption.encrypt(user.getLoginPassword());
            user.setLoginPassword(loginPassword);
        }
        return iBaseDao.saveOrUpdate(user);
    }

    /**
     * 域账户检查
     *
     * @param adName
     * @param password
     * @return Boger
     * 2016年4月21日下午1:30:26
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public synchronized boolean checkAdUser(String adName, String password) {
        Hashtable HashEnv = new Hashtable();

        try {
            //window2008 R2 AD IP 192.168.6.89
//            String ad_name = ConfigUtil.get("ad.name");
            //注意用户名的写法：domain\User 或 User@domain.com
//            String adminName = ad_name + adName;
//            String adminPassword = password;

            HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
            /*HashEnv.put(Context.SECURITY_PRINCIPAL, adminName); //AD User
            HashEnv.put(Context.SECURITY_CREDENTIALS, adminPassword); //AD Password*/
//            HashEnv.put(Context.SECURITY_PRINCIPAL, "uid="+adName+",ou=applications,o=hisense.com,o=isp"); //AD User
            HashEnv.put(Context.SECURITY_PRINCIPAL, "uid="+adName+",ou=People,o=hisense.com,o=isp"); //AD User
            HashEnv.put(Context.SECURITY_CREDENTIALS, password); //AD Password
            HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
            HashEnv.put(Context.PROVIDER_URL, LDAP_URL);

            LdapContext ctx = new InitialLdapContext(HashEnv, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            /*String searchFilter = "sAMAccountName=lwang168";
            String searchBase = "DC=ap,DC=jnj,DC=com";*/
//            String searchFilter = "(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*))(uid=yujie)(uid=huangxiaojian)(uid=jiashaoqian))";
//            String searchBase = "ou=orgs,o=hisense.com,o=isp";
            String searchFilter = "(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*))(uid=yujie)(uid=huangxiaojian)(uid=jiashaoqian))";
            String searchBase = "ou=orgs,o=hisense.com,o=isp";
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            if (!(answer == null || answer.equals(null))) {
                ctx.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取系统用户
     *
     * @param loginName
     * @return Boger
     * 2016年4月21日下午1:34:24
     */
    public SysUser getSysUsers(String loginName) {
        return sysUserDao.getSysUsers(loginName);
    }

    /**
     * 根据用户ID和报表工具ID获取报表映射关系信息
     *
     * @param reportToolId
     * @param userId
     * @return
     * @throws Exception Boger
     *                   2016年4月19日下午2:47:58
     */
    @Override
    public ReportUserMapping getReportUserMapping(String reportToolId,
                                                  String userId) throws Exception {
        return sysUserDao.getReportUserMapping(reportToolId, userId);
    }
    
    public static void main(String[] args) {
		Map<String, String> map=LDAPUtil.checkLdapInfo("jiashaoqian");
		System.out.println(map);
	}

    /**
     * @throws Exception 
     * @description 同步ldap user信息
     */
	@Override
	public void doSynLdapUserInfo() throws Exception {
		Map<String, String> paraMap=new HashMap<String,String>();
		paraMap.put("userFlag", "1");
		paraMap.put("isDelete", "0");
		List<SysUser> list=sysUserDao.getSysUserList(paraMap);
		for(SysUser user:list){
			Map<String, String> maps=LDAPUtil.checkLdapInfo(user.getLoginName());
			if(maps!=null){
				//更新数据库
				if(StringUtils.isNotBlank(maps.get("sn"))){
					//姓
					user.setLastName(maps.get("sn"));
				}
				if(StringUtils.isNotBlank(maps.get("cn"))){
					//姓名
					String firstName=maps.get("cn");
					if(StringUtils.isNotBlank(maps.get("sn"))&&firstName.startsWith(maps.get("sn"))){
						firstName=firstName.substring(maps.get("sn").length(), firstName.length());
					}
					user.setFirstName(firstName);
				}
				if(StringUtils.isNotBlank(maps.get("cn"))){
					//全名
					user.setUserFullName(maps.get("cn"));
				}
				if(StringUtils.isNotBlank(maps.get("mobile"))){
					//手机号
					user.setUserMobile(maps.get("mobile"));
				}
				if(StringUtils.isNotBlank(maps.get("o"))){
					//部门
					user.setUserDepartment(maps.get("o"));
				}
				if(StringUtils.isNotBlank(maps.get("mail"))){
					//邮箱
					user.setUserEmail(maps.get("mail"));
				}
				sysUserDao.update(user);
				logger.info("update {} ldapinfo success.",user.getLoginName());
			}else{
				logger.error("get {} ldapinfo error!",user.getLoginName());
			}
		}
	}

}
