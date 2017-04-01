package com.analytic.portal.common.sys;

import java.util.ArrayList;
import java.util.HashMap;

public class SysConfig {

	@SuppressWarnings("rawtypes")
	public static String getSysConfigValue(String strArgKey) {
		try {
			ArrayList ls = DataOperation
					.getDatas("select * from sys_config where param_code='"
							+ strArgKey + "'");
			if (ls == null || ls.size() == 0) {

				return "";
			} else {
				HashMap map = (HashMap) ls.get(0);

				return (String) map.get("param_value");
			}
		} catch (Exception e) {
			return "";
		}
	}
}
