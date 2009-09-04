package com.izhi.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsFunctionDao;
import com.izhi.cms.model.CmsFunction;
import com.izhi.platform.util.PageParameter;
@Service("cmsFunctionDao")
public class CmsFunctionDaoImpl extends HibernateDaoSupport implements ICmsFunctionDao{

	@Override
	public boolean deleteFunction(int id) {
		CmsFunction m=(CmsFunction)this.getHibernateTemplate().load(CmsFunction.class, id);
		this.getHibernateTemplate().delete(m);
		return true;
	}

	@Override
	public boolean deleteFunctions(List<Integer> ids) {
		for(Integer id:ids){
			this.deleteFunction(id);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.modelId as modelId,o.name as name,o.tableName as tableName,o.entityClass as entityClass) from CmsFunction o where o.modelId=?";
		return this.getHibernateTemplate().find(sql, id);
	}

	@Override
	public CmsFunction findFunctionById(int id) {
		CmsFunction m=(CmsFunction)this.getHibernateTemplate().load(CmsFunction.class, id);
		return m;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,int modelId) {
		String sql="select new map(o.modelId as modelId,o.name as name,o.tableName as tableName,o.entityClass as entityClass) from CmsFunction o where o.model.modelId=:modelId";
		if(pp!=null){
			if(pp.getSort()!=null){
				sql+=" order by o."+pp.getSort();
				if(pp.getDir()!=null){
					sql+=" "+pp.getDir();
				}
			}
			Session s=this.getSession();
			Query q=s.createQuery(sql);
			q.setInteger("modelId", modelId);
			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());
			
			return q.list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int modelId) {
		String sql="select count(o) from CmsFunction o where o.model.modelId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,modelId);
		return l.get(0).intValue();
	}

	@Override
	public int saveFunction(CmsFunction obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public int updateFunction(CmsFunction obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

}
