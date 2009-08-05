package com.izhi.shop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IProductConfigDao;
import com.izhi.shop.model.ProductConfig;
@Service("productConfigDao")
public class ProductConfigDaoImpl extends HibernateDaoSupport implements IProductConfigDao {

	@Override
	public boolean deleteProductConfig(int id) {
		String sql="delete from ProductConfig o where o.productConfigId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteProductConfigs(List<Integer> ids) {
		String sql="delete from ProductConfig o where o.productConfigId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public ProductConfig findProductConfigById(int id) {
		return (ProductConfig)this.getHibernateTemplate().load(ProductConfig.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductConfig> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(ProductConfig.class);
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
		String sql="select count(*) from ProductConfig";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveProductConfig(ProductConfig obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateProductConfig(ProductConfig obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductConfig findDefaultProductConfig() {
		String sql="from ProductConfig pc where pc.defaultConfig=true";
		List<ProductConfig> list=this.getHibernateTemplate().find(sql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean setDefaultConfig(int id) {
		String sql="update ProductConfig pc set pc.defaultConfig=false";
		this.getHibernateTemplate().bulkUpdate(sql);
		String sql1="update ProductConfig pc set pc.defaultConfig=true where id=?";
		int i=this.getHibernateTemplate().bulkUpdate(sql1, id);
		return i>0;
	}

}
