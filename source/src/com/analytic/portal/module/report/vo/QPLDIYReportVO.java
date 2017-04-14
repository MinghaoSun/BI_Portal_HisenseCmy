package com.analytic.portal.module.report.vo;

import java.io.Serializable;

/**
 * @description 全品类自主设计报表VO类
 * @author Minghao
 * @date 2017年4月10日16:54:03
 */
public class QPLDIYReportVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8635150873434914227L;
	
	private String areaName; //区域名称
	private String productName; //品类名称
	private String value; //值
	private String lmpercent; //环比百分比
	private String lympercent; //同比百分比
	private String occup; //占有率
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
	public String getOccup() {
		return occup;
	}
	public void setOccup(String occup) {
		this.occup = occup;
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
	

}
