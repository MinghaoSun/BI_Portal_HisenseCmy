package com.analytic.portal.module.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.dao.impl.BaseDaoImp;
import com.analytic.portal.module.system.dao.interfaces.SysRoleDao;
import com.analytic.portal.module.system.model.SysRole;

@Repository("sysRoleDao")
public class SysRoleDaoImpl extends BaseDaoImp implements SysRoleDao {
	
	@Resource
	IBaseDao iBaseDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getSysRolesListByPage(SysRole sysRole, int currentPage) throws Exception {
		Map map=new HashMap();
		 
		String hql="FROM SysRole WHERE 1=1 ";
		//过滤管理员角色,约定管理员角色默认为SR0000001
		hql+="AND CODE<>'SR0000001' ";
		hql+=getQueryWhere(sysRole);
		String hqlCount="SELECT count(*) as count1 "+hql;
		int pageSize=GlobalConstants.PAGE_SIZE;
		map=super.getListByPage(hql, hqlCount, (currentPage-1)*pageSize, pageSize);
		//增加总页数
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return map;
	}

	private String getQueryWhere(SysRole sysRole){
		StringBuilder strWhere=new StringBuilder();	
		//删除标识
		strWhere.append(" AND  isDelete = '"+sysRole.getIsDelete()+"' ");

		if(StringUtils.isNotEmpty(sysRole.getRoleNameZh())){
			strWhere.append("and ");
			strWhere.append(" (roleNameZh like '%"+sysRole.getRoleNameZh()+"%' or roleNameEn like '%"+sysRole.getRoleNameEn()+"%')");
		}
		if(StringUtils.isNotEmpty(sysRole.getRoleType())){
			strWhere.append(" and ");
			strWhere.append("roleType='"+sysRole.getRoleType()+"'");
		}

		return strWhere.toString();
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> getSysRoleList(SysRole SysRole ) throws Exception {
		// TODO Auto-generated method stub
		String hql = "From  SysRole WHERE 1=1 ";
		hql+=getQueryList(SysRole);
		String hqlCount=hql;
		return iBaseDao.getListByHql(hqlCount);
	}

	private String getQueryList(SysRole SysRole){
		StringBuilder strWhere=new StringBuilder();	
		//删除标识
		strWhere.append(" AND  isDelete = '0' ");
		//是否启用
		strWhere.append(" AND  roleStatus = '1' ");
		if(StringUtils.isNotEmpty(SysRole.getRoleType())){
			strWhere.append(" AND ");
			strWhere.append("roleType='"+SysRole.getRoleType()+"'");
		}

		return strWhere.toString();
	}

	/**
	 * 获取唯一的角色对象
	 * @param roleName
	 * @return
	 * @throws Exception
	 * admin
	 * 2016年4月21日下午2:44:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SysRole getSysRole(String roleNameZh) throws Exception {
		String hql="FROM SysRole WHERE 1=1 ";
		hql += " and isDelete=0 and roleNameZh='"+roleNameZh+"'";
		
		List<SysRole> list=iBaseDao.getListByHql(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	};
	
}
