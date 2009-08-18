package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameTemplateDao;
import com.izhi.framework.model.FrameTemplate;
import com.izhi.platform.util.PageParameter;
@Service("frameTemplateDao")
public class FrameTemplateDaoImpl extends HibernateDaoSupport implements IFrameTemplateDao {

	@Override
	public boolean deleteTemplate(int id) {
		String sql="delete from FrameTemplate o where o.templateId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		String sql="delete from FrameTemplate o where o.templateId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameTemplate findTemplateById(int id) {
		return (FrameTemplate)this.getHibernateTemplate().load(FrameTemplate.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.templateId as templateId,o.name as name,o.fileName as fileName,o.type as type) from FrameTemplate o where 1=1";
		
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
		String sql="select count(*) from FrameTemplate";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveTemplate(FrameTemplate obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateTemplate(FrameTemplate obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameTemplate findTemplateByName(String name) {
		String sql="from FrameTemplate o where o.name=?";
		List<FrameTemplate> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findJsonById(int id) {
		String sql="select new map(o.templateId as templateId,o.name as name,o.fileName as fileName,o.type as type) from FrameTemplate o where o.templateId=:id";
		
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		
		return l;
	}
	@SuppressWarnings("unchecked")
	@Override
	public FrameTemplate findTemplateByFileName(String name) {
		String sql="from FrameTemplate o where o.fileName=?";
		List<FrameTemplate> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonByType(String type) {
		String sql="select new map(o.templateId as templateId,o.name as name,o.fileName as fileName)from FrameTemplate o where o.type=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,type);
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCountByType(String type) {
		String sql="select count(*) from FrameTemplate o where o.type=?";
		List<Long> l=this.getHibernateTemplate().find(sql,type);
		return l.get(0).intValue();
	}

}
