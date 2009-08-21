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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.izhi.workflow.dao.IFlowProcDAO;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowProc;
import com.izhi.workflow.model.FlowProcRelativeData;
import com.izhi.workflow.model.FlowProcTransaction;
import com.izhi.workflow.model.FlowProcTransition;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.model.WorkflowMeta;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IFlowProcService;
import com.izhi.workflow.util.FlowDataField;
import com.izhi.workflow.util.TimeUtil;
@Service("workflowFlowProcService")
public class FlowProcServiceImpl implements IFlowProcService {
	private static Log log = LogFactory.getLog(FlowProcServiceImpl.class);
	
	@Resource(name="workflowFlowProcDao")
	private IFlowProcDAO flowProcDAO;
	@Resource(name="workflowFlowDeployService")
	private IFlowDeployService deployService;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaService;

	public FlowProc findFlowProc(String flowProcID) {
		return flowProcDAO.getFlowProc(new Long(flowProcID));
	}

	public FlowProc saveFlowProc(FlowProc flowProc) {
		flowProcDAO.saveFlowProc(flowProc);
		return flowProc;
	}

	public void deleteFlowProc(String flowProcID) {
		FlowProc flowProc = findFlowProc(flowProcID);
		// ��ɹ¶��Ա���ɾ��
		// flowProc.toOrphan();
		//
		flowProc.clear();
		this.saveFlowProc(flowProc);

		flowProcDAO.removeFlowProc(new Long(flowProcID));
	}

	public FlowProcRelativeData findFlowProcRelativeData(
			String procRelativeDataID) {
		return flowProcDAO
				.getFlowProcRelativeData(new Long(procRelativeDataID));
	}

	public FlowProcTransaction findFlowProcTransaction(String procTransactionID) {
		return flowProcDAO.getFlowProcTransaction(new Long(procTransactionID));
	}

	public FlowProcTransaction saveFlowProcTransaction(
			FlowProcTransaction procTransaction) {
		flowProcDAO.saveFlowProcTransaction(procTransaction);
		return procTransaction;
	}

	public FlowProcTransition saveFlowProcTransition(
			FlowProcTransition procTransition) {
		flowProcDAO.saveFlowProcTransition(procTransition);
		return procTransition;
	}

	public FlowProcTransition findFlowProcTransition(String procTransitionID) {
		return flowProcDAO.getFlowProcTransition(new Long(procTransitionID));
	}

	public FlowProc doCreateFlowProc(String flowDeployID, String linkFlowProcID,
			String starterUserID) {
		FlowDeploy flowDeploy = deployService.findFlowDeploy(flowDeployID);
		FlowProc linkFlowProc = this.findFlowProc(linkFlowProcID);
		return this.doStartAFlow(flowDeploy, linkFlowProc, starterUserID);
	}

	public FlowProc doStartAFlow(FlowDeploy flowDeploy, FlowProc linkFlowProc,
			String startUser) {
		FlowProc flowProc = new FlowProc();
		flowProc.setStarterUserID(startUser);
		flowProc.setStartTime(TimeUtil.getTimeStamp().toString());
		if (linkFlowProc != null) {
			linkFlowProc.addLinkedFlowProc(flowProc);
		} else {
			flowProc.setLinkFlowProc(null);
		}

		flowDeploy.addFlowProc(flowProc);

		deployService.saveFlowDeploy(flowDeploy);
		return flowProc;
	}

	public void doCompleteTransaction(FlowProcTransaction procTransaction) {
		procTransaction.completeTransaction();
		flowProcDAO.saveFlowProcTransaction(procTransaction);
	}

	public FlowProcTransaction doCreateProcTransaction(FlowProc flowProc) {
		FlowProcTransaction procTransaction = new FlowProcTransaction();
		flowProc.addProcTransaction(procTransaction);
		this.saveFlowProcTransaction(procTransaction);

		return procTransaction;
	}

	/**
	 * mergeProcTransaction ��oldTransaction�ϲ���newTransaction��oldTransaction�ᱻɾ��
	 * 
	 * @param newTransaction
	 *            FlowProcTransaction
	 * @param oldTransaction
	 *            FlowProcTransaction
	 * @return FlowProcTransaction
	 */
	public FlowProcTransaction doMergeProcTransaction(
			FlowProcTransaction newTransaction,
			FlowProcTransaction oldTransaction) {
		FlowProc fp = newTransaction.getFlowProc();
		// newTransaction = flowProcDAO.getFlowProcTransaction(newTransaction.
		// getTransactionID());
		// oldTransaction = flowProcDAO.getFlowProcTransaction(oldTransaction.
		// getTransactionID());
		List oldFlowProcTransitions = oldTransaction.getFlowProcTransitions();
		List oldFlowTasks = oldTransaction.getFlowTasks();

		if (log.isDebugEnabled()) {
			log.debug("" + oldFlowProcTransitions.size());
		}

		for (Iterator it = oldFlowProcTransitions.iterator(); it.hasNext();) {
			FlowProcTransition ft = (FlowProcTransition) it.next();
			// FlowProcTransition ftClone=new FlowProcTransition();
			// ftClone.setFromNodeID(ft.getFromNodeID());
			// ftClone.setToNodeID(ft.getToNodeID());
			// ftClone.setWorkflowTransitionID(ft.getWorkflowTransitionID());
			newTransaction.addFlowProcTransition(ft);
		}
		for (Iterator it = oldFlowTasks.iterator(); it.hasNext();) {
			newTransaction.addFlowTask((FlowTask) it.next());
		}

		this.saveFlowProcTransaction(newTransaction);

		// �����Ϊ��,�ᵼ���ظ��������:deleted object would be re-saved by cascade
		oldTransaction.setFlowProcTransitions(null);
		oldTransaction.setFlowTasks(null);
		// saveFlowProcTransaction(oldTransaction);
		fp.removeProcTransaction(oldTransaction);
		this.saveFlowProc(fp);

		return newTransaction;
	}

	public FlowProcTransition doCreateFlowProcTransition(String fromNodeID,
			String toNodeID, String workflowTransitionID,
			FlowProcTransaction procTransaction) {
		FlowProcTransition procTransition = new FlowProcTransition();
		procTransition.setFromNodeID(fromNodeID);
		procTransition.setToNodeID(toNodeID);
		procTransition.setWorkflowTransitionID(workflowTransitionID);

		procTransaction.addFlowProcTransition(procTransition);
		flowProcDAO.saveFlowProcTransaction(procTransaction);

		return procTransition;
	}

	public void updateProcState(FlowProc flowProc, WorkflowDriver flowDriver,
			Map hashMap) {
		if (hashMap == null || hashMap.size() == 0) {
			log.warn("��[" + flowDriver.getFlowDriverName() + "]û������κβ���");
			return;
		}
		
		for (Iterator it = hashMap.keySet().iterator(); it.hasNext();) {
			String driverParamName = (String) it.next();
			if(log.isDebugEnabled()){
				log.debug("DriverParamName:"+driverParamName);
			}
			String driverParamValue = hashMap.get(driverParamName).toString();
			FlowProcRelativeData procRelativeData = flowProc
					.findProcRelativeDataByDriverParamName(driverParamName);
			if (procRelativeData != null) {
				procRelativeData.setDriverParamValue(driverParamValue);
			} else {
				procRelativeData = new FlowProcRelativeData();
				procRelativeData.setDriverParamValue(driverParamValue);
				procRelativeData.setFlowNodeOutputParamBinding(flowProc
						.getFlowDeploy().findNodeOutputParamBindingByDriver(
								flowDriver, driverParamName));
				flowProc.addFlowProcRelativeData(procRelativeData);
			}
		}
		this.saveFlowProc(flowProc);
	}

	public void setFlowProcDAO(IFlowProcDAO flowProcDAO) {
		this.flowProcDAO = flowProcDAO;
	}

	public void setDeployManager(IFlowDeployService deployService) {
		this.deployService = deployService;
	}

	/**
	 * getActiveFlowProcsByDeploy
	 * 
	 * @param flowDeployID
	 *            String
	 * @return List
	 */
	public List findActiveFlowProcsByDeploy(String flowDeployID) {
		List result = flowProcDAO.getActiveFlowProcsByDeploy(new Long(
				flowDeployID));
		this.procPreviewProcess(result);

		return result;
	}

	/**
	 * �������Ԥ������
	 * 
	 * @param result
	 *            List
	 */
	public void procPreviewProcess(List result) {
		for (Iterator it = result.iterator(); it.hasNext();) {
			FlowProc fp = (FlowProc) it.next();
			WorkflowMeta flowMetaWithFile = flowMetaService
					.findWorkflowMetaWithFile(fp.getFlowDeploy()
							.getWorkflowMeta().getFlowMetaID().toString());

			HashMap dataFields = flowMetaWithFile.getDataFields();
			HashMap procState = fp.getProcState();
			if (log.isDebugEnabled()) {
				log.debug("���״̬[" + procState + "]");
				log.debug("������[" + fp.getFlowProcRelativeDatas() + "]");
				log.debug("��̱�[" + dataFields + "]");
			}

			String taskPreviewStr = "";
			if (procState != null && procState.size() > 0 && dataFields != null
					&& dataFields.size() > 0) {
				taskPreviewStr = "(";
				int i = 0;
				for (Iterator it2 = procState.keySet().iterator(); it2
						.hasNext();) {
					String varToPreview = (String) it2.next();
					i++;
					FlowDataField fdField = (FlowDataField) dataFields
							.get(varToPreview);
					if (fdField == null) {
						taskPreviewStr += ("Ԥ�1�" + varToPreview + "������!");
						continue;
					} else {
						taskPreviewStr += (fdField.getFieldName()
								+ ":"
								+ "<font color='red'>"
								+ (procState.get(varToPreview) != null ? procState
										.get(varToPreview)
										: "&nbsp") + "</font>");
						if ((i + 1) < procState.size()) {
							taskPreviewStr += "|";
						}
					}
				}
				taskPreviewStr += ")";
			}
			if (log.isDebugEnabled()) {
				log.debug("���Ԥ��[" + taskPreviewStr + "]");
			}
			fp.setPreviewText(taskPreviewStr);
		}
	}

	public IFlowDeployService getDeployService() {
		return deployService;
	}

	public void setDeployService(IFlowDeployService deployService) {
		this.deployService = deployService;
	}

	public IFlowMetaService getFlowMetaService() {
		return flowMetaService;
	}

	public void setFlowMetaService(IFlowMetaService flowMetaService) {
		this.flowMetaService = flowMetaService;
	}

	

}
