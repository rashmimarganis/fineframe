/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowDeployDAO;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowNodeBinding;
@Service("workflowFlowDeployDao")
public class FlowDeployDAOImpl extends HibernateDaoSupport implements
		IFlowDeployDAO {
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(FlowDeployDAOImpl.class);

	public FlowDeploy findFlowDeploy(Long flowDeployID) {
		return (FlowDeploy) getHibernateTemplate().load(FlowDeploy.class,
				flowDeployID);
	}

	public void saveFlowDeploy(FlowDeploy flowDeploy) {
		getHibernateTemplate().saveOrUpdate(flowDeploy);
		getHibernateTemplate().flush();
	}

	public void saveFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		getHibernateTemplate().saveOrUpdate(flowNodeBinding);
		getHibernateTemplate().flush();
	}

	public void deleteFlowDeploy(Long flowDeployID) {
		FlowDeploy flowDeploy = this.findFlowDeploy(flowDeployID);
		getHibernateTemplate().delete(flowDeploy);
		getHibernateTemplate().flush();
	}

	public FlowNodeBinding findFlowNodeBinding(Long flowNodeBindingID) {
		return (FlowNodeBinding) getHibernateTemplate().load(
				FlowNodeBinding.class, flowNodeBindingID);
	}

	public List findFlowNodeBindingsByDriver(Long flowDriverID) {
		return getHibernateTemplate().findByNamedQuery(
				"FlowNodeBindingsByDriver", flowDriverID);
	}

	public List findFlowNodeBindsByUserPerformer(Long userID) {
		return getHibernateTemplate().findByNamedQuery(
				"FlowNodeBindsByUserPerformer", userID);
	}

	public List findFlowNodeBindsByRolePerformer(Long roleID) {
		return getHibernateTemplate().findByNamedQuery(
				"FlowNodeBindsByRolePerformer", roleID);
	}

	@Override
	public List<Map<String,Object>> findPage(PageParameter pp, Long flowMetaId) {
		String sql="select new map(o.flowDeployID as flowDeployID,o.flowDeployName as flowDeployName,o.createTime as createTime,o.currentState as currentState,o.memo as memo) from FlowDeploy o where o.workflowMeta.flowMetaID=:flowMetaId order by o."+pp.getSort()+" "+pp.getDir();
		Query q=this.getSession().createQuery(sql);
		q.setLong("flowMetaId", flowMetaId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public int findTotalCount(Long flowMetaId) {
		String sql="select count(*) from FlowDeploy o where o.workflowMeta.flowMetaID=?";
		List<Long> l=this.getHibernateTemplate().find(sql, flowMetaId);
		return l.get(0).intValue();
	}

	@Override
	public Map<String, Object> findById(Long id) {
		String sql="select new map(o.flowDeployID as flowDeployID,o.flowDeployName as flowDeployName,o.createTime as createTime,o.currentState as currentState,o.memo as memo) from FlowDeploy o where o.flowDeployID=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,id);
		if(l.size()>0){
			return l.get(0);
		}
		return new HashMap<String,Object>();
	}
}
