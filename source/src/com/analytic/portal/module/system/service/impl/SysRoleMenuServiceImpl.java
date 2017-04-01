package com.analytic.portal.module.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.module.system.dao.interfaces.SysRoleMenuDao;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleMenu;
import com.analytic.portal.module.system.service.interfaces.SysRoleMenuService;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysRoleMenu> getSysRoleMenu4SysRoleId(String sysRoleId) throws Exception{
		Map map = new HashMap<>();
		map.put("roleId", sysRoleId);
		return sysRoleMenuDao.getSysRoleMenuList(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysRoleMenu4RoleId(SysRole sysRole) throws Exception {
		boolean flag = sysRoleMenuDao.deleteSysRoleMenu4RoleId(sysRole.getId());
		if (sysRole.getSysRoleMenuIds() != null) {
			List<String> sysRoleMenuIds = (List<String>)new ObjectMapper().readValue(sysRole.getSysRoleMenuIds(), List.class);
			for (String roleMenuId : sysRoleMenuIds) {
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setMenuId(roleMenuId);
				sysRoleMenu.setRoleId(sysRole.getId());
				sysRoleMenu.setId(DataOperation.getSequenseStr());
				sysRoleMenuDao.save(sysRoleMenu);
			}
		}
		return flag;
		}
	}
