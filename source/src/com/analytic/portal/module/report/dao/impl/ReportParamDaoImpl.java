package com.analytic.portal.module.report.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.analytic.portal.module.report.model.ReportRefreshParam;
import com.analytic.portal.module.system.model.SysUserParamSetting;
import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.ReportParamDao;
import com.analytic.portal.module.report.model.ReportParam;
@Repository
public class ReportParamDaoImpl implements ReportParamDao{

	@Resource
	IBaseDao iBaseDao;
	
	/**
	 * 删除报表参数对象
	 * @param id
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:47:31
	 */
	@Override
	public boolean deleteReportParamById(String id) throws Exception {
		ReportParam reportParam= getReportParamById(id);

		try {
			reportParam.setIsDelete(GlobalConstants.DATA_STATUS_DELETE);
			
			return iBaseDao.update(reportParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 新增报表参数对象
	 * @param reportParam
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:47:28
	 */
	@Override
	public boolean addReportParam(ReportParam reportParam) throws Exception {
		try {
			return iBaseDao.save(reportParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新报表参数对象
	 * @param reportParam
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:47:11
	 */
	@Override
	public boolean updateReportParam(ReportParam reportParam) throws Exception {
		try{
			return iBaseDao.update(reportParam);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取报表对象
	 * @param id
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:46:01
	 */
	@Override
	public ReportParam getReportParamById(String id) throws Exception {
		ReportParam reportParam=(ReportParam)iBaseDao.getObjById(ReportParam.class, id);
		
		return reportParam;
	}

	/**
	 * 获取报表参数集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:45:22
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportParam> getReportParamList() throws Exception {
		String hql = "from ReportParam where isDelete = '0' ";
		
		return iBaseDao.getListByHql(hql);
	}

	/**
	 * 获取分页的报表参数集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:43:39
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getReportParamListByPage(Map formMap) throws Exception {
		
		Map map=new HashMap();
		
		String hql="FROM ReportParam WHERE 1=1 ";
		hql+=getQueryWhere(formMap);
		String hqlCount="SELECT count(*) as count1 "+hql;
		int currentPage=Integer.valueOf(formMap.get(GlobalConstants.PAGE_CURRENT_PAGE).toString().trim());
		int pageSize=Integer.valueOf(formMap.get(GlobalConstants.PAGE_PAGE_SIZE).toString().trim());
		map=iBaseDao.getListByPage(hql, hqlCount, (currentPage-1)*pageSize, pageSize);
		
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return map;
		
		/*Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer("select id,code ,param_name as paramName,param_key as paramKey,"
				+ "param_order as paramOrder,param_remark as paramRemark,create_time as createTime,create_user as createUser,"
				+ "update_time as updateTime,update_user as updateUser,is_delete as isDelete from report_param where 1=1 and is_delete=0");
		Vector<Object> params = new Vector<>();
		
		if(StringUtils.isNotEmpty(reportParam.getParamKey())){
			sql.append(" and param_key like ? ");
			params.add("%"+reportParam.getParamKey()+"%");
		}
		if(StringUtils.isNotEmpty(reportParam.getParamName())){
			sql.append(" and param_name like ?");
			params.add("%"+reportParam.getParamName()+"%");
		}
		
		sql.append(" order by createTime desc");
		
		try {
			FPage fPage = new FPage();
			fPage.setSql(sql.toString());
			fPage.setCurrentPage(page);
			fPage.setPageSize(GlobalConstants.PAGE_SIZE);
			iBaseDao.getListByJDBCPage(fPage, params);
			map.put("result", fPage.getList());
			map.put("currentPage", fPage.getCurrentPage());
			map.put("totalPages", fPage.getPageCount());
			map.put("count", fPage.getRecordCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;*/
	}

	/**
	 * 获取分页的报表参数集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:43:39
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getUserReportParamSettingListByPage(Map formMap) throws Exception {
		Map map=new HashMap();

		StringBuilder strWhere=new StringBuilder();
		//paramName
		if (formMap.containsKey("paramName")){
			String paramName=formMap.get("paramName").toString().trim();
			if (paramName!=null&&StringUtil.isNotEmpty(paramName)){
				strWhere.append(" AND ");
				strWhere.append(" r1.param_name like '%"+paramName+"%' ");
			}
		}
		//reportParamKey
		if (formMap.containsKey("paramKey")){
			String paramKey=formMap.get("paramKey").toString().trim();
			if (paramKey!=null&&StringUtil.isNotEmpty(paramKey)){
				strWhere.append(" AND ");
				strWhere.append(" r1.param_key like '%"+paramKey+"%' ");
			}
		}
		String hqlTemp="";
		String hql="";
		if (strWhere.toString()!=null&&!"".equals(strWhere.toString().trim())){
			hqlTemp="from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+formMap.get("userId")+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id "+strWhere.toString()+" left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+formMap.get("userId")+"'";
			hql="select r1.*,r2.param_value,r2.param_operat_type,r2.param_value_type,r2.param_value_relate,r3.refresh_value,r2.param_type  from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+formMap.get("userId")+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id "+strWhere.toString()+"left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+formMap.get("userId")+"'";
		}else{
			hqlTemp="from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+formMap.get("userId")+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+formMap.get("userId")+"'";
			hql="select r1.*,r2.param_value,r2.param_operat_type,r2.param_value_type,r2.param_value_relate,r3.refresh_value,r2.param_type  from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+formMap.get("userId")+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+formMap.get("userId")+"'";
		}
		String hqlCount="SELECT count(*) as count1 "+hqlTemp;
		int currentPage=Integer.valueOf(formMap.get(GlobalConstants.PAGE_CURRENT_PAGE).toString().trim());
		int pageSize=Integer.valueOf(formMap.get(GlobalConstants.PAGE_PAGE_SIZE).toString().trim());
		map=iBaseDao.getSqlQueryListByPage(hql, hqlCount, (currentPage-1)*pageSize, pageSize);

		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return map;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ReportRefreshParam> getSysRoleParamAndRefreshParamList(String userId) throws Exception {
		Map map=new HashMap();
		String hql="select r1.*,r2.param_value,r2.param_operat_type,r2.param_value_type,r2.param_value_relate,r3.refresh_value,r2.param_type   from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+userId+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+userId+"'";
		List<ReportRefreshParam> reportRefreshParamList= (List<ReportRefreshParam>) iBaseDao.getListBySql(hql, ReportRefreshParam.class);
		return reportRefreshParamList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ReportRefreshParam> getUserReportParamSettingList(Map formMap) throws Exception {
		String hql="select r1.*,r2.param_value,r2.param_operat_type,r2.param_value_type,r2.param_value_relate,r3.refresh_value,r2.param_type   from report_param r1 inner join (select t.* from (SELECT  * from sys_role_param where role_id in (select role_id from sys_user_role t1 inner join sys_role t2 on t1.role_id=t2.id  where t1.user_id='"+formMap.get("userId")+"' and t2.is_delete='0' and t2.role_type='3'))t)r2 on r1.id=r2.param_id left join sys_user_param_setting as r3 on r1.id=r3.param_id and r3.user_id='"+formMap.get("userId")+"'";

		List<ReportRefreshParam> reportParamList= (List<ReportRefreshParam>) iBaseDao.getListBySql(hql, ReportRefreshParam.class);
		return reportParamList;
	}

	/**
	 * 组合当前的查询条件
	 * @param map
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2015年8月5日下午1:36:35
	 */
	@SuppressWarnings("rawtypes")
	private String getQueryWhere(Map map) throws Exception{
		StringBuilder strWhere=new StringBuilder();
		
		//删除标识
		strWhere.append("AND ");
		strWhere.append("isDelete = '"+GlobalConstants.DATA_STATUS_NORMAL+"' ");
		
		//参数CODE
		if (map.containsKey("code")){
			String code=map.get("code").toString().trim();
			if (code!=null&&StringUtil.isNotEmpty(code)){
				strWhere.append("AND ");
				strWhere.append("code like '%"+code+"%' ");
			}
		}
		//paramName
		if (map.containsKey("paramName")){
			String paramName=map.get("paramName").toString().trim();
			if (paramName!=null&&StringUtil.isNotEmpty(paramName)){
				strWhere.append("AND ");
				strWhere.append("paramName like '%"+paramName+"%' ");
			}
		}
		//reportParamKey
		if (map.containsKey("paramKey")){
			String paramKey=map.get("paramKey").toString().trim();
			if (paramKey!=null&&StringUtil.isNotEmpty(paramKey)){
				strWhere.append("AND ");
				strWhere.append("paramKey like '%"+paramKey+"%' ");
			}
		}
		//paramOrder
		if (map.containsKey("paramOrder")){
			String paramOrder=map.get("paramOrder").toString().trim();
			if (paramOrder!=null&&StringUtil.isNotEmpty(paramOrder)){
				strWhere.append("AND ");
				strWhere.append("paramOrder like '%"+paramOrder+"%' ");
			}
		}
		
		return strWhere.toString();
	}
	
	
	/**
	 * 组合当前的查询条件
	 * @param map
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2015年8月5日下午1:36:35
	 */
	@SuppressWarnings("rawtypes")
	private String getQueryWhereOnly(Map map) throws Exception{
		StringBuilder strWhere=new StringBuilder();
		
		//删除标识
		strWhere.append("AND ");
		strWhere.append("isDelete = '"+GlobalConstants.DATA_STATUS_NORMAL+"' ");
		
		//参数CODE
		if (map.containsKey("code")){
			String code=map.get("code").toString().trim();
			if (code!=null&&StringUtil.isNotEmpty(code)){
				strWhere.append("AND ");
				strWhere.append("code = '"+code+"' ");
			}
		}
		//paramName
		if (map.containsKey("paramName")){
			String paramName=map.get("paramName").toString().trim();
			if (paramName!=null&&StringUtil.isNotEmpty(paramName)){
				strWhere.append("AND ");
				strWhere.append("paramName = '"+paramName+"' ");
			}
		}
		//reportParamKey
		if (map.containsKey("paramKey")){
			String paramKey=map.get("paramKey").toString().trim();
			if (paramKey!=null&&StringUtil.isNotEmpty(paramKey)){
				strWhere.append("AND ");
				strWhere.append("paramKey = '"+paramKey+"' ");
			}
		}
		//paramOrder
		if (map.containsKey("paramOrder")){
			String paramOrder=map.get("paramOrder").toString().trim();
			if (paramOrder!=null&&StringUtil.isNotEmpty(paramOrder)){
				strWhere.append("AND ");
				strWhere.append("paramOrder = '"+paramOrder+"' ");
			}
		}
		
		return strWhere.toString();
	}

	/**
	 * 获取第一条报表参数对象
	 * @param formMap
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年4月15日下午1:32:33
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ReportParam getReportParam(Map formMap) throws Exception {
		String hql="FROM ReportParam WHERE 1=1 ";
		hql+=getQueryWhereOnly(formMap);
		
		List<ReportParam> list=iBaseDao.getListByHql(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
