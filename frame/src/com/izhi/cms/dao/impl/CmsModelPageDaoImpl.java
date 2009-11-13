package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsModelPageDao;
import com.izhi.cms.model.CmsModelPage;
import com.izhi.platform.util.PageParameter;
@Service("cmsModelPageDao")
public class CmsModelPageDaoImpl extends HibernateDaoSupport implements ICmsModelPageDao{

	@Override
	public boolean deleteModelPage(int id) {
		CmsModelPage m=(CmsModelPage)this.getHibernateTemplate().load(CmsModelPage.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteModelPages(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteModelPage(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.pageId as pageId,o.pageName as pageName,o.model.modelId as modelId,o.model.name as modelName) from CmsModelPage o where o.pageId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsModelPage findModelPageById(int id) {
		CmsModelPage m=(CmsModelPage)this.getHibernateTemplate().load(CmsModelPage.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,int modelId) {
		String sql="select new map(o.pageId as pageId,o.pageName as pageName) from CmsModelPage o where o.model.modelId=:modelId";
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
		String sql="select count(o) from CmsModelPage o where o.model.modelId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,modelId);
		return l.get(0).intValue();
	}

	@Override
	public int saveModelPage(CmsModelPage obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateModelPage(CmsModelPage obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@Override
	public List<Map<String, Object>> findAll(int modelId) {
		String sql="select new map(o.pageId as pageId,o.pageName as pageName) from CmsModelPage o where o.model.modelId=?";
		return this.getHibernateTemplate().find(sql, modelId);
	}

}
