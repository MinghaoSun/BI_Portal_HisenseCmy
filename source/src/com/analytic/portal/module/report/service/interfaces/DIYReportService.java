package com.analytic.portal.module.report.service.interfaces;

import java.util.List;

import com.analytic.portal.module.report.vo.AllTypeVO;
import com.analytic.portal.module.report.vo.AlltypeDIYReportVO;
import com.analytic.portal.module.report.vo.AlltypeParam;

/**
 * @description 自主设计报表Service接口
 * @author Minghao
 * @date 2017年4月10日13:47:02
 */
public interface DIYReportService {

	
	List<AllTypeVO> getReportResultByParam();

	AlltypeDIYReportVO getAllTypeResult(AlltypeParam param);

}
