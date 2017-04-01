package com.analytic.portal.module.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.analytic.portal.module.system.model.SysDic;

@Controller
@RequestMapping("/sysDic")
public class SysDicController extends BaseController{

	/**
	 * 获取字典信息
	 * @param request
	 * @param response
	 * Boger
	 * 2016年5月11日上午11:45:36
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/getSysDic", method=RequestMethod.GET)
	public void getSysDic(HttpServletRequest request, HttpServletResponse response){
		Map map=new HashMap<>();
		List<SysDic> list=new ArrayList<SysDic>();
		
		try {
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
			String dicCode = getParameter(request, "dicCode");
			Map<String, String> dicMap=GlobalCache.getDic(dicCode);
			for(Map.Entry<String, String> entry : dicMap.entrySet()){
				SysDic sysDic=new SysDic();
				sysDic.setDicValue(entry.getValue());
				list.add(sysDic);
			}
			map.put("list",list);
			
			LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
					GlobalConstants.lOGGER_LEVEL_INFO_END);
		} catch (Exception e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
		writerJSON(map, response);
	}

}
