/**
 * 系统菜单
 */
package com.analytic.portal.module.system.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalCache;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.model.ReportMenuParam;
import com.analytic.portal.module.report.model.ReportMenuUrl;
import com.analytic.portal.module.system.dao.interfaces.SysMenuDao;
import com.analytic.portal.module.system.model.SysMenu;

/**
 * 系统菜单定义
 * @author Boger
 */
@Repository
public class SysMenuDaoImpl implements SysMenuDao {

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
	public void deleteReportMenuUrlByUserId(String userId) throws Exception {
		String hql="";

		hql+="DELETE FROM ReportMenuUrl WHERE 1=1 ";
		hql+="AND userId='"+userId+"'";
		iBaseDao.executeByHql(hql);
	}

	@Override
	public void deleteReportMenuUrlByUserIdAndMenuId(String userId,String menuId) throws Exception {
		String hql="";

		hql+="DELETE FROM ReportMenuUrl WHERE 1=1 ";
		hql+="AND userId='"+userId+"'";
		hql+="AND menuId='"+menuId+"'";
		iBaseDao.executeByHql(hql);
	}

	/**
	 * 根据用户ID获取报表菜单信息
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月18日下午5:38:26
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<SysMenu> getReportMenuByUserId(String userId) throws Exception {
		List<SysMenu> list=new ArrayList<SysMenu>();
		StringBuilder reportMenuBuilder=new StringBuilder();
		
		//用户角色表信息
		reportMenuBuilder.append("FROM SysMenu WHERE 1=1 ");
		reportMenuBuilder.append("AND menuAttribute='"+GlobalCache.getDic(GlobalConstants.DIC_REPORT_MENU_TYPE).
				get(GlobalConstants.REPORT_MENU_TYPE_REPORT)+"' ");
		reportMenuBuilder.append("AND id IN (");
		reportMenuBuilder.append("SELECT menuId FROM SysRoleMenu WHERE roleId IN ( ");
		reportMenuBuilder.append("SELECT roleId FROM SysUserRole WHERE userId='"+userId+"')) ");

		list=iBaseDao.getListByHql(reportMenuBuilder.toString());

		return list;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<SysMenu> getReportMenuByMenuId(String menuId) throws Exception {
		List<SysMenu> list=new ArrayList<SysMenu>();
		StringBuilder reportMenuBuilder=new StringBuilder();

		//用户角色表信息
		reportMenuBuilder.append("FROM SysMenu WHERE 1=1 ");
		reportMenuBuilder.append("AND id='"+menuId+"'");

		list=iBaseDao.getListByHql(reportMenuBuilder.toString());

		return list;
	}

	/**
	 * 按条件获取菜单列表信息
	 * @param formMap
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月23日上午11:06:14
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysMenu> getMenuList(Map formMap) throws Exception {
		List<SysMenu> list=new ArrayList<SysMenu>();
		 
		String hql="FROM SysMenu WHERE 1=1 ";
		hql+=getQueryWhere(formMap);
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}

	/**
	 * 获取报表菜单参数列表
	 * @param menuId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月29日下午5:42:36
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportMenuParam> getReportMenuParamListByMenuId(String menuId)
			throws Exception {
		List<ReportMenuParam> list=new ArrayList<ReportMenuParam>();
		 
		String hql="FROM ReportMenuParam WHERE 1=1 ";
		hql+="AND menuId='"+menuId+"' ";
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}

	/**
	 * 删除报表菜单对应的参数信息
	 * @param menuId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月22日下午5:02:15
	 */
	@Override
	public void deleteReportMenuParamByMenuId(String menuId)
			throws Exception {
		String hql="DELETE FROM ReportMenuParam WHERE menuId='"+menuId+"'";
		iBaseDao.executeByHql(hql);
	}

	/**
	 * 分页查询系统菜单列表
	 * @param formMap
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月22日下午5:12:27
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getMenuListByPage(Map formMap) throws Exception {
		Map map=new HashMap();
		 
		String hql="FROM SysMenu WHERE 1=1 ";
		hql+=getQueryWhere(formMap);
		String hqlCount="SELECT count(*) as count1 "+hql;
		hql+="ORDER BY menuOrder ASC,createTime DESC ";
		int currentPage=Integer.valueOf(formMap.get(GlobalConstants.PAGE_CURRENT_PAGE).toString().trim());
		int pageSize=Integer.valueOf(formMap.get(GlobalConstants.PAGE_PAGE_SIZE).toString().trim());
		map=iBaseDao.getListByPage(hql, hqlCount, (currentPage-1)*pageSize, pageSize);
		//增加总页数
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return map;
	}
	
	/**
	 * 获取集成报表的有效访问地址
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月25日下午3:42:26
	 */
	@Override
	public ReportMenuUrl getReportMenuUrl(String userId, String menuId) throws Exception {
		ReportMenuUrl reportMenuUrl=new ReportMenuUrl();

		String hql="FROM ReportMenuUrl WHERE 1=1 ";
		hql+="AND menuId='"+menuId+"' ";
		hql+="AND userId='"+userId+"' ";
		reportMenuUrl=(ReportMenuUrl)iBaseDao.getObjByHql(hql);
		
		return reportMenuUrl;
	}
	
	/**
	 * 根据父级菜单ID获取菜单列表信息
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月25日下午4:28:07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getReportMenuListByParentMenuId(String menuParentId, String userId)
			throws Exception {
		List<SysMenu> list=new ArrayList<>();
		StringBuilder hqlBuilder=new StringBuilder();
		
		//父级菜单信息
		hqlBuilder.append("FROM SysMenu WHERE 1=1 ");
		hqlBuilder.append("AND menuParentId ='"+menuParentId+"' ");
		//控制用户选择的功能角色
		hqlBuilder.append(getUserMenuQuery(userId));
		
		list=iBaseDao.getListByHql(hqlBuilder.toString());
		
		return list;
	}
	
	/**
	 * 根据用户获取功能菜单
	 * 保持2级菜单关系
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月25日下午4:51:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getMenuHierarchyListByUserId(String userId)
			throws Exception {
		List<SysMenu> list=new ArrayList<>();
		StringBuilder hqlBuilder=new StringBuilder();
		String hqlTemp="SELECT id FROM SysMenu WHERE menuParentId =''";
		//用户选择的菜单信息
		hqlBuilder.append("FROM SysMenu WHERE 1=1 ");
		hqlBuilder.append("AND isDelete='"+GlobalConstants.DATA_STATUS_NORMAL+"' ");
		hqlBuilder.append(getUserMenuQuery(userId));
		//控制两级菜单数据(包括菜单条)
		hqlBuilder.append("AND id IN ( ");
		hqlBuilder.append(hqlTemp+" ");
		hqlBuilder.append("OR menuParentId IN ("+hqlTemp+") ");
		hqlBuilder.append("OR menuParentId IN ( SELECT id FROM SysMenu WHERE menuParentId IN ("+hqlTemp+"))");
		hqlBuilder.append("OR menuParentId IN ( SELECT id FROM SysMenu WHERE menuParentId IN (SELECT id FROM SysMenu WHERE menuParentId IN ("+hqlTemp+")))");
		hqlBuilder.append(" ) ");
		hqlBuilder.append("ORDER BY menuOrder ASC");
		list=iBaseDao.getListByHql(hqlBuilder.toString());
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getPersonalSettingMenuHierarchyListByUserId(String userId,String settingType)
			throws Exception {
		List<SysMenu> list=new ArrayList<>();
		String hqlTemp=" select t2.* from sys_user_report_setting as t1 inner JOIN sys_menu as t2 on t1.menu_id=t2.id ";
		hqlTemp+=" where t1.user_id='"+userId+"' and t1.setting_type='"+settingType+"'";
		hqlTemp+=" and t2.id in";
		hqlTemp+="(SELECT menu_id from  sys_role_menu  where role_id in( " +
				"SELECT role_id from sys_user_role where user_id='"+userId+"' and role_id in( " +
				"SELECT id from sys_role where is_delete='0' and role_status='1')))";
		hqlTemp+="ORDER BY t2.menu_order ASC";
		list= (List<SysMenu>) iBaseDao.getListBySql(hqlTemp,SysMenu.class);
		return list;
	}


	/**
	 * 根据用户ID获取用户角色相关所有报表
	 * @param userId
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月25日下午4:51:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getMenuSettingListByUserId(Map formMap,String userId)
			throws Exception {
		List<SysMenu> list=new ArrayList<>();
		StringBuilder hqlBuilder=new StringBuilder();
		//用户选择的菜单信息
		hqlBuilder.append("FROM SysMenu WHERE 1=1 ");
		hqlBuilder.append("AND menuType='02' ");

		hqlBuilder.append(getQueryWhere(formMap));

		hqlBuilder.append("AND isDelete='"+GlobalConstants.DATA_STATUS_NORMAL+"' ");
		hqlBuilder.append(getUserMenuSettingQuery(userId));
		hqlBuilder.append("ORDER BY menuOrder ASC");

		Map map=new HashMap();
		String hqlCount="SELECT count(*) as count1 "+hqlBuilder.toString();
		int currentPage=Integer.valueOf(formMap.get(GlobalConstants.PAGE_CURRENT_PAGE).toString().trim());
		int pageSize=Integer.valueOf(formMap.get(GlobalConstants.PAGE_PAGE_SIZE).toString().trim());
		map=iBaseDao.getListByPage(hqlBuilder.toString(), hqlCount, (currentPage-1)*pageSize, pageSize);
		//增加总页数
		int count=Integer.valueOf(map.get("count").toString());
		int totalPages=(count+pageSize-1)/pageSize;
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return  map;
	}
	
	/**
	 * 根据菜单类型选择上级菜单列表
	 * @param menuType
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月25日下午5:43:39
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getMenuHierarchyDirectoryListByMenuType(String menuType)
			throws Exception {
		List<SysMenu> list=new ArrayList<>();

		String hql="FROM SysMenu WHERE 1=1 ";
		hql+="AND menuType='"+menuType+"' ";
		hql+="AND id IN ( ";
		hql+="SELECT menuParentId FROM SysMenu WHERE menuParentId<>'' ";
		hql+=") ";
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}
	
	/**
	 * 通过父级菜单ID获取菜单列表
	 * @param parentStr
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月29日下午5:41:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getSysMenuListByParentStr(String parentStr) throws Exception {
		List<SysMenu> list=new ArrayList<SysMenu>();
		 
		String hql="FROM SysMenu WHERE 1=1 ";
		hql+="AND menuParentId IN ('"+parentStr+"') ";
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}

	/**
	 * 获取有效的菜单列表
	 * @param parentSt
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年4月29日下午5:55:45
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysMenu> getEnableSysMenuList(String parentSt, Map formMap)
			throws Exception {
		List<SysMenu> list=new ArrayList<SysMenu>();
		 
		String hql="FROM SysMenu WHERE 1=1 ";
		hql+=" AND id NOT IN ('"+parentSt+"') ";
		hql+=getQueryWhere(formMap);
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}
	
	/**
	 * 获取用户选择菜单的子查询语句
	 * @param userId
	 * @return
	 * Boger
	 * 2016年3月25日下午5:25:49
	 */
	private String getUserMenuQuery(String userId){
		StringBuilder hqlBuilder=new StringBuilder();

		//用户选择的菜单信息
		hqlBuilder.append("AND id IN ( ");
		hqlBuilder.append("SELECT menuId FROM SysRoleMenu WHERE roleId IN ( ");
		hqlBuilder.append("SELECT roleId FROM SysUserRole WHERE userId = '"+userId+"' AND roleId IN ( " );
		hqlBuilder.append("SELECT id FROM SysRole WHERE isDelete='"+GlobalConstants.DATA_STATUS_NORMAL+"' AND roleStatus = 1 ) ");
		hqlBuilder.append(") ");
		hqlBuilder.append(") ");
		
		return hqlBuilder.toString();
	}

	/**
	 * 获取用户选择菜单的子查询语句
	 * @param userId
	 * @return
	 * Boger
	 * 2016年3月25日下午5:25:49
	 */
	private String getUserMenuSettingQuery(String userId){
		StringBuilder hqlBuilder=new StringBuilder();

		//用户选择的菜单信息
		hqlBuilder.append("AND menu_attribute='1' ");
		hqlBuilder.append("AND id IN ( ");
		hqlBuilder.append("SELECT menuId FROM SysRoleMenu WHERE roleId IN ( ");
		hqlBuilder.append("SELECT roleId FROM SysUserRole WHERE userId = '"+userId+"' AND roleId IN ( " );
		hqlBuilder.append("SELECT id FROM SysRole WHERE isDelete='"+GlobalConstants.DATA_STATUS_NORMAL+"' AND roleStatus = 1 ) ");
		hqlBuilder.append(") ");
		hqlBuilder.append(") ");

		return hqlBuilder.toString();
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
		
		//删除标识
		strWhere.append("AND ");
		strWhere.append("isDelete = '"+GlobalConstants.DATA_STATUS_NORMAL+"' ");
		//菜单ID
		if (map.containsKey("menuId")){
			String menuId=map.get("menuId").toString().trim();
			if (menuId!=null&&StringUtil.isNotEmpty(menuId)){
				strWhere.append("AND ");
				strWhere.append("id ='"+menuId+"' ");
			}
		}
		//菜单代码
		if (map.containsKey("code")){
			String code=map.get("code").toString().trim();
			if (code!=null&&StringUtil.isNotEmpty(code)){
				strWhere.append("AND ");
				strWhere.append("code = '%"+code+"%' ");
			}
		}
		//菜单名称
		if (map.containsKey("menuName")){
			String menuName=map.get("menuName").toString().trim();
			if (menuName!=null&&StringUtil.isNotEmpty(menuName)){
				strWhere.append("AND ");
				strWhere.append("menuName like '%"+menuName+"%' ");
			}
		}
		//中文名称
		if (map.containsKey("menuNameZh")){
			String menuNameZh=map.get("menuNameZh").toString().trim();
			if (menuNameZh!=null&&StringUtil.isNotEmpty(menuNameZh)){
				strWhere.append("AND ");
				strWhere.append("menuNameZh like '%"+menuNameZh+"%' ");
			}
		}
		//英文名称
		if (map.containsKey("menuNameEn")){
			String menuNameEn=map.get("menuNameEn").toString().trim();
			if (menuNameEn!=null&&StringUtil.isNotEmpty(menuNameEn)){
				strWhere.append("AND ");
				strWhere.append("menuNameEn like '%"+menuNameEn+"%' ");
			}
		}
		//上级菜单
		if (map.containsKey("menuParentId")){
			String menuParentId=map.get("menuParentId").toString().trim();
			if (menuParentId!=null&&StringUtil.isNotEmpty(menuParentId)){
				strWhere.append("AND ");
				strWhere.append("menuParentId ='"+menuParentId+"' ");
			}
		}
		//菜单状态
		if (map.containsKey("menuStatus")){
			String menuStatus=map.get("menuStatus").toString().trim();
			if (menuStatus!=null&&StringUtil.isNotEmpty(menuStatus)){
				strWhere.append("AND ");
				strWhere.append("menuStatus ='"+menuStatus+"' ");
			}
		}
		//菜单类型
		if (map.containsKey("menuType")){
			String menuType=map.get("menuType").toString().trim();
			if (menuType!=null&&StringUtil.isNotEmpty(menuType)){
				strWhere.append("AND ");
				strWhere.append("menuType ='"+menuType+"' ");
			}
		}
		//报表属性
		if (map.containsKey("menuAttribute")){
			String menuAttribute=map.get("menuAttribute").toString().trim();
			if (menuAttribute!=null&&StringUtil.isNotEmpty(menuAttribute)){
				strWhere.append("AND ");
				strWhere.append("menuAttribute ='"+menuAttribute+"' ");
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
		
		//菜单类型
		if (map.containsKey("notMenuType")){
			String notMenuType=map.get("notMenuType").toString().trim();
			if (notMenuType!=null&&StringUtil.isNotEmpty(notMenuType)){
				strWhere.append("AND ");
				strWhere.append("menuType !='"+notMenuType+"' ");
			}
		}

		return strWhere.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getReportFolders(String userId) {
		String hql = "from SysMenu where menuType='02' and menuAttribute is not null and menuAttribute<>'' and isDelete=0 "
				+ "and menuStatus=1" + getUserMenuQuery(userId);
		return iBaseDao.getListByHql(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportMenuParam> getReportMenuParamList4MenuId(String menuId)
			throws Exception {
		List<ReportMenuParam> list=new ArrayList<ReportMenuParam>();
		 
		String hql="FROM ReportMenuParam WHERE 1=1 ";
		hql+="AND menuId='"+menuId+"' ";
		list=iBaseDao.getListByHql(hql);
		
		return list;
	}

	/**
	 * 获取唯一的菜单
	 * @param menuNameZh
	 * @return
	 * @throws Exception
	 * admin
	 * 2016年4月21日下午4:21:25
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SysMenu getSysMenu(String menuNameZh) throws Exception {
		String hql="FROM SysMenu WHERE 1=1 ";
		hql += " and isDelete=0 and menuNameEn='"+menuNameZh+"'";
		
		List<SysMenu> list=iBaseDao.getListByHql(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
