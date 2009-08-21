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

import com.izhi.platform.model.Role;
import com.izhi.platform.model.User;
import com.izhi.platform.service.IRoleService;
import com.izhi.platform.service.IUserService;
import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowDeployDAO;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowNodeBinding;
import com.izhi.workflow.model.FlowNodeInputParamBinding;
import com.izhi.workflow.model.FlowNodeOutputParamBinding;
import com.izhi.workflow.model.FlowNodeOutputParamEnumBinding;
import com.izhi.workflow.model.FlowRolePerformer;
import com.izhi.workflow.model.FlowUserPerformer;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.model.WFDriverOutputParamEnum;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IBusinessTypeService;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IWorkflowDriverService;
@Service("workflowFlowDeployService")
public class FlowDeployServiceImpl implements IFlowDeployService {
	private static Logger log = Logger
			.getLogger(FlowDeployServiceImpl.class);
	@Resource(name="workflowFlowDeployDao")
	private IFlowDeployDAO flowDeployDAO;
	@Resource(name="workflowDriverService")
	private IWorkflowDriverService workflowDriverService;
	@Resource(name="userService")
	private IUserService userService;
	@Resource(name="roleService")
	private IRoleService roleService;
	@Resource(name="workflowBusinessTypeService")
	private IBusinessTypeService typeService;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService metaService;

	public void setFlowDeployDAO(IFlowDeployDAO dao) {
		this.flowDeployDAO = dao;
	}

	public FlowDeploy findFlowDeploy(String flowDeployID) {
		return flowDeployDAO.findFlowDeploy(new Long(flowDeployID));
	}

	public Map<String, Object> findById(Long id) {
		return flowDeployDAO.findById(id);
	}

	public FlowNodeBinding findFlowNodeBinding(String flowNodeBindingID) {
		return flowDeployDAO.findFlowNodeBinding(new Long(flowNodeBindingID));
	}

	public FlowDeploy saveFlowDeploy(FlowDeploy flowDeploy) {
		flowDeployDAO.saveFlowDeploy(flowDeploy);
		return flowDeploy;
	}

	public FlowDeploy updateFlowDeploy(FlowDeploy flowDeploy) {
		FlowDeploy target = this.findFlowDeploy(flowDeploy.getFlowDeployID()
				.toString());
		target.setFlowDeployName(flowDeploy.getFlowDeployName());
		target.setMemo(flowDeploy.getMemo());
		saveFlowDeploy(target);
		return target;
	}

	public FlowDeploy doEnableFlowDeploy(String flowDeployID) {
		FlowDeploy flowDeploy = this.findFlowDeploy(flowDeployID);
		flowDeploy.enableFlowDeploy();
		saveFlowDeploy(flowDeploy);
		return flowDeploy;
	}

	public FlowDeploy doDisableFlowDeploy(String flowDeployID) {
		FlowDeploy flowDeploy = this.findFlowDeploy(flowDeployID);
		flowDeploy.disableFlowDeploy();
		saveFlowDeploy(flowDeploy);
		return flowDeploy;
	}


	public FlowNodeBinding updateFlowNodeBinding(String flowDeployID,
			String nodeID, String flowDriverID) {
		FlowDeploy flowDeploy = this.findFlowDeploy(flowDeployID);
		WorkflowDriver wd = workflowDriverService
				.findWorkflowDriver(flowDriverID);

		FlowNodeBinding flowNodeBinding = new FlowNodeBinding();
		flowNodeBinding.setFlowNodeID(nodeID);
		flowNodeBinding.setWorkflowDriver(wd);
		flowDeploy.addFlowNodeBinding(flowNodeBinding);
		this.saveFlowDeploy(flowDeploy);

		return flowNodeBinding;
	}

	public void deleteFlowDeploy(String flowDeployID) {
		FlowDeploy flowDeploy = this.findFlowDeploy(flowDeployID);
		
		if (log.isDebugEnabled()) {
			log.debug("��ͼɾ��'����������С'��PO[FlowDeploy:" + flowDeployID
					+ "]!!!");
		}
		flowDeploy.clear();
		flowDeploy.getWorkflowMeta().removeFlowDeploy(flowDeploy);

		this.saveFlowDeploy(flowDeploy);
	}

	public FlowNodeBinding saveFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		flowDeployDAO.saveFlowNodeBinding(flowNodeBinding);
		return flowNodeBinding;
	}

	public FlowNodeBinding updateFlowNodeParamBinding(String flowNodeBindingID,
			Map inputParamMap, Map outputParamMap,
			Map outoutParamEnumeMap) {
		if (log.isDebugEnabled()) {
			log.debug(flowNodeBindingID);
			log.debug(inputParamMap);
			log.debug(outputParamMap);
			log.debug(outoutParamEnumeMap);
		}

		FlowNodeBinding flowNodeBinding = findFlowNodeBinding(flowNodeBindingID);
		for (Iterator it = inputParamMap.keySet().iterator(); it.hasNext();) {
			String flowNodeParamID = (String) it.next();
			String driverInputParamID = (String) inputParamMap
					.get(flowNodeParamID);
			WFDriverInputParam driverInputParam = this.workflowDriverService
					.findDriverInputParam(driverInputParamID);

			FlowNodeInputParamBinding nodeInputParamBinding = flowNodeBinding
					.findNodeInputParamBindingByNodeParamID(flowNodeParamID);
			if (nodeInputParamBinding == null) {
				nodeInputParamBinding = new FlowNodeInputParamBinding();
				flowNodeBinding
						.addFlowNodeInputParamBinding(nodeInputParamBinding);
				nodeInputParamBinding.setFlowNodeParamID(flowNodeParamID);
			}
			nodeInputParamBinding.setWfDriverInputParam(driverInputParam);
		}
		for (Iterator it = outputParamMap.keySet().iterator(); it.hasNext();) {
			String flowNodeParamID = (String) it.next();
			String driverOutputParamID = (String) outputParamMap
					.get(flowNodeParamID);
			WFDriverOutputParam driverOutputParam = this.workflowDriverService
					.findDriverOutputParam(driverOutputParamID);

			FlowNodeOutputParamBinding nodeOutputParamBinding = flowNodeBinding
					.findNodeOutputParamBindingByNodeParamID(flowNodeParamID);
			if (nodeOutputParamBinding == null) {
				nodeOutputParamBinding = new FlowNodeOutputParamBinding();
				flowNodeBinding
						.addFlowNodeOutputParamBinding(nodeOutputParamBinding);
				nodeOutputParamBinding.setFlowNodeParamID(flowNodeParamID);
			}
			nodeOutputParamBinding.setWfDriverOutputParam(driverOutputParam);
			for (Iterator it2 = outoutParamEnumeMap.keySet().iterator(); it2
					.hasNext();) {
				String flowNodeParamEnume = (String) it2.next();
				String driverOutputParamEnumeID = (String) outoutParamEnumeMap
						.get(flowNodeParamEnume);
				WFDriverOutputParamEnum driverOutputParamEnume = this.workflowDriverService
						.findDriverOutputParamEnume(driverOutputParamEnumeID);

				FlowNodeOutputParamEnumBinding nodeOutputParamEnume = nodeOutputParamBinding
						.findNodeOutputParamEnumeBindingByNodeParamEnume(flowNodeParamEnume);
				if (nodeOutputParamEnume == null) {
					nodeOutputParamEnume = new FlowNodeOutputParamEnumBinding();

					nodeOutputParamBinding
							.addFlowNodeOutputParamEnumeBinding(nodeOutputParamEnume);

					nodeOutputParamEnume
							.setNodeOutputParamEnum(flowNodeParamEnume);
				}
				nodeOutputParamEnume
						.setWfDriverOutputParamEnum(driverOutputParamEnume);
			}
		}
		return this.saveFlowNodeBinding(flowNodeBinding);
	}

	public List<FlowNodeBinding> findFlowNodeBindingsByDriver(String flowDriverID) {
		return flowDeployDAO
				.findFlowNodeBindingsByDriver(new Long(flowDriverID));
	}

	public void doEnableFounder(String flowNodeBindingID) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableFounder();
		this.saveFlowNodeBinding(nodeBinding);
	}

	public void doEnableStatic(String flowNodeBindingID) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableStatic();
		this.saveFlowNodeBinding(nodeBinding);
	}

	public FlowNodeBinding updateOtherPerformer(String flowNodeBindingID,
			String flowNodeID) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableOtherPerformer();
		nodeBinding.setPerformerDetail(flowNodeID);
		return this.saveFlowNodeBinding(nodeBinding);
	}

	public FlowNodeBinding updateAssign(String flowNodeBindingID,
			String flowNodeID) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableAssign();
		nodeBinding.setPerformerDetail(flowNodeID);
		return this.saveFlowNodeBinding(nodeBinding);
	}

	public FlowNodeBinding updateVariable(String flowNodeBindingID,
			String variableID) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableVariable();
		nodeBinding.setPerformerDetail(variableID);
		return this.saveFlowNodeBinding(nodeBinding);
	}

	public FlowNodeBinding updateRule(String flowNodeBindingID,
			String ruleDetail) {
		FlowNodeBinding nodeBinding = this
				.findFlowNodeBinding(flowNodeBindingID);
		nodeBinding.enableRule();
		nodeBinding.setPerformerDetail(ruleDetail);
		return this.saveFlowNodeBinding(nodeBinding);
	}

	// ------------------------------------------------------------------------------

	public void doAddUserPerformer(String userID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		nodeBinding.addUserPerformer(new Long(userID));
		this.saveFlowNodeBinding(nodeBinding);
	}

	public void doAddRolePerformer(String roleID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		nodeBinding.addRolePerformer(new Long(roleID));
		this.saveFlowNodeBinding(nodeBinding);
	}

	public boolean isUserPerformer(String userID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		return nodeBinding.hasUserPerformer(new Long(userID));
	}

	public boolean isRolePerformer(String roleID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		return nodeBinding.hasRolePerformer(new Long(roleID));
	}

	public boolean isNodePerformer(String userID, String nodeBindingID) {
		if (isUserPerformer(userID, nodeBindingID)) {
			return true;
		}
		User user = userService.findById(Integer.parseInt(userID));
		for (Iterator it = user.getRoles().iterator(); it.hasNext();) {
			Role role = (Role) it.next();
			if (isRolePerformer(role.getRoleId() + "", nodeBindingID)) {
				return true;
			}
		}
		return false;
	}

	public List findUsersByNodeBinding(String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		List result = new ArrayList();
		for (Iterator it = nodeBinding.getFlowUserPerformers().iterator(); it
				.hasNext();) {
			FlowUserPerformer fup = (FlowUserPerformer) it.next();
			result.add(userService.findById(fup.getUserID().intValue()));
		}
		return result;
	}

	public List findRolesByNodeBinding(String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		List result = new ArrayList();
		for (Iterator it = nodeBinding.getFlowRolePerformers().iterator(); it
				.hasNext();) {
			FlowRolePerformer frp = (FlowRolePerformer) it.next();
			result.add(roleService.findById(frp.getRoleID().intValue()));
		}

		return result;
	}

	public void deleteUserPerformer(String userID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		nodeBinding.removeUserPerformer(new Long(userID));
		this.saveFlowNodeBinding(nodeBinding);
	}

	public void deleteRolePerformer(String roleID, String nodeBindingID) {
		FlowNodeBinding nodeBinding = this.findFlowNodeBinding(nodeBindingID);
		nodeBinding.removeRolePerformer(new Long(roleID));
		this.saveFlowNodeBinding(nodeBinding);
	}

	public List doCalcAllMyPerformingNodes(String userID) {
		List result = new ArrayList();
		User user = userService.findById(Integer.parseInt(userID));
		result.addAll(flowDeployDAO.findFlowNodeBindsByUserPerformer(new Long(
				userID)));
		for (Iterator it = user.getRoles().iterator(); it.hasNext();) {
			Role role = (Role) it.next();
			result.addAll(flowDeployDAO
					.findFlowNodeBindsByRolePerformer(new Long(role.getRoleId())));
		}
		return result;
	}

	public void setWorkflowDriverService(
			IWorkflowDriverService workflowDriverService) {
		this.workflowDriverService = workflowDriverService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IBusinessTypeService getTypeService() {
		return typeService;
	}

	public void setTypeService(IBusinessTypeService typeService) {
		this.typeService = typeService;
	}

	public IFlowMetaService getMetaService() {
		return metaService;
	}

	public void setMetaService(IFlowMetaService metaService) {
		this.metaService = metaService;
	}

	@Override
	public List<Map<String, Object>> findNodes() {
		List<Map<String, Object>> types = this.typeService.findDeployTree();
		for (Map<String, Object> bt : types) {
			String typeId = bt.get("id").toString();
			String typeName = bt.get("text").toString();

			bt.put("id", "bt_" + typeId);
			List<Map<String, Object>> c = this.findNodes(new Long(typeId));
			bt.put("text", typeName + "(" + c.size() + ")");
			/*
			 * if(c.size()==0){ bt.put("leaf", "false"); }else{ bt.put("leaf",
			 * "true"); }
			 */
			bt.put("children", c);
		}
		return types;
	}

	@Override
	public List<Map<String, Object>> findNodes(Long typeId) {
		return metaService.findByType(typeId);
	}

	@Override
	public Map<String, Object> findPage(PageParameter pp, Long flowMetaId) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("objs", this.flowDeployDAO.findPage(pp, flowMetaId));
		m.put("totalCount", this.findTotalCount(flowMetaId));
		return m;
	}

	@Override
	public int findTotalCount(Long flowMetaId) {
		return this.flowDeployDAO.findTotalCount(flowMetaId);
	}
}
