package com.izhi.framework.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameModelDao;
import com.izhi.framework.model.FrameModel;
import com.izhi.platform.util.PageParameter;
@Service("frameModelDao")
public class FrameModelDaoImpl extends HibernateDaoSupport implements IFrameModelDao{
	@Override
	public boolean deleteModel(int id) {
		String sql="delete from FrameModel o where o.projectId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteModels(List<Integer> ids) {
		String sql="delete from FrameModel o where o.projectId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameModel findModelById(int id) {
		return (FrameModel)this.getHibernateTemplate().load(FrameModel.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FrameModel> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(FrameModel.class);
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
		String sql="select count(*) from FrameModel";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveModel(FrameModel obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateModel(FrameModel obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameModel findModelByName(String name) {
		String sql="from FrameModel o where o.name=?";
		List<FrameModel> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}
}