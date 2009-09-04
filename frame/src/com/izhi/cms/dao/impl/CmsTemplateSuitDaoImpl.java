package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateSuitDao;
import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.platform.util.PageParameter;
@Service("cmsTemplateSuitDao")
public class CmsTemplateSuitDaoImpl extends HibernateDaoSupport implements ICmsTemplateSuitDao{

	@Override
	public boolean deleteSuit(int id) {
		CmsTemplateSuit m=(CmsTemplateSuit)this.getHibernateTemplate().load(CmsTemplateSuit.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteSuits(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteSuit(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.suitId as suitId,o.name as name,o.packageName as packageName) from CmsTemplateSuit o where o.suitId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsTemplateSuit findSuitById(int id) {
		CmsTemplateSuit m=(CmsTemplateSuit)this.getHibernateTemplate().load(CmsTemplateSuit.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql="select new map(o.suitId as suitId,o.name as name,o.packageName as packageName) from CmsTemplateSuit o ";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
			Session s=this.getSession();
			Query q=s.createQuery(sql);
			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());
			
			return q.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount() {
		String sql="select count(o) from CmsTemplateSuit o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveSuit(CmsTemplateSuit obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateSuit(CmsTemplateSuit obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
