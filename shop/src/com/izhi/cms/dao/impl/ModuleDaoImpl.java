package com.izhi.cms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.IModuleDao;
import com.izhi.cms.model.Module;
import com.izhi.platform.util.PageParameter;
@Service("moduleDao")
public class ModuleDaoImpl extends HibernateDaoSupport implements IModuleDao {

	@Override
	public boolean deleteModule(int id) {
		String sql="delete from Module o where o.moduleId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteModules(List<Integer> ids) {
		String sql="delete from Module o where o.moduleId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public Module findModuleById(int id) {
		return (Module)this.getHibernateTemplate().load(Module.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Module> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
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
		String sql="select count(*) from Module";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveModule(Module obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateModule(Module obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Module findModuleByName(String name) {
		String sql="from Module o where o.name=?";
		List<Module> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

}
