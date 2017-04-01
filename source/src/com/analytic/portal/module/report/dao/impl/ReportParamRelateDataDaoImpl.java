package com.analytic.portal.module.report.dao.impl;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.ReportParamDao;
import com.analytic.portal.module.report.dao.interfaces.ReportParamRelateDataDao;
import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.model.ReportParamRelateData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportParamRelateDataDaoImpl implements ReportParamRelateDataDao{

	@Resource
	IBaseDao iBaseDao;
	

	@Override
	public List<ReportParamRelateData> getRelateDataByType(String type) throws Exception {
		String hql="FROM ReportParamRelateData WHERE 1=1 ";
		hql += " and type='"+type+"'";

		List<ReportParamRelateData> list=iBaseDao.getListByHql(hql);
		return list;
	}
}
