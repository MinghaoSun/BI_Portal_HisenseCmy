package com.analytic.portal.module.report.service.interfaces;

import java.util.List;
import com.analytic.portal.module.report.vo.QPLDIYReportVO;

/**
 * @description 自主设计报表Service接口
 * @author Minghao
 * @date 2017年4月10日13:47:02
 */
public interface DIYReportService {

	
	List<QPLDIYReportVO> getReportResultByParam();

}
