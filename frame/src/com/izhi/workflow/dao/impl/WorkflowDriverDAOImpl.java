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

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IWorkflowDriverDAO;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.model.WFDriverOutputParamEnum;
import com.izhi.workflow.model.WorkflowDriver;
@Service("workflowDriverDao")
public class WorkflowDriverDAOImpl extends HibernateDaoSupport implements
		IWorkflowDriverDAO {
	private static Log log = LogFactory.getLog(WorkflowDriverDAOImpl.class);

	public List getAllWorkflowDrivers() {
		return getHibernateTemplate().findByNamedQuery("AllWorkflowDrivers");
	}

	public WorkflowDriver getWorkflowDriver(Long flowDriverID) {
		return (WorkflowDriver) getHibernateTemplate().load(
				WorkflowDriver.class, flowDriverID);
	}

	public void saveWorkflowDriver(WorkflowDriver flowDriver) {
		getHibernateTemplate().saveOrUpdate(flowDriver);
		getHibernateTemplate().flush();
	}

	public void removeWorkflowDriver(Long flowDriverID) {
		Object flowDriver = getWorkflowDriver(flowDriverID);
		getHibernateTemplate().delete(flowDriver);
		getHibernateTemplate().flush();
	}

	public List getFlowDriversByContextPath(String contextPath) {
		return getHibernateTemplate().findByNamedQuery(
				"FlowDriversByContextPath", contextPath);
	}

	public List findDriverByReadDO(String contextPath, String requestURL) {
		return getHibernateTemplate().findByNamedQuery("DriverByReadDO",
				new Object[] { contextPath, requestURL });
	}

	public List findDriverByWriteDO(String contextPath, String requestURL) {
		return getHibernateTemplate().findByNamedQuery("DriverByWriteDO",
				new Object[] { contextPath, requestURL });
	}

	public List<String> getAllDriverContextPath() {
		return getHibernateTemplate().findByNamedQuery("AllDriverContextPath");
	}

	public WFDriverOutputParam getDriverOutputParam(Long driverOutputParamID) {
		return (WFDriverOutputParam) getHibernateTemplate().load(
				WFDriverOutputParam.class, driverOutputParamID);
	}

	public WFDriverInputParam getDriverInputParam(Long driverInputParamID) {
		return (WFDriverInputParam) getHibernateTemplate().load(
				WFDriverInputParam.class, driverInputParamID);
	}

	public WFDriverOutputParamEnum getDriverOutputParamEnume(
			Long driverOutputParamEnumeID) {
		return (WFDriverOutputParamEnum) getHibernateTemplate().load(
				WFDriverOutputParamEnum.class, driverOutputParamEnumeID);
	}

	public void saveDriverOutputParam(WFDriverOutputParam driverOutputParam) {
		getHibernateTemplate().saveOrUpdate(driverOutputParam);
		getHibernateTemplate().flush();
	}
	
	@Override
	public Map<String, Object> findById(Long flowDriverId) {
		
		String sql="select new map(o.flowDriverID as flowDriverID,o.flowDriverName as flowDriverName,o.memo as memo,o.readURL as readURL,o.writeURL as writeURL,o.contextPath as contextPath)  from WorkflowDriver o where o.flowDriverID=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,flowDriverId);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,
			String contextPath) {
		int start=0;
		int limit=18;
		
		String sort=null;
		String dir=null;
		if(pp!=null){
		    start=pp.getStart();
			limit=pp.getLimit();
			sort=pp.getSort();
			dir=pp.getDir();
		}
		String sql="select new map(o.flowDriverID as flowDriverID,o.flowDriverName as flowDriverName,o.memo as memo,o.readURL as readURL,o.writeURL as writeURL,o.contextPath as contextPath)  from WorkflowDriver o where o.contextPath=:cp";
		if(sort!=null){
			sql+=" order by o."+sort;
			if(dir!=null){
				sql+=" "+dir;
			}
		}
		Query q=this.getSession().createQuery(sql);
		q.setString("cp", contextPath);
		q.setFirstResult(start);
		q.setMaxResults(limit);
		return q.list();
	}

	@Override
	public Long findTotalCount(String contextPath) {
		String sql="select count(*) from WorkflowDriver o where o.contextPath=?";
		List<Long> l=this.getHibernateTemplate().find(sql,contextPath);
		return l.get(0);
	}

	@Override
	public List<Map<String, Object>> findAllDriverContextPath() {
		String sql="select distinct new map(o.contextPath as id,o.contextPath as text,'file' as cls,'true' as leaf,'0' as parentId) from WorkflowDriver o";
		return this.getHibernateTemplate().find(sql);
	}

	@Override
	public Long findDriverInputParamCount(Long driverId) {
		String sql="select count(*) from WFDriverInputParam o where o.workflowDriver.flowDriverID=?";
		Long c=(Long)this.getHibernateTemplate().find(sql,driverId).get(0);
		return c;
	}

	@Override
	public List<Map<String, Object>> findDriverInputParamPage(PageParameter pp,
			Long driverId) {
		String sql="select new map(o.driverInputParamID as driverInputParamID,o.paramName as paramName,o.paramAlias as paramAlias) from WFDriverInputParam o where o.workflowDriver.flowDriverID=:driverId order by o."+pp.getSort()+" "+pp.getDir();
		Query q=this.getSession().createQuery(sql);
		q.setLong("driverId", driverId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public Long findDriverOutputParamCount(Long driverId) {
		String sql="select count(*) from WFDriverOutputParam o where o.workflowDriver.flowDriverID=?";
		Long c=(Long)this.getHibernateTemplate().find(sql,driverId).get(0);
		return c;
	}

	@Override
	public List<Map<String, Object>> findDriverOutputParamPage(PageParameter pp,
			Long driverId) {
		String sql="select new map(o.driverOutputParamID as driverOutputParamID,o.paramName as paramName,o.paramAlias as paramAlias) from WFDriverOutputParam o where o.workflowDriver.flowDriverID=:driverId order by o."+pp.getSort()+" "+pp.getDir();
		Query q=this.getSession().createQuery(sql);
		q.setLong("driverId", driverId);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}

	@Override
	public Map<String, Object> findDriverInputParamById(Long driverInputParamID) {
		String sql="select new map(o.driverInputParamID as driverInputParamID,o.paramName as paramName,o.paramAlias as paramAlias,o.workflowDriver.flowDriverID as flowDriverID) from WFDriverInputParam o where o.driverInputParamID=?";
		List<Map<String, Object>> l=this.getHibernateTemplate().find(sql,driverInputParamID);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> findDriverOutputParamById(
			Long driverOutputParamID) {
		String sql="select new map(o.driverOutputParamID as driverOutputParamID,o.paramName as paramName,o.paramAlias as paramAlias,o.workflowDriver.flowDriverID as flowDriverID) from WFDriverOutputParam o where o.driverOutputParamID=?";
		List<Map<String, Object>> l=this.getHibernateTemplate().find(sql,driverOutputParamID);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}
}
