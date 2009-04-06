package com.izhi.platform.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IPersonDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.PageParameter;
import com.izhi.platform.model.Person;
@Service("personDao")
public class PersonDaoImpl extends BaseDaoImpl<Person, Integer> implements
		IPersonDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPage(PageParameter pp, Org org) {
		String sort=pp.getDir();
		String sortField=pp.getSort();
		DetachedCriteria dc = DetachedCriteria.forClass(Person.class);
		dc.add(Property.forName("org.id").eq( org.getId()));
		if (sort != null && sortField != null) {
			sort = sort.toLowerCase();
			if (sort.equals("desc")) {
				dc.addOrder(Order.desc(sortField));
			} else if (sort.equals("asc")) {
				dc.addOrder(Order.asc(sortField));
			}
		}
		return (List<Person>)this.getHibernateTemplate().findByCriteria(dc, pp.getStart(),
				pp.getLimit());
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPageBySort(PageParameter pp, Org org) {
		String sort=pp.getDir();
		String sortField=pp.getSort();
		DetachedCriteria dc = DetachedCriteria.forClass(Person.class);
		dc.add(Property.forName("org.id").eq( org.getId()));
		if (sort != null && sortField != null) {
			sort = sort.toLowerCase();
			if (sort.equals("desc")) {
				dc.addOrder(Order.desc("sort"));
			} else if (sort.equals("asc")) {
				dc.addOrder(Order.asc("sort"));
			}
		}
		return (List<Person>)this.getHibernateTemplate().findByCriteria(dc, pp.getStart(),
				pp.getLimit());
	}
	@Override
	public int findTotalCount(Org org) {
		int i=0;
		Long i1=(Long)(this.getHibernateTemplate().find("select count(obj) from Person obj where obj.org.id = ?",org.getId()).get(0));
		i=i1.intValue();
		return i;
	}

}
