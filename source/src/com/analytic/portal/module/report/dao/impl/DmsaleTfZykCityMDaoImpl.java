package com.analytic.portal.module.report.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.DmsaleTfZykCityMDao;
@Repository("dmsaleTfZykCityMDao")
public class DmsaleTfZykCityMDaoImpl implements DmsaleTfZykCityMDao{
	
	@Autowired
	@Qualifier(value="iBaseDaoHsbi")
	private IBaseDao iBaseDao;

	@SuppressWarnings("unchecked")
	@Override
	public List getReportResultByParam() {
		String strSQL="select region_ggrp,line_name,sum(sale_qty),sum(sale_amt) from dmsale_tf_zyk_city_m  where brand_name='海信' and ol_typ=0 and dt_month='201601' group by line_name,region_ggrp order by region_ggrp,line_name";
		List<Object> list=(List<Object>) iBaseDao.getListBySQL(0, 0, strSQL,new String[]{});
		return list;
	}
	

}
