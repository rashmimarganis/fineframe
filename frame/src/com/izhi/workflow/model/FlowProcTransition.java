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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��¼һ������������Ѿ��߹��·��
 * 
 * @hibernate.class table="WF_FLOW_PROC_TRANSITION"
 *                  <p>
 *                  Title: PowerStone
 *                  </p>
 */
@Entity
@Table(name="flow_proc_transition")
public class FlowProcTransition implements BaseObject {

	private static final long serialVersionUID = 5181651595895162577L;
	private static Log log = LogFactory.getLog(FlowProcTransition.class);
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transition_id")
	private Long id = new Long(-1);
	
	@Column(name="from_node_id")
	private String fromNodeID;
	
	@Column(name="workflow_transition_id")
	private String workflowTransitionID;
	
	@Column(name="to_node_id")
	private String toNodeID;
	
	@Column(name="condition_type")
	private String conditionType;
	
	@Column(name="condition_express")
	private String conditionExpress;
	
	@ManyToOne
	@JoinColumn(name="proc_transition_id")
	private FlowProcTransaction flowProcTransaction;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromNodeID() {
		return fromNodeID;
	}

	public void setFromNodeID(String fromNodeID) {
		this.fromNodeID = fromNodeID;
	}


	public String getWorkflowTransitionID() {
		return workflowTransitionID;
	}

	public void setWorkflowTransitionID(String workflowTransitionID) {
		this.workflowTransitionID = workflowTransitionID;
	}

	public String getToNodeID() {
		return toNodeID;
	}

	public void setToNodeID(String toNodeID) {
		this.toNodeID = toNodeID;
	}

	public FlowProcTransaction getFlowProcTransaction() {
		return flowProcTransaction;
	}

	public void setFlowProcTransaction(FlowProcTransaction flowProcTransaction) {
		this.flowProcTransaction = flowProcTransaction;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getConditionExpress() {
		return conditionExpress;
	}

	public void setConditionExpress(String conditionExpress) {
		this.conditionExpress = conditionExpress;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowProcTransition)) {
			return false;
		}
		FlowProcTransition fnt = (FlowProcTransition) object;
		if (log.isDebugEnabled()) {
			log.debug("[" + this + "--------\n--------" + fnt + "]");
		}
		return new EqualsBuilder().append(this.getWorkflowTransitionID(),
				fnt.getWorkflowTransitionID()).append(this.getToNodeID(),
				fnt.getToNodeID()).append(this.getFromNodeID(),
				fnt.getFromNodeID()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2056335803, 207569255).append(
				this.getWorkflowTransitionID().toString()).append(
				this.getToNodeID()).append(this.getFromNodeID()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("TransitionID", this.getWorkflowTransitionID()).append(
						"toNodeID", this.getToNodeID()).append("fromNodeID",
						this.getFromNodeID()).toString();
	}
}
