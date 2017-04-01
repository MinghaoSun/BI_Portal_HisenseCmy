/*
 * $Id: LoggingInterceptor.java,v 1.1 2010/12/24 01:43:45 zl Exp $
 *
 * Copyright (c) 2006 UserEasy 
 * 
 */

package com.analytic.portal.common.sys;

import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * Class description goes here.
 *
 * $Revision: 1.1 $
 * $Author: zl $
 */
public class LoggingInterceptor implements MethodBeforeAdvice {

	
	public void before(Method method, Object[] objects, Object o)
			throws Throwable {
	}
}
