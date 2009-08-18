package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameModelDao;
import com.izhi.framework.model.FrameModel;
import com.izhi.platform.util.PageParameter;
@Service("frameModelDao")
public class FrameModelDaoImpl extends HibernateDaoSupport implements IFrameModelDao{
	@Override
	public boolean deleteModel(int id) {
		String sql="delete from FrameModel o where o.modelId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteModels(List<Integer> ids) {
		String sql="delete from FrameModel o where o.modelId in(:ids)";
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
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.modelId as modelId,o.packageName as packageName,o.sourcePath as sourcePath,o.webPath as webPath,o.name as name,o.encode as encode,o.basePath as basePath) from FrameModel o where 1=1";
		
		String sortField=pp.getSort();
		String sort=pp.getDir();
		sql+=" order by o."+sortField+" "+sort;
		Session s=this.getSession();
		
		Query q=s.createQuery(sql);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		
		return q.list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findJsonById(int id) {
		String sql="select new map(o.modelId as modelId,o.packageName as packageName,o.sourcePath as sourcePath,o.webPath as webPath,o.name as name,o.encode as encode,o.basePath as basePath) from FrameModel o where o.modelId=:id";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		
		return l;
	}

	
}
