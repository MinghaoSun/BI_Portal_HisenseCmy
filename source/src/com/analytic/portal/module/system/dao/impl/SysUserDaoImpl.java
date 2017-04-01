package com.analytic.portal.module.system.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.common.util.FieldNameUtil;
import com.analytic.portal.module.report.model.ReportUserMapping;
import com.analytic.portal.module.system.dao.interfaces.SysUserDao;
import com.analytic.portal.module.system.model.SysUser;

@Repository("sysUserDao")
public class SysUserDaoImpl extends BaseDaoImp implements SysUserDao {
	@Resource
	private IBaseDao iBaseDao;

	/**
	 * 查询用户列表
	 * @param paraMap
	 * @return
	 * @throws Exception
	 *   
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysUser> getSysUserList(Map paraMap) throws Exception {
		List<SysUser> list = new ArrayList<SysUser>();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		List<String> fieldNames = FieldNameUtil.getFieldNames(SysUser.class);
		for (Object key : paraMap.keySet()) {
			Object value = paraMap.get(key);
			if (fieldNames.contains(key) && (value != null && !value.equals(""))) {
				criteria.add(Restrictions.eq((String) key, value));
			}
		}
		list = super.getHibernateTemplates().findByCriteria(criteria);
		
		return list;
	}

	/**
	 * 获取用户查询列表（分页查询）
	 * @param sysUser
	 * @param page
	 * @return
	 * Boger
	 * 2016年4月21日下午1:16:39
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getSysUsersListByPage(SysUser sysUser, int page) {
		Map map=new HashMap();
		String hql="FROM SysUser WHERE 1=1 ";
		hql+=getQueryWhere(sysUser);
		String hqlCount="SELECT count(*) as count1 "+hql;
		hql+="ORDER BY createTime DESC";
		int pageSize=GlobalConstants.PAGE_SIZE;
		
		try {
			map=super.getListByPage(hql, hqlCount, (page-1)*pageSize, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//增加总页数
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", page);
		return map;
	}

	/**
	 * 组合查询条件
	 * @param sysUser
	 * @return
	 * Boger
	 * 2016年4月21日下午1:17:13
	 */
	private String getQueryWhere(SysUser sysUser){
		StringBuilder strWhere=new StringBuilder();	
		//删除标识
		strWhere.append(" AND  isDelete = '"+sysUser.getIsDelete()+"' ");
		
		strWhere.append("AND  loginName!='admin'");
		
		if(StringUtils.isNotEmpty(sysUser.getLoginName())){
			strWhere.append(" and ");
			strWhere.append("loginName='"+sysUser.getLoginName()+"'");
		}
		if(StringUtils.isNotEmpty(sysUser.getUserFullName())){
			strWhere.append(" and ");
			strWhere.append("userFullName='"+sysUser.getUserFullName()+"'");
		}
		
		if(StringUtils.isNotEmpty(sysUser.getUserMobile())){
			strWhere.append(" and ");
			strWhere.append("userMobile='"+sysUser.getUserMobile()+"'");
		}

		return strWhere.toString();
	};
	
	/**
	 * 获取用户
	 * @param loginName
	 * @return
	 * Boger
	 * 2016年4月21日下午1:17:47
	 */
	@SuppressWarnings("unchecked")
	public SysUser getSysUsers(String loginName) {
		String hql="from SysUser u where u.loginName=? and u.isDelete=0";
		List<SysUser> list=iBaseDao.getListByHql(hql,new Object[]{loginName});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更新用户
	 * @param sysUser
	 * @return
	 * Boger
	 * 2016年4月21日下午1:18:15
	 */
	@Override
	public boolean updateSysUser4Role(SysUser sysUser) {
		if (null != sysUser) {
			String sql = "update sys_user set login_name=?,user_enabled=?,user_full_name=?,"
					+ "last_name=?,login_password=?, user_email=?,user_mobile=?,user_birthday=?,"
					+ "user_sex=?,create_user=?,create_time=?,update_user = ?,update_time = ? where id=?";
			List<Object> list = new ArrayList<>();
			list.add(sysUser.getId());
			list.add(sysUser.getLoginName());
			list.add(sysUser.getUserEnabled());
			list.add(sysUser.getUserFullName());
			list.add(sysUser.getLastName());
			list.add(sysUser.getLoginPassword());
			list.add(sysUser.getUserEmail());
			list.add(sysUser.getUserMobile());
			list.add(sysUser.getUserBirthday());
			list.add(sysUser.getUserSex());
			list.add(sysUser.getCreateUser());
			list.add(sysUser.getCreateTime());
			list.add(sysUser.getUpdateUser());
			list.add(sysUser.getUpdateTime());
			return super.updateByListSQL(sql, list);
		}
		return false;
	}

	/**
	 * 根据用户ID和报表工具ID获取报表映射信息
	 * @param reportToolId
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月19日下午2:49:39
	 */
	@Override
	public ReportUserMapping getReportUserMapping(String reportToolId,
			String userId) throws Exception {
		String hql="";
		ReportUserMapping reportUserMapping=new ReportUserMapping();
		
		hql+="FROM ReportUserMapping WHERE 1=1 ";
		hql+="AND userId='"+userId+"' ";
		hql+="AND reportToolId='"+reportToolId+"' ";
		reportUserMapping=(ReportUserMapping)iBaseDao.getObjByHql(hql);
		
		return reportUserMapping;
	}

}
