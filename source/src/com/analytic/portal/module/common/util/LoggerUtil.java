/**
 * 日志处理
 */
package com.analytic.portal.module.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义日志处理接口
 * @author Boger
 */
public class LoggerUtil {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private static LoggerUtil instance=new LoggerUtil();
	
	/**
	 * 初始化实例
	 * @return
	 * Boger
	 * 2016年3月16日下午1:38:43
	 */
	public static LoggerUtil getInstance(){
		return instance;
	}

	/**
	 * 输出操作日志信息
	 * @param loggerLevel
	 * @param loggerBeginOrEnd
	 * @throws Exception
	 * Boger
	 * 2016年3月16日下午2:27:39
	 */
	public void loggerInfoOutput(String loggerLevel, String loggerBeginOrEnd) {
		try {
			Map<String, Object> map=getCallMethodAndClass();
			String strMethodName=map.get("methodName").toString();
			logger=(Logger)map.get("logger");
			logger.info(strMethodName+" "+loggerBeginOrEnd);
		} catch (Exception e) {
			logger.error("loggerInfoOutput error: errorMsg=",e.getMessage(),e);
		}

	}

	/**
	 * 输出异常日志信息
	 * @param loggerLevel
	 * @param exception
	 * @throws Exception
	 * Boger
	 * 2016年3月16日下午2:24:10
	 */
	public void loggerErrorOutput(String loggerLevel, Object exception) {
		try {
			Exception e=(Exception)exception;
			Map<String, Object> map=getCallMethodAndClass();
			String strMethodName=map.get("methodName").toString();
			logger=(Logger)map.get("logger");
			logger.error(strMethodName+" error: errorMsg=",e.getMessage(),e);
		} catch (Exception e) {
			logger.error("loggerErrorOutput error: errorMsg=",e.getMessage(),e);
		}
	}
	
	/**
	 * 生成日志读取器
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年3月16日下午2:21:45
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Object> getCallMethodAndClass() throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		
		StackTraceElement[] stackTraceElementArr=Thread.currentThread().getStackTrace();
		//获取父级的父级的堆栈属性
		StackTraceElement stackTraceElement=stackTraceElementArr[3];
		String methodName= stackTraceElement.getMethodName();
		String className=stackTraceElement.getClassName();
		Class loggerClass=Class.forName(className);
		Object loggerObject=loggerClass.newInstance();
		Logger logger=LoggerFactory.getLogger(loggerObject.getClass());
		
		map.put("methodName", methodName);
		map.put("className", className);
		map.put("logger", logger);
		
		return map;
	}
	
}
