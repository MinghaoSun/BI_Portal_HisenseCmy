package com.analytic.portal.module.system.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface SysLogDao {
	
	@SuppressWarnings("rawtypes")
	public Map getLogListByPage(Map formMap) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List getSysLogListByUser(String LoginName) throws Exception;

}
