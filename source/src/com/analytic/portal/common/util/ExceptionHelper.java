package com.analytic.portal.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 
 * @ClassName: ExceptionHelper 
 * @Description: 异常基本业务处理 
 * @author Minghao 
 * @date 2017年3月16日14:42:19 
 */
public class ExceptionHelper {
	/** 
	* @Description: 异常信息输出
	* @param t 异常
	* @date 2017年3月16日14:42:33
	* @return String    返回类型  
	*/ 
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		String result=buffer.toString().replace("'", "\"");
		return result;
	}
}

