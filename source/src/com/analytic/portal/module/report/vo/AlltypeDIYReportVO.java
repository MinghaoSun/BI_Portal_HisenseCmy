package com.analytic.portal.module.report.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @description 全品类DIY页面返回VO类
 * @author Minghao
 * @date 2017年4月17日09:55:36
 */
public class AlltypeDIYReportVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3812384061818856851L;
	
	private String histoal; //海信旗下销售额/销售量
	private String hytotal; //行业整体销售额/销售量
	private String histotalhb; //海信旗下环比
	private String hytotalhb; //行业整体环比
	private String histotaltb; //海信旗下同比
	private String hytotaltb; //行业整体同比
	private String histotalzhyl; //海信旗下占有率
	private String histotalppzhsh; //海信旗下品牌指数
	
	private List<AlltypeValueVO> values; //值
	
	
	public String getHistoal() {
		return histoal;
	}
	public void setHistoal(String histoal) {
		this.histoal = histoal;
	}
	public String getHytotal() {
		return hytotal;
	}
	public void setHytotal(String hytotal) {
		this.hytotal = hytotal;
	}
	public String getHistotalhb() {
		return histotalhb;
	}
	public void setHistotalhb(String histotalhb) {
		this.histotalhb = histotalhb;
	}
	public String getHytotalhb() {
		return hytotalhb;
	}
	public void setHytotalhb(String hytotalhb) {
		this.hytotalhb = hytotalhb;
	}
	public String getHistotaltb() {
		return histotaltb;
	}
	public void setHistotaltb(String histotaltb) {
		this.histotaltb = histotaltb;
	}
	public String getHytotaltb() {
		return hytotaltb;
	}
	public void setHytotaltb(String hytotaltb) {
		this.hytotaltb = hytotaltb;
	}
	public String getHistotalzhyl() {
		return histotalzhyl;
	}
	public void setHistotalzhyl(String histotalzhyl) {
		this.histotalzhyl = histotalzhyl;
	}
	public String getHistotalppzhsh() {
		return histotalppzhsh;
	}
	public void setHistotalppzhsh(String histotalppzhsh) {
		this.histotalppzhsh = histotalppzhsh;
	}
	public List<AlltypeValueVO> getValues() {
		return values;
	}
	public void setValues(List<AlltypeValueVO> values) {
		this.values = values;
	}

}
