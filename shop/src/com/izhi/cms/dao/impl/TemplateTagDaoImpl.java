package com.izhi.cms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ITemplateTagDao;
import com.izhi.cms.model.TemplateTag;
import com.izhi.platform.util.PageParameter;
@Service("templateTagDao")
public class TemplateTagDaoImpl extends HibernateDaoSupport implements ITemplateTagDao {

	@Override
	public boolean deleteTemplateTag(int id) {
		String sql="delete from TemplateTag o where o.tagId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteTemplateTags(List<Integer> ids) {
		String sql="delete from TemplateTag o where o.tagId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public TemplateTag findTemplateTagById(int id) {
		return (TemplateTag)this.getHibernateTemplate().load(TemplateTag.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TemplateTag> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(TemplateTag.class);
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
		String sql="select count(*) from TemplateTag";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveTemplateTag(TemplateTag obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateTemplateTag(TemplateTag obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TemplateTag findTemplateTagByName(String name) {
		String sql="from TemplateTag o where o.tagName=?";
		List<TemplateTag> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

}
