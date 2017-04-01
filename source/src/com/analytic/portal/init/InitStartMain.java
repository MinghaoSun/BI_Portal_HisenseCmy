package com.analytic.portal.init;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.service.IBaseService;
import com.analytic.portal.module.common.util.LoggerUtil;
import com.yzd.plat.common.util.ehcache.CacheUtil;

/**
 * 项目启动时需要做的一些事情
 * @author Louis
 */
public class InitStartMain {

	@Resource
	private IBaseService iBaseService;
	
	/**
	 * 启动所有初始化信息
	 */
	public void initStartService(){
		LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
				GlobalConstants.lOGGER_LEVEL_INFO_BEGIN);
		initConfigCache();
		initDicCache();
		LoggerUtil.getInstance().loggerInfoOutput(GlobalConstants.lOGGER_LEVEL_INFO, 
				GlobalConstants.lOGGER_LEVEL_INFO_END);
	}
	
	/**
	 * 初始化系统参数信息
	 * Boger
	 * 2016年3月10日下午1:23:51
	 */
	@SuppressWarnings("rawtypes")
	private  void initConfigCache(){
		String sql="SELECT param_code,param_value FROM sys_config";
		
		try {
			List<HashMap> list=iBaseService.getDatasByJDBCSQL(sql);
			for(HashMap map:list){
				String key=map.get("param_code").toString();
				String vaue=map.get("param_value").toString();
				CacheUtil.getInstance(GlobalConstants.CACHE_CONFIG_SIGN).saveOrUpdate2Cache(key, vaue);
			}
		} catch (SQLException e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
	/**
	 * 初始化数据字典信息
	 * Boger
	 * 2016年3月10日下午1:32:08
	 */
	@SuppressWarnings("rawtypes")
	private  void initDicCache(){
		String sql="SELECT dic_code,dic_value FROM sys_dic";
		
		try {
			List<HashMap> list=iBaseService.getDatasByJDBCSQL(sql);
			for(HashMap map:list){
				String key=map.get("dic_code").toString();
				String vaue=map.get("dic_value").toString();
				CacheUtil.getInstance(GlobalConstants.CACHE_DIC_SIGN).saveOrUpdate2Cache(key, vaue);
			}
		} catch (SQLException e) {
			LoggerUtil.getInstance().loggerErrorOutput(GlobalConstants.lOGGER_LEVEL_ERROR, e);
		}
	}
	
}
