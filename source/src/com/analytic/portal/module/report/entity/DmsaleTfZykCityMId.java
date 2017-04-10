package com.analytic.portal.module.report.entity;


import java.math.BigDecimal;
import java.util.Date;

/**
 * DmsaleTfZykCityMId entity. @author MyEclipse Persistence Tools
 */

public class DmsaleTfZykCityMId implements java.io.Serializable {

	// Fields

	private String dtMonth;
	private String revCmpCode;
	private String lineCode;
	private String lineName;
	private String brandCode;
	private String brandName;
	private BigDecimal olTyp;
	private String chnlTyp;
	private String marktLevel;
	private String regionGgrp;
	private String regionOrg;
	private String regionAll;
	private String marktCenter;
	private String subCmpName;
	private String prov;
	private String cityName;
	private String countryCode;
	private String countryName;
	private String yoyFlag;
	private String isDisp;
	private BigDecimal price;
	private BigDecimal saleQty;
	private BigDecimal saleAmt;
	private BigDecimal saleTyQty;
	private BigDecimal saleTyAmt;
	private BigDecimal saleLmQty;
	private BigDecimal saleLmAmt;
	private BigDecimal saleLmyQty;
	private BigDecimal saleLmyAmt;
	private BigDecimal saleLymQty;
	private BigDecimal saleLymAmt;
	private BigDecimal saleLyQty;
	private BigDecimal saleLyAmt;
	private Date loadDt;

	// Constructors

	/** default constructor */
	public DmsaleTfZykCityMId() {
	}

	/** minimal constructor */
	public DmsaleTfZykCityMId(String lineCode, String lineName) {
		this.lineCode = lineCode;
		this.lineName = lineName;
	}

	/** full constructor */
	public DmsaleTfZykCityMId(String dtMonth, String revCmpCode,
			String lineCode, String lineName, String brandCode,
			String brandName, BigDecimal olTyp, String chnlTyp,
			String marktLevel, String regionGgrp, String regionOrg,
			String regionAll, String marktCenter, String subCmpName,
			String prov, String cityName, String countryCode,
			String countryName, String yoyFlag, String isDisp,
			BigDecimal price, BigDecimal saleQty, BigDecimal saleAmt,
			BigDecimal saleTyQty, BigDecimal saleTyAmt, BigDecimal saleLmQty,
			BigDecimal saleLmAmt, BigDecimal saleLmyQty, BigDecimal saleLmyAmt,
			BigDecimal saleLymQty, BigDecimal saleLymAmt, BigDecimal saleLyQty,
			BigDecimal saleLyAmt, Date loadDt) {
		this.dtMonth = dtMonth;
		this.revCmpCode = revCmpCode;
		this.lineCode = lineCode;
		this.lineName = lineName;
		this.brandCode = brandCode;
		this.brandName = brandName;
		this.olTyp = olTyp;
		this.chnlTyp = chnlTyp;
		this.marktLevel = marktLevel;
		this.regionGgrp = regionGgrp;
		this.regionOrg = regionOrg;
		this.regionAll = regionAll;
		this.marktCenter = marktCenter;
		this.subCmpName = subCmpName;
		this.prov = prov;
		this.cityName = cityName;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.yoyFlag = yoyFlag;
		this.isDisp = isDisp;
		this.price = price;
		this.saleQty = saleQty;
		this.saleAmt = saleAmt;
		this.saleTyQty = saleTyQty;
		this.saleTyAmt = saleTyAmt;
		this.saleLmQty = saleLmQty;
		this.saleLmAmt = saleLmAmt;
		this.saleLmyQty = saleLmyQty;
		this.saleLmyAmt = saleLmyAmt;
		this.saleLymQty = saleLymQty;
		this.saleLymAmt = saleLymAmt;
		this.saleLyQty = saleLyQty;
		this.saleLyAmt = saleLyAmt;
		this.loadDt = loadDt;
	}

	// Property accessors

	public String getDtMonth() {
		return this.dtMonth;
	}

	public void setDtMonth(String dtMonth) {
		this.dtMonth = dtMonth;
	}

	public String getRevCmpCode() {
		return this.revCmpCode;
	}

	public void setRevCmpCode(String revCmpCode) {
		this.revCmpCode = revCmpCode;
	}

	public String getLineCode() {
		return this.lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public BigDecimal getOlTyp() {
		return this.olTyp;
	}

	public void setOlTyp(BigDecimal olTyp) {
		this.olTyp = olTyp;
	}

	public String getChnlTyp() {
		return this.chnlTyp;
	}

	public void setChnlTyp(String chnlTyp) {
		this.chnlTyp = chnlTyp;
	}

	public String getMarktLevel() {
		return this.marktLevel;
	}

	public void setMarktLevel(String marktLevel) {
		this.marktLevel = marktLevel;
	}

	public String getRegionGgrp() {
		return this.regionGgrp;
	}

	public void setRegionGgrp(String regionGgrp) {
		this.regionGgrp = regionGgrp;
	}

	public String getRegionOrg() {
		return this.regionOrg;
	}

	public void setRegionOrg(String regionOrg) {
		this.regionOrg = regionOrg;
	}

	public String getRegionAll() {
		return this.regionAll;
	}

	public void setRegionAll(String regionAll) {
		this.regionAll = regionAll;
	}

	public String getMarktCenter() {
		return this.marktCenter;
	}

	public void setMarktCenter(String marktCenter) {
		this.marktCenter = marktCenter;
	}

	public String getSubCmpName() {
		return this.subCmpName;
	}

	public void setSubCmpName(String subCmpName) {
		this.subCmpName = subCmpName;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getYoyFlag() {
		return this.yoyFlag;
	}

	public void setYoyFlag(String yoyFlag) {
		this.yoyFlag = yoyFlag;
	}

	public String getIsDisp() {
		return this.isDisp;
	}

	public void setIsDisp(String isDisp) {
		this.isDisp = isDisp;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSaleQty() {
		return this.saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

	public BigDecimal getSaleAmt() {
		return this.saleAmt;
	}

	public void setSaleAmt(BigDecimal saleAmt) {
		this.saleAmt = saleAmt;
	}

	public BigDecimal getSaleTyQty() {
		return this.saleTyQty;
	}

	public void setSaleTyQty(BigDecimal saleTyQty) {
		this.saleTyQty = saleTyQty;
	}

	public BigDecimal getSaleTyAmt() {
		return this.saleTyAmt;
	}

	public void setSaleTyAmt(BigDecimal saleTyAmt) {
		this.saleTyAmt = saleTyAmt;
	}

	public BigDecimal getSaleLmQty() {
		return this.saleLmQty;
	}

	public void setSaleLmQty(BigDecimal saleLmQty) {
		this.saleLmQty = saleLmQty;
	}

	public BigDecimal getSaleLmAmt() {
		return this.saleLmAmt;
	}

	public void setSaleLmAmt(BigDecimal saleLmAmt) {
		this.saleLmAmt = saleLmAmt;
	}

	public BigDecimal getSaleLmyQty() {
		return this.saleLmyQty;
	}

	public void setSaleLmyQty(BigDecimal saleLmyQty) {
		this.saleLmyQty = saleLmyQty;
	}

	public BigDecimal getSaleLmyAmt() {
		return this.saleLmyAmt;
	}

	public void setSaleLmyAmt(BigDecimal saleLmyAmt) {
		this.saleLmyAmt = saleLmyAmt;
	}

	public BigDecimal getSaleLymQty() {
		return this.saleLymQty;
	}

	public void setSaleLymQty(BigDecimal saleLymQty) {
		this.saleLymQty = saleLymQty;
	}

	public BigDecimal getSaleLymAmt() {
		return this.saleLymAmt;
	}

	public void setSaleLymAmt(BigDecimal saleLymAmt) {
		this.saleLymAmt = saleLymAmt;
	}

	public BigDecimal getSaleLyQty() {
		return this.saleLyQty;
	}

	public void setSaleLyQty(BigDecimal saleLyQty) {
		this.saleLyQty = saleLyQty;
	}

	public BigDecimal getSaleLyAmt() {
		return this.saleLyAmt;
	}

	public void setSaleLyAmt(BigDecimal saleLyAmt) {
		this.saleLyAmt = saleLyAmt;
	}

	public Date getLoadDt() {
		return this.loadDt;
	}

	public void setLoadDt(Date loadDt) {
		this.loadDt = loadDt;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DmsaleTfZykCityMId))
			return false;
		DmsaleTfZykCityMId castOther = (DmsaleTfZykCityMId) other;

		return ((this.getDtMonth() == castOther.getDtMonth()) || (this
				.getDtMonth() != null && castOther.getDtMonth() != null && this
				.getDtMonth().equals(castOther.getDtMonth())))
				&& ((this.getRevCmpCode() == castOther.getRevCmpCode()) || (this
						.getRevCmpCode() != null
						&& castOther.getRevCmpCode() != null && this
						.getRevCmpCode().equals(castOther.getRevCmpCode())))
				&& ((this.getLineCode() == castOther.getLineCode()) || (this
						.getLineCode() != null
						&& castOther.getLineCode() != null && this
						.getLineCode().equals(castOther.getLineCode())))
				&& ((this.getLineName() == castOther.getLineName()) || (this
						.getLineName() != null
						&& castOther.getLineName() != null && this
						.getLineName().equals(castOther.getLineName())))
				&& ((this.getBrandCode() == castOther.getBrandCode()) || (this
						.getBrandCode() != null
						&& castOther.getBrandCode() != null && this
						.getBrandCode().equals(castOther.getBrandCode())))
				&& ((this.getBrandName() == castOther.getBrandName()) || (this
						.getBrandName() != null
						&& castOther.getBrandName() != null && this
						.getBrandName().equals(castOther.getBrandName())))
				&& ((this.getOlTyp() == castOther.getOlTyp()) || (this
						.getOlTyp() != null && castOther.getOlTyp() != null && this
						.getOlTyp().equals(castOther.getOlTyp())))
				&& ((this.getChnlTyp() == castOther.getChnlTyp()) || (this
						.getChnlTyp() != null && castOther.getChnlTyp() != null && this
						.getChnlTyp().equals(castOther.getChnlTyp())))
				&& ((this.getMarktLevel() == castOther.getMarktLevel()) || (this
						.getMarktLevel() != null
						&& castOther.getMarktLevel() != null && this
						.getMarktLevel().equals(castOther.getMarktLevel())))
				&& ((this.getRegionGgrp() == castOther.getRegionGgrp()) || (this
						.getRegionGgrp() != null
						&& castOther.getRegionGgrp() != null && this
						.getRegionGgrp().equals(castOther.getRegionGgrp())))
				&& ((this.getRegionOrg() == castOther.getRegionOrg()) || (this
						.getRegionOrg() != null
						&& castOther.getRegionOrg() != null && this
						.getRegionOrg().equals(castOther.getRegionOrg())))
				&& ((this.getRegionAll() == castOther.getRegionAll()) || (this
						.getRegionAll() != null
						&& castOther.getRegionAll() != null && this
						.getRegionAll().equals(castOther.getRegionAll())))
				&& ((this.getMarktCenter() == castOther.getMarktCenter()) || (this
						.getMarktCenter() != null
						&& castOther.getMarktCenter() != null && this
						.getMarktCenter().equals(castOther.getMarktCenter())))
				&& ((this.getSubCmpName() == castOther.getSubCmpName()) || (this
						.getSubCmpName() != null
						&& castOther.getSubCmpName() != null && this
						.getSubCmpName().equals(castOther.getSubCmpName())))
				&& ((this.getProv() == castOther.getProv()) || (this.getProv() != null
						&& castOther.getProv() != null && this.getProv()
						.equals(castOther.getProv())))
				&& ((this.getCityName() == castOther.getCityName()) || (this
						.getCityName() != null
						&& castOther.getCityName() != null && this
						.getCityName().equals(castOther.getCityName())))
				&& ((this.getCountryCode() == castOther.getCountryCode()) || (this
						.getCountryCode() != null
						&& castOther.getCountryCode() != null && this
						.getCountryCode().equals(castOther.getCountryCode())))
				&& ((this.getCountryName() == castOther.getCountryName()) || (this
						.getCountryName() != null
						&& castOther.getCountryName() != null && this
						.getCountryName().equals(castOther.getCountryName())))
				&& ((this.getYoyFlag() == castOther.getYoyFlag()) || (this
						.getYoyFlag() != null && castOther.getYoyFlag() != null && this
						.getYoyFlag().equals(castOther.getYoyFlag())))
				&& ((this.getIsDisp() == castOther.getIsDisp()) || (this
						.getIsDisp() != null && castOther.getIsDisp() != null && this
						.getIsDisp().equals(castOther.getIsDisp())))
				&& ((this.getPrice() == castOther.getPrice()) || (this
						.getPrice() != null && castOther.getPrice() != null && this
						.getPrice().equals(castOther.getPrice())))
				&& ((this.getSaleQty() == castOther.getSaleQty()) || (this
						.getSaleQty() != null && castOther.getSaleQty() != null && this
						.getSaleQty().equals(castOther.getSaleQty())))
				&& ((this.getSaleAmt() == castOther.getSaleAmt()) || (this
						.getSaleAmt() != null && castOther.getSaleAmt() != null && this
						.getSaleAmt().equals(castOther.getSaleAmt())))
				&& ((this.getSaleTyQty() == castOther.getSaleTyQty()) || (this
						.getSaleTyQty() != null
						&& castOther.getSaleTyQty() != null && this
						.getSaleTyQty().equals(castOther.getSaleTyQty())))
				&& ((this.getSaleTyAmt() == castOther.getSaleTyAmt()) || (this
						.getSaleTyAmt() != null
						&& castOther.getSaleTyAmt() != null && this
						.getSaleTyAmt().equals(castOther.getSaleTyAmt())))
				&& ((this.getSaleLmQty() == castOther.getSaleLmQty()) || (this
						.getSaleLmQty() != null
						&& castOther.getSaleLmQty() != null && this
						.getSaleLmQty().equals(castOther.getSaleLmQty())))
				&& ((this.getSaleLmAmt() == castOther.getSaleLmAmt()) || (this
						.getSaleLmAmt() != null
						&& castOther.getSaleLmAmt() != null && this
						.getSaleLmAmt().equals(castOther.getSaleLmAmt())))
				&& ((this.getSaleLmyQty() == castOther.getSaleLmyQty()) || (this
						.getSaleLmyQty() != null
						&& castOther.getSaleLmyQty() != null && this
						.getSaleLmyQty().equals(castOther.getSaleLmyQty())))
				&& ((this.getSaleLmyAmt() == castOther.getSaleLmyAmt()) || (this
						.getSaleLmyAmt() != null
						&& castOther.getSaleLmyAmt() != null && this
						.getSaleLmyAmt().equals(castOther.getSaleLmyAmt())))
				&& ((this.getSaleLymQty() == castOther.getSaleLymQty()) || (this
						.getSaleLymQty() != null
						&& castOther.getSaleLymQty() != null && this
						.getSaleLymQty().equals(castOther.getSaleLymQty())))
				&& ((this.getSaleLymAmt() == castOther.getSaleLymAmt()) || (this
						.getSaleLymAmt() != null
						&& castOther.getSaleLymAmt() != null && this
						.getSaleLymAmt().equals(castOther.getSaleLymAmt())))
				&& ((this.getSaleLyQty() == castOther.getSaleLyQty()) || (this
						.getSaleLyQty() != null
						&& castOther.getSaleLyQty() != null && this
						.getSaleLyQty().equals(castOther.getSaleLyQty())))
				&& ((this.getSaleLyAmt() == castOther.getSaleLyAmt()) || (this
						.getSaleLyAmt() != null
						&& castOther.getSaleLyAmt() != null && this
						.getSaleLyAmt().equals(castOther.getSaleLyAmt())))
				&& ((this.getLoadDt() == castOther.getLoadDt()) || (this
						.getLoadDt() != null && castOther.getLoadDt() != null && this
						.getLoadDt().equals(castOther.getLoadDt())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDtMonth() == null ? 0 : this.getDtMonth().hashCode());
		result = 37
				* result
				+ (getRevCmpCode() == null ? 0 : this.getRevCmpCode()
						.hashCode());
		result = 37 * result
				+ (getLineCode() == null ? 0 : this.getLineCode().hashCode());
		result = 37 * result
				+ (getLineName() == null ? 0 : this.getLineName().hashCode());
		result = 37 * result
				+ (getBrandCode() == null ? 0 : this.getBrandCode().hashCode());
		result = 37 * result
				+ (getBrandName() == null ? 0 : this.getBrandName().hashCode());
		result = 37 * result
				+ (getOlTyp() == null ? 0 : this.getOlTyp().hashCode());
		result = 37 * result
				+ (getChnlTyp() == null ? 0 : this.getChnlTyp().hashCode());
		result = 37
				* result
				+ (getMarktLevel() == null ? 0 : this.getMarktLevel()
						.hashCode());
		result = 37
				* result
				+ (getRegionGgrp() == null ? 0 : this.getRegionGgrp()
						.hashCode());
		result = 37 * result
				+ (getRegionOrg() == null ? 0 : this.getRegionOrg().hashCode());
		result = 37 * result
				+ (getRegionAll() == null ? 0 : this.getRegionAll().hashCode());
		result = 37
				* result
				+ (getMarktCenter() == null ? 0 : this.getMarktCenter()
						.hashCode());
		result = 37
				* result
				+ (getSubCmpName() == null ? 0 : this.getSubCmpName()
						.hashCode());
		result = 37 * result
				+ (getProv() == null ? 0 : this.getProv().hashCode());
		result = 37 * result
				+ (getCityName() == null ? 0 : this.getCityName().hashCode());
		result = 37
				* result
				+ (getCountryCode() == null ? 0 : this.getCountryCode()
						.hashCode());
		result = 37
				* result
				+ (getCountryName() == null ? 0 : this.getCountryName()
						.hashCode());
		result = 37 * result
				+ (getYoyFlag() == null ? 0 : this.getYoyFlag().hashCode());
		result = 37 * result
				+ (getIsDisp() == null ? 0 : this.getIsDisp().hashCode());
		result = 37 * result
				+ (getPrice() == null ? 0 : this.getPrice().hashCode());
		result = 37 * result
				+ (getSaleQty() == null ? 0 : this.getSaleQty().hashCode());
		result = 37 * result
				+ (getSaleAmt() == null ? 0 : this.getSaleAmt().hashCode());
		result = 37 * result
				+ (getSaleTyQty() == null ? 0 : this.getSaleTyQty().hashCode());
		result = 37 * result
				+ (getSaleTyAmt() == null ? 0 : this.getSaleTyAmt().hashCode());
		result = 37 * result
				+ (getSaleLmQty() == null ? 0 : this.getSaleLmQty().hashCode());
		result = 37 * result
				+ (getSaleLmAmt() == null ? 0 : this.getSaleLmAmt().hashCode());
		result = 37
				* result
				+ (getSaleLmyQty() == null ? 0 : this.getSaleLmyQty()
						.hashCode());
		result = 37
				* result
				+ (getSaleLmyAmt() == null ? 0 : this.getSaleLmyAmt()
						.hashCode());
		result = 37
				* result
				+ (getSaleLymQty() == null ? 0 : this.getSaleLymQty()
						.hashCode());
		result = 37
				* result
				+ (getSaleLymAmt() == null ? 0 : this.getSaleLymAmt()
						.hashCode());
		result = 37 * result
				+ (getSaleLyQty() == null ? 0 : this.getSaleLyQty().hashCode());
		result = 37 * result
				+ (getSaleLyAmt() == null ? 0 : this.getSaleLyAmt().hashCode());
		result = 37 * result
				+ (getLoadDt() == null ? 0 : this.getLoadDt().hashCode());
		return result;
	}

}