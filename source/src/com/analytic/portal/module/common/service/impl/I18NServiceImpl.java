package com.analytic.portal.module.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.ConfigUtil;
import com.analytic.portal.common.util.LocaleUtil;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.service.I18NService;

@Service
public class I18NServiceImpl implements I18NService {

	/**
	 * 获取当前的国际化语言信息
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午1:34:50
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getCurrentLanguage() throws Exception {
		Map<String, String> map=new HashMap<String, String>();
		String localLanguage="";
		
		try {
			if (GlobalCache.getConfig(GlobalConstants.CONFIG_I18N_START_TYPE).
					equals(GlobalConstants.I18N_START_CONFIG)){
				localLanguage=ConfigUtil.get(GlobalConstants.I18N_LOCAL_LANGUAGE);
			}
			if (GlobalCache.getConfig(GlobalConstants.CONFIG_I18N_START_TYPE).
					equals(GlobalConstants.I18N_START_OS)){
				localLanguage=LocaleUtil.getLocaleProperty().get(GlobalConstants.OS_ENV_LANGUAGE);
			}
			//过滤配置异常
			if (StringUtil.isEmpty(localLanguage)){
				localLanguage=GlobalConstants.I18N_ZH_CN;
			}
			map.put(GlobalConstants.I18N_LOCAL_LANGUAGE, localLanguage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
