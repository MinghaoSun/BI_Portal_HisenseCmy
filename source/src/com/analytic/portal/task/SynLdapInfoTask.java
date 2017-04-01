package com.analytic.portal.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.analytic.portal.module.system.service.interfaces.SysUserService;

@Component
@Lazy(false)
public class SynLdapInfoTask {
	
	private static final Logger logger=LoggerFactory.getLogger(SynLdapInfoTask.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * @description 每天晚上10点执行一次 更新数据库中用户属性是ldap的用户的信息
	 *
	 */
	@Scheduled(cron = "0 0 22 * * ?")
	//@Scheduled(cron = "10 * * * * ?")
	public void synLdapInfo(){
		logger.info("syn ldap userinfo start..");
		try {
			sysUserService.doSynLdapUserInfo();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("syn ldap userinfo error");
		}
		logger.info("syn ldap userinfo stop..");
		
	}

}
