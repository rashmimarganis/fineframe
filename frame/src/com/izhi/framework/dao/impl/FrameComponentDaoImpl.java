package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameComponentDao;
import com.izhi.framework.model.FrameComponent;
import com.izhi.platform.util.PageParameter;
@Service("frameComponentDao")
public class FrameComponentDaoImpl extends HibernateDaoSupport implements IFrameComponentDao{
	@Override
	public boolean deleteComponent(int id) {
		String sql="delete from FrameComponent o where o.componentId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteComponents(List<Integer> ids) {
		String sql="delete from FrameComponent o where o.componentId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameComponent findComponentById(int id) {
		return (FrameComponent)this.getHibernateTemplate().load(FrameComponent.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.componentId as componentId,o.name as name,o.packageName as packageName,o.type as type,o.template.id as templateId,o.template.name as templateName) from FrameComponent o where 1=1";
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
		String sql="select count(*) from FrameComponent";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveComponent(FrameComponent obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateComponent(FrameComponent obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameComponent findComponentByName(String name) {
		String sql="from FrameComponent o where o.name=?";
		List<FrameComponent> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.componentId as componentId,o.name as name,o.packageName as packageName,o.type as type,o.template.id as templateId) from FrameComponent o where o.componentId=:id";
		
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		
		return l;
	}
}
