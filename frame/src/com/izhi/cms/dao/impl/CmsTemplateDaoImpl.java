package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateDao;
import com.izhi.cms.model.CmsTemplate;
import com.izhi.platform.util.PageParameter;
@Service("cmsTemplateDao")
public class CmsTemplateDaoImpl extends HibernateDaoSupport implements ICmsTemplateDao{

	@Override
	public boolean deleteTemplate(int id) {
		CmsTemplate m=(CmsTemplate)this.getHibernateTemplate().load(CmsTemplate.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteTemplate(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.templateId as templateId,o.name as name,o.fileName as fileName,o.type as type, o.suit.suitId as templateSuitId,o.suit.name as templateSuitName) from CmsTemplate o where o.templateId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsTemplate findTemplateById(int id) {
		CmsTemplate m=(CmsTemplate)this.getHibernateTemplate().load(CmsTemplate.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql="select new map(o.templateId as templateId,o.name as name,o.fileName as fileName,o.type as type, o.suit.suitId as templateSuitId,o.suit.name as templateSuitName) from CmsTemplate o ";
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
		String sql="select count(o) from CmsTemplate o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveTemplate(CmsTemplate obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateTemplate(CmsTemplate obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
