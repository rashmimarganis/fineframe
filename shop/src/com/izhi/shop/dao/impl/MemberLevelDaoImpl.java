package com.izhi.shop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IMemberLevelDao;
import com.izhi.shop.model.MemberLevel;
@Service("memberLevelDao")
public class MemberLevelDaoImpl extends HibernateDaoSupport implements IMemberLevelDao {

	@Override
	public boolean deleteMemberLevel(int id) {
		String sql="delete from MemberLevel o where o.memberLevelId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteMemberLevels(List<Integer> ids) {
		String sql="delete from MemberLevel o where o.memberLevelId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public MemberLevel findMemberLevelById(int id) {
		return (MemberLevel)this.getHibernateTemplate().load(MemberLevel.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberLevel> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(MemberLevel.class);
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
		String sql="select count(*) from MemberLevel";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveMemberLevel(MemberLevel obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateMemberLevel(MemberLevel obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

}
