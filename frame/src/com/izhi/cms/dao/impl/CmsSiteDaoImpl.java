package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsSiteDao;
import com.izhi.cms.model.CmsSite;
import com.izhi.platform.util.PageParameter;
@Service("cmsSiteDao")
public class CmsSiteDaoImpl extends HibernateDaoSupport implements ICmsSiteDao{

	@Override
	public boolean deleteSite(int id) {
		CmsSite m=(CmsSite)this.getHibernateTemplate().load(CmsSite.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteSites(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteSite(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.siteId as siteId,o.name as name,o.title as title,o.watermark as watermark,o.siteUrl as siteUrl,o.htmlPath as htmlPath,o.closed as closed,o.closeReason as closeReason,o.watermarkPic as watermarkPic,o.watermarkPosition as watermarkerPosition,o.templateSuit.suitId as templateSuitId,o.templateSuit.name as templateSuitName) from CmsSite o where o.siteId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsSite findSiteById(int id) {
		CmsSite m=(CmsSite)this.getHibernateTemplate().load(CmsSite.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql="select new map(o.siteId as siteId,o.name as name,o.title as title,o.watermark as watermark,o.siteUrl as siteUrl,o.htmlPath as htmlPath,o.closed as closed,o.closeReason as closeReason,o.watermarkPic as watermarkPic,o.watermarkPosition as watermarkerPosition,o.templateSuit.suitId as templateSuitId,o.templateSuit.name as templateSuitName) from CmsSite o ";
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
		String sql="select count(o) from CmsSite o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveSite(CmsSite obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateSite(CmsSite obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findAll() {
		String sql="select new map(o.siteId as id,o.name as text) from CmsSite o";
		return this.getHibernateTemplate().find(sql);
	}

}
