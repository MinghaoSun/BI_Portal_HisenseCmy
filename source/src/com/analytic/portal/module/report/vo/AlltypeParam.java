package com.analytic.portal.module.report.vo;

import java.io.Serializable;

public class AlltypeParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3287479224976464784L;
	
	private String ym;//年月
	private String saleType;//销售类型（销售额：0 销售量：1）
	private String tblType;//报表类型（月报:0 周报：1 ）
	private String queryLat;//查询类型（地理纬度：0 竞争纬度：1）
	private String areaName;//大区名称
	private String centerName;//营销中心名称
	private String proName;//省份名称
	private String cityName;//城市名称
	private String olType="0";//类型（线上1线下0）默认值0
	
	public String getYm() {
		return ym;
	}
	public void setYm(String ym) {
		this.ym = ym;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getTblType() {
		return tblType;
	}
	public void setTblType(String tblType) {
		this.tblType = tblType;
	}
	public String getQueryLat() {
		return queryLat;
	}
	public void setQueryLat(String queryLat) {
		this.queryLat = queryLat;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getOlType() {
		return olType;
	}
	public void setOlType(String olType) {
		this.olType = olType;
	}


}
