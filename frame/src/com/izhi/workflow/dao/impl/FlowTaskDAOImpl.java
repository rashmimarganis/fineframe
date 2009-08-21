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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowTaskDAO;
import com.izhi.workflow.model.FlowNodeBinding;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.FlowTaskRole;
import com.izhi.workflow.model.FlowTaskUser;
@Service("workflowFlowTaskDao")
public class FlowTaskDAOImpl extends HibernateDaoSupport implements IFlowTaskDAO {
	private static Log log = LogFactory.getLog(FlowTaskDAOImpl.class);

	public FlowTask getFlowTask(Long taskID) {
		return (FlowTask) getHibernateTemplate().load(FlowTask.class, taskID);
	}

	public void saveFlowTask(FlowTask flowTask) {
		getHibernateTemplate().saveOrUpdate(flowTask);
		getHibernateTemplate().flush();
	}

	public boolean isTaskOwner(String userID, Long taskID) {
		String sql = "select ftu from FlowTaskUser ftu inner join ftu.flowTask ft where ftu.userID = ? and ft.taskID = ?";
		return (getHibernateTemplate().find(sql,
				new Object[] { userID, taskID }).size() > 0);
	}

	public boolean isTaskAssigner(String userID, Long taskID) {
		String sql = "select fta from FlowTaskAssigner fta inner join fta.flowTask ft where fta.userID = ? and ft.taskID = ?";
		return (getHibernateTemplate().find(sql,
				new Object[] { userID, taskID }).size() > 0);
	}

	public void removeFlowTask(Long taskID) {
		FlowTask flowTask = getFlowTask(taskID);
		getHibernateTemplate().delete(flowTask);
		getHibernateTemplate().flush();
	}

	public FlowTaskUser getFlowTaskUser(Long id) {
		return (FlowTaskUser) getHibernateTemplate().load(FlowTaskUser.class,
				id);
	}

	public FlowTaskRole getFlowTaskRole(Long id) {
		return (FlowTaskRole) getHibernateTemplate().load(FlowTaskRole.class,
				id);
	}

	public List findAllMyNewTasks(List myPerformingNodes) {
		List result = new ArrayList();
		String sql = "select task from FlowTask task join task.flowNodeBinding node where node.nodeBindingID = ? and task.taskState = ?";
		for (Iterator it = myPerformingNodes.iterator(); it.hasNext();) {
			FlowNodeBinding nodeBinding = (FlowNodeBinding) it.next();
			List tasks = getHibernateTemplate().find(
					sql,
					new Object[] { nodeBinding.getNodeBindingID(),
							FlowTask.TASK_STATE_FREE });
			result.addAll(tasks);
		}
		return result;
	}

	public List findMyNewTasksByType(String userID, Long typeID,
			PageParameter pp) {
		try {
			String sql = "select new map(task.taskID as taskID,task.createTime as createTime,task.flowNodeBinding.workflowDriver.flowDriverName as flowDriverName,task.flowNodeBinding.flowDeploy.flowDeployName as flowDeployName) from NewTask nt join nt.flowTask task join task.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where nt.taskCandidateUserID = ? and bt.typeID = ? and task.taskState in(?) ";
			// order by task.createTime desc
			if (pp.getSort() != null && pp.getSort().length() > 0) {
				sql += " order by task." + pp.getSort();
				if (pp.getDir() != null) {
					sql += " " + pp.getDir();
				}
			}
			Query q = getSession().createQuery(sql);
			q.setString(0, userID);
			q.setLong(1, typeID.longValue());
			q.setString(2, FlowTask.TASK_STATE_FREE);

			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());

			return q.list();
		} catch (HibernateException ex) {
			log.info(ex);
		}
		return null;
	}

	public List findMyExecutingTasksByType(String userID, Long typeID,
			PageParameter pp) {
		try {
			String sql = "select new map(task.taskID as taskID,task.createTime as createTime,task.startTime as startTime,task.flowNodeBinding.flowDeploy.flowDeployName as flowDeployName,task.flowNodeBinding.workflowDriver.flowDriverName as flowDriverName) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and bt.typeID = ? and task.taskState in(?,?) order by task.startTime desc";
			if (pp.getSort() != null && pp.getSort().length() > 0) {
				sql += " order by task." + pp.getSort();
				if (pp.getDir() != null) {
					sql += " " + pp.getDir();
				}
			}
			Query q = getSession().createQuery(sql);
			q.setString(0, userID);
			q.setLong(1, typeID.longValue());
			q.setString(2, FlowTask.TASK_STATE_LOCKED);
			q.setString(3, FlowTask.TASK_STATE_ASSIGNED);

			q.setFirstResult(pp.getStart());
			q.setMaxResults(pp.getLimit());

			return q.list();
		} catch (HibernateException ex) {
			log.info(ex);
		}
		return null;
	}

	public List findMyNewTasksKinds(String userID) {
		String sql = "select bt.typeID,bt.typeName,count(*) from NewTask nt join nt.flowTask task join task.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where nt.taskCandidateUserID = ? and task.taskState in (?) group by bt";
		return getHibernateTemplate().find(sql,
				new Object[] { userID, FlowTask.TASK_STATE_FREE });
	}

	@SuppressWarnings("unchecked")
	public Integer findMyNewTasksNum(String userID) {
		String sql = "select count(*) from NewTask nt join nt.flowTask task "
				+ " where nt.taskCandidateUserID = ? and task.taskState =?";
		List<Integer> l = this.getHibernateTemplate().find(sql,
				new Object[] { new Long(userID), FlowTask.TASK_STATE_FREE });

		return l.get(0);

		/*
		 * return (Integer) getHibernateTemplate().iterate( "select count(*)
		 * from NewTask nt join nt.flowTask task " + " where
		 * nt.taskCandidateUserID = '" + userID + "' and task.taskState = '" +
		 * FlowTask.TASK_STATE_FREE + "'").next();
		 */
	}

	public Integer findMyNewTasksNumByType(String userID, Long typeID) {
		String sql = "select count(*) from NewTask nt join nt.flowTask task join task.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where nt.taskCandidateUserID = ? and task.taskState in(?) and bt.typeID = ?";
		List l = this.getHibernateTemplate().find(sql,
				new Object[] { userID, FlowTask.TASK_STATE_FREE, typeID });
		return ((Long) l.get(0)).intValue();
		/*
		 * Long l = (Long) getHibernateTemplate().findByNamedQuery(
		 * "MyNewTasksNumByType", new Object[] { userID,
		 * FlowTask.TASK_STATE_FREE, typeID }).get( 0); return l.intValue();
		 */
	}

	public List findMyExecutingTasksKinds(String userID) {
		String sql = "select bt.typeID,bt.typeName,count(*) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and task.taskState in (?,?) group by bt";
		return getHibernateTemplate().find(
				sql,
				new Object[] { userID, FlowTask.TASK_STATE_LOCKED,
						FlowTask.TASK_STATE_ASSIGNED });
	}

	public FlowTask findTaskByNodeAndProc(String flowNodeID, Long flowProcID) {
		String sql = "select task from FlowTask task join task.flowNodeBinding node join task.flowProcTransaction.flowProc flowProc where node.flowNodeID = ? and flowProc.flowProcID = ?";
		List tasks = getHibernateTemplate().find(sql,
				new Object[] { flowNodeID, flowProcID });
		if (tasks != null && tasks.size() > 0) {
			return (FlowTask) tasks.get(0);
		}
		log.warn("�ڵ�[" + flowNodeID + "]û�ж�Ӧ������");
		return null;
	}

	public List findMyTasksToAssign(String userID) {
		String sql = "select count(*) from FlowTaskAssigner fta join fta.flowTask task where fta.userID = ? and task.taskState in (?)";
		return getHibernateTemplate().find(sql,
				new Object[] { userID, FlowTask.TASK_STATE_NEED_TO_ASSIGN });
	}

	public Integer findMyTasksToAssignNum(String userID) {
		try {
			String sql = "select count(*) from FlowTaskAssigner fta join fta.flowTask task where fta.userID = ? and task.taskState in (?)";
			Query q = getSession().createQuery(sql);
			q.setString(0, userID);
			q.setString(1, FlowTask.TASK_STATE_NEED_TO_ASSIGN);

			return (Integer) q.iterate().next();
		} catch (HibernateException ex) {
			log.info(ex);
		}
		return null;
	}

	public List findMyRefusedTasks(String userID) {
		String sql = "select task from FlowTaskUser tu join tu.flowTask task where task.taskState in (?) and tu.userID = ?";
		return getHibernateTemplate().find(sql,
				new Object[] { FlowTask.TASK_STATE_REFUSED, userID });
	}

	public Integer findMyRefusedTasksNum(String userID) {
		String sql = "select count(*) from FlowTaskUser tu where tu.flowTask.taskState='"
				+ FlowTask.TASK_STATE_REFUSED + "' and tu.userID = ?";
		List l = getHibernateTemplate().find(sql, new Long(userID));

		return ((Long) l.get(0)).intValue();
	}

	public List findNewTasksNotEmailed() {
		String sql = "select task from FlowTask task where task.taskState in (?) and task.sendEmail = ?";
		return getHibernateTemplate().find(
				sql,
				new Object[] { FlowTask.TASK_STATE_FREE,
						FlowTask.NEW_TASK_NOT_EMAILED });
	}

	public List findMyFinishedTasksKinds(String userID) {
		String sql = "select bt.typeID,bt.typeName,count(*) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and task.taskState= ? group by bt";
		return getHibernateTemplate().find(sql,
				new Object[] { userID, FlowTask.TASK_STATE_FINISHED });
	}

	public Integer findMyExecutingTasksNum(String userID) {
		String sql = "select count(*) from FlowTaskUser tu join tu.flowTask task "
				+ " where tu.userID =? and task.taskState in ('"
				+ FlowTask.TASK_STATE_LOCKED
				+ "','"
				+ FlowTask.TASK_STATE_ASSIGNED + "')";
		List l = getHibernateTemplate().find(sql, new Long(userID));
		return ((Long) l.get(0)).intValue();
	}

	public Integer findMyOtherExecutingTasksNum(String userID) {
		String sql = "select count(*) from FlowTaskUser tu join tu.flowTask task "
				+ " where tu.userID = ? and "
				+ " task.flowNodeBinding.flowDeploy.workflowMeta.businessType is null "
				+ " and task.taskState in ('"
				+ FlowTask.TASK_STATE_LOCKED
				+ "','" + FlowTask.TASK_STATE_ASSIGNED + "')";
		return ((Long) getHibernateTemplate().iterate(sql, new Long(userID))
				.next()).intValue();
	}

	public Integer findMyExecutingTasksNumByType(String userID, Long typeID) {
		String sql = "select count(*) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and task.taskState in(?,?) and bt.typeID = ?";
		List l = getHibernateTemplate().find(
				sql,
				new Object[] { userID, FlowTask.TASK_STATE_LOCKED,
						FlowTask.TASK_STATE_ASSIGNED, typeID });
		return ((Long) l.get(0)).intValue();
	}

	public Integer findMyFinishedTasksNum(String userID) {
		String sql = "select count(*) from FlowTaskUser tu join tu.flowTask task "
				+ " where tu.userID = ? and task.taskState='"
				+ FlowTask.TASK_STATE_FINISHED + "'";
		List l = getHibernateTemplate().find(sql, new Long(userID));
		return ((Long) l.get(0)).intValue();
	}

	public Integer findMyFinishedTasksNumByType(String userID, Long typeID) {
		String sql = "select count(*) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and task.taskState in(?) and bt.typeID = ?";
		List l = getHibernateTemplate().find(sql,
				new Object[] { userID, FlowTask.TASK_STATE_FINISHED, typeID });
		return ((Long) l.get(0)).intValue();
	}

	public List findMyFinishedTasksByType(String userID, Long typeID,
			PageParameter pp) {
		try {
			String sql = "select new map(task.taskID as taskID,task.overTime as overTime,task.startTime as startTime,task.flowNodeBinding.flowDeploy.flowDeployName as flowDeployName,task.flowNodeBinding.workflowDriver.flowDriverName as flowDriverName) from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and bt.typeID = ? and task.taskState in(?) ";
			if (pp.getSort() != null && pp.getSort().length() > 0) {
				sql += " order by task." + pp.getSort();
				if (pp.getDir() != null) {
					sql += " " + pp.getDir();
				}
			}

			Query q = getSession().createQuery(sql);
			q.setString(0, userID);
			q.setLong(1, typeID.longValue());
			q.setString(2, FlowTask.TASK_STATE_FINISHED);
			q.setMaxResults(pp.getLimit());
			q.setFirstResult(pp.getStart());
			return q.list();
		} catch (HibernateException ex) {
			log.info(ex);
		}
		return null;
	}

	public Integer findMyOtherFinishedTasksNum(String userID) {
		String sql="select count(*) from FlowTaskUser tu join tu.flowTask task "
			+ " where tu.userID = ? and "
			+ " task.flowNodeBinding.flowDeploy.workflowMeta.businessType is null "
			+ " and task.taskState='"
			+ FlowTask.TASK_STATE_FINISHED + "'";
		List l = getHibernateTemplate()
				.find(sql,new Long(userID)
						);
		return ((Long) l.get(0)).intValue();
	}

	public Integer findMyOtherNewTasksNum(String userID) {
		String sql="select count(*) from NewTask nt join nt.flowTask task "
			+ " where nt.taskCandidateUserID =? and "
			+ " task.flowNodeBinding.flowDeploy.workflowMeta.businessType is null "
			+ " and task.taskState='"
			+ FlowTask.TASK_STATE_FREE + "'";
		return ((Long) getHibernateTemplate()
				.iterate(sql,new Long(userID)
						).next())
				.intValue();
	}

	/**
	 * findTasksByProc
	 * 
	 * @param flowNodeID
	 *            String
	 * @return FlowTask
	 */
	public List findTasksByProc(Long flowProcID) {
		String sql="select task from FlowTask task join task.flowProcTransaction.flowProc flowProc where flowProc.flowProcID = ?";
		return getHibernateTemplate().find(sql,
				new Object[] { flowProcID });
	}

	@Override
	public List findMyExecutingTasksByType(String userID, Long typeID) {
		try {
			String sql="select task from FlowTaskUser tu join tu.flowTask task join tu.flowTask.flowNodeBinding.flowDeploy.workflowMeta.businessType bt where tu.userID = ? and bt.typeID = ? and task.taskState in(?,?) order by task.startTime desc";
			Query q = getSession()
					.createQuery(sql);
			q.setString(0, userID);
			q.setLong(1, typeID.longValue());
			q.setString(2, FlowTask.TASK_STATE_LOCKED);
			q.setString(3, FlowTask.TASK_STATE_ASSIGNED);
			return q.list();
		} catch (HibernateException ex) {
			log.info(ex);
		}
		return null;
	}

}
