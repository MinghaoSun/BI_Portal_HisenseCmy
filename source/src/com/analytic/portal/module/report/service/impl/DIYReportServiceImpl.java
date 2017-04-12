package com.analytic.portal.module.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytic.portal.module.report.dao.interfaces.DmsaleTfZykCityMDao;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;

/**
 * @description 自主设计报表Service接口实现类
 * @author Minghao
 * @date 2017年4月10日13:47:46
 */
@Service("dIYReportService")
public class DIYReportServiceImpl implements DIYReportService {
	
	@Autowired
	private DmsaleTfZykCityMDao dmsaleTfZykCityMDao;

	@Override
	public List<QPLDIYReportVO> getReportResultByParam() {
		//2016年1月份所有大区的线下销售额情况
		return dmsaleTfZykCityMDao.getReportResultByParam(null,null,"201601","0","0");
		
	}

}
