package com.analytic.portal.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.analytic.portal.common.sys.GlobalConstants;

public class LocaleUtil {

	public static Map<String, String> getLocaleProperty(){
		Map<String, String> map=new HashMap<String, String>();
		Locale locale=Locale.getDefault();
		
		//获取本地操作系统语言
		String sysLanguage = locale.getLanguage();
		if (sysLanguage.equals(String.valueOf(Locale.CHINESE))){
			map.put(GlobalConstants.OS_ENV_LANGUAGE, GlobalConstants.I18N_ZH_CN);
		}
		if (sysLanguage.equals(String.valueOf(Locale.ENGLISH))){
			map.put(GlobalConstants.OS_ENV_LANGUAGE, GlobalConstants.I18N_EN_US);
		}

		return map;
	}

}
