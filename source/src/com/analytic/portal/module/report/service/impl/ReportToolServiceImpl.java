package com.analytic.portal.module.report.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.service.IEncodingService;
import com.analytic.portal.module.report.dao.interfaces.ReportToolDao;
import com.analytic.portal.module.report.model.ReportTool;
import com.analytic.portal.module.report.service.interfaces.ReportToolService;

/**
 * 报表工具service
 * @author pengbo
 * 2016-03-09
 */
@Service
public class ReportToolServiceImpl  implements ReportToolService{

	@Resource
	private ReportToolDao reportToolDao;
	@Resource
	private IEncodingService iEncodingService;
	
	/**
	 * 删除报表工具
	 * @param id
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:18:49
	 */
	@Override
	public boolean deleteReportToolById(String id) throws Exception {
		/*ReportTool reportTool=reportToolDao.getObjById(ReportTool.class, id);
		
		reportTool.setDeleteStatus(GlobalConstants.DATA_STATUS_DELETE);*/
		return reportToolDao.deleteReportToolById(id);
	}

	/**
	 * 添加报表工具
	 * @param reportTool
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:18:33
	 */
	@Override
	public boolean addReportTool(ReportTool reportTool) throws Exception {
		
		reportTool.setId(DataOperation.getSequenseStr());
		
		String code=GlobalConstants.BUSINESS_ENCODING_REPORT_TOOL;
		code+=iEncodingService.getBussinessCode(ReportTool.class);
		
		reportTool.setCode(code);
		//reportParam.setCode("3");
		reportTool.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
		
		return reportToolDao.addReportTool(reportTool);
	}

	/**
	 * 更改报表工具
	 * @param reportTool
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:18:05
	 */
	@Override
	public boolean updateReportTool(ReportTool reportTool) throws Exception {
		return reportToolDao.updateReportTool(reportTool);
	}

	/**
	 * 获取报表工具
	 * @param id
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:17:58
	 */
	@Override
	public ReportTool getReportToolById(String id) throws Exception{
		return reportToolDao.getReportToolById(id);
	}
	
	/**
	 * 获取报表工具
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:17:25
	 */
	@Override
	public List<ReportTool> getReportToolList() throws Exception {
		return reportToolDao.getReportToolList();
	}
	
	/**
	 * 根据条件获取唯一的报表工具
	 * @param formMap
	 * @return
	 * @throws Exception
	 * admin
	 * 2016年4月15日上午11:03:45
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ReportTool getReportTool(Map formMap) throws Exception {
		return reportToolDao.getReportTool(formMap);
	}
	
	/**
	 * 获取报表工具集合
	 * @param reportTool
	 * @param page
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:19:11
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getReportToolListByPage(Map formMap) throws Exception {
		// TODO Auto-generated method stub
		return reportToolDao.getReportToolListByPage(formMap);
	}
	
}
