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
import com.izhi.shop.dao.ICategoryDao;
import com.izhi.shop.model.ProductCategory;
@Service("productCategoryDao")
public class CategoryDaoImpl extends HibernateDaoSupport implements ICategoryDao {

	@Override
	public boolean deleteCategory(int id) {
		String sql="delete from ProductCategory o where o.categoryId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteCategorys(List<Integer> ids) {
		String sql="delete from ProductCategory o where o.categoryId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public ProductCategory findCategoryById(int id) {
		return (ProductCategory)this.getHibernateTemplate().load(ProductCategory.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductCategory.class);
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
		String sql="select count(*) from ProductCategory";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveCategory(ProductCategory obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateCategory(ProductCategory obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findPage(PageParameter pp, int parentId) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductCategory.class);
		dc.add(Restrictions.eq("parent.categoryId", parentId));
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
		String sql="select count(*) from ProductCategory o where o.parent.categeryId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,parentId);
		return (l.get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategory> findTopAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(ProductCategory.class);
		dc.add(Restrictions.isNull("parent"));
		return this.getHibernateTemplate().findByCriteria(dc);
	}
}
