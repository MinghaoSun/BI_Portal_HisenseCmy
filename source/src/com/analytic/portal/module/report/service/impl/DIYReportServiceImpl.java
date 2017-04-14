package com.analytic.portal.module.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analytic.portal.module.report.dao.interfaces.DmsaleTfZykCityMDao;
import com.analytic.portal.module.report.service.interfaces.DIYReportService;

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
	public List getReportResultByParam() {
		
		return dmsaleTfZykCityMDao.getReportResultByParam();
		
	}

}
