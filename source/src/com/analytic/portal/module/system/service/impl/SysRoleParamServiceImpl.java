package com.analytic.portal.module.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.system.dao.interfaces.SysRoleParamDao;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.service.interfaces.SysRoleParamService;
@Service
public class SysRoleParamServiceImpl implements SysRoleParamService {

	@Resource
	private SysRoleParamDao sysRoleParamDao;
	
	@Override
	public List<SysRoleParam> getSysRoleParam4SysRoleId(String sysRoleId) throws Exception{
		Map map = new HashMap<>();
		map.put("roleId", sysRoleId);
		return sysRoleParamDao.getSysRoleParamList(map);
	}

	@Override
	public boolean updateSysRoleParam4RoleId(SysRole sysRole) throws Exception {
		boolean flag = sysRoleParamDao.deleteSysRoleParam4RoleId(sysRole.getId());
		if (StringUtil.isNotEmpty(sysRole.getSysRoleParams())) {
			List<Map<String,String>> sysRoleParams = (List<Map<String,String>>)new ObjectMapper().readValue(sysRole.getSysRoleParams(), List.class);
			for (Map<String,String> map : sysRoleParams) {
				SysRoleParam sysRoleParam = new SysRoleParam();
				sysRoleParam.setId(DataOperation.getSequenseStr());
				sysRoleParam.setParamValue(map.get("paramValue"));
				sysRoleParam.setParamId(map.get("paramId"));
				sysRoleParam.setParamType(map.get("paramType"));
				if ("".equals(map.get("paramOperatType"))||map.get("paramOperatType")==null){
					sysRoleParam.setParamOperatType("0");
				}else{
					sysRoleParam.setParamOperatType(map.get("paramOperatType"));
				}
				if ("".equals(map.get("paramOperatType"))||map.get("paramOperatType")==null){
					sysRoleParam.setParamValueType("0");
				}else{
					sysRoleParam.setParamValueType(map.get("paramValueType"));
				}
				sysRoleParam.setParamValueRelate(map.get("paramValueRelate"));

				sysRoleParam.setRoleId(sysRole.getId());
				sysRoleParamDao.save(sysRoleParam);
			}
		}
		return flag;
	}
}
