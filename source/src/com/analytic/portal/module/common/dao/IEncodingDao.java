/**
 * 编码接口
 */
package com.analytic.portal.module.common.dao;

import java.util.List;

/**
 * 编码接口
 * @author Boger
 */
public interface IEncodingDao {

	@SuppressWarnings("rawtypes")
	public List getBussinessCode(String entityName) throws Exception;

}
