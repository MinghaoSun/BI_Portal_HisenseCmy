/*
 * $Id: AppContext.java,v 1.1 2010/12/24 01:43:45 zl Exp $
 *
 * Copyright (c) 2006 UserEasy
 * 
 */

package com.analytic.portal.common.sys;

import org.springframework.context.ApplicationContext;

import com.analytic.portal.common.filter.SpringContextUtil;



/**
 * Class description goes here.
 *
 * $Revision: 1.1 $
 * $Author: zl $
 */
public class AppContext {

	private static AppContext instance;

	//private AbstractApplicationContext appContext;

	public synchronized static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}

	private AppContext() {
		//String[] paths = { "applicationContext.xml"};
		//this.appContext = new ClassPathXmlApplicationContext(paths);
	}

	public ApplicationContext getAppContext() {
		ApplicationContext context=SpringContextUtil.getApplicationContext();
		return context;
	}
}

