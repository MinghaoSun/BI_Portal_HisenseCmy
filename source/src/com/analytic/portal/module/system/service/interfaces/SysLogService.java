package com.analytic.portal.module.system.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SysLogService {

	@SuppressWarnings("rawtypes")
	public Map getLogListByPage(Map formMap) throws Exception;

	public boolean saveSysLog(HttpServletRequest request,
                              HttpServletResponse response, String operType) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getSysLogListByUser(String LoginName) throws Exception;

}
