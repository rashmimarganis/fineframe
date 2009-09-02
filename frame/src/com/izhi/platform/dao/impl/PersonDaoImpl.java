package com.izhi.platform.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IPersonDao;
import com.izhi.platform.model.Person;
import com.izhi.platform.util.PageParameter;
@Service("personDao")
public class PersonDaoImpl extends HibernateDaoSupport implements IPersonDao {

	@Override
	public boolean deletePerson(int id) {
		Person p=(Person)this.getHibernateTemplate().load(Person.class, id);
		p.setOrg(null);
		p.setUsers(null);
		this.getHibernateTemplate().delete(p);
		return true;
	}

	@Override
	public boolean deletePersons(List<Integer> ids) {
		for(Integer id:ids){
			this.deletePerson(id);
		}
		return true;
	}

	@Override
	public Person findById(int id) {
		Person p=(Person)this.getHibernateTemplate().load(Person.class, id);
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.personId as personId,o.realname as realname,o.gender as gender,o.age as age,o.address as address,o.birthday as birthday,o.homeTelephone as homeTelephone,o.officeTelephone as officeTelephone,o.email as email,o.mobilephone as mobilephone,o.org.orgId as orgId,o.org.name as orgName,o.sequence as sequence) from Person o where o.personId=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,id);
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, int orgId) {
		String sql="select new map(o.personId as personId,o.realname as realname,o.gender as gender,o.age as age,o.address as address,o.birthday as birthday,o.homeTelephone as homeTelephone,o.officeTelephone as officeTelephone,o.email as email,o.mobilephone as mobilephone,o.org.orgId as orgId,o.org.name as orgName,o.sequence as sequence) from Person o where o.org.orgId=:orgId";
		
		if(pp.getSort()!=null){
			sql+=" order by o."+pp.getSort();
			if(pp.getDir()!=null){
				sql+=" "+pp.getDir();
			}else{
				sql+=" desc";
			}
		}
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("orgId", orgId);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int orgId) {
		String sql="select count(o) from Person o where o.org.orgId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,orgId);
		return l.get(0).intValue();
	}

	@Override
	public int savePerson(Person o) {
		int i=(Integer)this.getHibernateTemplate().save(o);
		return i;
	}

	@Override
	public int updatePerson(Person o) {
		this.getHibernateTemplate().update(o);
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findPersons(int pid1) {
		Integer pid=null;
		if(pid1!=0){
			pid=pid1;
		}
		List<Map<String, Object>> childrens=new ArrayList<Map<String,Object>>();
		String sql="select new map('o_'||o.orgId as id,o.name as text,0 as person,o.orgId as pid) from Org o";
		if(pid==null){
			sql+=" where o.parent.orgId is null order by o.sort desc";
			childrens=this.getHibernateTemplate().find(sql);
		}else{
			sql+=" where o.parent.orgId =? order by o.sort desc";
			childrens=this.getHibernateTemplate().find(sql,pid);
		}
		
		for(Map<String,Object> c:childrens){
			Integer id=(Integer)c.get("pid");
			List<Map<String, Object>> cc =findPersons(id);
			cc.addAll(this.findPersonsByOrg(id));
			c.put("leaf", cc.size()==0);
			c.put("children", cc);
		}
		return childrens;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPersonsByOrg(int orgId) {
		String sql="select new map(o.personId as id,o.realname as text,1 as person,'true' as leaf,(case when o.gender='m' then 'userMale' else 'userFemale' end)as iconCls) from Person o where o.org.orgId=?";
		return this.getHibernateTemplate().find(sql,orgId);
	}

}
