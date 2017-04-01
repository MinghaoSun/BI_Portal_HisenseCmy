package com.analytic.portal.common.sys;

import java.util.*;

import com.yzd.plat.common.util.FileUtil;

public class FPageContext {
	
	private FPage page;

	private String databaseType;
	
	//private String databaseType = "sqlServer";


	public FPageContext(FPage argFpage) {
		this.databaseType = FileUtil.getProperty("databaseType");
		this.page = argFpage;
		iniPageCount();
	}
	public FPageContext(FPage argFpage,Vector<Object> params) {
		this.databaseType = FileUtil.getProperty("databaseType");
		this.page = argFpage;
		this.params = params;
		iniPageCount();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getDatas() {
		if (databaseType.equals("oracle"))
			return DataOperation.getDatas(getPageSqlOracle(),this.params);
		else if (databaseType.equals("sqlServer"))
			return DataOperation.getDatas(getPageSqlSqlServer(),this.params);
		else if(databaseType.equals("mySql"))
			return DataOperation.getDatas(getPageSqlMySql(),this.params);
		else
			return DataOperation.getDatas(getPageSqlOracle(),this.params);
	}
	private Vector<Object> params = null;

	private void iniPageCount() {
		String strTemp;
		if (page.getSql().indexOf("order by") > -1) {
			strTemp = page.getSql().substring(0, page.getSql().indexOf("order by"));
		} else {
			strTemp = page.getSql();
		}

		String sql = "select count(*) page_count from (" + strTemp + ") FPageCountT1";
		SQLObject tSqlObj = new SQLObject(sql);
		if(this.params!=null){
			tSqlObj.setParams(this.params);
		}
		int count = Integer.parseInt((String) DataOperation.getData(tSqlObj).get("page_count"));
		page.setRecordCount(count);
		if(page.getLimit()==0){
			page.setPageCount((count + page.getPageSize() - 1) / page.getPageSize());
		}else{
			page.setPageCount((count + page.getLimit() - 1) / page.getLimit());
		}
	}


	private String getPageSqlOracle() {
		StringBuffer tsql = new StringBuffer(page.getSql());
		if (page.getPageOrder() != null && !page.getPageOrder().equals("")) {
			int pos = tsql.toString().toLowerCase().indexOf("order by");
			if (pos == -1) {
				tsql.append(" order by ").append(page.getPageOrder());
				if (page.isDesc())
					tsql.append(" desc");
				else
					tsql.append(" asc");
			} else {
				if (page.isDesc())
					tsql.insert(pos + 9, page.getPageOrder() + " desc,");
				else
					tsql.insert(pos + 9, page.getPageOrder() + " asc,");
			}
		}
		if(page.getLimit()==0)
		{
			StringBuffer sql = new StringBuffer("select * from (select frame_operation_t1.*,rownum row_num from (").append(
					tsql).append(") frame_operation_t1 where rownum<=").append(page.getCurrentPage() * page.getPageSize())
					.append(") frame_operation_t2 where frame_operation_t2.row_num>").append(
							(page.getCurrentPage() - 1) * page.getPageSize());
			return sql.toString();
		}else{
			StringBuffer sql = new StringBuffer("select * from (select frame_operation_t1.*,rownum row_num from (").append(
					tsql).append(") frame_operation_t1 where rownum<=").append(page.getStart()+page.getLimit())
					.append(") frame_operation_t2 where frame_operation_t2.row_num>").append(
							page.getStart());
			return sql.toString();
		}
		
	}

	private String getPageSqlMySql() {
		StringBuffer tsql = new StringBuffer(page.getSql());
		if (page.getPageOrder() != null && !page.getPageOrder().equals("")) {
			int pos = tsql.toString().toLowerCase().indexOf("order by");
			if (pos == -1) {
				tsql.append(" order by ").append(page.getPageOrder());
				if (page.isDesc())
					tsql.append(" desc");
				else
					tsql.append(" asc");
			} else {
				if (page.isDesc())
					tsql.insert(pos + 9, page.getPageOrder() + " desc,");
				else
					tsql.insert(pos + 9, page.getPageOrder() + " asc,");
			}
		}
		if(page.getLimit()==0)
		{
			StringBuffer sql = new StringBuffer("select frame_operation_t1.* from (").append(
					tsql).append(") frame_operation_t1 limit ").append((page.getCurrentPage()-1) * page.getPageSize())
					.append(",").append(page.getPageSize());
			return sql.toString();
		}else{
			StringBuffer sql = new StringBuffer("select frame_operation_t1.* from (").append(
					tsql).append(") frame_operation_t1 limit ").append(page.getStart()).append(",").append(page.getStart()+page.getLimit());
			return sql.toString();
		}
		
	}
	
	
	private String getPageSqlSqlServer() {
		StringBuffer tsql = new StringBuffer("**"+page.getSql());
		String strOrderBy, strOrderByReserve;
		int topNum;
		String orderCondtion="";
		if (page.getPageOrder() != null && !page.getPageOrder().equals("")) {
			int pos = tsql.toString().toLowerCase().indexOf("order by");
			if (pos == -1) {
				tsql.append(" order by ").append(page.getPageOrder());
				if (page.isDesc())
					tsql.append(" desc");
				else
					tsql.append(" asc");
			} else {
				if (page.isDesc())
					tsql.insert(pos + 9, page.getPageOrder() + " desc,");
				else
					tsql.insert(pos + 9, page.getPageOrder() + " asc,");
			}
			orderCondtion=" "+page.getPageOrder()+" ";
		}
		if (tsql.toString().toLowerCase().indexOf("order by") < 0){
			tsql.append(" order by id desc");
			orderCondtion=" id ";
		}
		strOrderBy = tsql.substring(tsql.toString().toLowerCase().indexOf("order by")).toString();
		strOrderByReserve = strOrderBy.replace(orderCondtion+"asc", "***").replace(orderCondtion+"desc", "###");
		strOrderByReserve = strOrderByReserve.replace("***", orderCondtion+"desc").replace("###", orderCondtion+"asc");
		
		if(page.getLimit() == 0){
			if (page.getRecordCount() - (page.getCurrentPage() * page.getPageSize()) >= 0)
				topNum = page.getPageSize();
			else
				topNum = page.getRecordCount() - (page.getCurrentPage() - 1) * page.getPageSize();
			if(topNum < 0) {
				topNum = page.getPageSize();
				page.setCurrentPage(1);
			}
			StringBuffer sql = new StringBuffer("select * from (select top " + topNum + " * from (").append(
					tsql.toString().replace("**select ", "select top " + page.getCurrentPage() * page.getPageSize() + " "))
					.append(") table_t1 ").append(strOrderByReserve + ") table_t2 ").append(strOrderBy);
			return sql.toString(); 
		}else{
			topNum = (page.getLimit() + page.getStart()) > page.getRecordCount() ? page.getRecordCount() - page.getStart() : page.getLimit();
			if(topNum < 0) topNum = 0;
			StringBuffer sql = new StringBuffer("select * from (select top " + topNum + " * from (").append(
					tsql.toString().replace("**select ", "select top " + (page.getLimit() + page.getStart()) + " "))
					.append(") table_t1 ").append(strOrderByReserve + ") table_t2 ").append(strOrderBy);
			return sql.toString();
		}
	}

	/**
	 * @return Returns the databaseType.
	 */
	public String getDatabaseType() {
		return databaseType;
	}

	/**
	 * @param databaseType
	 *            The databaseType to set.
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(FPage page) {
		this.page = page;
	}

	public FPage getPage() {
		return page;
	}
}
