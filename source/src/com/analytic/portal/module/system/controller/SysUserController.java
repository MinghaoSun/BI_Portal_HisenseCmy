package com.analytic.portal.module.system.controller;

import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.analytic.portal.common.util.ConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.DateUtil;
import com.analytic.portal.common.util.LDAPUtil;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.CheckCodeUtil;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.model.SysUserRole;
import com.analytic.portal.module.system.service.interfaces.SysLogService;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysUserService;
import com.yzd.plat.common.util.StringUtil;
import com.yzd.plat.common.util.security.MD5Encryption;

@Controller
@RequestMapping("/sysUsers")
public class SysUserController  extends BaseController {
	private static final Logger logger=LoggerFactory.getLogger(SysUserController.class);
	
    private static final String LDAP_URL = ConfigUtil.get("ad.ip");
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysLogService sysLogService;
	@Resource
	private SysMenuService sysMenuService;
	
	/**
	 * 验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/checkCode",method=RequestMethod.GET)
	public void checkCode(HttpServletRequest request ,HttpServletResponse response){
		CheckCodeUtil.checkCode(request, response);
	}
	
	/**
	 * 获取登录类型
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/getlogInType",method=RequestMethod.GET)
	public void getlogInType(HttpServletRequest request ,HttpServletResponse response){
		String login_type = GlobalCache.getConfig(GlobalConstants.CONFIG_LOGIN_TYPE);
		Map map = new  HashMap();
		map.put("result", login_type);
		writerJSON(map,response);
	}
	
	
	/**
	 * @description 用户登陆（重写 by sunminghao）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin_new(HttpServletRequest request ,HttpServletResponse response){
		//获取前台过来的参数
		String loginName=this.getParameter(request, "loginName");
		String password=this.getParameter(request, "password");
		String localLanguage=this.getParameter(request, "languageSelect");
		logger.info("login.. \t username:{}, \t pwd:{}, \t lan:{}",new String[]{loginName,MD5Encryption.encrypt(password),localLanguage});
		//不能为空
		if(StringUtils.isBlank(loginName)||StringUtils.isBlank(password)||StringUtils.isBlank(localLanguage)){
			logger.error("login param is null return");
			response.setHeader("login-status", "403");
			return "redirect:/login.html?error=user";
		}
		//保存账户密钥信息（解密使用）
		request.getSession().setAttribute(GlobalConstants.SESSION_USER_PASS, password);
		//获取login_type
		String login_type = GlobalCache.getConfig(GlobalConstants.CONFIG_LOGIN_TYPE);
		//ldap验证(login_type=1)
		SysUser user=null;
		if("1".equals(login_type)){
			boolean loginState= LDAPUtil.validateUser(loginName, password);
			logger.info("ldap check result:{}",loginState);
			if(loginState){
				user=sysUserService.getSysUsers(loginName);
				if(user==null){
					logger.error("username:{} is not exit",loginName);
					response.setHeader("login-status", "403");
					return "redirect:/login.html?error=user";
				}
			}
		}
		//是否需要本地验证
		if(user==null){
			logger.info("username:{} need check with database",loginName);
			try {
				user = sysUserService.getSysUser(loginName,password);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("username:{} check with database error",loginName);
			}
			if(user==null){
				logger.error("username:{} is not exit or passworderror",loginName);
				response.setHeader("login-status", "403");
				return "redirect:/login.html?error=user";
			}
		}
		//设置session
		try {
			logger.info("username:{} login in success",loginName);
			user.setIsOnline("1");
			sysUserService.updateSysUser(user);
			request.getSession().setAttribute(GlobalConstants.SESSION_USER, user);
			//国际化session
			request.getSession().setAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE, localLanguage);
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_LOGIN);
			//获取首页横向纵向参数
			String configValues=GlobalCache.getConfig(GlobalConstants.CONFIG_INDEX_PAGE_DISPLAY);
			if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_H)){
				return "redirect:/index.html";
			}else if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_V)){
//				return "redirect:/index2.html";
				return "redirect:/index3.html";
//				return "redirect:/app_inbox.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("username:{} update databse error",loginName);
		}
		response.setHeader("login-status", "200");
		return "redirect:/login.html?error=user";
	}
	
	/**
	 * 登陸頁面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginPage(HttpServletRequest request ,HttpServletResponse response){
		return "redirect:/login.html";
	}
	
	/**
	 * 用户登陆
	 */
	//@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request ,HttpServletResponse response){
		try {
			String loginName=this.getParameter(request, "loginName");
			String password=this.getParameter(request, "password");
			String rand = this.getParameter(request, "code");
			String localLanguage=this.getParameter(request, "languageSelect");
//			String sessrand = request.getSession().getAttribute("rand").toString();
			/*
			* 注：海信端无需验证码校验
			* */
			/*if(rand==null || "".equals(rand) || !sessrand.toLowerCase().equals(rand.toLowerCase())){
				response.setHeader("login-status", "403");
				return "redirect:/login.html?error=code";
			}*/
			String login_type = GlobalCache.getConfig(GlobalConstants.CONFIG_LOGIN_TYPE);
			//保存账户密钥信息（解密使用）
			request.getSession().setAttribute(GlobalConstants.SESSION_USER_PASS, password);
			if("1".equals(login_type)){
				boolean loginState = sysUserService.checkAdUser(loginName, password);
				if(loginState){
					SysUser user = sysUserService.getSysUsers(loginName);
					if (user==null){
						//登陆失败,用户名或密码错误
						response.setHeader("login-status", "403");
						return "redirect:/login.html?error=user";
					}else{
						user.setIsOnline("1");
						sysUserService.updateSysUser(user);
						request.getSession().setAttribute(GlobalConstants.SESSION_USER, user);
						//国际化session
						request.getSession().setAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE, localLanguage);
						sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_LOGIN);
						//获取首页横向纵向参数
						String configValues=GlobalCache.getConfig(GlobalConstants.CONFIG_INDEX_PAGE_DISPLAY);
						if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_H)){
							return "redirect:/index.html";
						}else if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_V)){
//							return "redirect:/index2.html";
							return "redirect:/index3.html";
//							return "redirect:/app_inbox.html";
						}
						
					}
				}
			}
			SysUser user= null;
			user=sysUserService.getSysUser(loginName, password);
			if (user==null || StringUtil.isEmpty(loginName) || StringUtil.isEmpty(password)){
				//登陆失败,用户名或密码错误
				response.setHeader("login-status", "403");
				return "redirect:/login.html?error=user";
			}else{
				user.setIsOnline("1");
				sysUserService.updateSysUser(user);
				request.getSession().setAttribute(GlobalConstants.SESSION_USER, user);
				//国际化session
				request.getSession().setAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE, localLanguage);
				sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_LOGIN);
				//获取首页横向纵向参数
				String configValues=GlobalCache.getConfig(GlobalConstants.CONFIG_INDEX_PAGE_DISPLAY);
				if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_H)){
					return "redirect:/index.html";
				}else if(configValues.equals(GlobalConstants.INDEX_PAGE_DISPLAY_V)){
//					return "redirect:/index2.html";
					return "redirect:/index3.html";
//					return "redirect:/app_inbox.html";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setHeader("login-status", "200");
		return "redirect:/login.html?error=user";
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * Boger
	 * 2016年4月21日上午11:43:25
	 */
	@RequestMapping(value="/close",method=RequestMethod.GET)
	public String close(HttpServletRequest request ,HttpServletResponse response){
		try {
			SysUser sysUser = getLoginUser(request);
			sysUser.setIsOnline("0");
			sysUserService.updateSysUser(sysUser);
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_LOGOUT);
			request.getSession().removeAttribute(GlobalConstants.SESSION_USER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setHeader("login-status", "200");
		return "redirect:/";
	}

	/**
	 * 改变用户主题
	 * @param request
	 * @param response
	 * admin
	 * 2016年4月14日下午5:21:34
	 */
	@RequestMapping(value="/changeTheme",method=RequestMethod.POST)
	public void changeTheme(HttpServletRequest request ,HttpServletResponse response){
		int code = 0;
		try {
			String theme=this.getParameter(request, "theme");
			SysUser sysUser = getLoginUser(request);
			sysUser.setUserThemes(theme);
			boolean state = sysUserService.updateSysUser(sysUser);
			code=state ? 0 :-1;
			this.writeJSONResult(code, null,null, response);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
			this.writeJSONResult(code, null,null, response);
		}
	}
	
	/**
	 * 是否登录
	 */
	@RequestMapping(value="/isOnline",method=RequestMethod.GET)
	public void isOnline(HttpServletRequest request ,HttpServletResponse response){
		Object user = request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		if (user==null) {
			writerJSON("-1", response);
		}else{
			SysUser u = (SysUser) user;
			writeJSONResult(GlobalConstants.SUCCESS_STATE, null, u , response);
		}
	}
	
	/**
	 * 获取当前用户
	 * @param request
	 * @param response
	 * Boger
	 * 2016年4月21日上午11:50:02
	 */
	@RequestMapping(value="/getSysUser",method=RequestMethod.GET)
	public void getSysUser(HttpServletRequest request ,HttpServletResponse response){
		try {
			Object user = request.getSession().getAttribute(GlobalConstants.SESSION_USER);
			if(user != null){
				writerJSON(user, response);
			}
		} catch (Exception e) {
			writerJSON(-1, response);
		}
	}

	/**
	 * 个人密码修改
	 */
	@RequestMapping(value="/modifyPassword",method=RequestMethod.POST)
	public void modifyPassword(HttpServletRequest request ,HttpServletResponse response){
		String loginName = getParameter(request, "loginName");
		int code=0;
		try {
			String oldPwd=this.getParameter(request, "oldPassword");
			String password=this.getParameter(request, "newPassword");
			SysUser user=sysUserService.getSysUser(loginName, oldPwd);
			if(user==null){
				code=-1;
			}else{
				boolean state=sysUserService.updatePassword(user, password);
				code=state ? 0 :-1;
			}
			this.writeJSONResult(code, null,null, response);
		} catch (Exception e) {
			this.writeJSONResult(code, null,null, response);
		}
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 * Boger
	 * 2016年4月21日上午11:50:35
	 */
	@RequestMapping(value="/updateSysUserInfo",method=RequestMethod.POST)
	public void updateSysUserInfo(SysUser user,HttpServletRequest request,HttpServletResponse response){
		String newPassword = getParameter(request, "newPassword");
		String oldPassword = getParameter(request, "oldPassword");
		boolean updated = false;
		
		try {
			SysUser oldUser = getLoginUser(request);
			if(oldUser==null){
				noLogin(response);
				return;
			}
			
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setUserEmail(user.getUserEmail());
			oldUser.setUserFullName(oldUser.getLastName()+oldUser.getFirstName());
			oldUser.setUserMobile(user.getUserMobile());
			oldUser.setUpdateUser(oldUser.getLoginName());
			oldUser.setUpdateTime(DateUtil.getNowTime());
            oldUser.setUserFlag(user.getUserFlag());
			if (!oldPassword.equals(newPassword)){
				oldUser.setLoginPassword(newPassword);
				updated = sysUserService.updateSysUserInfo(oldUser,true);
			}else {
				updated = sysUserService.updateSysUserInfo(oldUser,false);
			}

			if(updated){
				request.getSession().setAttribute(GlobalConstants.SESSION_USER, oldUser);
			}
			writeJSONResult(updated ? 0:-1, null, oldUser, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取系统用户列表
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getSysUserListByPage",method=RequestMethod.GET)
	public void getSysUserListByPage(HttpServletRequest request ,HttpServletResponse response){
		int  status=GlobalConstants.SUCCESS_STATE;
		String searchInput = getParameter(request, "searchInput");//搜索的内容
		String userFullName = getParameter(request, "userFullName"); 
		String userMobile = getParameter(request, "userMobile");
		String currentPage = StringUtil.isEmpty(getParameter(request, "currentPage")) ? "1": getParameter(request, "currentPage").toString();//当前页
		SysUser sysUser=new SysUser();
		if(searchInput!=null && !"".equals(searchInput)){
			sysUser.setLoginName(searchInput);
		}
		if (StringUtil.isNotEmpty(userFullName)) {
			sysUser.setUserFullName(userFullName);
		}
		if (StringUtil.isNotEmpty(userMobile)) {
			sysUser.setUserMobile(userMobile);
		}
		Map map = new  HashMap();
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			map=sysUserService.getSysUsersListByPage(sysUser,Integer.parseInt(currentPage));
			String isLoged=request.getParameter("isLoged");
			if ("yes".equals(isLoged)) {
				sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_QUERY);
			}
			response.setStatus(status);
			writerJSON(map, response);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delSysUser",method=RequestMethod.POST)
	public void delSysUser(HttpServletRequest request ,HttpServletResponse response){
		String id = this.getParameter(request, "id").toString();
		int code=-1;
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			code =sysUserService.deleteSysUsersById(id) ? 0 : -1;
			//删除用户的暂存地址信息
			sysMenuService.deleteReportMenuUrlByUserId(id);
			writeJSONResult(code, null, null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_DELETE);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			writeJSONResult(code, null, null, response);
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 添加用户
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value="/saveSysUser",method=RequestMethod.POST)
	public void saveSysUser(SysUser sysUsers,HttpServletRequest request ,HttpServletResponse response){
		int status=GlobalConstants.SUCCESS_STATE;

		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		sysUsers.setCreateUser(user.getLoginName());
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			status=sysUserService.saveSysUsers(sysUsers) ? GlobalConstants.SUCCESS_STATE:GlobalConstants.FAILURE_STATE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_ADD);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
			
			response.setStatus(status);
			writeJSONResult(status, null, null,response);
			
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 保存用户角色关系数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveSysUserRole",method=RequestMethod.POST)
	public void saveSysUsersRole(SysUserRole sysUserRoles,HttpServletRequest request ,HttpServletResponse response){
		int status=GlobalConstants.SUCCESS_STATE;
		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		sysUserRoles.setCreateUser(user.getLoginName());
		
		try {
			status=sysUserService.saveSysUsersRole(sysUserRoles) ? GlobalConstants.SUCCESS_STATE:GlobalConstants.FAILURE_STATE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);
		} catch (Exception e) {
			status=GlobalConstants.FAILURE_STATE;
		}
		
		response.setStatus(status);
		writeJSONResult(status, null, null,response);
	}
	
	/**
	 * 根据用户Id查询角色数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getSysUserRoleUserId",method=RequestMethod.GET)
	public void getSysUserRoleUserId(HttpServletRequest request ,HttpServletResponse response){
		int  status = GlobalConstants.SUCCESS_STATE;
		List<SysUserRole> sysUserRole = new ArrayList<SysUserRole>();
		try {
			String userId=this.getParameter(request, "userId");
			sysUserRole = sysUserService.getSysUserRoleUserId(userId);
			
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writerJSON(sysUserRole, response);
	}
	
	/**
	 * 更新用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateSysUser",method=RequestMethod.POST)
	public void updateSysUser(SysUser sysUsers,HttpServletRequest request ,HttpServletResponse response){
		int  status=GlobalConstants.SUCCESS_STATE;
		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_USER);
		sysUsers.setUpdateUser(user.getLoginName());
		try {
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			//处理密码是否发生更改
			String oldPassword=request.getParameter("oldPassword").replace(' ','+');
			String userId=request.getParameter("menuId");
			String newPassword=sysUsers.getLoginPassword();
			if (!oldPassword.equals(newPassword)){
				sysUsers.setLoginPassword(MD5Encryption.encrypt(newPassword));
			}
			sysUsers.setUserFullName(sysUsers.getLastName()+sysUsers.getFirstName());
//			status=sysUserService.updateSysUser(sysUsers) ? GlobalConstants.SUCCESS_STATE:GlobalConstants.FAILURE_STATE;
			status=sysUserService.updateEditSysUser(sysUsers) ? GlobalConstants.SUCCESS_STATE:GlobalConstants.FAILURE_STATE;
			//更新报表暂存地址
//			sysMenuService.updateReportMenuUrl(request);

			response.setStatus(status);
			writeJSONResult(status,null,null, response);
			
			sysLogService.saveSysLog(request, response, GlobalConstants.SYS_OPER_TYPE_UPDATE);
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		
	}
	
	/**
	 * 查询是否重复
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getOnlyLoginName",method=RequestMethod.GET)
	public void getOnlyLoginName(HttpServletRequest request ,HttpServletResponse response){
		String loginName = getParameter(request, "loginName");
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			SysUser user=sysUserService.getSysUsers(loginName);
			if(null!=user){
				this.writeJSONResult(0, null,null, response);
			}else{
				this.writeJSONResult(1, null,null, response);
			}
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
			this.writeJSONResult(-1, null,null, response);
		}
	}
    /**
     * 查询ldap账户信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getlDapUser",method=RequestMethod.GET)
    public void getlDapUser(HttpServletRequest request ,HttpServletResponse response){
        Hashtable HashEnv = new Hashtable();
        String uid = getParameter(request, "uid");
        Map<String, String> ldap = new HashMap<String, String>();
        try {
            HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); //LDAP访问安全级别
            HashEnv.put(Context.SECURITY_PRINCIPAL, "uid=bxsyszlgl,ou=applications,o=hisense.com,o=isp"); //AD User
            HashEnv.put(Context.SECURITY_CREDENTIALS, "Bx@SysZL!23"); //AD Password
            HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); //LDAP工厂类
            HashEnv.put(Context.PROVIDER_URL, LDAP_URL);
            LdapContext ctx = new InitialLdapContext(HashEnv, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter="(|(&(objectClass=person)(!(nsaccountlock=true))(departmentNumber=jd_bx*)))";
            String searchBase = "uid="+uid+",ou=People,o=hisense.com,o=isp";
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            if (!(answer == null || answer.equals(null))) {
                    while (answer.hasMore()) {
                        SearchResult result = answer.next();
                        NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                        while (attrs.hasMore()) {
                            Attribute attr = attrs.next();
                            //System.out.println(attr.getID() + "=" + attr.get());
                            ldap.put(attr.getID(),attr.get().toString());
                        }
                    }
                ctx.close();
                writerJSON(ldap, response);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 保存用户映射数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/saveReportUserMapping",method=RequestMethod.POST)
	public void saveReportUserMapping(ReportUserMapping ReportUserMappings,HttpServletRequest request ,
			HttpServletResponse response){
		int status=GlobalConstants.SUCCESS_STATE;
		try {
			status=sysUserService.saveReportUserMapping(ReportUserMappings) ? GlobalConstants.SUCCESS_STATE:GlobalConstants.FAILURE_STATE;
		} catch (Exception e) {
			status=GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writeJSONResult(status, null, null,response);
	}
	
	/**
	 * 根据用户Id查询用户映射数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserReportMappingByReport")
	public void getUserReportMappingByReportList(HttpServletRequest request ,HttpServletResponse response){
		int  status = GlobalConstants.SUCCESS_STATE;
		List<ReportUserMapping> reportUserMappings = new ArrayList<ReportUserMapping>();
		try {
			String userId=this.getParameter(request, "userId");
			reportUserMappings = sysUserService.getUserReportMappingByReportUserId(userId);
			
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writerJSON(reportUserMappings, response);
	}
	
	/**
	 * 查询用户列表获取数据角色id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSysUserList")
	public void getSysUserList(HttpServletRequest request ,HttpServletResponse response){
		int  status = GlobalConstants.SUCCESS_STATE;
		List<SysUser> sysUserDataId= new ArrayList<SysUser>();
		try {
			String userId=this.getParameter(request, "userId");
			sysUserDataId = sysUserService.getDataRoleId(userId);
			
		} catch (Exception e) {
			status = GlobalConstants.FAILURE_STATE;
		}
		response.setStatus(status);
		writerJSON(sysUserDataId, response);
	}
	
	/**
	 * 获取用户最近访问列表
	 * @param request
	 * @param response
	 * Boger
	 * 2016年4月21日下午12:04:09
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getVisitReportListByUser")
	public void getVisitReportListByUser(HttpServletRequest request ,HttpServletResponse response){
		List list=new ArrayList();
	
		try {
			SysUser user = getLoginUser(request);
			list = sysUserService.getVistReportListByUser(user.getLoginName());
			response.setStatus(GlobalConstants.SUCCESS_STATE);
		} catch (Exception e) {
			response.setStatus(GlobalConstants.FAILURE_STATE);
		}

		writerJSON(list, response);
	}
	
	/**
	 * 根据用户选择获取本地国际化语言
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月9日下午3:15:11
	 */
	@RequestMapping(value="getLocalI18n",method=RequestMethod.GET)
	public void getLocalI18n(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> map=new HashMap<String, String>();

		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			HttpSession session=request.getSession();
			
			if (session!=null && session.getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE) != null){
				map.put(GlobalConstants.I18N_LOCAL_LANGUAGE, 
						session.getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE).toString());
			}else {
				//session失效，默认重置为简体中文
				map.put(GlobalConstants.I18N_LOCAL_LANGUAGE, GlobalConstants.I18N_ZH_CN);
			}
			
			writerJSON(map, response);

			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 获取当前用户登录名
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月10日下午5:35:31
	 */
	@RequestMapping(value="getCurUser",method=RequestMethod.GET)
	public void getCurUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> map=new HashMap<String, String>();

		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			
			HttpSession session=request.getSession();
			if (session!=null && session.getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE) != null){
				SysUser user=(SysUser)session.getAttribute(GlobalConstants.SESSION_USER);
				if (user!=null){
					map.put("curUser", user.getLoginName());
				}else {
					noLogin(response);
				}
			}
			writerJSON(map, response);

			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
}
