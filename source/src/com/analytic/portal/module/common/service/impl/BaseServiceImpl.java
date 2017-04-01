package com.analytic.portal.module.common.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.analytic.portal.common.sys.FPage;
import com.analytic.portal.common.sys.SQLObject;
import com.analytic.portal.module.common.dao.IBaseDao;
import com.analytic.portal.module.common.service.IBaseService;


@Service
public class BaseServiceImpl implements IBaseService {

	@Resource
	private IBaseDao iBaseDao;

	public int executeByHql(String hql) throws Exception {
		
		return iBaseDao.executeByHql(hql);
	}

	public void executeSql(String sql) throws Exception {
		iBaseDao.executeSql(sql);
	}

	@SuppressWarnings("rawtypes")
	public HashMap getDataByJDBCSQL(String sql) throws SQLException {
		return iBaseDao.getDataByJDBCSQL(sql);
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getDatasByJDBCSQL(String sql) throws SQLException {
		
		return iBaseDao.getDatasByJDBCSQL(sql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList(Class c) {
		
		return iBaseDao.getList(c);
	}

	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql) {
		
		return iBaseDao.getListByHql(hql);
	}

	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql, Object[] param) {
		
		return iBaseDao.getListByHql(hql, param);
	}

	public Object getObjByHql(String hql) {
		
		return iBaseDao.getObjByHql(hql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObjById(Class classObj, long id) {
		
		return iBaseDao.getObjById(classObj, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObjById(Class classObj, Integer id) {
		
		return iBaseDao.getObjById(classObj, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getObjById(Class classObj, String id) {
		
		return iBaseDao.getObjById(classObj, id);
	}

	public boolean remove(String hql) throws Exception {
		
		return iBaseDao.remove(hql);
	}

	public boolean remove(Object obj) throws Exception {
		
		return iBaseDao.remove(obj);
	}

	public Integer saveAndGetId4Integer(Object saveObj) throws Exception {
		
		return iBaseDao.saveAndGetId4Integer(saveObj);
	}

	public Long saveAndGetId4Long(Object saveObj) throws Exception {
		
		return iBaseDao.saveAndGetId4Long(saveObj);
	}

	public String saveAndGetId4String(Object saveObj) throws Exception {
		
		return iBaseDao.saveAndGetId4String(saveObj);
	}

	public boolean saveOrUpdate(Object obj) throws Exception {
		
		return iBaseDao.saveOrUpdate(obj);
	}

	@SuppressWarnings("rawtypes")
	public void saveOrUpdateAll(List entities) throws Exception {
		iBaseDao.saveOrUpdateAll(entities);
	}

	@SuppressWarnings("rawtypes")
	public Map getListByPage(String queryHql, String queryCountHql,
			int firstResult, int maxResult) throws Exception {
		
		return iBaseDao.getListByPage(queryHql, queryCountHql, firstResult, maxResult);
	}

	public boolean updateByHql(String hql) throws Exception {
		
		return iBaseDao.updateByHql(hql);
	}

	@SuppressWarnings("rawtypes")
	public List getListByLike(Class objClass, Object condtionObj) {
		
		return iBaseDao.getListByLike(objClass, condtionObj);
	}

	@SuppressWarnings("rawtypes")
	public List getList(Object condtionObj) {
		
		return iBaseDao.getList(condtionObj);
	}

	public FPage getListByJDBCPage(FPage page) {
		
		return iBaseDao.getListByJDBCPage(page);
	}

	public void excuteBatchSql(List<String> sqlList) throws Exception {
		iBaseDao.excuteBatchSql(sqlList);
	}

	@SuppressWarnings("rawtypes")
	public HashMap getDataByJDBCSQL(SQLObject sqlObj) throws SQLException {
		return iBaseDao.getDataByJDBCSQL(sqlObj);
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getDatasByJDBCSQL(SQLObject sqlObj)
			throws SQLException {
		
		return iBaseDao.getDatasByJDBCSQL(sqlObj);
	}

}
