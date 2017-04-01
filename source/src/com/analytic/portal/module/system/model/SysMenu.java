package com.analytic.portal.module.system.model;

import java.io.Serializable;
import java.util.List;

import com.analytic.portal.common.sys.GlobalConstants;
import com.analytic.portal.module.common.model.I18NModel;


/**
 * 菜单表
 * @author pengbo
 * 2016-09-09
 */
@SuppressWarnings("serial")
public class SysMenu extends I18NModel implements Serializable {

	//主键
	private String id;
	//菜单代码
	private String code;
	//菜单名称
	@SuppressWarnings("unused")
	private String menuName;
	//英文名称
	private String menuNameEn;
	//中文名称
	private String menuNameZh;
	private String menuIcon;
	//菜单地址
	private String menuUrl;
	//菜单状态
	private String menuStatus;
	//菜单顺序
	private Integer menuOrder;
	//上级菜单
	private String menuParentId;
	//菜单类型
	private String menuType;
	//报表目录
	private String menuAttribute;
	//报表工具
	private String menuReportTool;
	//菜单备注
	private String menuRemark;
	//是否删除
	private String isDelete;
	//创建时间
	private String createTime;
	//创建用户
	private String createUser;
	//更新时间
	private String updateTime;
	//更新用户
	private String updateUser;
	
	private String designType;
	

	//子菜单列表
	private List<SysMenu> menuChild;
	//报表参数
	private String reportMenuParam;
	private String status="否";

	public SysMenu() {
	}

	public SysMenu(String id, String code, String menuName, String menuNameEn,
			String menuNameZh,String menuIcon, String menuUrl,
			String menuStatus, Integer menuOrder, String menuParentId,
			String menuType, String menuAttribute, String menuRemark,
			String isDelete, String createTime, String createUser, String updateTime,
			String updateUser) {
		this.id = id;
		this.code = code;
		this.menuName = menuName;
		this.menuNameEn = menuNameEn;
		this.menuNameZh = menuNameZh;
		this.menuIcon = menuIcon;
		this.menuUrl = menuUrl;
		this.menuStatus = menuStatus;
		this.menuOrder = menuOrder;
		this.menuParentId = menuParentId;
		this.menuType = menuType;
		this.menuAttribute = menuAttribute;
		this.menuRemark = menuRemark;
		this.isDelete=isDelete;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMenuName() {
		if(localLanguage!=null&&!"".equals(localLanguage)){
			return GlobalConstants.I18N_ZH_CN.equals(localLanguage) ? this.menuNameZh : this.menuNameEn;
		}
		return this.menuNameZh;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuNameEn() {
		return this.menuNameEn;
	}
	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}

	public String getMenuNameZh() {
		return this.menuNameZh;
	}
	public void setMenuNameZh(String menuNameZh) {
		this.menuNameZh = menuNameZh;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuStatus() {
		return this.menuStatus;
	}
	public void setMenuStatus(String menuStatus) {
		this.menuStatus = menuStatus;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	
	public String getMenuParentId() {
		return menuParentId;
	}
	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	public String getMenuAttribute() {
		return menuAttribute;
	}

	public void setMenuAttribute(String menuAttribute) {
		this.menuAttribute = menuAttribute;
	}
	
	public String getMenuReportTool() {
		return menuReportTool;
	}
	public void setMenuReportTool(String menuReportTool) {
		this.menuReportTool = menuReportTool;
	}

	public String getMenuRemark() {
		return menuRemark;
	}
	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateUser() {
		return this.createUser;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateUser() {
		return this.updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}

	public List<SysMenu> getMenuChild() {
		return menuChild;
	}
	public void setMenuChild(List<SysMenu> menuChild) {
		this.menuChild = menuChild;
	}

	public String getReportMenuParam() {
		return reportMenuParam;
	}
	public void setReportMenuParam(String reportMenuParam) {
		this.reportMenuParam = reportMenuParam;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}
	
}