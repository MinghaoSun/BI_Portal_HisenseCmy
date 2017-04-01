/**
 * 编码接口实现
 */
package com.analytic.portal.module.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.dao.IEncodingDao;

/**
 * 编码接口实现
 * @author Boger
 */
@Service
public class EncodingDaoImpl implements IEncodingDao{

	@Resource
	IBaseDao iBaseDao;
	
	/**
	 * 获取业务代码
	 * @param entityName
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月10日上午10:59:46
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getBussinessCode(String entityName) throws Exception {
		List codeList=new ArrayList();
		
		//采用通用的CODE进行获取当前最大的编码
		String hql="SELECT MAX(code) FROM "+entityName;
		codeList=iBaseDao.getListByHql(hql);
		
		return codeList;
	}

}
