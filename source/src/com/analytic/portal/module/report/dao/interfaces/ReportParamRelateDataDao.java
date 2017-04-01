package com.analytic.portal.module.report.dao.interfaces;

import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.model.ReportParamRelateData;

import java.util.List;
import java.util.Map;

/**
 * 报表参数Dao
 * @author pengbo
 * 2016-03-11
 */
public interface ReportParamRelateDataDao {
	
	public List<ReportParamRelateData> getRelateDataByType(String type) throws Exception;

}
