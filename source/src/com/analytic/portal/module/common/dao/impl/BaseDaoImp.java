package com.analytic.portal.module.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.analytic.portal.module.report.model.ReportParam;
import com.analytic.portal.module.report.model.ReportRefreshParam;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.analytic.portal.common.sys.DataOperation;
import com.analytic.portal.common.sys.FPage;
import com.analytic.portal.common.sys.FPageContext;
import com.analytic.portal.common.sys.SQLObject;
import com.analytic.portal.module.common.dao.IBaseDao;


/****
 * 将sql语句通过hibernate与数据库进行访问
 * @author Louis
 *
 */
public class BaseDaoImp extends HibernateDaoSupport implements IBaseDao {

	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
	  super.setSessionFactory(sessionFactory);
	}
	
	/****
	 * 调用Hibernate的Session执行保存或更新
	 */
	public boolean saveOrUpdate(Object obj)throws Exception{
		boolean b=false;
		//Session session=null;
		try{
			/*session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			session.saveOrUpdate(obj);*/
			this.getHibernateTemplate().saveOrUpdate(obj);
			this.getHibernateTemplate().flush();
			b=true;
		}catch(Exception e){
			b=false;
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}finally{
			//session.close();
		}
		return b;
	}
	
	/****
	 * 根据条件查询该对象
	 */
	public <T> T getObjById(Class<T> classObj,Serializable id) {
		return this.getHibernateTemplate().get(classObj,id);
	}
	
	

	@SuppressWarnings("deprecation")
	public void executeSql(String sql) throws Exception {
		try{
			this.getHibernateTemplate().flush();
			Statement stmt=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().createStatement();
			stmt.execute(sql);
			stmt.close();//此处一定要关闭，因为oracle9i中最大的游标是300，如果超过300条sql语句会报错
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
	}
	

	@SuppressWarnings("deprecation")
	public void executeSqlBindThread(String sql)  {
		try{
			this.getHibernateTemplate().flush();
			Statement stmt=this.getSession().connection().createStatement();
			stmt.execute(sql);
			stmt.close();//此处一定要关闭，因为oracle9i中最大的游标是300，如果超过300条sql语句会报错
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public HashMap getDataByJDBCSQL(String sql) throws SQLException {
		Connection conn=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		SQLObject sqlObj=new SQLObject(sql);
		return DataOperation.getData(conn, sqlObj);
	}
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public List<HashMap> getDatasByJDBCSQL(String sql) throws SQLException {
		Connection conn=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		SQLObject sqlObj=new SQLObject(sql);
		return DataOperation.getDatas(conn, sqlObj);
	}
	
	public int executeByHql(String hql) throws Exception{
		try {
			int c=this.getHibernateTemplate().bulkUpdate(hql);
			this.getHibernateTemplate().flush();
			return c;
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> c) {
		List<T> list = null;
		try {
			list = this.getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(c));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql) {
		List list = null;
		try {
			list = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql, Object[] param) {
		List list = null;
		try {
			list = this.getHibernateTemplate().find(hql,param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Object getObjByHql(String hql) {
		Object ety = null;
		try {
			ety = this.getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ety;
	}

	public Object getListBySql(String sql,Class entityClassObj) {
		Object result = null;
		try {
//			result = this.getSession().createQuery(sql).setMaxResults(1).uniqueResult();
			result=this.getSession().createSQLQuery(sql).addEntity(entityClassObj).list();
//			this.getSession().createSQLQuery(sql).addEntity(ReportRefreshParam.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean remove(String hql) throws Exception {
		try {
			return this.executeByHql(hql) > 0 ? true : false;
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
	}

	public boolean remove(Object obj) throws Exception {
		boolean bo = false;
		try {
			this.getHibernateTemplate().delete(obj);
			this.getHibernateTemplate().flush();
			bo = true;
		} catch (Exception e) {
			bo = false;
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return bo;
	}

	public Integer saveAndGetId4Integer(Object saveObj)throws Exception {
		Integer id = null;
		try {
			id = (Integer) this.getHibernateTemplate().save(saveObj);
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return id;
	}
	
	public Long saveAndGetId4Long(Object saveObj) throws Exception {
		Long id = null;
		try {
			id = (Long) this.getHibernateTemplate().save(saveObj);
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return id;
	}

	public String saveAndGetId4String(Object saveObj)throws Exception {
		String id = null;
		try {
			id = (String) this.getHibernateTemplate().save(saveObj);
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return id;
	}

	public boolean updateByHql(String hql)throws Exception {
		boolean bo = false;
		try {
			int count = this.getHibernateTemplate().bulkUpdate(hql);
			bo = count > 0 ? true : false;
		} catch (Exception e) {
			bo = false;
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return bo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getListByPage(String queryHql, String queryCountHql,
			int firstResult, int maxResult) throws Exception {
		Map map=new HashMap();
		try {
			Session session = this.getSession();
			map.put("result", session.createQuery(queryHql).setFirstResult(firstResult)
					.setMaxResults(maxResult).list());
			map.put("count", session.createQuery(queryCountHql).setMaxResults(1)
					.uniqueResult());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getSqlQueryListByPage(String queryHql, String queryCountHql,
							 int firstResult, int maxResult) throws Exception {
		Map map=new HashMap();
		try {
			Session session = this.getSession();
//			query.setResultTransformer(Transformers.aliasToBean(ProductBillInfo.class));
			/*SQLQuery query= (SQLQuery) session.createSQLQuery(queryHql).setFirstResult(firstResult)
					.setMaxResults(maxResult);
			query.addScalar("id", Hibernate.STRING  );
			query.addScalar("code", Hibernate.STRING);
			query.addScalar("paramName", Hibernate.STRING);
			query.addScalar("paramKey", Hibernate.STRING);
			query.addScalar("paramOrder", Hibernate.STRING);
			query.addScalar("paramRemark", Hibernate.STRING);
			query.addScalar("isDelete", Hibernate.STRING);
			query.addScalar("createTime", Hibernate.STRING);
			query.addScalar("createUser", Hibernate.STRING);
			query.addScalar("updateTime", Hibernate.STRING);
			query.addScalar("updateUser", Hibernate.STRING);
			query.addScalar("paramValue", Hibernate.STRING);
			query.setResultTransformer(Transformers.aliasToBean(ReportRefreshParam.class));

			List<ReportParam> list= query.list();*/
			map.put("result", session.createSQLQuery(queryHql).addEntity(ReportRefreshParam.class).setFirstResult(firstResult)
					.setMaxResults(maxResult).list());
			map.put("count", session.createSQLQuery(queryCountHql).setMaxResults(1)
					.uniqueResult());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getListByPage(String queryHql, int firstResult, int maxResult)
			throws Exception {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		try {
			Session session = this.getSession();
			map.put("result", session.createQuery(queryHql).setFirstResult(firstResult)
					.setMaxResults(maxResult).list());
			map.put("count", (long)session.createQuery(queryHql).list().size());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getListByPage(String queryHql, String queryCountHql,
			int firstResult, int maxResult,Object entityBean) throws Exception {
		Map map=new HashMap();
		try {
			Session session = this.getSession();
			map.put("result", session.createQuery(queryHql).setFirstResult(firstResult)
					.setMaxResults(maxResult).setResultTransformer(Transformers.aliasToBean(entityBean.getClass())).list());
			map.put("count", session.createQuery(queryCountHql).setMaxResults(1)
					.uniqueResult());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	public void saveOrUpdateAll(List entities) throws Exception {
		getHibernateTemplate().saveOrUpdateAll(entities);
		this.getHibernateTemplate().flush();
	}

	public HibernateTemplate getHibernateTemplates(){
		return this.getHibernateTemplate();
	}

	public SessionFactory getSessionFactorys() {
		
		return this.getSessionFactory();
	}

	@SuppressWarnings("rawtypes")
	public FPage getListByJDBCPage(final FPage page) {
		FPageContext fPageContext = new FPageContext(page);
		List list = fPageContext.getDatas();
		page.setList(list);
		return page;
	}
	@SuppressWarnings("rawtypes")
	public FPage getListByJDBCPage(final FPage page,Vector<Object> params) {
		FPageContext fPageContext = new FPageContext(page,params);
		List list = fPageContext.getDatas();
		page.setList(list);
		return page;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListByLike(final Class objClass,final Object condtionObj) {
		if(objClass==null||condtionObj==null){
			return null;
		}
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Example ex = Example.create(condtionObj).ignoreCase().enableLike(MatchMode.ANYWHERE);
				return session.createCriteria(objClass).add(ex).list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	@SuppressWarnings("rawtypes")
	public List getList(Object condtionObj) {
		if(condtionObj==null){
			return null;
		}
		return (List) getHibernateTemplate().findByExample(condtionObj);
	}

	public boolean save(Object obj) throws Exception {
		boolean b=false;
		try{
			this.getHibernateTemplate().save(obj);
			this.getHibernateTemplate().flush();
			b=true;
		}catch(Exception e){
			b=false;
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return b;
	}
	public boolean update(Object obj) throws Exception {
		boolean b=false;
		try{
			this.getHibernateTemplate().update(obj);
			this.getHibernateTemplate().flush();
			b=true;
		}catch(Exception e){
			b=false;
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
		return b;
	}
	@SuppressWarnings("deprecation")
	public void excuteBatchSql(List<String> sqlList) throws Exception{
		try{
			this.getHibernateTemplate().flush();
			Statement stmt=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection().createStatement();
			int count=0;
			for(String sql:sqlList){
				stmt.addBatch(sql);
				count++;
				//每10条执行一次
				if(count%10==0){
					stmt.executeBatch();
					stmt.clearBatch();
					continue;
				}
			}
			//最后一次执行数据量不到100条的sql
			stmt.executeBatch();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);//AOP事务回滚,需要抛出RuntimeException异常
		}
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public HashMap getDataByJDBCSQL(SQLObject sqlObj) throws SQLException {
		Connection conn=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		return DataOperation.getData(conn, sqlObj);
	}

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public List<HashMap> getDatasByJDBCSQL(SQLObject sqlObj) throws SQLException {
		Connection conn=this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
		return DataOperation.getDatas(conn, sqlObj);
	}

	@SuppressWarnings("rawtypes")
	public List getListByHql(String hql, Object[] param, Object entityBean) {
		List list = null;
		try {
			Session session = this.getSession();
			session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(entityBean.getClass())).list();
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					session.createQuery(hql).setParameter(i, param[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean updateBySQL(String sql,Object... params){
		return DataOperation.updateSQL(sql,params);
	}

	@Override
	public boolean updateByListSQL(String sql, List<Object> list) {
		return DataOperation.updateListSQL(sql,list);
	}

	@Override
	public Map<String,Object> getListByPageParam(String queryHql, String queryCountHql, int currentPage, int pageSize,Object... params) {
		if(currentPage<=0){
			currentPage = 1;
		}
		int firstResult = (currentPage-1) * pageSize;
		Session session = this.getSession();
		Query resultQ = session.createQuery(queryHql);
		Query countQ = session.createQuery(queryCountHql);
		for(int i=0;i<params.length;i++){
			resultQ.setParameter(i, params[i]);
			countQ.setParameter(i, params[i]);
		}
		resultQ.setFirstResult(firstResult);
		resultQ.setMaxResults(pageSize);
//		resultQ.setResultTransformer(new AliasToBeanResultTransformer(clazz));
		Object count = countQ.setMaxResults(1).uniqueResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", resultQ.list());
		map.put("count", count);
		int totalPages = 1;
		if(count!=null&&!"".equals(count)){
			int totalCount = Integer.parseInt(count.toString());
			totalPages = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount/pageSize) + 1;
		}
		map.put("totalPages", totalPages);
		map.put("currentPage", currentPage);
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListByHqlLimit(String hql, int limit) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		return session.createQuery(hql).setFirstResult(0).setMaxResults(limit).list();
	
	}



}
