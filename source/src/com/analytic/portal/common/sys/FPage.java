package com.analytic.portal.common.sys;

import java.util.ArrayList;
import java.util.List;

import com.yzd.plat.common.util.FileUtil;
import com.yzd.plat.common.util.Tools;

public class FPage {
	private String tableName;

	private String sql;//��ѯ��sql���

	private int currentPage = 1;//��ǰҳ

	private int pageSize = 20;//ÿҳ��ʾ�ĸ���

	private int pageCount;//�ܹ���ҳ��

	private String pageOrder;//����

	private int recordCount;//�ܼ�¼����

	private boolean desc;//

	private String listUrl;//

	private String listTarget;//

	private int selectedRecord;
	
	private int start;
	private int limit;

	@SuppressWarnings("rawtypes")
	private List list = new ArrayList();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return Returns the selectedRecord.
	 */
	public int getSelectedRecord() {
		return selectedRecord;
	}

	/**
	 * @param selectedRecord
	 *            The selectedRecord to set.
	 */
	public void setSelectedRecord(int selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	public FPage() {
		if(Tools.isNumeric(FileUtil.getProperty("pageSize")))
		{
			pageSize = Integer.parseInt(FileUtil.getProperty("pageSize"));
		}
		else{
			pageSize = 20;
		}
		
		currentPage = 1;
		recordCount = 0;
		pageCount = 0;
		desc = false;
		listTarget = "_self";
		listUrl = "?operation=detail";
		selectedRecord = 0;
		pageOrder="";
	}
	public void ini() {
		if(SysConfig.getSysConfigValue("pageSize")==""){
			pageSize = 20;
		}else{
			pageSize = Integer.parseInt(SysConfig.getSysConfigValue("pageSize"));
		}
		
		currentPage = 1;
		recordCount = 0;
		pageCount = 0;
		desc = false;
		listTarget = "_self";
		listUrl = "?operation=detail";
		selectedRecord = 0;
		pageOrder="";
	}


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
//		autoSetCurrentPage();
	}


	public boolean isDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            The desc to set.
	 */
	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            The sql to set.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
//		autoSetCurrentPage();
	}

	/**
	 * ��ȡ�ܵ�ҳ��
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getPageOrder() {
		return pageOrder;
	}


	public void setPageOrder(String pageOrder) {
		this.pageOrder = pageOrder;
	}

	/**
	 * ��ȡ�ܼ�¼����
	 * @return Returns the recordCount.
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            The recordCount to set.
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
//		autoSetCurrentPage();
	}

	/**
	 * @return Returns the listTarget.
	 */
	public String getListTarget() {
		return listTarget;
	}

	/**
	 * @param listTarget
	 *            The listTarget to set.
	 */
	public void setListTarget(String listTarget) {
		this.listTarget = listTarget;
	}

	/**
	 * @return Returns the listUrl.
	 */
	public String getListUrl() {
		return listUrl;
	}

	/**
	 * @param listUrl
	 *            The listUrl to set.
	 */
	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	/**
	 * ��ȡ��ѯ�Ľ��
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	
}
