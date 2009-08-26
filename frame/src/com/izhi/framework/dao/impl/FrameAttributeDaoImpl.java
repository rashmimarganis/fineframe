package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameAttributeDao;
import com.izhi.framework.model.FrameAttribute;
import com.izhi.platform.util.PageParameter;
@Service("frameAttributeDao")
public class FrameAttributeDaoImpl extends HibernateDaoSupport implements IFrameAttributeDao{
	@Override
	public boolean deleteAttribute(int id) {
		String sql="delete from FrameAttribute o where o.attributeId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteAttributes(List<Integer> ids) {
		String sql="delete from FrameAttribute o where o.attributeId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameAttribute findAttributeById(int id) {
		return (FrameAttribute)this.getHibernateTemplate().load(FrameAttribute.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.attributeId as attributeId,o.name as name,o.label as label,o.length as length,o.javaClass as javaClass,o.isKey as isKey,o.required as required,o.control.controlId as controlId,o.control.label as controlLabel,o.model.modelId as modelId,o.model.label as modelLabel,o.control.controlId as controlId,o.control.label as controlLabel) from FrameAttribute o where 1=1";
		String sortField=pp.getSort();
		String sort=pp.getDir();
		sql+=" order by o."+sortField+" "+sort;
		Session s=this.getSession();
		
		Query q=s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount() {
		String sql="select count(*) from FrameAttribute";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveAttribute(FrameAttribute obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateAttribute(FrameAttribute obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameAttribute findAttributeByName(String name) {
		String sql="from FrameAttribute o where o.name=?";
		List<FrameAttribute> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.attributeId as attributeId,o.name as name,o.label as label,o.length as length,o.javaClass as javaClass,o.isKey as isKey,o.required as required,o.control.controlId as controlId,o.control.label as controlLabel,o.model.modelId as modelId,o.model.label as modelLabel,o.control.controlId as controlId,o.control.label as controlLabel) from FrameAttribute o  where o.attributeId=:id";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		return l;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPageByModel(int mid, PageParameter pp) {
		
		String sql="select new map(o.attributeId as attributeId,o.name as name,o.label as label,o.length as length,o.javaClass as javaClass,o.isKey as isKey,o.required as required,o.control.controlId as controlId,o.control.label as controlLabel,o.model.modelId as modelId,o.model.label as modelLabel,o.control.controlId as controlId,o.control.label as controlLabel) from FrameAttribute o  where o.model.modelId=:mid";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("mid", mid);
		List<Map<String,Object>> l=q.list();
		return l;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int mid) {
		String sql="select count(*) from FrameAttribute o where o.model.modelId=?";
		List l=this.getHibernateTemplate().find(sql,mid);
		return ((Long)l.get(0)).intValue();
	}
}
