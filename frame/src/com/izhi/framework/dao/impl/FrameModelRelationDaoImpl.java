package com.izhi.framework.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.framework.dao.IFrameModelRelationDao;
import com.izhi.framework.model.FrameModelRelation;
import com.izhi.platform.util.PageParameter;
@Service("frameModelRelationDao")
public class FrameModelRelationDaoImpl extends HibernateDaoSupport implements IFrameModelRelationDao {

	@Override
	public boolean deleteRelation(int id) {
		String sql="delete from FrameModelRelation o where o.relationId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteRelations(List<Integer> ids) {
		String sql="delete from FrameModelRelation o where o.relationId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public FrameModelRelation findRelationById(int id) {
		return (FrameModelRelation)this.getHibernateTemplate().load(FrameModelRelation.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findPage(PageParameter pp) {
		String sql="select new map(o.relationId as relationId,o.fieldName as fieldName,o.relation as relation,o.model.modelId as modelId,o.model.name as modelName,o.model.label as modelLabel,o.relationModel.modelId as relationModelId,o.relationModel.name as relationModelName,o.relationModel.label as relationModelLabel) from FrameModelRelation o where 1=1";
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
		String sql="select count(*) from FrameModelRelation";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveRelation(FrameModelRelation obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateRelation(FrameModelRelation obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FrameModelRelation findRelationByName(String name) {
		String sql="from FrameModelRelation o where o.name=?";
		List<FrameModelRelation> l=this.getHibernateTemplate().find(sql,name);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		String sql="select new map(o.relationId as relationId,o.fieldName as fieldName,o.relation as relation,o.model.modelId as modelId,o.model.name as modelName,o.model.label as modelLabel,o.relationModel.modelId as relationModelId,o.relationModel.name as relationModelName,o.relationModel.label as relationModelLabel) from FrameModelRelation o where o.relationId=:id";
		
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("id", id);
		List<Map<String,Object>> l=q.list();
		
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<FrameModelRelation> findAll() {
		String sql="from FrameRelation o where o.relationModel.enabled=true";
		return this.getHibernateTemplate().find(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findPageByModel(int mid, PageParameter pp) {
		String sql="select new map(o.relationId as relationId,o.fieldName as fieldName,o.relation as relation,o.model.modelId as modelId,o.model.name as modelName,o.model.label as modelLabel,o.relationModel.modelId as relationModelId,o.relationModel.name as relationModelName,o.relationModel.label as relationModelLabel) from  FrameModelRelation o  where o.model.modelId=:mid";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("mid", mid);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		List<Map<String,Object>> l=q.list();
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findTotalCount(int mid) {
		String sql="select count(*) from FrameModelRelation o where o.model.modelId=?";
		List<Long> l=this.getHibernateTemplate().find(sql,mid);
		return (l.get(0)).intValue();
	}

	@Override
	public List<Map<String, Object>> findNoRelation(int mid) {
		String sql="select new map(m.modelId as modelId,m.name as name,m.label as label) from FrameModel as m where m.modelId not in(select o.relationModel.modelId from FrameModelRelation  as o where o.model.modelId=:mid) and  m.modelId!=:mid";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("mid", mid);
		List<Map<String,Object>> l=q.list();
		return l;
	}

	@Override
	public int findNoRelationTotalCount(int mid) {
		String sql="select count(m) from FrameModel as m where m.modelId not in(select o.relationModel.modelId from FrameModelRelation  as o where o.model.modelId=:mid) and m.modelId!=:mid";
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setInteger("mid", mid);
		List<Long> l=q.list();
		return (l.get(0)).intValue();
	}

}
