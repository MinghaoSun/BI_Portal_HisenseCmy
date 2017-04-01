package com.analytic.portal.module.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.common.util.FieldNameUtil;
import com.analytic.portal.module.system.dao.interfaces.SysRoleMenuDao;
import com.analytic.portal.module.system.model.SysRoleMenu;
@Repository("sysRoleMenuDao")
public class SysRoleMenuDaoImpl extends BaseDaoImp implements SysRoleMenuDao {

	@Override
	public List<SysRoleMenu> getSysRoleMenuList(Map paraMap) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysRoleMenu.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(SysRoleMenu.class);
		for (Object key : paraMap.keySet()) {
			 Object value = paraMap.get(key);
			if (fieldNames.contains(key) && (value != null && !value.equals(""))) {
				criteria.add(Restrictions.eq((String)key, value));
			}
		}
		return super.getHibernateTemplates().findByCriteria(criteria);

	}

	@Override
	public boolean deleteSysRoleMenu4RoleId(String roleId) throws Exception{
		String hql = "delete SysRoleMenu s where s.roleId = " + roleId ;
		
		return super.updateByHql(hql);
	}

}
