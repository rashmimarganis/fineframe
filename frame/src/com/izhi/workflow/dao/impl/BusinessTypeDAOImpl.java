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

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IBusinessTypeDAO;
import com.izhi.workflow.model.BusinessType;
import com.izhi.workflow.model.FlowTask;


@Service("workflowBusinessTypeDao")
public class BusinessTypeDAOImpl extends HibernateDaoSupport implements
		IBusinessTypeDAO {
	@SuppressWarnings("unchecked")
	public List<BusinessType> findAllBusinessTypes() {
		return getHibernateTemplate().findByNamedQuery("AllBusinessTypes");
	}

	public BusinessType findBusinessType(Long businessTypeID) {
		return (BusinessType) getHibernateTemplate().load(BusinessType.class,
				businessTypeID);
	}

	public void saveBusinessType(BusinessType o) {
		if(o!=null){
			if(o.getTypeID().longValue()==-1){
				this.getHibernateTemplate().saveOrUpdate(o);
			}else{
				BusinessType o1=this.findBusinessType(o.getTypeID());
				o1.setNote(o.getNote());
				o1.setTypeName(o.getTypeName());
				o1.setSort(o.getSort());
				this.getHibernateTemplate().saveOrUpdate(o1);
			}
			this.getHibernateTemplate().flush();
		}
	}

	public void deleteBusinessType(String ids) {
		String sql="delete from BusinessType o where o.typeID in("+ids+")";
		getHibernateTemplate().bulkUpdate(sql);
		getHibernateTemplate().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> findPage(PageParameter pp){
		String sql="select new map(o.typeID as id,o.typeName as name,o.sort as sort,o.note as note) from BusinessType o order by o."+pp.getSort()+" "+pp.getDir();
		Session s=this.getSession();
		Query q=s.createQuery(sql);
		q.setFirstResult(pp.getStart());
		q.setMaxResults(pp.getLimit());
		return q.list();
	}
	
	public int findTotalCount(){
		String sql="select count(*) from BusinessType";
		List<?> l=this.getHibernateTemplate().find(sql);
		return ((Long)l.get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<Map<String,Object>> findAll() {
		String sql="select new map(o.typeID as id,o.typeName as name,o.sort as sort,o.note as note) from BusinessType o";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql);
		return l;
	}
	
	public List<Map<String,Object>> findMyNewTaskKinds(){
		String userID=SecurityUser.getUserId()+"";
		String sql="select new map(bt.typeID as id,bt.typeName||count(*) as name,bt.sort as sort from NewTask nt join nt.flowTask task join task.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where nt.taskCandidateUserID = ? and task.taskState in (?) group by bt";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,new Object[]{userID, FlowTask.TASK_STATE_FREE});
		return l;
	}

	@Override
	public Map<String, Object> findById(Long businessTypeID) {
		String sql="select new map(o.typeID as id,o.typeName as name,o.note as note) from BusinessType o where o.typeID=?";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql,businessTypeID);
		if(l.size()>0){
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findTree() {
		String sql="select new map(o.typeID as id,o.typeName as text,'folder' as cls,'true' as leaf,0 as parentId) from BusinessType o order by o.sort,o.id";
		return this.getHibernateTemplate().find(sql);
	}
	@Override
	public List<Map<String, Object>> findDeployTree() {
		String sql="select new map(o.typeID as id,o.typeName as text,'folder' as cls,0 as parentId) from BusinessType o order by o.sort,o.id";
		return this.getHibernateTemplate().find(sql);
	}

}
