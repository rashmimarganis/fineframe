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
import java.util.Date;
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
import org.apache.log4j.Logger;

import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.exception.FlowDeployException;

@Entity
@Table(name="wf_flow_deploy")
public class FlowDeploy implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5769828354185171216L;
	public static final String DEPLOY_STATE_PREPARING = "preparing";
	public static final String DEPLOY_STATE_RUNNING = "running";
	public static final String DEPLOY_STATE_READY = "ready";
	private static Logger log = Logger.getLogger(FlowDeploy.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="flow_deploy_id")
	private Long flowDeployID = new Long(-1);
	
	@Column(name="flow_deploy_name")
	private String flowDeployName;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="current_state")
	private String currentState;
	
	@Column(name="flow_memo")
	private String memo;
	
	@ManyToOne
	@JoinColumn(name="flow_meta_id",updatable=true,insertable=true,nullable=true)
	private WorkflowMeta workflowMeta;
	
	@OneToMany(targetEntity=FlowNodeBinding.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="business_type_id",updatable=false,insertable=false)
	private List<FlowNodeBinding> flowNodeBindings = new ArrayList<FlowNodeBinding>();
	
	@OneToMany(targetEntity=FlowProc.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="flow_proc_id",updatable=false,insertable=false)
	private List<FlowProc> flowProcs = new ArrayList<FlowProc>();

	public FlowDeploy() {
	}

	public Long getFlowDeployID() {
		return flowDeployID;
	}

	public void setFlowDeployID(Long flowDeployID) {
		this.flowDeployID = flowDeployID;
	}

	public String getFlowDeployName() {
		return flowDeployName;
	}

	public void setFlowDeployName(String flowDeployName) {
		this.flowDeployName = flowDeployName;
	}

	
	public String getCurrentState() {
		return currentState;
	}


	public boolean isReady() {
		return (getCurrentState() != null && getCurrentState().equals(
				FlowDeploy.DEPLOY_STATE_READY));
	}

	public void enableFlowDeploy() {
		setCurrentState(DEPLOY_STATE_READY);
	}

	public void disableFlowDeploy() {
		setCurrentState(DEPLOY_STATE_PREPARING);
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public WorkflowMeta getWorkflowMeta() {
		return workflowMeta;
	}

	public void setWorkflowMeta(WorkflowMeta workflowMeta) {
		this.workflowMeta = workflowMeta;
	}

	
	public List<FlowNodeBinding> getFlowNodeBindings() {
		return flowNodeBindings;
	}

	public void setFlowNodeBindings(List<FlowNodeBinding> flowNodeBindings) {
		this.flowNodeBindings = flowNodeBindings;
	}

	public void addFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		if (getFlowNodeBindings().size() > 0) {
			boolean created = false;
			for (Iterator<FlowNodeBinding> it = getFlowNodeBindings().iterator(); it.hasNext();) {
				FlowNodeBinding fnb = (FlowNodeBinding) it.next();
				if (fnb.getFlowNodeID().equals(flowNodeBinding.getFlowNodeID())) {
					fnb.setWorkflowDriver(flowNodeBinding.getWorkflowDriver());
					created = true;
				} else {
					if (fnb.getWorkflowDriver() != null
							&& fnb.getWorkflowDriver().equals(
									flowNodeBinding.getWorkflowDriver())) {
						throw new FlowDeployException(
								ExceptionMessage.ERROR_FLOWDEPLOY_DRIVER_REUSEE);
					}
				}
			}
			if (created) {
				return;
			}
		}

		getFlowNodeBindings().add(flowNodeBinding);
		flowNodeBinding.setFlowDeploy(this);
	}

	public List<FlowProc> getFlowProcs() {
		return flowProcs;
	}

	public void removeFlowProc(FlowProc flowProc) {
		flowProc.setFlowDeploy(null);
		this.getFlowProcs().remove(flowProc);
	}

	public void removeAllFlowProcs() {
		for (Iterator<FlowProc> it = getFlowProcs().iterator(); it.hasNext();) {
			FlowProc flowProc = (FlowProc) it.next();
			flowProc.setFlowDeploy(null);
		}
		this.getFlowProcs().clear();
	}

	public void addFlowProc(FlowProc flowProc) {
		flowProc.setFlowDeploy(this);
		getFlowProcs().add(flowProc);
	}

	public void setFlowProcs(List<FlowProc> flowProcs) {
		this.flowProcs = flowProcs;
	}

	public void clear() {
		if (this.getFlowProcs().size() > 0) {
			for (Iterator<FlowProc> it = getFlowProcs().iterator(); it.hasNext();) {
				FlowProc flowProc = (FlowProc) it.next();
				flowProc.setFlowDeploy(null);
			}
			getFlowProcs().clear();
		}
		if (this.getFlowNodeBindings().size() > 0) {
			for (Iterator<FlowNodeBinding> it = getFlowNodeBindings().iterator(); it.hasNext();) {
				FlowNodeBinding flowNodeBinding = (FlowNodeBinding) it.next();
				flowNodeBinding.setFlowDeploy(null);
			}
			getFlowNodeBindings().clear();
		}
	}

	public FlowNodeBinding getFlowNodeBindingByNodeID(String nodeID) {
		for (Iterator<FlowNodeBinding> it = this.getFlowNodeBindings().iterator(); it.hasNext();) {
			FlowNodeBinding fnb = (FlowNodeBinding) it.next();
			if (fnb.getFlowNodeID().equals(nodeID)) {
				return fnb;
			}
		}
		return null;
	}

	public FlowNodeOutputParamBinding findNodeOutputParamBindingByDriver(
			WorkflowDriver flowDriver, String driverParamName) {
		if (log.isDebugEnabled()) {
			log.debug("flowDriver[" + flowDriver + "]|driverParamName["
					+ driverParamName + "]");
		}
		for (Iterator<FlowNodeBinding> it = this.getFlowNodeBindings().iterator(); it.hasNext();) {
			FlowNodeBinding fnb = (FlowNodeBinding) it.next();
			if (fnb.getWorkflowDriver().equals(flowDriver)) {
				FlowNodeOutputParamBinding result = fnb
						.findNodeOutputParamBindingByDriverParamName(driverParamName);
				if (log.isDebugEnabled()) {
					log.debug("result[" + result + "]");
				}
				return result;
			}
		}
		return null;
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowDeploy)) {
			return false;
		}
		FlowDeploy fd = (FlowDeploy) object;
		return new EqualsBuilder().append(this.getFlowDeployID().toString(),
				fd.getFlowDeployID().toString()).append(
				this.getFlowDeployName(), fd.getFlowDeployName()).append(
				this.getMemo(), fd.getMemo()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(1156335803, 117569255).append(
				this.getFlowDeployID().toString()).append(
				this.getFlowDeployName()).append(this.getMemo()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowDeployID", this.getFlowDeployID().toString())
				.append("flowDeployName", this.getFlowDeployName()).append(
						"memo", this.getMemo()).toString();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
