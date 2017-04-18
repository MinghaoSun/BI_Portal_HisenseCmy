package com.analytic.portal.module.report.vo;

import java.io.Serializable;

/**
 * @description value类用于绘制矩形图
 * @author Minghao
 * @date 2017年4月17日10:05:56
 */
public class AlltypeValueVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3074381316609402752L;
	
	private String areaName; //区域名称（大区、营销中心、省份、城市）
	private String productName; //品类名称（冰箱、空调..）
	private String value; //值(销售额、销售量)
	private String lmpercent; //环比百分比（某个区域某个品类的，下同）
	private String lympercent; //同比百分比
	private String occupy; //占有率
	private String lmoccuppercent; //环比占有率
	private String lymoccuppercent; //同比占有率
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLmpercent() {
		return lmpercent;
	}
	public void setLmpercent(String lmpercent) {
		this.lmpercent = lmpercent;
	}
	public String getLympercent() {
		return lympercent;
	}
	public void setLympercent(String lympercent) {
		this.lympercent = lympercent;
	}
	public String getLmoccuppercent() {
		return lmoccuppercent;
	}
	public void setLmoccuppercent(String lmoccuppercent) {
		this.lmoccuppercent = lmoccuppercent;
	}
	public String getLymoccuppercent() {
		return lymoccuppercent;
	}
	public void setLymoccuppercent(String lymoccuppercent) {
		this.lymoccuppercent = lymoccuppercent;
	}
	public String getOccupy() {
		return occupy;
	}
	public void setOccupy(String occupy) {
		this.occupy = occupy;
	}

}