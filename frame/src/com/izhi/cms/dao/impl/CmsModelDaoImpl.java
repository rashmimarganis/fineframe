package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsModelDao;
import com.izhi.cms.model.CmsModel;
import com.izhi.platform.util.PageParameter;
@Service("cmsModelDao")
public class CmsModelDaoImpl extends HibernateDaoSupport implements ICmsModelDao{

	@Override
	public boolean deleteModel(int id) {
		CmsModel m=(CmsModel)this.getHibernateTemplate().load(CmsModel.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteModels(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteModel(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.modelId as modelId,o.name as name,o.show as show,o.hasChild as hasChild,o.sequence as sequence,o.tableName as tableName,o.entityClass as entityClass) from CmsModel o where o.modelId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsModel findModelById(int id) {
		CmsModel m=(CmsModel)this.getHibernateTemplate().load(CmsModel.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		String sql="select new map(o.modelId as modelId,o.name as name,o.tableName as tableName,o.entityClass as entityClass) from CmsModel o ";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
			Session s=this.getSession();
			Query q=s.createQuery(sql);
			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());
			
			return q.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount() {
		String sql="select count(o) from CmsModel o";
		List<Long> l=this.getHibernateTemplate().find(sql);
		return l.get(0).intValue();
	}

	@Override
	public int saveModel(CmsModel obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateModel(CmsModel obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findTree() {
		String sql="select new map(o.modelId as id,o.name as text,1 as leaf) from CmsModel o ";
		return this.getHibernateTemplate().find(sql);
	}

}
