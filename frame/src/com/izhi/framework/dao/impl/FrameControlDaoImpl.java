package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameControlDao;
import com.izhi.framework.model.FrameControl;
import com.izhi.platform.util.PageParameter;
@Service("frameControlDao")
public class FrameControlDaoImpl extends HibernateDaoSupport implements IFrameControlDao{
	@Override
	public boolean deleteControl(int id) {
		String sql="delete from FrameControl o where o.controlId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteControls(List<Integer> ids) {
		String sql="delete from FrameControl o where o.controlId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameControl findControlById(int id) {
		return (FrameControl)this.getHibernateTemplate().load(FrameControl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.controlId as controlId,o.name as name,o.label as label,o.template.id as templateId,o.template.name as templateName) from FrameControl o where 1=1";
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
		String sql="select count(*) from FrameControl";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveControl(FrameControl obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateControl(FrameControl obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameControl findControlByName(String name) {
		String sql="from FrameControl o where o.name=?";
		List<FrameControl> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.controlId as controlId,o.name as name,o.label as label,o.template.id as templateId) from FrameControl o where o.controlId=:id";
		
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		
		return l;
	}
}
