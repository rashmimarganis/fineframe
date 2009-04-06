package com.izhi.platform.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDaoImpl<T, Id extends Serializable> extends
		HibernateDaoSupport {
	protected final  Logger log = Logger.getLogger(getClass());
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void delete(Integer id) {
		this.getHibernateTemplate().delete(
				this.getHibernateTemplate().load(entityClass, id));
	}
	public void delete(Long id) {
		this.getHibernateTemplate().delete(
				this.getHibernateTemplate().load(entityClass, id));
	}

	public void delete(T obj) {
		this.getHibernateTemplate().delete(obj);
	}

	public int delete(String ids, String id) {
		String sql = "delete from " + entityClass.getName()
				+ " obj where obj."+id+" in(" + ids + ")";
		return this.getHibernateTemplate().bulkUpdate(sql);
	}
	
	public int delete(String ids) {
		return this.delete(ids, "id");
	}

	public int deleteAll() {
		String sql = "delete from " + entityClass.getName();
		return this.getHibernateTemplate().bulkUpdate(sql);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String sql) {
		return this.getHibernateTemplate().find(sql);
	}
	
	public boolean findIsExist(String name,Object obj){
		String sql="select count(o."+name+")from "+entityClass.getName()+" o where o."+name+"=?";
		return ((Long)this.getHibernateTemplate().find(sql, obj).get(0)).intValue()>0;
	}
	
	public boolean findIsExist(Integer id){
		String sql="select count(o) from "+entityClass.getName()+" o where o.id=?";
		return ((Long)this.getHibernateTemplate().find(sql, id).get(0)).intValue()>0;
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String sql, Object obj) {
		return this.getHibernateTemplate().find(sql, obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String sql, String[] keys, Object[] objs) {
		return this.getHibernateTemplate().findByNamedParam(sql, keys, objs);
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		Object o=this.getHibernateTemplate().load(entityClass, id);
		return (T) o;
	}
	@SuppressWarnings("unchecked")
	public T findById(Long id) {
		Object o=this.getHibernateTemplate().load(entityClass, id);
		return (T) o;
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNameQuery(String queryname, String name, String value) {
		return this.getHibernateTemplate().findByNamedParam(queryname, name,
				value);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNameQuery(String queryname, String[] names,
			String[] values) {
		return this.getHibernateTemplate().findByNamedParam(queryname, names,
				values);
	}

	@SuppressWarnings("unchecked")
	public List<T> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
		if (sort != null && sortField != null) {
			sort = sort.toLowerCase();
			if (sort.equals("desc")) {
				dc.addOrder(Order.desc(sortField));
			} else if (sort.equals("asc")) {
				dc.addOrder(Order.asc(sortField));
			}
		}
		if(maxResult==0){
			maxResult=10;
		}
		return this.getHibernateTemplate().findByCriteria(dc, firstResult,
				maxResult);
	}

	@SuppressWarnings("unchecked")
	public Id save(T obj) {
		return (Id) this.getHibernateTemplate().save(obj);
	}

	public void saveOrUpdate(T obj) {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	public void update(T obj) {
		this.getHibernateTemplate().update(obj);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public int findTotalCount(){
		int i=0;
		Long i1=(Long)this.find("select count(obj) from "+entityClass.getName()+" obj").get(0);
		i=i1.intValue();
		return i;
	}

}
