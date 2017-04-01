/*
 * $Id: SessionListener.java,v 1.1 2010/12/24 01:43:45 zl Exp $
 *
 * Copyright (c) 2006 UserEasy
 * 
 */

package com.analytic.portal.common.sys;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * Class description goes here.
 *
 * $Revision: 1.1 $ 
 * $Author: zl $
 */
public class SessionListener  implements HttpSessionListener,ServletContextListener{
	
    public void sessionCreated(HttpSessionEvent event) { 
    	//event.getSession().setAttribute("_listener",new SessionContainer());
    } 

    public void sessionDestroyed(HttpSessionEvent event) {
		
    }

	public void contextDestroyed(ServletContextEvent sce) {
    	
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
	}
}
