package com.analytic.portal.module.report.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;

@Controller
@RequestMapping("/diyreport")
public class DIYReportContoller extends BaseController{
	
	@Autowired
	private DIYReportService dIYReportService;
	
	@RequestMapping("/testvalue")
	public @ResponseBody String testValue(){
		dIYReportService.getReportResultByParam();
		return null;
	}

}
