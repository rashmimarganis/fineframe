package com.izhi.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.izhi.platform.dao.IOrgDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.util.PageParameter;
@Service("orgDao")
public class OrgDaoImpl extends BaseDaoImpl<Org, Integer> implements IOrgDao {

	@Override
	public List<Org> findChildren(Integer parentId) {
		String sql="";
		if(parentId==0){
			sql="select o from Org o where o.parent.id is null";
			return this.find(sql);
		}else{
			sql="select o from Org o where o.parent.id=?";
			return this.find(sql, parentId);
		}
	}

	@Override
	public List<Map<String,Object>> findChildNodes(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findIsExist(String nameFiled, String name) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public int saveOrg(Org obj) {
		return (Integer)this.getHibernateTemplate().save(obj);
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Org> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(Org.class);
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
	public List<Org> findPage(PageParameter pp,int parentId) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(Org.class);
		dc.add(Restrictions.eq("parent.orgId", parentId));
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
	public int findTotalCount(int parentId) {
		String sql="select count(*) from Org o where o.parent.orgId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,parentId);
		return (l.get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Org> findTopPages(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(Org.class);
		dc.add(Restrictions.isNull("parent"));
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
	public int findTopTotalCount() {
		String sql="select count(*) from Org o where o.parent is null";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return (l.get(0)).intValue();
	}

	@Override
	public int updateOrg(Org obj, String on) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findExist(String name,String oname) {
		String sql=null;
		List<Long> l=null;
		if(oname==null){
			sql="select count(*) from Org o where o.orgName=?";
			l=this.getHibernateTemplate().find(sql, name);
		}else{
			sql="select count(*) from Org o where (o.orgName=? and o.orgName!=?) or (?=?)";
			l=this.getHibernateTemplate().find(sql, new Object[]{name,oname,name,oname});
		}
		return l.get(0)>0;
	}

	@Override
	public boolean deleteOrgs(List<Integer> ids) {
		String sql="delete from Org o where o.orgId in(:ids)";
		Query q=this.getSession().createQuery(sql);
		q.setParameterList("ids", ids);
		return q.executeUpdate()>0;
	}

}
