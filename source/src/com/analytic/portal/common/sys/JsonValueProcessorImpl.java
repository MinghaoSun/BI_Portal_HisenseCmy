/**
 * @Author Lucifer Zhou 4:52:31 PM Jan 13, 2009
 * @MSN zhoujianguo_leo@hotmail.com
 * @Mail leo821031@gmail.com
 * @Copyright 2007-2008 The ZhouPhratry Software Organization
 * @FileName JsonValueProcessorImpl.java
 * @JDKVersion 1.4.2
 */
package com.analytic.portal.common.sys;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON日期转换处理类
 * @Author Lucifer.Zhou 4:52:31 PM Jan 13, 2009
 * 
 */
public class JsonValueProcessorImpl implements JsonValueProcessor {
	private String format="yyyy-MM-dd HH:mm:ss";//默认日期时间
	/**
	 * @Author Lucifer.Zhou 4:52:31 PM Jan 13, 2009
	 * JsonValueProcessorImpl
	 */
	public JsonValueProcessorImpl() {
		// TODO Auto-generated constructor stub
	}

	public JsonValueProcessorImpl(String format){
		this.format=format;//覆盖默认日期时间
	}
	/**
	 * 
	 * @Author Lucifer.Zhou 4:56:10 PM Jan 13, 2009
	 * @Method processArrayValue
	 * @see cn.com.zhouphratry.core.json.processors.JsonValueProcessor#processArrayValue(Object, cn.com.zhouphratry.core.json.JsonConfig)
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj={};
		if(value instanceof Date[]){
			SimpleDateFormat sf=new SimpleDateFormat(format);
			Date[] dates=(Date[])value;
			obj =new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
			obj[i]=sf.format(dates[i]);
			}
		}
		return obj;
	}
	/**
	 * 
	 * @Author Lucifer.Zhou 4:56:14 PM Jan 13, 2009
	 * @Method processObjectValue
	 * @see cn.com.zhouphratry.core.json.processors.JsonValueProcessor#processObjectValue(String, Object, cn.com.zhouphratry.core.json.JsonConfig)
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if(value==null)
			return "";
		if(value instanceof Long){
			return String.valueOf(value);
		}else if(value instanceof Date){
			String str=new SimpleDateFormat(format).format((Date)value);
			return str;
		}
		return value.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
