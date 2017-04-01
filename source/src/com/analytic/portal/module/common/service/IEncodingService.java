/**
 * 编码接口
 */
package com.analytic.portal.module.common.service;

import java.util.HashMap;
import java.util.List;

/**
 * 编码接口
 * @author Boger
 */
public interface IEncodingService {
	
	@SuppressWarnings("rawtypes")
	public String getBussinessCode(Class classObj) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List<HashMap> getEncodeHandlerList(List<HashMap> beforeUpdateList, String strPrefix) throws Exception;
	
}
