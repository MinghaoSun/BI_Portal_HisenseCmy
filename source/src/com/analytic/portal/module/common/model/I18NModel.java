package com.analytic.portal.module.common.model;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.analytic.portal.common.sys.GlobalConstants;

public class I18NModel {
	
	//public final String localLanguage = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getParameter("locale");
	public final String localLanguage = (String) (((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession()).getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE);
}
