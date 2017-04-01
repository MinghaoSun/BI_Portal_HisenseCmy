package com.analytic.portal.module.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.common.util.FieldNameUtil;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.dao.interfaces.ReportUserMappingDao;


@Repository("reportUserMappingDao")
public  class ReportUserMappingDaoImpl  extends BaseDaoImp implements ReportUserMappingDao {
			
	public List<ReportUserMapping> getReportUserMappingList(Map paraMap) throws Exception {
		List<ReportUserMapping> list=new ArrayList<ReportUserMapping>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportUserMapping.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(ReportUserMapping.class);
		for (Object key : paraMap.keySet()) {
			 Object value = paraMap.get(key);
			if (fieldNames.contains(key) && (value != null && !value.equals(""))) {
				criteria.add(Restrictions.eq((String)key, value));
			}
		}
		return list = super.getHibernateTemplates().findByCriteria(criteria);
	}
}
