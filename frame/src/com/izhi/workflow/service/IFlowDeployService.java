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

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowDeployDAO;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowNodeBinding;
@SuppressWarnings("unchecked")
public interface IFlowDeployService {

	public void setFlowDeployDAO(IFlowDeployDAO dao);

	public void setWorkflowDriverService(IWorkflowDriverService flowDriverManager);

	public FlowDeploy findFlowDeploy(String flowDeployID);
	public Map<String,Object> findById(Long flowDeployID);

	/**
	 * û�е�Ԫ����
	 * 
	 * @param flowNodeBindingID
	 *            String
	 * @return FlowNodeBinding
	 */
	public FlowNodeBinding findFlowNodeBinding(String flowNodeBindingID);

	public List<FlowNodeBinding> findFlowNodeBindingsByDriver(String flowDriverID);

	public FlowDeploy saveFlowDeploy(FlowDeploy flowDeploy);

	public FlowDeploy updateFlowDeploy(FlowDeploy flowDeploy);

	public FlowDeploy doEnableFlowDeploy(String flowDeployID);

	public FlowDeploy doDisableFlowDeploy(String flowDeployID);

	public FlowNodeBinding updateFlowNodeBinding(String flowDeployID,
			String nodeID, String flowDriverID);

	
	public FlowNodeBinding updateFlowNodeParamBinding(String flowNodeBindingID,
			Map inputParamMap, Map outputParamMap,
			Map outoutParamEnumeMap);

	public FlowNodeBinding saveFlowNodeBinding(FlowNodeBinding flowNodeBinding);

	public void deleteFlowDeploy(String flowDeployID);

	public void doEnableFounder(String flowNodeBindingID);

	public void doEnableStatic(String flowNodeBindingID);

	public FlowNodeBinding updateOtherPerformer(String flowNodeBindingID,
			String flowNodeID);

	public FlowNodeBinding updateAssign(String flowNodeBindingID,
			String flowNodeID);

	public FlowNodeBinding updateVariable(String flowNodeBindingID,
			String variableID);

	public FlowNodeBinding updateRule(String flowNodeBindingID,
			String ruleDetail);

	public void doAddUserPerformer(String userID, String nodeBindingID);

	public void doAddRolePerformer(String roleID, String nodeBindingID);

	public boolean isUserPerformer(String userID, String nodeBindingID);

	public boolean isRolePerformer(String roleID, String nodeBindingID);

	public boolean isNodePerformer(String userID, String nodeBindingID);

	public List findUsersByNodeBinding(String nodeBindingID);

	public List findRolesByNodeBinding(String nodeBindingID);

	public void deleteUserPerformer(String userID, String nodeBindingID);

	public void deleteRolePerformer(String roleID, String nodeBindingID);

	public List doCalcAllMyPerformingNodes(String userID);
	
	public List<Map<String,Object>> findNodes();
	public List<Map<String,Object>> findNodes(Long typeId);
	public Map<String,Object> findPage(PageParameter pp,Long flowMetaId);
	public int findTotalCount(Long flowMetaId);
	
	
}
