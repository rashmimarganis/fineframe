package com.izhi.framework.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameTemplateSuitDao;
import com.izhi.platform.util.PageParameter;
import com.izhi.framework.model.FrameTemplateSuit;
@Service("frameTemplateSuitDao")
public class FrameTemplateSuitDaoImpl extends HibernateDaoSupport implements IFrameTemplateSuitDao {

	@Override
	public boolean deleteTemplateSuit(int id) {
		String sql="delete from FrameTemplateSuit o where o.suitId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteTemplateSuits(List<Integer> ids) {
		String sql="delete from FrameTemplateSuit o where o.suitId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameTemplateSuit findTemplateSuitById(int id) {
		return (FrameTemplateSuit)this.getHibernateTemplate().load(FrameTemplateSuit.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FrameTemplateSuit> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(FrameTemplateSuit.class);
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
	@Override
	public int findTotalCount() {
		String sql="select count(*) from FrameTemplateSuit";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveTemplateSuit(FrameTemplateSuit obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateTemplateSuit(FrameTemplateSuit obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameTemplateSuit findTemplateSuitByName(String name) {
		String sql="from FrameTemplateSuit o where o.suitName=?";
		List<FrameTemplateSuit> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameTemplateSuit findDefaultTemplateSuit() {
		String sql="from FrameTemplateSuit o where o.default=true";
		List<FrameTemplateSuit> l=this.getHibernateTemplate().find(sql);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

}
