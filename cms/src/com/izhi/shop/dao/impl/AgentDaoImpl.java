package com.izhi.shop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IAgentLevelDao;
import com.izhi.shop.model.AgentLevel;
@Service("agentLevelDao")
public class AgentDaoImpl extends HibernateDaoSupport implements IAgentLevelDao {

	@Override
	public boolean deleteAgentLevel(int id) {
		String sql="delete from AgentLevel o where o.agentLevelId=? ";
		int i=this.getHibernateTemplate().bulkUpdate(sql, id);
		return i>0;
	}

	@Override
	public boolean deleteAgentLevels(List<Integer> ids) {
		String sql="delete from AgentLevel o where o.agentLevelId in(:ids)";
		Session session=this.getSession();
		Query q=session.createQuery(sql);
		q.setParameterList("ids", ids);
		int i=q.executeUpdate();
		return i>0;
	}

	@Override
	public AgentLevel findAgentLevelById(int id) {
		return (AgentLevel)this.getHibernateTemplate().load(AgentLevel.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentLevel> findPage(PageParameter pp) {
		String sortField=pp.getSort();
		String sort=pp.getDir();
		int maxResult=pp.getLimit();
		int firstResult=pp.getStart();
		DetachedCriteria dc = DetachedCriteria.forClass(AgentLevel.class);
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
		String sql="select count(*) from AgentLevel";
		List l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@Override
	public int saveAgentLevel(AgentLevel obj) {
		int i=(Integer)this.getHibernateTemplate().save(obj);
		return i;
	}

	@Override
	public boolean updateAgentLevel(AgentLevel obj) {
		this.getHibernateTemplate().update(obj);
		return true;
	}

}
