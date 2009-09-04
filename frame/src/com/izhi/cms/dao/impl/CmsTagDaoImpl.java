package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTagDao;
import com.izhi.cms.model.CmsTag;
import com.izhi.platform.util.PageParameter;
@Service("cmsTagDao")
public class CmsTagDaoImpl extends HibernateDaoSupport implements ICmsTagDao{

	@Override
	public boolean deleteTag(int id) {
		CmsTag m=(CmsTag)this.getHibernateTemplate().load(CmsTag.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteTags(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteTag(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.tagId as tagId,o.name as name,o.title as title,o.maxResult as maxResult,o.paged as paged,o.model.modelId as modelId,o.model.name as modelName,o.template.templateId as templateId,o.template.name as templateName) from CmsTag o where o.tagId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsTag findTagById(int id) {
		CmsTag m=(CmsTag)this.getHibernateTemplate().load(CmsTag.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql="select new map(o.tagId as tagId,o.name as name,o.title as title,o.maxResult as maxResult,o.paged as paged,o.model.modelId as modelId,o.model.name as modelName,o.template.templateId as templateId,o.template.name as templateName) from CmsTag o ";
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
		String sql="select count(o) from CmsTag o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveTag(CmsTag obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateTag(CmsTag obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
