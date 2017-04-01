/**
 * 系统菜单
 */
package com.analytic.portal.module.system.dao.impl;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.common.util.FieldNameUtil;
import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.system.dao.interfaces.SysMenuDao;
import com.analytic.portal.module.system.dao.interfaces.SysUserPersonalizedSettingDao;
import com.analytic.portal.module.system.model.SysMenu;
import com.analytic.portal.module.system.model.SysUserParamSetting;
import com.analytic.portal.module.system.model.SysUserReportSetting;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统菜单定义
 * @author Boger
 */
@Repository
public class SysUserPersonalizedSettingDaoImpl extends BaseDaoImp implements SysUserPersonalizedSettingDao {

	@Resource
	IBaseDao iBaseDao;
	
	private SimpleDateFormat dateFormatShort=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

	/**
	 * 根据用户ID获取报表菜单地址
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月18日下午4:22:48
	 */
	@Override
	public void deleteUserReportSetting(String userId,String menuId, String settingType) throws Exception {
		String hql="";
		
		hql+="DELETE FROM SysUserReportSetting WHERE 1=1 ";
		hql+="AND userId='"+userId+"'";
		if (menuId!=null&&!"".equals(menuId.trim())){
			hql+="AND menuId='"+menuId+"'";
		}
		hql+="AND settingType='"+settingType+"'";
		iBaseDao.executeByHql(hql);
	}


	@Override
	public List<SysUserReportSetting> getUserReportSettingList(Map paraMap) throws Exception {
		List<SysUserReportSetting> list = new ArrayList<SysUserReportSetting>();

		DetachedCriteria criteria = DetachedCriteria.forClass(SysUserReportSetting.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(SysUserReportSetting.class);
		for (Object key : paraMap.keySet()) {
			Object value = paraMap.get(key);
			if (fieldNames.contains(key) && (value != null && !value.equals(""))) {
				criteria.add(Restrictions.eq((String) key, value));
			}
		}
		list = super.getHibernateTemplates().findByCriteria(criteria);

		return list;
	}

	@Override
	public List<SysUserParamSetting> getUserParamSettingList(String userId, String paramId) throws Exception {
		List<SysUserParamSetting> list = new ArrayList<SysUserParamSetting>();

		DetachedCriteria criteria = DetachedCriteria.forClass(SysUserParamSetting.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(SysUserParamSetting.class);
		if (fieldNames.contains("userId") && (userId != null && !userId.equals(""))) {
			criteria.add(Restrictions.eq("userId", userId));
		}
		if (fieldNames.contains("paramId") && (paramId != null && !paramId.equals(""))) {
			criteria.add(Restrictions.eq("paramId", paramId));
		}
		list = super.getHibernateTemplates().findByCriteria(criteria);

		return list;
	}

	@Override
	public void deleteUserParamSetting(String userId,String paramId) throws Exception {
		String hql="";

		hql+="DELETE FROM SysUserParamSetting WHERE 1=1 ";
		hql+="AND userId='"+userId+"'";
		hql+="AND paramId='"+paramId+"'";
		iBaseDao.executeByHql(hql);
	}
}
