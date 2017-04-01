/**
 * 国际化
 */
package com.analytic.portal.module.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.service.I18NService;
import com.analytic.portal.module.common.util.LoggerUtil;

/**
 * 国际化处理
 * @author Boger
 *
 */
@Controller
@RequestMapping("/i18n")
public class I18NController extends BaseController {

	@Resource
	private I18NService i18NService;
	
	/**
	 * 根据操作系统获取本地国际化语言
	 * @param request
	 * @param response
	 * Boger
	 * 2016年3月25日上午10:03:47
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getLocalI18n",method=RequestMethod.GET)
	public void getLocalI18n(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> map=new HashMap<String, String>();

		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			HttpSession session=request.getSession();
			
			if (session!=null && session.getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE) != null){
				map.put(GlobalConstants.I18N_LOCAL_LANGUAGE, 
						session.getAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE).toString());
			}else {
				map=i18NService.getCurrentLanguage();
				if(session!=null)
				session.setAttribute(GlobalConstants.I18N_LOCAL_LANGUAGE, 
						map.get(GlobalConstants.I18N_LOCAL_LANGUAGE));
			}
			
			writerJSON(map, response);

			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
}
