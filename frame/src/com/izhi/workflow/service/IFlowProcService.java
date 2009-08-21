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
package com.izhi.workflow.service;

import java.util.List;
import java.util.Map;

import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowProc;
import com.izhi.workflow.model.FlowProcRelativeData;
import com.izhi.workflow.model.FlowProcTransaction;
import com.izhi.workflow.model.FlowProcTransition;
import com.izhi.workflow.model.WorkflowDriver;
@SuppressWarnings("unchecked")
public interface IFlowProcService {
	public FlowProc findFlowProc(String flowProcID);

	public FlowProc saveFlowProc(FlowProc flowProc);

	public void deleteFlowProc(String flowProcID);

	public FlowProcRelativeData findFlowProcRelativeData(
			String procRelativeDataID);

	public FlowProcTransaction findFlowProcTransaction(String procTransactionID);

	public FlowProcTransaction saveFlowProcTransaction(
			FlowProcTransaction procTransaction);

	public FlowProcTransition saveFlowProcTransition(
			FlowProcTransition procTransition);

	public FlowProcTransition findFlowProcTransition(String procTransitionID);

	public FlowProc doCreateFlowProc(String flowDeployID, String linkFlowProcID,
			String starterUserID);

	public FlowProc doStartAFlow(FlowDeploy flowDeploy, FlowProc linkFlowProc,
			String startUser);

	public void doCompleteTransaction(FlowProcTransaction procTransaction);

	public FlowProcTransaction doCreateProcTransaction(FlowProc flowProc);

	public FlowProcTransaction doMergeProcTransaction(
			FlowProcTransaction newTransaction,
			FlowProcTransaction oldTransaction);

	public FlowProcTransition doCreateFlowProcTransition(String fromNodeID,
			String toNodeID, String workflowTransitionID,
			FlowProcTransaction procTransaction);

	public void updateProcState(FlowProc flowProc, WorkflowDriver flowDriver,
			Map hashMap);

	/**
	 * 
	 * @param flowDeployID
	 *            String
	 * @return List
	 */
	public List findActiveFlowProcsByDeploy(String flowDeployID);

}
