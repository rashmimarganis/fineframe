package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsGroupDao;
import com.izhi.cms.model.CmsGroup;
import com.izhi.platform.util.PageParameter;
@Service("cmsGroupDao")
public class CmsGroupDaoImpl extends HibernateDaoSupport implements ICmsGroupDao {

	@Override
	public boolean deleteGroup(int id) {
		String sql="delete from CmsGroup o where o.groupId=?";
		int r=this.getHibernateTemplate().bulkUpdate(sql, id);
		return r>0;
	}

	@Override
	public boolean deleteGroups(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteGroup(id);
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		Session s=this.getSession();
		String sql="select new map(o.groupId as groupId,o.groupName as groupName) from CmsGroup o where groupId=:id";
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		return q.list();
	}

	@Override
	public CmsGroup findObjById(int id) {
		CmsGroup o=(CmsGroup)this.getHibernateTemplate().load(CmsGroup.class, id);
		return o;
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		Session s=this.getSession();
		String sql="select new map(o.groupId as groupId,o.groupName as groupName) from CmsGroup o";
		sql+=" order by o."+pp.getSort()+" "+pp.getDir();
		Query q=s.createQuery(sql);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public int findTotalCount() {
		String sql="select count(*) from CmsGroup";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveGroup(CmsGroup o) {
		int r=(Integer)this.getHibernateTemplate().save(o);
		return r;
	}

	@Override
	public int updateGroup(CmsGroup r) {
		this.getHibernateTemplate().update(r);
		return 1;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		String sql="select new map(o.groupId as id,o.groupName as name) from CmsGroup o";
		return this.getHibernateTemplate().find(sql);
	}

}
