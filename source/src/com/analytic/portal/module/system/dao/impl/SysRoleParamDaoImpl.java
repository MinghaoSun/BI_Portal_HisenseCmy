package com.analytic.portal.module.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.common.util.FieldNameUtil;
import com.analytic.portal.module.system.dao.interfaces.SysRoleParamDao;
import com.analytic.portal.module.system.model.SysRoleParam;

@Repository("sysRoleParamDao")
public class SysRoleParamDaoImpl extends BaseDaoImp implements SysRoleParamDao {

	@Override
	public List<SysRoleParam> getSysRoleParamList(Map paraMap) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRoleParam.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(SysRoleParam.class);
		for (Object key : paraMap.keySet()) {
			 Object value = paraMap.get(key);
			if (fieldNames.contains(key) && (value != null && !value.equals(""))) {
				criteria.add(Restrictions.eq((String)key, value));
			}
		}
		return super.getHibernateTemplates().findByCriteria(criteria);

	}

	@Override
	public boolean deleteSysRoleParam4RoleId(String roleId) throws Exception{
		String hql = "delete SysRoleParam s where s.roleId = " + roleId ;
		
		return super.updateByHql(hql);
	}
	
}
