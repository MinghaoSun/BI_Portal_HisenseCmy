package com.analytic.portal.module.report.dao.interfaces;

import java.util.List;

import com.analytic.portal.module.report.vo.AlltypeParam;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;

public interface DmsaleTfZykCityMDao {

	/**
	 * 根据条件查询DmsaleTfZykCityM表
	 * @param region 区域
	 * @param market 市场
	 * @param month  年月
	 * @param oltype 线上线下（0线下1线上）
	 * @param type 类型（0额1量）
	 * @return 
	 */
	List<QPLDIYReportVO> getReportResultByParam(String region,String market,String month,String oltype,String type);

	List<Object> getReportResultByParam(AlltypeParam param, String string);

}
