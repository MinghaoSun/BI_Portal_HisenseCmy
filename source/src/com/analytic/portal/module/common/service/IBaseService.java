package com.analytic.portal.module.common.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analytic.portal.common.sys.FPage;
import com.analytic.portal.common.sys.SQLObject;

public interface IBaseService {

	/**
	 * 通过Id获取对象
	 * @param classObj	class类
	 * @param id	id号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object getObjById(Class classObj, long id);
	
	/**
	 * 通过Id获取对象
	 * @param classObj	class类
	 * @param id	id号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object getObjById(Class classObj, Integer id);
	
	/**
	 * 通过Id获取对象
	 * @param classObj	class类
	 * @param id	id号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object getObjById(Class classObj, String id);
	
	/**
	 * 新增或保存对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrUpdate(Object obj)throws Exception;
	
	/**
	 * 批量保存多个对象
	 * @param entities	实体类集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdateAll(List entities)throws Exception;
	
	/****
	 * 根据sql语句执行
	 * @param sql
	 * @throws Exception
	 */
	public void executeSql(String sql)throws Exception;
	
	/****
	 * 通过sql语句获取数据集合  
	 * @param sql	jdbc形式的sql语句
	 * @return	List
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getDatasByJDBCSQL(String sql) throws SQLException;
	
	/****
	 * 通过sql语句获取单条数据
	 * @param sql	jdbc形式的sql语句
	 * @return	List
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getDataByJDBCSQL(String sql)throws SQLException;
	
	/**
	 * 保存一个对象后返回id号为int类型
	 * @param saveObj	保存的对象
	 * @return
	 */
	public Integer saveAndGetId4Integer(Object saveObj)throws Exception;
	
	/**
	 * 保存一个对象后返回id号为long类型
	 * @param saveObj	保存的对象
	 * @return
	 */
	public Long saveAndGetId4Long(Object saveObj)throws Exception;
	
	/**
	 * 保存一个对象后返回id号为String类型
	 * @param saveObj	保存的对象
	 * @return
	 */
	public String saveAndGetId4String(Object saveObj)throws Exception;
	
	/**
	 * 通过hql语句修改,返回修改是否成功
	 * @param hql
	 * @return
	 */
	public boolean updateByHql(String hql)throws Exception;
	
	/**
	 * 执行hql语句,返回影响的行数
	 * @param hql
	 * @return
	 */
	public int executeByHql(String hql)throws Exception;
	
	/**
	 * 通过hql语句获取对象
	 * @param hql
	 * @return
	 */
	public Object getObjByHql(String hql);
	
	/**
	 * 通过hql语句获取集合
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql);
	
	/**
	 * 带参数的通过hql语句获取集合
	 * @param hql
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql, Object[] param);
	
	
	/**
	 * 通过对象获取List
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getList(Class c);
	
	/**
	 * 通过hql语句删除
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public boolean remove(String hql) throws Exception;
	
	/**
	 * 直接删除对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean remove(Object obj) throws Exception;
	
	/**
	 * 通过hql语句分页查询,map.get("count")返回总记录个数,map.get("result")返回所有记录
	 * @param queryHql	hql语句
	 * @param queryCountHql	总个数语句
	 * @param firstResult	开始下标
	 * @param maxResult	最大个数
	 * @return map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map getListByPage(String queryHql, String queryCountHql,
                             int firstResult, int maxResult) throws Exception;
	
	/**
	 * 通过hibernate对象的属性自动模糊查询。注意:默认情况下值为null的属性将被排除。 如果你的持久类的属性有int等类型，则须给它赋值，因为默认情况下它的值不是null，而是0
	 * @param objClass	对象的类
	 * @param condtionObj	实体对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListByLike(final Class objClass, Object condtionObj);
	
	/**
	 * 通过hibernate对象的属性自动精确查询。注意:默认情况下值为null的属性将被排除。 如果你的持久类的属性有int等类型，则须给它赋值，因为默认情况下它的值不是null，而是0
	 * @param condtionObj	实体对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getList(Object condtionObj);
	
	/**
	 * 通过jdbc分页查询
	 * sql语句封装:page.setSql(sql);
	 * 如果查询第几页则直接通过page.setCurrentPage(num)进行查询,如果通过下标查询则设置page.setLimit(limit);page.setStart(index);
	 * @param page 分页对象
	 * @return
	 */
	public FPage getListByJDBCPage(final FPage page);
	
	/**
	 * 批量执行Sql语句
	 * @param sqlList	Sql语句集合
	 * @return
	 */
	public void excuteBatchSql(List<String> sqlList)throws Exception;
	
	/****
	 * 通过sql对象获取单条数据,需要传递参数,防止SQL注入
	 * @param sqlObj	jdbc形式的sql语句对象
	 * @return	List
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getDataByJDBCSQL(SQLObject sqlObj)throws SQLException;
	
	/****
	 * 通过sql语句获取数据集合,需要传递参数,防止SQL注入  
	 * @param sqlObj	jdbc形式的sql语句对象
	 * @return	List
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> getDatasByJDBCSQL(SQLObject sqlObj) throws SQLException;
}
