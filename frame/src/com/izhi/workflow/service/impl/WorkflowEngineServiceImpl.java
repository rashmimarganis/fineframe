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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Role;
import com.izhi.platform.model.User;
import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.exception.WorkflowEngineException;
import com.izhi.workflow.model.ActivityReport;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowNodeBinding;
import com.izhi.workflow.model.FlowProc;
import com.izhi.workflow.model.FlowProcTransaction;
import com.izhi.workflow.model.FlowProcTransition;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.FlowTaskUser;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.model.WorkflowMeta;
import com.izhi.workflow.model.WorkflowNode;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IFlowProcService;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.service.IWorkflowDriverService;
import com.izhi.workflow.service.IWorkflowEngineService;
@Service("workflowEngineService")
public class WorkflowEngineServiceImpl implements IWorkflowEngineService {
	private static Logger log = Logger.getLogger(WorkflowEngineServiceImpl.class);
	public static final boolean VALIDATE_START_FLOW = true;

	// 发起进程时是否验证权限
	public boolean validateStartFlow = VALIDATE_START_FLOW;

	@Resource(name="workflowFlowProcService")
	private IFlowProcService procManager;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService taskManager;
	@Resource(name="workflowFlowDeployService")
	private IFlowDeployService deployManager;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaManager;
	@Resource(name="workflowDriverService")
	private IWorkflowDriverService flowDriverManager;

	/**
	 * 如果是普通节点：产生任务 如果是路邮节点：递归处理
	 * 
	 * @param flowProc
	 *            FlowProc
	 * @param flowMetaWithFile
	 *            WorkflowMeta
	 * @param procTransaction
	 *            FlowProcTransaction
	 * @param nodeToProcess
	 *            WorkflowNode
	 */
	private void generateFlowTask(FlowProc flowProc,
			WorkflowMeta flowMetaWithFile, FlowProcTransaction procTransaction,
			WorkflowNode nodeToProcess) {
		// 先检查nodeToProcess是否存在前置事务，如存在就将其（包含的任务和路径）合并到新的事务procTransaction
		FlowProcTransaction preTransaction = flowProc.getPreTransactionOfNode(
				nodeToProcess.getNodeID(), procTransaction);
		int i = procTransaction.getFlowProcTransitions().size();
		int j = procTransaction.getFlowTasks().size();
		if (preTransaction != null) {
			procManager.doMergeProcTransaction(procTransaction, preTransaction);
			if (log.isDebugEnabled()) {
				log
						.debug("'事务'["
								+ procTransaction.getTransactionID()
								+ "]合并前后包涵的任务和路径数对比["
								+ j
								+ ","
								+ i
								+ "|"
								+ procTransaction.getFlowTasks().size()
								+ ","
								+ procTransaction.getFlowProcTransitions()
										.size() + "]");
			}
		}

		if (!isNodeActive(flowProc, nodeToProcess)) {
			if (log.isDebugEnabled()) {
				log.debug("工作流节点[" + nodeToProcess.getNodeID() + "]还不能产生任务");
			}
			return;
		}
		if (!nodeToProcess.isRouteNode()) { // 如果是普通节点：产生任务
			FlowTask flowTask = taskManager.doCreateFlowTask(procTransaction,
					flowProc.getFlowDeploy().getFlowNodeBindingByNodeID(
							nodeToProcess.getNodeID()));

			if (log.isDebugEnabled()) {
				log.debug("产生新任务[" + flowTask.getTaskID() + "][流程="
						+ flowMetaWithFile.getFlowProcessID() + "|节点="
						+ nodeToProcess.getNodeID() + "|PerformerRule="
						+ flowTask.getFlowNodeBinding().getPerformerRule()
						+ "]");
			}
			FlowNodeBinding nodeBinding = flowTask.getFlowNodeBinding();

			if (nodeBinding.isStatic()) {
				for (Iterator it = deployManager.findUsersByNodeBinding(
						nodeBinding.getNodeBindingID().toString()).iterator(); it
						.hasNext();) {
					User auser = (User) it.next();
					taskManager.doFenfaNewTask(auser.getUserId()+"", flowTask
							.getTaskID().toString());
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID()
								+ "]流程参与者逻辑isStatic,分发给了用户[" + auser.getUserId()
								+ "]");
					}
				}
				for (Iterator it = deployManager.findRolesByNodeBinding(
						nodeBinding.getNodeBindingID().toString()).iterator(); it
						.hasNext();) {
					Role role = (Role) it.next();
					for (Iterator it2 = role.getUsers().iterator(); it2
							.hasNext();) {
						User auser = (User) it2.next();
						taskManager.doFenfaNewTask(auser.getUserId()+"",
								flowTask.getTaskID().toString());
						if (log.isDebugEnabled()) {
							log.debug("任务[" + flowTask.getTaskID()
									+ "]流程参与者逻辑isStatic,(通过Role["
									+ role.getRoleName() + "])分发给了用户["
									+ auser.getUserId() + "]");
						}
					}
				}

				// 新任务没有分发给任何人
				if (!flowTask.hasTaskCandidate()) {
					taskManager.doAssignTask(flowTask.getFlowProc()
							.getStarterUserID(), flowTask.getTaskID()
							.toString());
					log.warn("流程[" + flowMetaWithFile.getFlowProcessID()
							+ "]的节点[" + nodeToProcess.getNodeName()
							+ "]没有指定参与者，请完善部署！");
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID()
								+ "]被assign给了流程发起者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]");
					}
				}
			} else if (nodeBinding.isFounder()) { // 任务分发给该工作流进程的创建者
				taskManager.doAssignTask(flowTask.getFlowProc()
						.getStarterUserID(), flowTask.getTaskID().toString());
				if (log.isDebugEnabled()) {
					log.debug("任务[" + flowTask.getTaskID() + "]被assign给了流程发起者["
							+ flowTask.getFlowProc().getStarterUserID() + "]");
				}
			} else if (nodeBinding.isOtherPerformer()) { // 任务分发给该工作流进程的其它任务持有者
				FlowTask otherTask = taskManager.findTaskByNodeAndProc(
						nodeBinding.getPerformerDetail(), procTransaction
								.getFlowProc().getFlowProcID().toString());
				// 节点没有对应的任务，比如首节点
				if (otherTask == null) {
					log.warn("流程[" + flowMetaWithFile.getFlowProcessID()
							+ "]的节点[" + nodeToProcess.getNodeName()
							+ "]没有对应的任务");
					taskManager.doAssignTask(flowTask.getFlowProc()
							.getStarterUserID(), flowTask.getTaskID()
							.toString());
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID()
								+ "]被assign给了流程发起者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]");
					}
					return;
				}
				// 任务还没有持有者，比如新任务
				if (otherTask.getTaskUsers().size() == 0) {
					log.warn("任务[" + otherTask.getTaskID() + "]还没有持有者");
					taskManager.doAssignTask(flowTask.getFlowProc()
							.getStarterUserID(), flowTask.getTaskID()
							.toString());
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID()
								+ "]被assign给了流程发起者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]");
					}
					return;
				}

				for (Iterator it = otherTask.getTaskUsers().iterator(); it
						.hasNext();) {
					FlowTaskUser flowTaskUser = (FlowTaskUser) it.next();
					taskManager.doAssignTask(flowTaskUser.getUserID(), flowTask
							.getTaskID().toString());

					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID()
								+ "]被assign给了节点[" + nodeBinding.getFlowNodeID()
								+ "]对应任务的所有者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]");
					}
				}
			} else if (nodeBinding.isVariable()) { // 任务分发给该工作流进程的某个变量指定的人员
				HashMap procState = flowTask.getFlowProc().getProcState();
				String variablePerformer = (String) procState.get(nodeBinding
						.getPerformerDetail());
				taskManager.doAssignTask(variablePerformer, flowTask.getTaskID()
						.toString());

				if (log.isDebugEnabled()) {
					log.debug("任务[" + flowTask.getTaskID() + "]被assign给了流程变量["
							+ nodeBinding.getPerformerDetail() + "]对应的用户["
							+ variablePerformer + "]");
				}
			} else if (nodeBinding.isAssign()) { // 任务需要由该工作流进程的其它任务持有者指派
				FlowTask otherTask = taskManager.findTaskByNodeAndProc(
						nodeBinding.getPerformerDetail(), procTransaction
								.getFlowProc().getFlowProcID().toString());
				// 节点没有对应的任务，比如首节点
				if (otherTask == null) {
					log.warn("流程[" + flowMetaWithFile.getFlowProcessID()
							+ "]的节点[" + nodeToProcess.getNodeName()
							+ "]没有对应的任务");
					taskManager.doNeedAsssign(flowTask.getFlowProc()
							.getStarterUserID(), flowTask.getTaskID()
							.toString());
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID() + "]由流程发起者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]指派");
					}
					return;
				}
				// 任务还没有持有者，比如新任务
				if (otherTask.getTaskUsers().size() == 0) {
					log.warn("任务[" + otherTask.getTaskID() + "]还没有持有者");
					taskManager.doNeedAsssign(flowTask.getFlowProc()
							.getStarterUserID(), flowTask.getTaskID()
							.toString());
					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID() + "]由流程发起者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]指派");
					}
					return;
				}

				for (Iterator it = otherTask.getTaskUsers().iterator(); it
						.hasNext();) {
					FlowTaskUser flowTaskUser = (FlowTaskUser) it.next();
					taskManager.doNeedAsssign(flowTaskUser.getUserID(), flowTask
							.getTaskID().toString());

					if (log.isDebugEnabled()) {
						log.debug("任务[" + flowTask.getTaskID() + "]需要由节点["
								+ nodeBinding.getFlowNodeID() + "]对应任务的所有者["
								+ flowTask.getFlowProc().getStarterUserID()
								+ "]指派");
					}
				}
			} else {
				log.warn("任务分配逻辑异常:[PerformerRule="
						+ nodeBinding.getPerformerRule() + "|PerformerDetail="
						+ nodeBinding.getPerformerDetail() + "]");
			}
		} else { // 如果是路邮节点：递归处理
			routeFlow(flowProc, procTransaction, flowMetaWithFile,
					nodeToProcess);
		}
	}

	/**
	 * 判断节点是否可以通过
	 * 
	 * @param flowProc
	 *            FlowProc
	 * @param workflowNode
	 *            WorkflowNode
	 * @return boolean
	 */
	private boolean isNodeActive(FlowProc flowProc, WorkflowNode workflowNode) {
		if (log.isDebugEnabled()) {
			log.debug("判断节点是否可以通过:[流程"
					+ flowProc.getFlowDeploy().getWorkflowMeta()
							.getFlowProcessID() + "]-[节点"
					+ workflowNode.getNodeID() + "]");
		}

		Collection walkedTransitions = flowProc.getFlowProcTransitions();
		HashMap myWalkedTransitions = new HashMap(); // 该流程目前已打通的路径
		if (walkedTransitions.size() > 0) {
			for (Iterator it = walkedTransitions.iterator(); it.hasNext();) {
				FlowProcTransition procTransition = (FlowProcTransition) it
						.next();
				// 为了支持循环流程
				/**
				 * 不需要这样做了：当产生一个任务时，检查是否存在相同节点的任务，如果存在就删除那些从他出发的路径
				 */
				// if (procTransition.getFlowProcTransaction().isActive()) {
				myWalkedTransitions.put(procTransition
						.getWorkflowTransitionID(), "haha");
				// }
			}
			if (log.isDebugEnabled()) {
				log.debug("进程[" + flowProc.getFlowProcID() + "]目前已打通的路径["
						+ myWalkedTransitions + "]");
			}
		}
		// 此节点的前置转换
		String[] preTrans = workflowNode.getPreTrans();
		if (log.isDebugEnabled()) {
			log.debug("节点[" + workflowNode.getNodeID() + "]有["
					+ preTrans.length + "]个前置转换");
		}

		String joinType = workflowNode.getJoinType();
		if (joinType == null || joinType.equals("XOR")) {
			for (int i = 0; i < preTrans.length; i++) {
				if (log.isDebugEnabled()) {
					log.debug("节点[" + workflowNode.getNodeID() + "]的前置转换["
							+ preTrans[i] + "]");
				}

				if (myWalkedTransitions.get(preTrans[i]) != null) { // ok
					return true;
				}
			}
			return false;
		} else if (joinType.equals("AND")) {
			for (int i = 0; i < preTrans.length; i++) {
				if (myWalkedTransitions.get(preTrans[i]) == null) {
					if (log.isDebugEnabled()) {
						log.debug("AND:路经[" + preTrans[i] + "]还没有打通");
					}
					return false;
				}
				if (log.isDebugEnabled()) {
					log.debug("AND:路经[" + preTrans[i] + "]已经打通");
				}
			}
			return true;
		} else {
			log.warn("奇怪的joinType[" + joinType + "]既不是AND也不是OR(return true)!");
			return true;
		}
	}

	/**
	 * 处理提交任务
	 * 
	 * @param flowTask
	 *            FlowTask
	 */
	private void processTask(FlowTask flowTask) {
		FlowProc flowProc = flowTask.getFlowProc();
		WorkflowMeta flowMetaWithFile = flowMetaManager
				.findWorkflowMetaWithFile(flowProc.getFlowDeploy()
						.getWorkflowMeta().getFlowMetaID().toString());
		String nodeID = flowTask.getFlowNodeBinding().getFlowNodeID();
		WorkflowNode currNode = flowMetaWithFile.findWorkflowNodeByID(nodeID);

		// 结束一个事务
		if (!currNode.isRouteNode()) {
			procManager.doCompleteTransaction(flowTask.getFlowProcTransaction());
			if (log.isDebugEnabled()) {
				log.debug("结束了一个事务["
						+ flowTask.getFlowProcTransaction().getTransactionID()
						+ "]");
			}
		}

		// 进行工作流路由
		routeFlow(flowProc, flowTask.getFlowProcTransaction(),
				flowMetaWithFile, currNode);
	}

	/**
	 * 进行工作流进程的导航，修改进程状态，产生任务项
	 * 
	 * @param flowProc
	 *            FlowProc
	 * @param flowMetaWithFile
	 *            WorkflowMeta
	 * @param nodeID
	 *            String
	 */
	private void routeFlow(FlowProc flowProc,
			FlowProcTransaction procTransaction, WorkflowMeta flowMetaWithFile,
			WorkflowNode currNode) {
		String nodeID = currNode.getNodeID();
		String[] postTransRefs = currNode.getPostTrans(); // 后继转换的次序
		String[] postTransitionIDs = flowMetaWithFile
				.getTransitionIDsFrom(nodeID);

		if (postTransRefs == null || postTransRefs.length == 0) { // 没有后继节点：进程结束或者只是一个分支结束
			// 结束事务，来防止最后一个任务取回
			procManager.doCompleteTransaction(procTransaction);
			if (log.isDebugEnabled()) {
				log.debug("流程[" + flowMetaWithFile.getFlowProcessID()
						+ "]到达了一个结束节点[" + nodeID + "]");
			}
			return;
		} else { // 有后继节点：处理后继转换
			FlowProcTransaction newTransaction = null;

			if (!procTransaction.isActive()) { // 如果前一个事务结束了：启动一个新的事务
				newTransaction = procManager.doCreateProcTransaction(flowProc);
				if (log.isDebugEnabled()) {
					log.debug("新建了一个流程'事务'["
							+ newTransaction.getTransactionID() + "]");
				}
			} else { // 否则加入其中(例如处理路由节点时)?????死循环
				newTransaction = procTransaction;
				if (log.isDebugEnabled()) {
					log.debug("沿用了流程'事务'[" + newTransaction.getTransactionID()
							+ "]");
				}
				if (newTransaction.getFlowProcTransitions().size() > 100) {
					log.warn("流程'事务'[" + newTransaction.getTransactionID()
							+ "]中包含了超过100个Transition，很可能出现了死循环，强制退出！");
					return;
				}
			}

			String splitType = currNode.getSplitType(); // 后继转换的类型
			if (splitType == null || splitType.equals("AND")) {
				for (int i = 0; i < postTransitionIDs.length; i++) {
					FlowProcTransition ft = flowMetaWithFile
							.getTransitionByID(postTransitionIDs[i]);
					WorkflowNode postNode = flowMetaWithFile
							.findWorkflowNodeByID(ft.getToNodeID());

					// 记录工作流转换
					procManager.doCreateFlowProcTransition(nodeID, ft
							.getToNodeID(), ft.getWorkflowTransitionID(),
							newTransaction);
					// 生成任务
					generateFlowTask(flowProc, flowMetaWithFile,
							newTransaction, postNode);
				}
			} else if (splitType.equals("XOR")) {
				for (int i = 0; i < postTransRefs.length; i++) {
					if (isTransitionAble(flowProc, flowMetaWithFile
							.getTransitionByID(postTransRefs[i]))) {
						FlowProcTransition ft = flowMetaWithFile
								.getTransitionByID(postTransRefs[i]);
						WorkflowNode postNode = flowMetaWithFile
								.findWorkflowNodeByID(ft.getToNodeID());
						// 记录工作流转换
						procManager.doCreateFlowProcTransition(nodeID, ft
								.getToNodeID(), ft.getWorkflowTransitionID(),
								newTransaction);
						// 生成任务
						generateFlowTask(flowProc, flowMetaWithFile,
								newTransaction, postNode);
						break;
					}
				}
			} else {
				log.warn("奇怪的splitType[" + splitType + "]既不是AND也不是OR!");
				return;
			}
		}
	}

	/**
	 * 判断路径是否可以通过
	 * 
	 * @param flowProc
	 *            FlowProc
	 * @param flowTransitionID
	 *            String
	 * @return boolean
	 */
	private boolean isTransitionAble(FlowProc flowProc,
			FlowProcTransition flowTransition) {

		if (flowTransition.getConditionType().equals("OTHERWISE")) {
			if (log.isDebugEnabled()) {
				log.debug("OTHERWISE[流程:"
						+ flowProc.getFlowDeploy().getWorkflowMeta()
								.getFlowProcessID() + ",节点:"
						+ flowTransition.getFromNodeID() + "]");
			}
			return true;
		}

		String condition = flowTransition.getConditionExpress().trim();
		if (log.isDebugEnabled()) {
			log.debug("判断条件:" + condition);
		}

		if (condition == null || condition.trim().length() == 0) {
			return true;
		}

		int startOfRightOperand = condition.indexOf("\"");
		String rightOperand = condition.substring(startOfRightOperand + 1,
				condition.length() - 1); // 右操作数

		condition = condition.substring(0, startOfRightOperand).trim(); // 截掉右操作数

		String leftOperand = "";
		String operator = ""; // 运算符
		int startOfOperator = 0;
		if (condition.indexOf("==") > 0) {
			operator = "==";
			startOfOperator = condition.indexOf("==");
		} else if (condition.indexOf("&") > 0) {
			operator = condition.substring(condition.indexOf("&"));
			startOfOperator = condition.indexOf("&");
		}

		leftOperand = condition.substring(0, startOfOperator).trim(); // 左操作数
		String leftOperandValue = null;
		leftOperandValue = (String) flowProc.getProcState().get(leftOperand);
		if (leftOperandValue == null) {
			log.warn("判断条件[" + condition + "]时，左操作数[" + leftOperand + "]为空值！");
			return false;
		}

		if (operator.equals("==")) {
			boolean result = leftOperandValue.equals(rightOperand);
			if (log.isDebugEnabled()) {
				log.debug("判断条件[" + condition + "]-[" + leftOperandValue + ","
						+ rightOperand + "]:" + result);
			}

			return result;
		} else if (operator.equals("&gt;")) {
			boolean result = (new Integer(leftOperandValue).intValue()) > (new Integer(
					rightOperand).intValue());

			if (log.isDebugEnabled()) {
				log.debug("判断条件[" + condition + "]-[" + leftOperandValue + ","
						+ rightOperand + "]:" + result);
			}

			return result;
		} else if (operator.equals("&lt;")) {
			boolean result = (new Integer(leftOperandValue).intValue()) < (new Integer(
					rightOperand).intValue());
			if (log.isDebugEnabled()) {
				log.debug("判断条件[" + condition + "]-[" + leftOperandValue + ","
						+ rightOperand + "]:" + result);
			}

			return result;
		} else {
			return false;
		}
	}

	// ------------------------------------------------------------------------------
	/**
	 * 处理一个任务提交
	 * 
	 * @param userID
	 *            String
	 * @param taskID
	 *            String
	 */
	public void processSubmitTask(String userID, String taskID) {
		log.info("\n-----------处理一个任务提交[userID=" + userID + "|TaskID=" + taskID
				+ "]---------------------");

		// 先判断userID是否任务taskID持有者
		if (!taskManager.isTaskOwner(userID, taskID)) {
			log.warn("用户[" + userID + "]不是任务[" + taskID + "]的持有人");
			throw new WorkflowEngineException(
					ExceptionMessage.ERROR_FLOWTASK_NOT_TASKOWNER);
		}
		FlowTask flowTask = taskManager.findFlowTask(taskID);
		// 判断任务是否处于正确的状态
		if (FlowTask.TASK_STATE_FINISHED.equals(flowTask.getTaskState())
		// ||!flowTask.getFlowProcTransaction().isActive()------2005-06-13修改
		) {
			log.warn("任务[" + taskID + "|"
					+ flowTask.getFlowNodeBinding().getFlowNodeID()
					+ "]已经被完成[TaskState=" + flowTask.getTaskState()
					+ "]，不能重复提交！");
			throw new WorkflowEngineException(
					ExceptionMessage.ERROR_FLOWTASK_INVALID_STATE);
		}

		processTask(flowTask);

		// 修改任务状态
		taskManager.doFinishTask(taskID);
		if (log.isDebugEnabled()) {
			log.debug("用户[" + userID + "]完成了任务[" + taskID + "]");
		}

		// 实现流程自动连接--------------------------
		Collection linkedFlowProcs = flowTask.getFlowProc()
				.getLinkedFlowProcs();
		if (linkedFlowProcs != null) {
			for (Iterator it = linkedFlowProcs.iterator(); it.hasNext();) {
				FlowProc flowProc = (FlowProc) it.next();
				WorkflowMeta flowMetaWithFile = flowMetaManager
						.findWorkflowMetaWithFile(flowProc.getFlowDeploy()
								.getWorkflowMeta().getFlowMetaID().toString());

				WorkflowNode currNode = flowMetaWithFile
						.findWorkflowNodeByID(flowMetaWithFile.getStartNodeID());

				// 进行工作流路邮
				routeFlow(flowProc,
						procManager.doCreateProcTransaction(flowProc), // 新建事务
						flowMetaWithFile, currNode);
				if (log.isDebugEnabled()) {
					log.debug("流程自动连接:进程["
							+ flowTask.getFlowProc().getFlowProcID() + "]->进程["
							+ flowProc.getFlowProcID() + "]");
				}
			}
		}
	}

	// ------------------------------------------------------------------------------
	/**
	 * 处理一个活动通报（某个驱动WriteAction的一次提交）
	 * 
	 * @param userID
	 *            String
	 * @param activityReport
	 *            ActivityReport
	 */
	public void processActivityReport(String userID,
			ActivityReport activityReport) {
		log.info("-----------处理一个活动通报------------");
		log.info("[userID=" + userID + "|driverID="
				+ activityReport.getDriverID() + "|TaskID="
				+ activityReport.getFlowTaskID() + "]\n");
		log.debug("----------------FlowMetaManager:"+flowMetaManager!=null);
		String driverID = activityReport.getDriverID();
		FlowTask flowTask = null;
		WorkflowDriver flowDriver = null;
		if (activityReport.getFlowTaskID() != null) {
			flowTask = taskManager.findFlowTask(activityReport.getFlowTaskID());
			flowDriver = flowTask.getFlowNodeBinding().getWorkflowDriver();
		} else if (driverID != null) {
			flowDriver = flowDriverManager.findWorkflowDriver(driverID);
		}
		
		// 查找相关的工作流进程,包括'自身'
		Collection<FlowProc> relativeProcs = this.getRelativeProcs(userID, flowTask,
				flowDriver);

		if (relativeProcs.size() <= 0) {
			log.warn("没有驱动任何流程[userID=" + userID + "，flowDriver="
					+ flowDriver.getFlowDriverName() + "]");
			throw new WorkflowEngineException(
					ExceptionMessage.ERROR_FLOWENGINE_DIDNOT_DRIVE_ANY_FLOW);
		}
		for (FlowProc currFlowProc:relativeProcs) {
			//FlowProc currFlowProc = (FlowProc) it.next();
			// 对所相关的工作流进程：用驱动的输出参数修改进程状态数据
			procManager.updateProcState(currFlowProc, flowDriver,
					activityReport.getDriverOutputData());
			
			// 如果这个活动通报还没处于一个进程中，对进程进行工作流路由
			if (activityReport.getFlowTaskID() == null) {
				WorkflowMeta flowMetaWithFile = flowMetaManager
						.findWorkflowMetaWithFile(currFlowProc.getFlowDeploy()
								.getWorkflowMeta().getFlowMetaID().toString());

				WorkflowNode currNode = null;
				// 是触发的进程:从流程StartNode开始路由
				currNode = flowMetaWithFile
						.findWorkflowNodeByID(flowMetaWithFile.getStartNodeID());
				routeFlow(currFlowProc, procManager
						.doCreateProcTransaction(currFlowProc), // 新建事务
						flowMetaWithFile, currNode);
			}
		}
	}

	/**
	 * 查找可以驱动的的工作流进程
	 * 
	 * @param flowTaskID
	 *            String
	 * @param flowDriver
	 *            WorkflowDriver
	 * @return Collection<FlowProc>
	 */
	private Collection getRelativeProcs(String userID, FlowTask flowTask,
			WorkflowDriver flowDriver) {
		ArrayList result = new ArrayList();
		FlowProc linkFlowProc = null;

		if (flowTask != null) {
			result.add(flowTask.getFlowProc());
			if (flowTask.getFlowProc().getLinkedFlowProcs().size() > 0) {
				for (Iterator it = flowTask.getFlowProc().getLinkedFlowProcs()
						.iterator(); it.hasNext();) {
					result.add(it.next());
				}
				return result; // 如果已经触发过了，则不需要再触发
			}
			linkFlowProc = flowTask.getFlowProc();
		}
		// 查找可触发的流程
		Collection nodeBindings = deployManager
				.findFlowNodeBindingsByDriver(flowDriver.getFlowDriverID()
						.toString());
		// 是否触发了流程
		boolean flowTrigger = false;
		if (nodeBindings != null && nodeBindings.size() > 0) {
			for (Iterator it = nodeBindings.iterator(); it.hasNext();) {
				FlowNodeBinding nodeBinding = (FlowNodeBinding) it.next();
				if (!nodeBinding.getFlowDeploy().isReady()) {
					if (log.isDebugEnabled()) {
						log.debug("工作流部署["
								+ nodeBinding.getFlowDeploy()
										.getFlowDeployName() + "]尚未启用！");
					}
					continue;
				} else {
					flowTrigger = true;
				}

				WorkflowMeta flowMeta = flowMetaManager
						.findWorkflowMetaWithFile(nodeBinding.getFlowDeploy()
								.getWorkflowMeta().getFlowMetaID().toString());

				// 如果恰好是开始节点，创建新的工作流进程
				if (flowMeta.getStartNodeID().equals(
						nodeBinding.getFlowNodeID())) {

					// 其它流程(防止多次重复发起'自身'进程所在的流程！！！)<--多余:流程的首节点不会产生任务
					// if (flowTask != null &&
					// flowTask.getFlowProc().getFlowDeploy().equals(nodeBinding.
					// getFlowDeploy())) {
					// if (log.isDebugEnabled()) {
					// log.debug("防止多次重复发起'自身'进程所在的流程部署！！！[" +
					// flowMeta.getFlowProcessID() + "|" +
					// nodeBinding.getFlowDeploy().getFlowDeployID() + "]");
					// }
					// continue;
					// }

					// 是否检查发起者权限
					if (!this.isValidateStartFlow()
							|| deployManager.isNodePerformer(userID,
									nodeBinding.getNodeBindingID().toString())) {
						FlowProc newProc = this.startAFlow(nodeBinding
								.getFlowDeploy(), linkFlowProc, userID);
						result.add(newProc);
					} else {
						log.warn("用户[" + userID + "]无权发起流程["
								+ flowMeta.getFlowProcessID() + "]");
						continue;
					}
				}
			}
		}
		if (!flowTrigger) {
			log.warn("用户[" + userID + "]提交[" + flowDriver.getFlowDriverName()
					+ "]没有任何流程能够匹配！");
		}

		return result;
	}

	/**
	 * 启动一个新进程
	 * 
	 * @param flowDeploy
	 *            FlowDeploy
	 * @param linkFlowProc
	 *            FlowProc
	 * @param startUser
	 *            String
	 * @return FlowProc
	 */
	private FlowProc startAFlow(FlowDeploy flowDeploy, FlowProc linkFlowProc,
			String startUser) {
		FlowProc flowProc = procManager.doStartAFlow(flowDeploy, linkFlowProc,
				startUser);
		if (log.isDebugEnabled()) {
			log.debug("工作流[" + flowDeploy.getWorkflowMeta().getFlowProcessID()
					+ "]启动一个新进程[" + flowProc.getFlowProcID() + "],startUser["
					+ startUser + "]");
		}
		return flowProc;
	}

	// ------------------------------------------------------------------------------
	/**
	 * 处理批量发起进程
	 * 
	 * @param userID
	 *            String
	 * @param activityReports
	 *            Collection
	 */
	public void processBatchActivityReports(String userID,
			Collection activityReports) {
		/**
		 * @todo Implement this org.powerstone.workflow.service.WorkflowEngine
		 *       method
		 */
		throw new java.lang.UnsupportedOperationException(
				"Method processBatchActivityReports() not yet implemented.");
	}

	// ------------------------------------------------------------------------------
	

	public boolean isValidateStartFlow() {
		return validateStartFlow;
	}

	public void setValidateStartFlow(boolean validateStartFlow) {
		this.validateStartFlow = validateStartFlow;
	}

	/**
	 * simulateFLowStart
	 * 
	 * @param flowDeployID
	 *            String
	 * @param driverOutputData
	 *            HashMap
	 */
	public void simulateFLowStart(String flowDeployID, HashMap driverOutputData) {
	}

	/**
	 * simulateFLowSubmitTask
	 * 
	 * @param flowProcID
	 *            String
	 * @param nodeID
	 *            String
	 * @param driverOutputData
	 *            HashMap
	 */
	public void simulateFLowSubmitTask(String flowProcID, String nodeID,
			HashMap driverOutputData) {
	}

	public IFlowProcService getProcService() {
		return procManager;
	}

	public void setProcService(IFlowProcService procManager) {
		this.procManager = procManager;
	}

	public IFlowTaskService getTaskService() {
		return taskManager;
	}

	public void setTaskService(IFlowTaskService taskManager) {
		this.taskManager = taskManager;
	}

	public IFlowDeployService getDeployService() {
		return deployManager;
	}

	public void setDeployService(IFlowDeployService deployManager) {
		this.deployManager = deployManager;
	}

	public IFlowMetaService getFlowMetaService() {
		return flowMetaManager;
	}

	public void setFlowMetaService(IFlowMetaService flowMetaManager) {
		this.flowMetaManager = flowMetaManager;
	}

	public IWorkflowDriverService getFlowDriverService() {
		return flowDriverManager;
	}

	public void setFlowDriverService(IWorkflowDriverService flowDriverManager) {
		this.flowDriverManager = flowDriverManager;
	}
}
