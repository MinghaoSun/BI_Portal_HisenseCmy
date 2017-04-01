package com.analytic.portal.module.report.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.report.dao.interfaces.ReportToolDao;
import com.analytic.portal.module.report.model.ReportTool;


@Repository
public class ReportToolDaoImpl implements ReportToolDao {

	@Resource
	IBaseDao iBaseDao;
	
	/**
	 * 根据删除报表工具对象
	 * @param id
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:22:43
	 */
	@Override
	public boolean deleteReportToolById(String id) {
		
		ReportTool reportTool= getReportToolById(id);
		try {
			reportTool.setIsDelete(GlobalConstants.DATA_STATUS_DELETE);
			
			return iBaseDao.update(reportTool);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 新增ReportTool对象
	 * @param reportTool
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:23:47
	 */
	@Override
	public boolean addReportTool(ReportTool reportTool) {
		try {
			return iBaseDao.save(reportTool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新报表工具对象
	 * @param reportTool
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:23:53
	 */
	@Override
	public boolean updateReportTool(ReportTool reportTool) {
		try{
			return iBaseDao.update(reportTool);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取报表工具对象
	 * @param id
	 * @return
	 * pengbo
	 * 2016年3月11日上午11:24:00
	 */
	@Override
	public ReportTool getReportToolById(String id) {
		
		ReportTool reportTool=(ReportTool)iBaseDao.getObjById(ReportTool.class, id);
		
		return reportTool;
	}

	/**
	 * 获取报表工具集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:24:06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportTool> getReportToolList() throws Exception {
		// TODO Auto-generated method stub
		String hql = "From  ReportTool WHERE 1=1  and isDelete='0'";
		
		return iBaseDao.getListByHql(hql);
	}

	/**
	 * 获取分页的报表工具集合
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月11日上午11:24:06
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getReportToolListByPage(Map formMap) throws Exception {
		// TODO Auto-generated method stub
		
		Map map=new HashMap();
		
		String hql="FROM ReportTool WHERE 1=1 ";
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
		StringBuffer sql = new StringBuffer("select id,code,report_tool_name as reportToolName,report_tool_type as reportToolType,report_tool_url as reportToolUrl,report_tool_version as reportToolVersion,"
				+ "report_tool_display as reportToolDisplay,report_tool_remark as reportToolRemark,create_time as createTime,create_user,"
				+ "update_time,update_user, is_delete as isDelete from report_tool where 1=1 and is_delete=0");
		Vector<Object> params = new Vector<>();
		
		if(StringUtils.isNotEmpty(reportTool.getReportToolName())){
			sql.append(" and report_tool_name like ? ");
			params.add("%"+reportTool.getReportToolName()+"%");
		}
		if(StringUtils.isNotEmpty(reportTool.getReportToolType())){
			sql.append(" and report_tool_type like ?");
			params.add("%"+reportTool.getReportToolType()+"%");
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
	 * 
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年3月22日下午2:24:31
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getReportToolList4Menu() throws Exception {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		StringBuilder reportMenuBuilder=new StringBuilder();
		
		//用户角色表信息
		reportMenuBuilder.append("select * FROM report_tool WHERE 1=1 and delete_status=0");
		
		list= iBaseDao.getListByHql(reportMenuBuilder.toString());

		return list;
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
		//reportToolName
		if (map.containsKey("reportToolName")){
			String reportToolName=map.get("reportToolName").toString().trim();
			if (reportToolName!=null&&StringUtil.isNotEmpty(reportToolName)){
				strWhere.append("AND ");
				strWhere.append("reportToolName like '%"+reportToolName+"%' ");
			}
		}
		//reportToolType
		if (map.containsKey("reportToolType")){
			String reportToolType=map.get("reportToolType").toString().trim();
			if (reportToolType!=null&&StringUtil.isNotEmpty(reportToolType)){
				strWhere.append("AND ");
				strWhere.append("reportToolType like '%"+reportToolType+"%' ");
			}
		}
		//reportToolType
		if (map.containsKey("reportToolVersion")){
			String reportToolVersion=map.get("reportToolVersion").toString().trim();
			if (reportToolVersion!=null&&StringUtil.isNotEmpty(reportToolVersion)){
				strWhere.append("AND ");
				strWhere.append("reportToolVersion like '%"+reportToolVersion+"%' ");
			}
		}
		
		return strWhere.toString();
	}

	/**
	 * 根据条件获取第一条报表工具对象
	 * @param formMap
	 * @return
	 * @throws Exception
	 * pengbo
	 * 2016年4月15日上午10:56:11
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ReportTool getReportTool(Map formMap) throws Exception {
		String hql="FROM ReportTool WHERE 1=1 ";
		hql+=getQueryWhere(formMap);
		
		List<ReportTool> list=iBaseDao.getListByHql(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
