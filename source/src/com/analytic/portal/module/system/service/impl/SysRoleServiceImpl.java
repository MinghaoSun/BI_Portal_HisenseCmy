package com.analytic.portal.module.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.analytic.portal.module.report.dao.interfaces.ReportParamDao;
import com.analytic.portal.module.report.dao.interfaces.ReportParamRelateDataDao;
import com.analytic.portal.module.report.model.ReportParamRelateData;
import com.analytic.portal.module.report.model.ReportRefreshParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.common.util.StringUtil;
import com.analytic.portal.module.common.service.IEncodingService;
import com.analytic.portal.module.system.dao.interfaces.SysRoleDao;
import com.analytic.portal.module.system.dao.interfaces.SysRoleMenuDao;
import com.analytic.portal.module.system.dao.interfaces.SysRoleParamDao;
import com.analytic.portal.module.system.model.SysRole;
import com.analytic.portal.module.system.model.SysRoleMenu;
import com.analytic.portal.module.system.model.SysRoleParam;
import com.analytic.portal.module.system.model.SysUser;
import com.analytic.portal.module.system.service.interfaces.SysMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleMenuService;
import com.analytic.portal.module.system.service.interfaces.SysRoleParamService;
import com.analytic.portal.module.system.service.interfaces.SysRoleService;
import com.yzd.plat.common.util.DateUtil;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;

	@Resource
	private SysRoleParamDao sysRoleParamDao;

	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

	@Resource
	private ReportParamDao reportParamDao;
	
	@Resource
	private SysRoleParamService sysRoleParamService;

	@Resource
	private SysRoleMenuService sysRoleMenuService;
	
	@Resource
	private IEncodingService iEncodingService;
	
	@Resource
	private SysMenuService sysMenuService;

	@Resource
	private ReportParamRelateDataDao reportParamRelateDataDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Map getSysRolesListByPage(SysRole sysRole, int page) throws Exception{
		sysRole.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
		return sysRoleDao.getSysRolesListByPage(sysRole, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveSysRole(SysRole sysRole) throws Exception {
		String roleId = DataOperation.getSequenseStr();
		sysRole.setIsDelete(GlobalConstants.DATA_STATUS_NORMAL);
		sysRole.setCreateTime(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
		String code = GlobalConstants.BUSINESS_ENCODING_SYSTEM_ROLE;
		code += iEncodingService.getBussinessCode(SysRole.class);
		sysRole.setCode(code);
		//sysRole.setCode(DateUtil.getDateFormat(new Date(),"yyyyMMddHHmmssSSS"));
		sysRole.setId(roleId);
		
		if ("1".equals(sysRole.getRoleType())) {
			//List<String> sysRoleMenuIds = (List<String>)new ObjectMapper().readValue(sysRole.getSysRoleMenuIds(), List.class);
			if(sysRole.getSysRoleMenuIds()!=null && !"".equals(sysRole.getSysRoleMenuIds())){
				String[] sysRoleMenuIds = sysRole.getSysRoleMenuIds().split(",");
				for (String roleMenuId : sysRoleMenuIds) {
					SysRoleMenu sysRoleMenu = new SysRoleMenu();
					sysRoleMenu.setMenuId(roleMenuId);
					sysRoleMenu.setRoleId(sysRole.getId());
					sysRoleMenu.setId(DataOperation.getSequenseStr());
					sysRoleMenuDao.save(sysRoleMenu);
				}
			}
		}
		if ("3".equals(sysRole.getRoleType())) {
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
					sysRoleParam.setRoleId(roleId);
					sysRoleParamDao.save(sysRoleParam);
				}
			}
		}
		
		return sysRoleDao.save(sysRole) ;
	}

	@Override
	public boolean updateSysRole(SysRole sysRole) throws Exception {
		boolean flag = true;
		sysRole.setUpdateTime(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
		if ("1".equals(sysRole.getRoleType())) {
			sysRoleParamDao.deleteSysRoleParam4RoleId(sysRole.getId());
			sysRoleMenuDao.deleteSysRoleMenu4RoleId(sysRole.getId());
			if (StringUtil.isNotEmpty(sysRole.getSysRoleMenuIds())) {
				String[] sysRoleMenuIds = sysRole.getSysRoleMenuIds().split(",");
				for (String roleMenuId : sysRoleMenuIds) {
					SysRoleMenu sysRoleMenu = new SysRoleMenu();
					sysRoleMenu.setMenuId(roleMenuId);
					sysRoleMenu.setRoleId(sysRole.getId());
					sysRoleMenu.setId(DataOperation.getSequenseStr());
					sysRoleMenuDao.save(sysRoleMenu);
				}
			}
		}
		if ("3".equals(sysRole.getRoleType())) {
			sysRoleMenuDao.deleteSysRoleMenu4RoleId(sysRole.getId());
			flag = sysRoleParamService.updateSysRoleParam4RoleId(sysRole);
		}
		
		return sysRoleDao.update(sysRole) && flag;
	}

	@Override
	public boolean deleteSysRole(String id,SysUser sysUser) throws Exception {
		SysRole sysRole = sysRoleDao.getObjById(SysRole.class, id);
		sysRole.setIsDelete(GlobalConstants.DATA_STATUS_DELETE);
		sysRole.setUpdateTime(DateUtil.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
		sysRole.setUpdateUser(sysUser.getUserFullName());
		return sysRoleDao.save(sysRole);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysRoleParam> getSysRoleParamByRoleId(String roleId)
			throws Exception {
		Map map = new HashMap<>();
		map.put("roleId", roleId);
		return sysRoleParamDao.getSysRoleParamList(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ReportRefreshParam> getSysRoleParamAndRefreshParamByUserId(String userId)
			throws Exception {
		return reportParamDao.getSysRoleParamAndRefreshParamList(userId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysRoleMenu> getSysRoleMenuByMenuId(String menuId)
			throws Exception {
		Map map = new HashMap<>();
		map.put("menuId", menuId);
		return sysRoleMenuDao.getSysRoleMenuList(map);
	}
	@Override
	public List<SysRole> getSysRoleList(SysRole SysRole) throws Exception {
		return sysRoleDao.getSysRoleList(SysRole);
	}
	
	@Override
	public SysRole getSysRole(String roleNameZh) throws Exception {
		// TODO Auto-generated method stub
		return sysRoleDao.getSysRole(roleNameZh);
	}

	@Override
	public List<ReportParamRelateData> getRelateDataByType(String type) throws Exception {
		// TODO Auto-generated method stub
		return reportParamRelateDataDao.getRelateDataByType(type);
	}

}
