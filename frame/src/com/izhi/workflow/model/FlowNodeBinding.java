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
package com.izhi.workflow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@Entity
@Table(name="wf_node_binding")
public class FlowNodeBinding implements BaseObject {
	
	private static final long serialVersionUID = 939320338375448175L;
	public final static String FLOW_PERFORMER_STATIC = "1";
	public final static String FLOW_PERFORMER_FOUNDER = "2";
	public final static String FLOW_PERFORMER_OTHER_PERFORMER = "3";
	public final static String FLOW_PERFORMER_VARIABLE = "4";
	public final static String FLOW_PERFORMER_RULE = "5";
	public final static String FLOW_PERFORMER_ASSIGN = "6";
	private static Log log = LogFactory.getLog(FlowNodeBinding.class);
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="node_binding_id")
	private Long nodeBindingID = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="flow_deploy_id",updatable=true,insertable=true,nullable=true)
	private FlowDeploy flowDeploy;
	
	@Column(name="flow_node_id")
	private String flowNodeID;
	
	@ManyToOne
	@JoinColumn(name="flow_driver_id",updatable=true,insertable=true,nullable=true)
	private WorkflowDriver workflowDriver;
	
	@OneToMany(targetEntity=FlowNodeOutputParamBinding.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="output_param_binding_id",updatable=true,insertable=true,nullable=true)
	private List<FlowNodeOutputParamBinding> flowNodeOutputParamBindings = new ArrayList<FlowNodeOutputParamBinding>();
	
	@OneToMany(targetEntity=FlowNodeInputParamBinding.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="input_param_binding_id",updatable=true,insertable=true,nullable=true)
	private List<FlowNodeInputParamBinding> flowNodeInputParamBindings = new ArrayList<FlowNodeInputParamBinding>();
	
	@Column(name="performer_rule",nullable=true,length=10)
	private String performerRule = FLOW_PERFORMER_STATIC;
	
	@Column(name="performer_detail",nullable=false)
	private String performerDetail;
	
	@OneToMany(targetEntity=FlowNodeBinding.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="user_performer_id",updatable=true,insertable=true,nullable=true)
	private List<FlowUserPerformer> flowUserPerformers = new ArrayList<FlowUserPerformer>();
	
	@OneToMany(targetEntity=FlowRolePerformer.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="role_performer_id",updatable=true,insertable=true,nullable=true)
	private List<FlowRolePerformer> flowRolePerformers = new ArrayList<FlowRolePerformer>();

	public Long getNodeBindingID() {
		return nodeBindingID;
	}

	public void setNodeBindingID(Long nodeBindingID) {
		this.nodeBindingID = nodeBindingID;
	}


	public FlowDeploy getFlowDeploy() {
		return flowDeploy;
	}

	public void setFlowDeploy(FlowDeploy flowDeploy) {
		this.flowDeploy = flowDeploy;
	}

	public String getFlowNodeID() {
		return flowNodeID;
	}

	public void setFlowNodeID(String flowNodeID) {
		this.flowNodeID = flowNodeID;
	}

	public String getPerformerRule() {
		return performerRule;
	}

	public void setPerformerRule(String performerRule) {
		this.performerRule = performerRule;
	}


	public String getPerformerDetail() {
		return performerDetail;
	}

	public void setPerformerDetail(String performerDetail) {
		this.performerDetail = performerDetail;
	}

	public WorkflowDriver getWorkflowDriver() {
		return workflowDriver;
	}

	public void setWorkflowDriver(WorkflowDriver workflowDriver) {
		this.workflowDriver = workflowDriver;
	}

	public List<FlowUserPerformer> getFlowUserPerformers() {
		return flowUserPerformers;
	}

	public List<FlowRolePerformer> getFlowRolePerformers() {
		return flowRolePerformers;
	}

	public void setFlowUserPerformers(List<FlowUserPerformer> flowUserPerformers) {
		this.flowUserPerformers = flowUserPerformers;
	}

	public void setFlowRolePerformers(List<FlowRolePerformer> flowRolePerformers) {
		this.flowRolePerformers = flowRolePerformers;
	}


	public List<FlowNodeOutputParamBinding> getFlowNodeOutputParamBindings() {
		return flowNodeOutputParamBindings;
	}

	public void addFlowNodeOutputParamBinding(
			FlowNodeOutputParamBinding nodeOutputParamBinding) {
		nodeOutputParamBinding.setFlowNodeBinding(this);
		getFlowNodeOutputParamBindings().add(nodeOutputParamBinding);
	}

	public void setFlowNodeOutputParamBindings(List<FlowNodeOutputParamBinding> flowNodeOutputParamBindings) {
		this.flowNodeOutputParamBindings = flowNodeOutputParamBindings;
	}


	public List<FlowNodeInputParamBinding> getFlowNodeInputParamBindings() {
		return flowNodeInputParamBindings;
	}

	public void addFlowNodeInputParamBinding(
			FlowNodeInputParamBinding nodeInputParamBinding) {
		nodeInputParamBinding.setFlowNodeBinding(this);
		getFlowNodeInputParamBindings().add(nodeInputParamBinding);
	}

	

	public WFDriverInputParam findDriverInputParamByNodeParamID(
			String nodeInputParamID) {
		for (Iterator<FlowNodeInputParamBinding> it = getFlowNodeInputParamBindings().iterator(); it
				.hasNext();) {
			FlowNodeInputParamBinding nodeInputParamBinding = (FlowNodeInputParamBinding) it
					.next();
			if (nodeInputParamBinding.getFlowNodeParamID().equals(
					nodeInputParamID)) {
				return nodeInputParamBinding.getWfDriverInputParam();
			}
		}
		return null;
	}

	public FlowNodeInputParamBinding findNodeInputParamBindingByNodeParamID(
			String nodeInputParamID) {
		for (Iterator<FlowNodeInputParamBinding> it = getFlowNodeInputParamBindings().iterator(); it
				.hasNext();) {
			FlowNodeInputParamBinding nodeInputParamBinding = (FlowNodeInputParamBinding) it
					.next();
			if (nodeInputParamBinding.getFlowNodeParamID().equals(
					nodeInputParamID)) {
				return nodeInputParamBinding;
			}
		}
		return null;
	}

	public WFDriverOutputParam findDriverOutputParamByNodeParamID(
			String nodeOutputParamID) {
		for (Iterator<FlowNodeOutputParamBinding> it = getFlowNodeOutputParamBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamBinding nodeOutputParamBinding = (FlowNodeOutputParamBinding) it
					.next();
			if (nodeOutputParamBinding.getFlowNodeParamID().equals(
					nodeOutputParamID)) {
				return nodeOutputParamBinding.getWfDriverOutputParam();
			}
		}
		return null;
	}

	public FlowNodeOutputParamBinding findNodeOutputParamBindingByNodeParamID(
			String nodeOutputParamID) {
		for (Iterator<FlowNodeOutputParamBinding> it = getFlowNodeOutputParamBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamBinding nodeOutputParamBinding = (FlowNodeOutputParamBinding) it
					.next();
			if (nodeOutputParamBinding.getFlowNodeParamID().equals(
					nodeOutputParamID)) {
				return nodeOutputParamBinding;
			}
		}
		return null;
	}

	public FlowNodeOutputParamBinding findNodeOutputParamBindingByDriverParamName(
			String driverOutputParamName) {

		for (Iterator<FlowNodeOutputParamBinding> it = getFlowNodeOutputParamBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamBinding nodeOutputParamBinding = (FlowNodeOutputParamBinding) it
					.next();
			if (log.isDebugEnabled()) {
				log.debug("equals["
						+ nodeOutputParamBinding.getWfDriverOutputParam()
								.getParamName() + "|" + driverOutputParamName
						+ "]");
			}

			if (nodeOutputParamBinding.getWfDriverOutputParam().getParamName()
					.equals(driverOutputParamName)) {
				return nodeOutputParamBinding;
			}
		}
		return null;
	}

	public void setFlowNodeInputParamBindings(List<FlowNodeInputParamBinding> flowNodeInputParamBindings) {
		this.flowNodeInputParamBindings = flowNodeInputParamBindings;
	}

	public boolean isAssign() {
		return FLOW_PERFORMER_ASSIGN.equals(getPerformerRule());
	}

	public boolean isFounder() {
		return FLOW_PERFORMER_FOUNDER.equals(getPerformerRule());
	}

	public boolean isOtherPerformer() {
		return FLOW_PERFORMER_OTHER_PERFORMER.equals(getPerformerRule());
	}

	public boolean isRule() {
		return FLOW_PERFORMER_RULE.equals(getPerformerRule());
	}

	public boolean isStatic() {
		return FLOW_PERFORMER_STATIC.equals(getPerformerRule());
	}

	public boolean isVariable() {
		return FLOW_PERFORMER_VARIABLE.equals(getPerformerRule());
	}

	public void enableFounder() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_FOUNDER);
	}

	public void enableOtherPerformer() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_OTHER_PERFORMER);
	}

	public void enableAssign() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_ASSIGN);
	}

	public void enableVariable() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_VARIABLE);
	}

	public void enableRule() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_RULE);
	}

	public void enableStatic() {
		this.setPerformerRule(FlowNodeBinding.FLOW_PERFORMER_STATIC);
		this.setPerformerDetail(null);
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowNodeBinding)) {
			return false;
		}
		FlowNodeBinding fnb = (FlowNodeBinding) object;
		return new EqualsBuilder().append(this.getNodeBindingID().toString(),
				fnb.getNodeBindingID().toString()).append(this.getFlowNodeID(),
				fnb.getFlowNodeID()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(1356335803, 137569255).append(
				this.getNodeBindingID().toString())
				.append(this.getFlowNodeID()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nodeBindingID", this.getNodeBindingID().toString())
				.append("flowNodeID", this.getFlowNodeID()).toString();
	}

	public void addUserPerformer(Long userID) {
		if (this.hasUserPerformer(userID)) {
			log.warn("User[" + userID + "] has already bee performer!");
			return;
		}
		FlowUserPerformer fup = new FlowUserPerformer();
		fup.setFlowNodeBinding(this);
		fup.setUserID(userID);
		this.getFlowUserPerformers().add(fup);
	}

	public void addRolePerformer(Long roleID) {
		if (this.hasRolePerformer(roleID)) {
			log.warn("Role[" + roleID + "] has already bee performer!");
			return;
		}

		FlowRolePerformer frp = new FlowRolePerformer();
		frp.setFlowNodeBinding(this);
		frp.setRoleID(roleID);
		this.getFlowRolePerformers().add(frp);
	}

	public boolean hasUserPerformer(Long userID) {
		for (Iterator<FlowUserPerformer> it = this.getFlowUserPerformers().iterator(); it
				.hasNext();) {
			FlowUserPerformer fup = (FlowUserPerformer) it.next();
			if (fup.getUserID().equals(userID)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRolePerformer(Long roleID) {
		for (Iterator<FlowRolePerformer> it = this.getFlowRolePerformers().iterator(); it
				.hasNext();) {
			FlowRolePerformer frp = (FlowRolePerformer) it.next();
			if (frp.getRoleID().equals(roleID)) {
				return true;
			}
		}
		return false;
	}

	public void removeUserPerformer(Long userID) {
		FlowUserPerformer target = null;
		for (Iterator<FlowUserPerformer> it = this.getFlowUserPerformers().iterator(); it
				.hasNext();) {
			target = (FlowUserPerformer) it.next();
			if (target.getUserID().equals(userID)) {
				break;
			}
		}
		if (target == null) {
			log.warn("User[" + userID
					+ "] is not performer of FlowNodeBinding[" + this + "]");
		} else {
			target.setFlowNodeBinding(null);
			getFlowUserPerformers().remove(target);
		}
	}

	public void removeRolePerformer(Long roleID) {
		FlowRolePerformer target = null;
		for (Iterator<FlowRolePerformer> it = this.getFlowRolePerformers().iterator(); it
				.hasNext();) {
			target = (FlowRolePerformer) it.next();
			if (target.getRoleID().equals(roleID)) {
				break;
			}
		}
		if (target == null) {
			log.warn("Role[" + roleID
					+ "] is not performer of FlowNodeBinding[" + this + "]");
		} else {
			target.setFlowNodeBinding(null);
			getFlowRolePerformers().remove(target);
		}
	}
}
