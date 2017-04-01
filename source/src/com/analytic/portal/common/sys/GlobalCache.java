package com.analytic.portal.common.sys;

import java.util.HashMap;
import java.util.Map;

import com.yzd.plat.common.util.ehcache.CacheUtil;

public class GlobalCache {
	
	/**
	 * 获取系统配置信息
	 * @param paramCode
	 * @return
	 */
	public static String getConfig(String paramCode){
		Object paramObj=CacheUtil.getInstance(GlobalConstants.CACHE_CONFIG_SIGN).getCacheValue(paramCode);
		if(paramObj!=null){
			return paramObj.toString();
		}else{
			return null;
		}
	}

	/**
	 * 获取数据字典信息
	 * @param paramCode
	 * @return
	 * Boger
	 * 2016年3月10日下午1:39:26
	 */
	public static Map<String, String> getDic(String dicCode){
		
		Map<String, String> dicMap=new HashMap<String, String>();
	
		Object dicObj=CacheUtil.getInstance(GlobalConstants.CACHE_DIC_SIGN).getCacheValue(dicCode);
		if(dicObj!=null){
			String[] dicArrary=dicObj.toString().split(GlobalConstants.DIC_ITEM_SPLIT_SIGN);
			//菜单类型
			if (GlobalConstants.DIC_SYS_MENU_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_MENU_TYPE_MANAGE, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_MENU_TYPE_REPORT, dicArrary[1]);
				dicMap.put(GlobalConstants.SYS_MENU_TYPE_OTHER, dicArrary[2]);
			}
			//用户性别
			if (GlobalConstants.DIC_SYS_USER_SEX.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_USER_SEX_MALE, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_USER_SEX_FEMALE, dicArrary[1]);
			}
			//操作状态
			if (GlobalConstants.DIC_SYS_OPER_STAUTS.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_OPER_STATUS_ENABLE, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_OPER_STATUS_DISABLE, dicArrary[1]);
			}
			//角色类别
			if (GlobalConstants.DIC_SYS_ROLE_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_ROLE_TYPE_FUNCTION, dicArrary[0]);
				//dicMap.put(GlobalConstants.SYS_ROLE_TYPE_REPORT, dicArrary[1]);
				dicMap.put(GlobalConstants.SYS_ROLE_TYPE_DATA, dicArrary[1]);
			}
			//操作类型
			if (GlobalConstants.DIC_SYS_OPER_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_LOGIN, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_LOGOUT, dicArrary[1]);
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_ADD, dicArrary[2]);
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_DELETE, dicArrary[3]);
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_UPDATE, dicArrary[4]);
				dicMap.put(GlobalConstants.SYS_OPER_TYPE_QUERY, dicArrary[5]);
			}
			//报表工具
			if (GlobalConstants.DIC_REPORT_TOOL_CATEGORY.equals(dicCode)){
				dicMap.put(GlobalConstants.REPORT_TOOL_CATEGORY_BO, dicArrary[0]);
				dicMap.put(GlobalConstants.REPORT_TOOL_CATEGORY_TABLEAU, dicArrary[1]);
				dicMap.put(GlobalConstants.REPORT_TOOL_CATEGORY_QV, dicArrary[2]);
			}
			//报表工具展现方式
			if (GlobalConstants.DIC_REPORT_DISPLAY_MODE.equals(dicCode)){
				dicMap.put(GlobalConstants.REPORT_DISPLAY_MODE_TAB, dicArrary[0]);
				dicMap.put(GlobalConstants.REPORT_DISPLAY_MODE_WINDOW, dicArrary[1]);
			}
			//报表工具展现方式
			if (GlobalConstants.DIC_REPORT_DISPLAY_MODE.equals(dicCode)){
				dicMap.put(GlobalConstants.REPORT_DISPLAY_MODE_TAB, dicArrary[0]);
				dicMap.put(GlobalConstants.REPORT_DISPLAY_MODE_WINDOW, dicArrary[1]);
			}
			//报表菜单类型
			if (GlobalConstants.DIC_REPORT_MENU_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.REPORT_MENU_TYPE_DIRECTORY, dicArrary[0]);
				dicMap.put(GlobalConstants.REPORT_MENU_TYPE_REPORT, dicArrary[1]);
			}
			//内置对象
			if (GlobalConstants.DIC_OBJECT_PARAM.equals(dicCode)){
				dicMap.put(GlobalConstants.INNER_OBJECT_LOGIN_NAME, dicArrary[0]);
			}
			/*新增*/
			if (GlobalConstants.DIC_SYS_PARAM_RELATE_DATA.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_PARAM_RELATE_DATA_COMPANY, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_PARAM_RELATE_DATA_CATEGORY, dicArrary[1]);
				dicMap.put(GlobalConstants.SYS_PARAM_RELATE_DATA_CALENDAR, dicArrary[2]);
			}

			if (GlobalConstants.DIC_SYS_PARAM_OPERAT_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_PARAM_OPERAT_TYPE_RADIO, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_PARAM_OPERAT_TYPE_MULTISELECT, dicArrary[1]);
			}

			if (GlobalConstants.DIC_SYS_PARAM_VALUE_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.SYS_PARAM_OPERAT_TYPE_SINGLEVALUE, dicArrary[0]);
				dicMap.put(GlobalConstants.SYS_PARAM_OPERAT_TYPE_MULTIVALUE, dicArrary[1]);
			}
			
			if (GlobalConstants.DIC_REPORT_DESIGN_TYPE.equals(dicCode)){
				dicMap.put(GlobalConstants.REPORT_MENU_DESIGNTYPE_JICHENG, dicArrary[0]);
				dicMap.put(GlobalConstants.REPORT_MENU_DESIGNTYPE_DIY, dicArrary[1]);
			}
			
			return dicMap;
		}else{
			return null;
		}
	}
	
}
