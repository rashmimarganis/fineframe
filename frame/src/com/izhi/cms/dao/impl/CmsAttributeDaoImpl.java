package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsAttributeDao;
import com.izhi.cms.model.CmsAttribute;
import com.izhi.platform.util.PageParameter;
@Service("cmsAttributeDao")
public class CmsAttributeDaoImpl extends HibernateDaoSupport implements ICmsAttributeDao{

	@Override
	public boolean deleteAttribute(int id) {
		CmsAttribute m=(CmsAttribute)this.getHibernateTemplate().load(CmsAttribute.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteAttributes(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteAttribute(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.attributeId as attributeId,o.name as name,o.label as label,o.model.modelId as modelId,o.model.name as modelName) from CmsAttribute o where o.attributeId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsAttribute findAttributeById(int id) {
		CmsAttribute m=(CmsAttribute)this.getHibernateTemplate().load(CmsAttribute.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,int modelId) {
		String sql="select new map(o.attributeId as attributeId,o.name as name,o.label as label) from CmsAttribute o where o.model.modelId=:modelId";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
			Session s=this.getSession();
			Query q=s.createQuery(sql);
			q.setInteger("modelId", modelId);
			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());
			
			return q.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int modelId) {
		String sql="select count(o) from CmsAttribute o where o.model.modelId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,modelId);
		return l.get(0).intValue();
	}

	@Override
	public int saveAttribute(CmsAttribute obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateAttribute(CmsAttribute obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
