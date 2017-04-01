package com.analytic.portal.security;

import java.util.List;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.analytic.portal.common.util.DESEncode;

/**
 * @description spring资源文件读取加密值
 * @author Minghao
 * @date 2017年3月14日09:51:57
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
	private List<String> encryptPropNames;
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if(encryptPropNames.contains(propertyName)){
			try {
				String value=DESEncode.Decrypt(propertyValue);
				return value;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.convertProperty(propertyName, propertyValue);
	}

	public List<String> getEncryptPropNames() {
		return encryptPropNames;
	}

	public void setEncryptPropNames(List<String> encryptPropNames) {
		this.encryptPropNames = encryptPropNames;
	}

}
