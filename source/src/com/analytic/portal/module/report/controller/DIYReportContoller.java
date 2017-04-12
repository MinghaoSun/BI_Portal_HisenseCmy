package com.analytic.portal.module.report.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.analytic.portal.module.common.controller.BaseController;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;

@Controller
@RequestMapping("/diyreport")
public class DIYReportContoller extends BaseController{
	
	@Autowired
	private DIYReportService dIYReportService;
	
	@RequestMapping("/testvalue")
	public void testValue(HttpServletRequest request ,HttpServletResponse response){
		List<QPLDIYReportVO> list=dIYReportService.getReportResultByParam();
		writerJSON(list, response);
	}

}
