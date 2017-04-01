/**
 * 编码接口实现
 */
package com.analytic.portal.module.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.dao.IEncodingDao;
import com.analytic.portal.module.common.service.IEncodingService;

/**
 * 编码接口实现
 * @author Boger
 */
@Service
public class EncodingServiceImpl implements IEncodingService {

	@Resource
	private IEncodingDao iEncodingDao;
	
	/**
	 * 获取业务代码
	 * 按照业务标识自动编码，格式为：2位业务标识+6位顺序号（从小到大）
	 * @param classObj
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月10日上午10:40:13
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getBussinessCode(Class classObj) throws Exception {
		String strBusinessCode="";		
		List codeList=new ArrayList();
		String strStart=GlobalConstants.BUSINESS_ENCODING_START;
		//获取最大CODE列表
		String entityName=classObj.getName();
		codeList=iEncodingDao.getBussinessCode(entityName);
		//计算当前的业务代码
		Object codeObj=(Object)codeList.get(0);
		if (codeObj==null){
			strBusinessCode=strStart;			
		}else {
			strBusinessCode=codeObj.toString();
			//从业务标识后获取当前的编码
			strBusinessCode=strBusinessCode.substring(2);
			//定义当前编码
			int codeNumber=Integer.parseInt(strBusinessCode)+1;
			//数字前面自动补0
			strBusinessCode=String.format("%06d", codeNumber);
		}
		
		return strBusinessCode;
	}

	/**
	 * 业务编码重新处理
	 * CODE采用数据库自增方式处理
	 * @param list
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月10日下午4:28:40
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<HashMap> getEncodeHandlerList(List<HashMap> beforeUpdateList,String strPrefix) throws Exception {
		List<HashMap> afterUpdateList=new ArrayList<HashMap>();
		int lengthStartCode=GlobalConstants.BUSINESS_ENCODING_START.length();
		
		for (HashMap hashMap : beforeUpdateList) {
			String strCode=hashMap.get("code").toString();
			String strZeroNumber=String.valueOf(lengthStartCode-strCode.length());
			strCode=String.format("%0"+strZeroNumber+"d", strCode);
			//增加前缀
			hashMap.put("code", strPrefix+strCode);
			afterUpdateList.add(hashMap);
		}
		
		return afterUpdateList;
	}
	
}
