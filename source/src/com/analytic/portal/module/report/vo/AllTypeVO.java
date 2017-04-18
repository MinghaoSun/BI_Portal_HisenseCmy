package com.analytic.portal.module.report.vo;

import java.io.Serializable;

public class AllTypeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -787725097683789542L;
	private String areaId;
	private String areaName;
	private String productId;
	private String productName;
	private String sale_num;
	private String sale_amt;
	private String hisense_sale_num;
	private String lmpercent; //环比百分比
	private String lympercent; //同比百分比
	private String occup; //占有率
	private String lmoccuppercent; //环比占有率
	private String lymoccuppercent; //同比占有率
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSale_num() {
		return sale_num;
	}
	public void setSale_num(String sale_num) {
		this.sale_num = sale_num;
	}
	public String getSale_amt() {
		return sale_amt;
	}
	public void setSale_amt(String sale_amt) {
		this.sale_amt = sale_amt;
	}
	public String getHisense_sale_num() {
		return hisense_sale_num;
	}
	public void setHisense_sale_num(String hisense_sale_num) {
		this.hisense_sale_num = hisense_sale_num;
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
