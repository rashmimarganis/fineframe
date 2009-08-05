package com.izhi.shop.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IBulletinDao;
import com.izhi.shop.model.Bulletin;
@Service("bulletinDao")
public class BulletinDaoImpl extends HibernateDaoSupport implements IBulletinDao {

	@Override
	public Bulletin findBulletinById(int id) {
		return (Bulletin)this.getHibernateTemplate().load(Bulletin.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bulletin> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(Bulletin.class);
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
		String sql="select count(*) from Bulletin";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveBulletin(Bulletin obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateBulletin(Bulletin obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@Override
	public boolean deleteBulletin(int id, int shipId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBulletins(List<Integer> ids, int shopId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bulletin> findPage(PageParameter pp, int shopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bulletin> findTopAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findTotalCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
