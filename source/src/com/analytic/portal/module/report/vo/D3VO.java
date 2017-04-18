package com.analytic.portal.module.report.vo;

import java.io.Serializable;
import java.util.List;
public class D3VO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4488320492347325695L;
	
	public D3VO() {
		// TODO Auto-generated constructor stub
	}
	
	public D3VO(String name, List<D3VO> children, String value) {
		super();
		if(name!=null)
		this.name = name;
		if(children!=null)
		this.children = children;
		if(value!=null)
		this.value = value;
	}
	private String name;
	private List<D3VO> children;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<D3VO> getChildren() {
		return children;
	}
	public void setChildren(List<D3VO> children) {
		this.children = children;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
