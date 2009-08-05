package com.izhi.framework.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameTemplateDao;
import com.izhi.framework.model.FrameTemplateFile;
import com.izhi.platform.util.PageParameter;
@Service("frameTemplateDao")
public class FrameTemplateDaoImpl extends HibernateDaoSupport implements IFrameTemplateDao {

	@Override
	public boolean deleteTemplate(int id) {
		String sql="delete from FrameTemplateFile o where o.templateId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		String sql="delete from FrameTemplateFile o where o.templateId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameTemplateFile findTemplateById(int id) {
		return (FrameTemplateFile)this.getHibernateTemplate().load(FrameTemplateFile.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FrameTemplateFile> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(FrameTemplateFile.class);
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
		String sql="select count(*) from FrameTemplateFile";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveTemplate(FrameTemplateFile obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateTemplate(FrameTemplateFile obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameTemplateFile findTemplateByName(String name) {
		String sql="from FrameTemplateFile o where o.name=?";
		List<FrameTemplateFile> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

}