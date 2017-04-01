package com.analytic.portal.module.report.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.service.IEncodingService;
import com.analytic.portal.module.report.dao.interfaces.ReportParamDao;
import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.service.interfaces.ReportParamService;

@Service
public class ReportParamServiceImpl  implements ReportParamService{

	@Resource
	private ReportParamDao reportParamDao;
	@Resource
	private IEncodingService iEncodingService;
	
	/**
	 * 删除报表参数
	 * @param id
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午12:04:30
	 */
	@Override
	public boolean deleteReportParamById(String id) throws Exception {
		return reportParamDao.deleteReportParamById(id);
	}

	/**
	 * 新增报表参数
	 * @param reportParam
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:49:01
	 */
	@Override
	public boolean addReportParam(ReportParam reportParam) throws Exception {
		
		reportParam.setId(DataOperation.getSequenseStr());
		
		String code=GlobalConstants.BUSINESS_ENCODING_REPORT_PARAM;
		code+=iEncodingService.getBussinessCode(ReportParam.class);
		reportParam.setCode(code);
		//reportParam.setCode("3");
		reportParam.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
		
		return reportParamDao.addReportParam(reportParam);
	}

	/**
	 * 修改报表参数
	 * @param reportParam
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:49:17
	 */
	@Override
	public boolean updateReportParam(ReportParam reportParam) throws Exception {
		return reportParamDao.updateReportParam(reportParam);
	}

	/**
	 * 获取报表参数对象
	 * @param id
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:49:30
	 */
	@Override
	public ReportParam getReportParamById(String id) throws Exception {
		return reportParamDao.getReportParamById(id);
	}

	/**
	 * 猎取报表参数集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:49:46
	 */
	@Override
	public List<ReportParam> getReportParamList() throws Exception {
		return reportParamDao.getReportParamList();
	}

	/**
	 * 获取分页的报表参数集合
	 * @param reportParam
	 * @param page
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日下午1:50:03
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getReportParamListByPage(Map formMap) throws Exception {
		return reportParamDao.getReportParamListByPage(formMap);
	}

	/**
	 * getReportParam
	 * @param formMap
	 * @return
	 * @throws Exception
	 * admin
	 * 2016年4月15日下午1:35:19
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ReportParam getReportParam(Map formMap) throws Exception {
		return reportParamDao.getReportParam(formMap);
	}

}
