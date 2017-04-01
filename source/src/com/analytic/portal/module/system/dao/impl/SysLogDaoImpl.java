/**
 * 系统访问日志
 */
package com.analytic.portal.module.system.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.system.dao.interfaces.SysLogDao;

/**
 * 系统访问日志定义
 * @author Boger
 */
@Repository
public class SysLogDaoImpl implements SysLogDao {

	@Resource
	private IBaseDao iBaseDao;
	
	private SimpleDateFormat dateFormatShort=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

	/**
	 * 获取日志信息列表（分页查询）
	 * @param map
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月14日下午3:59:40
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getLogListByPage(Map formMap) throws Exception {
		Map map=new HashMap();
		 
		String hql="FROM SysLog WHERE 1=1 ";
		hql+=getQueryWhere(formMap);
		String hqlCount="SELECT count(*) as count1 "+hql;
		hql+="ORDER BY createTime DESC";
		int currentPage=Integer.valueOf(formMap.get(GlobalConstants.PAGE_CURRENT_PAGE).toString().trim());
		int pageSize=Integer.valueOf(formMap.get(GlobalConstants.PAGE_PAGE_SIZE).toString().trim());
		map=iBaseDao.getListByPage(hql, hqlCount, (currentPage-1)*pageSize, pageSize);
		
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		return map;
	}
	
	/**
	 * 获取最近访问日志列表
	 * @param paraMap
	 * @return
	 * @throws Exception
	 * admin 
	 * 2016年4月11日下午1:24:09
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List getSysLogListByUser(String LoginName) throws Exception {
		List list=new ArrayList<>();
		StringBuilder hql=new StringBuilder();
		
		try {
			hql.append("SELECT menuReportId,MAX(createTime) ");
			hql.append("FROM SysLog WHERE 1=1 ");
			hql.append("AND menuReportId !='' ");
			if (StringUtils.isNotEmpty(LoginName)) {
				hql.append("AND loginName ='" + LoginName + "' ");
			}
			hql.append("GROUP BY menuReportId ");
			hql.append("ORDER BY MAX(createTime) DESC ");
			list=iBaseDao.getListByHql(hql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 组合当前的查询条件
	 * @param map
	 * @return
	 * @throws Exception
	 * Boger
	 * 2015年8月5日下午1:36:35
	 */
	@SuppressWarnings("rawtypes")
	private String getQueryWhere(Map map) throws Exception{
		StringBuilder strWhere=new StringBuilder();
		//登录账户
		if (map.containsKey("loginName")){
			String loginName=map.get("loginName").toString().trim();
			if (loginName!=null&&StringUtil.isNotEmpty(loginName)){
				strWhere.append("AND ");
				strWhere.append("loginName like '%"+loginName+"%' ");
			}
		}
		//用户姓名
		if (map.containsKey("userName")){
			String userName=map.get("userName").toString().trim();
			if (userName!=null&&StringUtil.isNotEmpty(userName)){
				strWhere.append("AND ");
				strWhere.append("userName like '%"+userName+"%' ");
			}
		}
		//访问地址
		if (map.containsKey("reqPath")){
			String reqPath=map.get("reqPath").toString().trim();
			if (reqPath!=null&&StringUtil.isNotEmpty(reqPath)){
				strWhere.append("AND ");
				strWhere.append("reqPath like '%"+reqPath+"%' ");
			}
		}
		//功能菜单
		if (map.containsKey("menuName")){
			String menuName=map.get("menuName").toString().trim();
			if (menuName!=null&&StringUtil.isNotEmpty(menuName)){
				strWhere.append("AND ");
				strWhere.append("menuName like '%"+menuName+"%' ");
			}
		}
		//操作方式
		if (map.containsKey("operType")){
			String operType=map.get("operType").toString().trim();
			if (operType!=null&&StringUtil.isNotEmpty(operType)){
				strWhere.append("AND ");
				strWhere.append("operType ='"+operType+"' ");
			}
		}
		//创建日期
		if (map.containsKey("beginCreateTime")){
			String beginTime=map.get("beginCreateTime").toString().trim();
			beginTime=dateFormatShort.format(dateFormatShort.parse(beginTime));
			if (beginTime!=null&&StringUtil.isNotEmpty(beginTime)){
				strWhere.append("AND ");
				strWhere.append("beginCreateTime>='"+beginTime+" 00:00:00' ");
			}
		}
		if (map.containsKey("endCreateTime")){
			String endTime=map.get("endCreateTime").toString().trim();
			endTime=dateFormatShort.format(dateFormatShort.parse(endTime));
			if (endTime!=null&&StringUtil.isNotEmpty(endTime)){
				strWhere.append("AND ");
				strWhere.append("endCreateTime<='"+endTime+" 23:59:59' ");
			}
		}
		
		return strWhere.toString();
	}
	
}
