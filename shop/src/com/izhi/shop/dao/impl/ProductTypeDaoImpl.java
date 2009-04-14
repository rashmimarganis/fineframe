package com.izhi.shop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductTypeDao;
import com.izhi.shop.model.ProductType;
@Service("productTypeDao")
public class ProductTypeDaoImpl extends HibernateDaoSupport implements IProductTypeDao {

	@Override
	public boolean deleteProductType(int id) {
		String sql="delete from ProductType o where o.productTypeId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteProductTypes(List<Integer> ids) {
		String sql="delete from ProductType o where o.productTypeId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public ProductType findProductTypeById(int id) {
		return (ProductType)this.getHibernateTemplate().load(ProductType.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductType.class);
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
		String sql="select count(*) from ProductType";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveProductType(ProductType obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateProductType(ProductType obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> findPage(PageParameter pp, int parentId) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductType.class);
		dc.add(Restrictions.eq("parent.productTypeId", parentId));
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
		String sql="select count(*) from ProductType o where o.parent.productTypeId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,parentId);
		return (l.get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> findAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(ProductType.class);
		return this.getHibernateTemplate().findByCriteria(dc);
	}
}
