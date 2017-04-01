/**
 * 操作系统
 */
package com.analytic.portal.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.analytic.portal.common.sys.GlobalConstants;

/**
 * 操作系统工具类
 * @author Boger
 */
public class OSUtil {
	
	public static Map<String, String> getOSProperty(){
		Properties props=System.getProperties();
		Map<String, String> map=new HashMap<String, String>();
		
		//获取操作系统语言
		String sysLanguage = props.getProperty("user.language");
		map.put(GlobalConstants.OS_ENV_LANGUAGE, sysLanguage);
		//获取操作系统国家
		String sysCountry=props.getProperty("user.country");
		map.put(GlobalConstants.OS_ENV_COUNTRY, sysCountry);
		
		return map;
	}
}
