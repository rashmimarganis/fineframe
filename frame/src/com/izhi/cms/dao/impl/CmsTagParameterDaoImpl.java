package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTagParameterDao;
import com.izhi.cms.model.CmsTagParameter;
import com.izhi.platform.util.PageParameter;
@Service("cmsTagParameterDao")
public class CmsTagParameterDaoImpl extends HibernateDaoSupport implements ICmsTagParameterDao{

	@Override
	public boolean deleteTagParameter(int id) {
		CmsTagParameter m=(CmsTagParameter)this.getHibernateTemplate().load(CmsTagParameter.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteTagParameters(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteTagParameter(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.parameterId as parameterId,o.name as name,o.label as label,o.value as value,o.tag.tagId as tagId) from CmsTagParameter o where o.parameterId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsTagParameter findTagParameterById(int id) {
		CmsTagParameter m=(CmsTagParameter)this.getHibernateTemplate().load(CmsTagParameter.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,int tagId) {
		String sql="select new map(o.parameterId as parameterId,o.name as name,o.label as label,o.value as value,o.tag.tagId as tagId) from CmsTagParameter o where o.tag.tagId=:tagId";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
			Session s=this.getSession();
			Query q=s.createQuery(sql);
			q.setInteger("tagId", tagId);
			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());
			
			return q.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int tagId) {
		String sql="select count(o) from CmsTagParameter o where o.tag.tagId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,tagId);
		return l.get(0).intValue();
	}

	@Override
	public int saveTagParameter(CmsTagParameter obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateTagParameter(CmsTagParameter obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
