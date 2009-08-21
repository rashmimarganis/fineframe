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
package com.izhi.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowTaskDAO;
import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.exception.FlowTaskException;
import com.izhi.workflow.model.BusinessType;
import com.izhi.workflow.model.FlowNodeBinding;
import com.izhi.workflow.model.FlowProcTransaction;
import com.izhi.workflow.model.FlowProcTransition;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.WorkflowMeta;
import com.izhi.workflow.model.WorkflowNode;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IFlowProcService;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.util.FlowDataField;
import com.izhi.workflow.util.TimeUtil;
@Service("workflowFlowTaskService")
public class FlowTaskServiceImpl implements IFlowTaskService {
	private static Logger log = Logger.getLogger(FlowTaskServiceImpl.class);
	@Resource(name="workflowFlowTaskDao")
	private IFlowTaskDAO flowTaskDAO;
	@Resource(name="workflowFlowProcService")
	private IFlowProcService flowProcService;
	@Resource(name="workflowFlowDeployService")
	private IFlowDeployService deployService;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaService;

	public void setFlowProcService(IFlowProcService flowProcService) {
		this.flowProcService = flowProcService;
	}

	public void setDeployService(IFlowDeployService deployService) {
		this.deployService = deployService;
	}

	public void setFlowMetaService(IFlowMetaService flowMetaService) {
		this.flowMetaService = flowMetaService;
	}

	public boolean isTaskOwner(String userID, String taskID) {
		return flowTaskDAO.isTaskOwner(userID, new Long(taskID));
	}

	public boolean isTaskAssigner(String userID, String taskID) {
		return flowTaskDAO.isTaskAssigner(userID, new Long(taskID));
	}

	public FlowTask findFlowTask(String taskID) {
		return flowTaskDAO.getFlowTask(new Long(taskID));
	}

	public FlowTask doCreateFlowTask(FlowProcTransaction procTransaction,
			FlowNodeBinding flowNodeBinding) {
		FlowTask flowTask = new FlowTask();
		flowTask.setCreateTime(TimeUtil.getTimeStamp());
		flowTask.setFlowNodeBinding(flowNodeBinding);
		flowTask.free();

		procTransaction.addFlowTask(flowTask);
		flowTaskDAO.saveFlowTask(flowTask);
		flowProcService.saveFlowProcTransaction(procTransaction);

		if (log.isDebugEnabled()) {
			log.debug("---------------------" + flowTask.getCreateTime());
		}
		return flowTask;
	}

	public void doCheckOutTask(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		if (!flowTask.getTaskState().equals(flowTask.TASK_STATE_FREE)) {
			log.warn("�û�[" + userID + "]��ͼ��ȡ������[" + taskID + "]�Ѿ��������������ߣ�");
			throw new FlowTaskException(
					ExceptionMessage.ERROR_FLOWTASK_INVALID_STATE);
		}
		flowTask.checkOutTask(userID);
		flowTaskDAO.saveFlowTask(flowTask);
	}

	public void doAssignTask(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		flowTask.assignToUser(userID);
		flowTaskDAO.saveFlowTask(flowTask);
		if (log.isDebugEnabled()) {
			log.debug("����[" + taskID + "]��assign�����û�[" + userID + "]");
		}
	}

	/**
	 * needAsssign
	 * 
	 * @param userID
	 *            String
	 * @param taskID
	 *            String
	 */
	public void doNeedAsssign(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		flowTask.needAsssign(userID);
		flowTaskDAO.saveFlowTask(flowTask);
		if (log.isDebugEnabled()) {
			log.debug("����[" + taskID + "]��Ҫ���û�[" + userID + "]ָ��");
		}
	}

	public void doDistributeTask(String userID, String taskID,
			String userToDistribute) {
		FlowTask flowTask = findFlowTask(taskID);
		doAbortTask(userID, taskID);

		flowTask.checkOutTask(userToDistribute);
		flowTaskDAO.saveFlowTask(flowTask);
	}

	public void doFenfaNewTask(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		flowTask.addTaskCandidate(userID);
		flowTaskDAO.saveFlowTask(flowTask);
	}

	public void doFinishTask(String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		flowTask.finish();
		flowTaskDAO.saveFlowTask(flowTask);
	}

	public void doEmailTask(String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		if (flowTask.isEmailed()
				|| !flowTask.getTaskState().equals(FlowTask.TASK_STATE_FREE)) {
			log.warn("����[" + taskID + "]�����µĻ��Ѿ�֪ͨ����!");
			return;
		}
		flowTask.emailTask();
		flowTaskDAO.saveFlowTask(flowTask);
		if (log.isDebugEnabled()) {
			log.debug("flowTask[" + flowTask + "]");
		}
	}

	public void doAbortTask(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		flowTask.abort(userID);
		flowTaskDAO.saveFlowTask(flowTask);
	}

	public List findAllMyNewTasks(String userID) {
		List coll = deployService.doCalcAllMyPerformingNodes(userID);
		List myPerformingNodes = new ArrayList();
		HashMap myPerformingNodesMap = new HashMap();
		for (Iterator it = coll.iterator(); it.hasNext();) {
			FlowNodeBinding nodeBinding = (FlowNodeBinding) it.next();
			myPerformingNodesMap.put(nodeBinding.getNodeBindingID().toString(),
					nodeBinding);
		}
		for (Iterator it = myPerformingNodesMap.keySet().iterator(); it
				.hasNext();) {
			myPerformingNodes.add(myPerformingNodesMap.get(it.next()));
		}
		List result = flowTaskDAO.findAllMyNewTasks(myPerformingNodes);
		this.doTaskPreviewProcess(result);

		return result;
	}

	public List findMyExecutingTasksKinds(String userID) {
		List result = new ArrayList();
		for (Iterator it = flowTaskDAO.findMyExecutingTasksKinds(userID)
				.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();

				Map<String,Object> m=new HashMap<String, Object>();
				m.put("id", obj[0]);
				m.put("text", obj[1]+"("+obj[2]+")");
				m.put("cls", "fold");
				m.put("leaf", true);
				m.put("parentId", 0);
				result.add(m);

		}
		BusinessType bt = new BusinessType();
		bt.otherType();
		bt.setTasksNum(flowTaskDAO.findMyOtherExecutingTasksNum(userID));
		if (bt.getTasksNum().intValue() > 0) {
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("id", bt.getTypeID());
			m.put("text", bt.getTypeName()+"("+bt.getTasksNum().toString()+"");
			m.put("cls", "fold");
			m.put("leaf", true);
			m.put("parentId", 0);
			result.add(m);
		}

		return result;
	}

	public List findMyRefusedTasks(String userID) {
		return flowTaskDAO.findMyRefusedTasks(userID);
	}

	public List findNewTasksNotEmailed() {
		return flowTaskDAO.findNewTasksNotEmailed();
	}

	public List findMyFinishedTasksKinds(String userID) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List l=flowTaskDAO.findMyFinishedTasksKinds(userID);
		for (int i=0,size=l.size();i<size;i++) {
			Object[] obj = (Object[])l.get(i);
			if (obj.length == 3) {
				Map<String,Object> m=new HashMap<String, Object>();
				m.put("id", obj[0]);
				m.put("text", obj[1]+"("+obj[2]+")");
				m.put("cls", "fold");
				m.put("leaf", true);
				m.put("parentId", 0);
				result.add(m);
			}
		}
		BusinessType bt = new BusinessType();
		bt.otherType();
		bt.setTasksNum(flowTaskDAO.findMyOtherNewTasksNum(userID));
		if (bt.getTasksNum().intValue() > 0) {
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("id", bt.getTypeID());
			m.put("text", bt.getTypeName()+"("+bt.getTasksNum()+")");
			m.put("cls", "fold");
			m.put("leaf", true);
			m.put("parentId", 0);
			result.add(m);
		}
		return result;
	}

	public Integer findMyNewTasksNum(String userID) {
		return flowTaskDAO.findMyNewTasksNum(userID);
	}

	
	@SuppressWarnings("unchecked")
	public List findMyNewTasksKinds(String userID) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List l=flowTaskDAO.findMyNewTasksKinds(userID);
		for (int i=0,size=l.size();i<size;i++) {
			Object[] obj = (Object[])l.get(i);
			if (obj.length == 3) {
				Map<String,Object> m=new HashMap<String, Object>();
				m.put("id", obj[0]);
				m.put("text", obj[1]+"("+obj[2]+")");
				m.put("cls", "fold");
				m.put("leaf", true);
				m.put("parentId", 0);
				result.add(m);
			}
		}
		BusinessType bt = new BusinessType();
		bt.otherType();
		bt.setTasksNum(flowTaskDAO.findMyOtherNewTasksNum(userID));
		if (bt.getTasksNum().intValue() > 0) {
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("id", bt.getTypeID());
			m.put("text", bt.getTypeName()+"("+bt.getTasksNum()+")");
			m.put("cls", "fold");
			m.put("leaf", true);
			m.put("parentId", 0);
			result.add(m);
		}
		return result;
	}

	public Integer findMyNewTasksNumByType(String userID, String typeID) {
		return flowTaskDAO.findMyNewTasksNumByType(userID, new Long(typeID));
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> findMyNewTasksByType(String userID, String typeID, PageParameter pp) {
		List result = flowTaskDAO.findMyNewTasksByType(userID,
				new Long(typeID), pp);
		
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("totalCount", findMyNewTasksNumByType(userID,typeID));

		m.put("objs", result);
		return m;
	}

	public Integer findMyExecutingTasksNum(String userID) {
		return flowTaskDAO.findMyExecutingTasksNum(userID);
	}

	public Integer findMyExecutingTasksNumByType(String userID, String typeID) {
		return flowTaskDAO.findMyExecutingTasksNumByType(userID, new Long(
				typeID));
	}

/*	public List findMyExecutingTasksByType(String userID, String typeID,
			int pageNum, int pageSize) {
		List result = flowTaskDAO.findMyExecutingTasksByType(userID, new Long(
				typeID), pageNum, pageSize);

		doTaskPreviewProcess(result);
		return result;
	}*/

	public Integer findMyRefusedTasksNum(String userID) {
		return flowTaskDAO.findMyRefusedTasksNum(userID);
	}

	public Integer findMyFinishedTasksNum(String userID) {
		return flowTaskDAO.findMyFinishedTasksNum(userID);
	}

	public Integer findMyFinishedTasksNumByType(String userID, String typeID) {
		return flowTaskDAO.findMyFinishedTasksNumByType(userID,
				new Long(typeID));
	}

	public Map<String,Object> findMyFinishedTasksByType(String userID, String typeID,
			PageParameter pp) {
		List result = flowTaskDAO.findMyFinishedTasksByType(userID,
				new Long(typeID), pp);
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("totalCount", findMyFinishedTasksNumByType(userID,typeID));
		m.put("objs", result);
		return m;
	}

	public FlowTask findTaskByNodeAndProc(String flowNodeID, String flowProcID) {
		return flowTaskDAO.findTaskByNodeAndProc(flowNodeID, new Long(
				flowProcID));
	}

	/**
	 * �������Ԥ������
	 * 
	 * @param result
	 *            List
	 */
	public void doTaskPreviewProcess(List result) {
		for (Iterator it = result.iterator(); it.hasNext();) {
			FlowTask task = (FlowTask) it.next();
			WorkflowMeta flowMetaWithFile = flowMetaService
					.findWorkflowMetaWithFile(task.getFlowNodeBinding()
							.getFlowDeploy().getWorkflowMeta().getFlowMetaID()
							.toString());
			WorkflowNode workflowNode = flowMetaWithFile
					.findWorkflowNodeByID(task.getFlowNodeBinding()
							.getFlowNodeID());
			String[] nodeVariablesToPreview = workflowNode
					.getVariableToPreview();
			HashMap dataFields = flowMetaWithFile.getDataFields();
			HashMap procState = task.getFlowProc().getProcState();
			String taskPreviewStr = "";
			if (procState != null && procState.size() > 0
					&& nodeVariablesToPreview != null
					&& nodeVariablesToPreview.length > 0 && dataFields != null
					&& dataFields.size() > 0) {
				taskPreviewStr = "(";
				for (int i = 0; i < nodeVariablesToPreview.length; i++) {
					FlowDataField fdField = (FlowDataField) dataFields
							.get(nodeVariablesToPreview[i]);
					if (fdField == null) {
						taskPreviewStr += ("Ԥ�1�" + nodeVariablesToPreview[i] + "������!");
						continue;
					} else {
						taskPreviewStr += (fdField.getFieldName()
								+ ":"
								+ "<font color='red'>"
								+ (procState.get(nodeVariablesToPreview[i]) != null ? procState
										.get(nodeVariablesToPreview[i])
										: "&nbsp") + "</font>");
						if ((i + 1) < nodeVariablesToPreview.length) {
							taskPreviewStr += "|";
						}
					}
				}
				taskPreviewStr += ")";
			}
			// if (log.isDebugEnabled()) {
			// log.debug("����Ԥ��[" + taskPreviewStr + "]");
			// }
			task.setPreviewText(taskPreviewStr);
		}
	}

	public Integer findMyTasksToAssignNum(String userID) {
		return flowTaskDAO.findMyTasksToAssignNum(userID);
	}

	public List findMyTasksToAssign(String userID) {
		List result = flowTaskDAO.findMyTasksToAssign(userID);
		doTaskPreviewProcess(result);
		return result;
	}

	public List findTasksByProc(String flowProcID) {
		List result = flowTaskDAO.findTasksByProc(new Long(flowProcID));
		for (Iterator it = result.iterator(); it.hasNext();) {
			FlowTask ft = (FlowTask) it.next();
			String driverName = ft.getFlowNodeBinding().getWorkflowDriver()
					.getFlowDriverName();
			int newTasksNum = ft.getNewTasks().size();
			int taskUsersNum = ft.getTaskUsers().size();
			if (log.isDebugEnabled()) {
				log.debug("driverName=" + driverName + "|newTasksNum="
						+ newTasksNum + "|taskUsersNum=" + taskUsersNum
						+ "|TaskState=" + ft.getTaskState());
			}
		}
		return result;
	}

	// ------------------------------------------------------------------------------
	/**
	 * ����������ڵ��ں��������еĿɼ���ΧpostRange�� ȷ���Ƿ��ܻع�: ���postRange�д��ڷ�free������,���ܻع� �ع�
	 * ɾ��postRange�е������·��,����true; ���ܻع�:����false;
	 */
	private boolean taskRollBack(FlowTask flowTask) {
		String currNodeID = flowTask.getFlowNodeBinding().getFlowNodeID();
		FlowProcTransaction postTransaction = flowTask.getFlowProc()
				.getPostTransactionOfNode(currNodeID);
		if (postTransaction == null) {
			log.warn("~~~~~~Task(" + flowTask
					+ ")û��postTransaction,���Ҷ�ӽڵ㲻�ܻع�~~~~~~");
			return false;
		}

		HashMap postRange = postTransaction.getRangeOfNode(currNodeID);
		boolean canRollBack = true;
		for (Iterator it = postRange.keySet().iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof FlowTask) {
				FlowTask ft = (FlowTask) obj;
				if (ft.getTaskState().equals(FlowTask.TASK_STATE_FREE)) {
					continue;
				} else {
					if (log.isDebugEnabled()) {
						log.debug("�ڵ�[" + currNodeID + "]��'postRange'�е�����("
								+ ft + ")״̬Ϊ[" + ft.getTaskState()
								+ "]�������䲻�ܻ��");
					}
					canRollBack = false;
					break;
				}
			}
		}

		if (canRollBack) {
			for (Iterator it = postRange.keySet().iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof FlowProcTransition) {
					FlowProcTransition fpt = (FlowProcTransition) obj;
					postTransaction.removeFlowProcTransition(fpt);
					// flowProcService.saveFlowProcTransition(fpt);
				}
				if (obj instanceof FlowTask) {
					FlowTask ft = (FlowTask) obj;
					postTransaction.removeFlowTask(ft);
					// flowTaskDAO.saveFlowTask(ft);
				}
			}
			this.flowProcService.saveFlowProcTransaction(postTransaction);
			if (log.isDebugEnabled()) {
				log.debug("�ڵ�[" + currNodeID + "]��Ӧ����[" + flowTask.getTaskID()
						+ "]�ɹ��ع�");
			}
			return true;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("�ڵ�[" + currNodeID + "]��Ӧ����[" + flowTask.getTaskID()
						+ "]û�лع�");
			}
			return false;
		}
	}

	/**
	 * ��������״̬Ϊrefused,����¼�������ɺ���Ա
	 * 
	 * @param taskID
	 *            String
	 * @param refuseFor
	 *            String
	 * @param refUserID
	 *            String
	 * @return boolean
	 */
	private boolean refuseTask(String taskID, String refuseFor, String refUserID) {
		FlowTask flowTask = findFlowTask(taskID);
		if (this.taskRollBack(flowTask)) {
			flowTask.refuse(refuseFor, refUserID);
			flowTaskDAO.saveFlowTask(flowTask);
			return true;
		}

		return false;
	}

	// ------------------------------------------------------------------------------
	/**
	 * ��������
	 * 
	 * @param taskID
	 *            String
	 * @param tasksToRefuse
	 *            String[]
	 * @param refuseFor
	 *            String
	 * @param refUserID
	 *            String
	 * @return int
	 */
	public int doRefuseTasks(String taskID, String[] tasksToRefuse,
			String refuseFor, String refUserID) {
		if (!this.isTaskOwner(refUserID, taskID)) {
			log.warn("�û�[" + refUserID + "]��ͼ�ܾ������������[" + taskID + "]");
			throw new FlowTaskException(
					ExceptionMessage.ERROR_FLOWTASK_NOT_TASKOWNER);
		}
		int refuseNum = 0;
		FlowTask flowTask = findFlowTask(taskID);
		List tasksCanRefuse = this.findTasksToRefuse(taskID);
		// ������ǰ������ǰ,�ȰѸ������״̬��Ϊfree
		String oldState = flowTask.getTaskState();
		flowTask.free();
		flowTaskDAO.saveFlowTask(flowTask);
		if (log.isDebugEnabled()) {
			log.debug("������ǰ������ǰ,�ȰѸ�����[" + taskID + "]״̬��Ϊfree(����ǰ���������޷��ع�!)");
		}

		for (int i = 0; i < tasksToRefuse.length; i++) {
			for (Iterator it = tasksCanRefuse.iterator(); it.hasNext();) {
				FlowTask ft = (FlowTask) it.next();
				if (ft.getTaskID().toString().equals(tasksToRefuse[i])) {
					if (this.refuseTask(tasksToRefuse[i], refuseFor, refUserID)) {
						refuseNum++;
					}
					break;
				}
			}
		}
		if (refuseNum == 0) { // һ������Ҳû�в���
			flowTask.setTaskState(oldState);
			flowTaskDAO.saveFlowTask(flowTask);
			if (log.isDebugEnabled()) {
				log.debug("һ��ǰ������Ҳû�в���,������[" + taskID + "]�û�ԭ4��״̬[" + oldState
						+ "]!");
			}
		}

		return refuseNum;
	}

	/**
	 * ����taskID��ǰ��������4���أ�
	 * 
	 * @param taskID
	 *            String
	 * @return List
	 */
	public List findTasksToRefuse(String taskID) {
		return this.findFlowTask(taskID).getFlowProcTransaction()
				.getEntranceTasks();
	}

	// ------------------------------------------------------------------------------
	/**
	 * ȡ������:�ع�����Ȼ��checkOut֮
	 * 
	 * @param userID
	 *            String
	 * @param taskID
	 *            String
	 * @return boolean
	 */
	public boolean findBackFlowTask(String userID, String taskID) {
		FlowTask flowTask = findFlowTask(taskID);
		if (!this.isTaskOwner(userID, taskID)) {
			log.warn("�û�[" + userID + "]��ͼȡ�ز������������[" + taskID + "]");
			throw new FlowTaskException(
					ExceptionMessage.ERROR_FLOWTASK_NOT_TASKOWNER);
		}
		if (!flowTask.getTaskState().equals(FlowTask.TASK_STATE_FINISHED)) {
			log.warn("�û�[" + userID + "]��ͼȡ��״̬Ϊ[" + flowTask.getTaskState()
					+ "]������");
			throw new FlowTaskException(
					ExceptionMessage.ERROR_FLOWTASK_INVALID_STATE);
		}
		if (this.taskRollBack(flowTask)) {
			if (flowTask.getFlowNodeBinding().isStatic()) { // ��ֹ���ַ��������û�˿��ü������
				flowTask.checkOutTask(userID);
			} else {
				flowTask.assignToUser(userID);
			}
			this.flowTaskDAO.saveFlowTask(flowTask);
			return true;
		} else {
			log.warn("�û�[" + userID + "]������[" + taskID + "]�Ѿ�����ȡ�أ�");
			return false;
		}
	}

	// ------------------------------------------------------------------------------
	public void setFlowTaskDAO(IFlowTaskDAO flowTaskDAO) {
		this.flowTaskDAO = flowTaskDAO;
	}

	@Override
	public Map<String,Object> findMyExecutingTasksByType(String userID,String typeID,PageParameter pp) {
		Map<String,Object> m=new HashMap<String, Object>();
		List result = flowTaskDAO.findMyExecutingTasksByType(userID, new Long(
				typeID),pp);
		m.put("totalCount", this.findMyExecutingTasksNumByType(userID, typeID));
		m.put("objs", result);
		//doTaskPreviewProcess(result);
		return m;
	}


	

}
