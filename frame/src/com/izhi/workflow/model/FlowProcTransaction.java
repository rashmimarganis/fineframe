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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="wf_proc_transaction")
public class FlowProcTransaction implements BaseObject {

	private static final long serialVersionUID = 4260188116471171250L;
	private static Log log = LogFactory.getLog(FlowProcTransaction.class);
	public static final String TRANSACTION_STATE_ACTIVE = "active";
	public static final String TRANSACTION_STATE_OVER = "over";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="transaction_id")
	private Long transactionID = new Long(-1);
	
	@OneToMany
	@JoinColumn(name="proc_transaction_id")
	private List<FlowProcTransition> flowProcTransitions = new ArrayList<FlowProcTransition>();
	@OneToMany
	@JoinColumn(name="proc_transaction_id")
	private List<FlowTask> flowTasks = new ArrayList<FlowTask>();
	
	@ManyToOne
	@JoinColumn(name="flow_proc_id")
	private FlowProc flowProc;
	
	@Column(name="transaction_state")
	private String transactionState = TRANSACTION_STATE_ACTIVE;

	public Long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}

	public FlowProc getFlowProc() {
		return flowProc;
	}

	public void setFlowProc(FlowProc flowProc) {
		this.flowProc = flowProc;
	}

	public List<FlowProcTransition> getFlowProcTransitions() {
		return flowProcTransitions;
	}

	public void addFlowProcTransition(FlowProcTransition procTransition) {
		procTransition.setFlowProcTransaction(this);
		getFlowProcTransitions().add(procTransition);
	}

	public void setFlowProcTransitions(List<FlowProcTransition> flowProcTransitions) {
		this.flowProcTransitions = flowProcTransitions;
	}

	public List<FlowTask> getFlowTasks() {
		return flowTasks;
	}

	public void addFlowTask(FlowTask flowTask) {
		flowTask.setFlowProcTransaction(this);
		getFlowTasks().add(flowTask);
	}

	public void removeFlowTask(FlowTask flowTask) {
		int i = getFlowTasks().size();
		getFlowTasks().remove(flowTask);
		flowTask.setFlowProcTransaction(null);
		if (log.isDebugEnabled()) {
			log.debug("FlowTask Num[" + i + "|" + getFlowTasks().size() + "]");
		}
	}

	public void removeFlowProcTransition(FlowProcTransition fpt) {
		int i = getFlowProcTransitions().size();
		getFlowProcTransitions().remove(fpt);
		fpt.setFlowProcTransaction(null);
		if (log.isDebugEnabled()) {
			log.debug("Transitions Num[" + i + "|"
					+ getFlowProcTransitions().size() + "]");
		}
	}

	public void setFlowTasks(List<FlowTask> flowTasks) {
		this.flowTasks = flowTasks;
	}

	public String getTransactionState() {
		return transactionState;
	}

	public boolean isActive() {
		return (getTransactionState() != null && getTransactionState().equals(
				TRANSACTION_STATE_ACTIVE));
	}

	public void completeTransaction() {
		this.setTransactionState(TRANSACTION_STATE_OVER);
	}

	public void setTransactionState(String transactionState) {
		this.transactionState = transactionState;
	}

	public void clear() {
		if (this.getFlowProcTransitions().size() > 0) {
			for (Iterator<FlowProcTransition> it = getFlowProcTransitions().iterator(); it
					.hasNext();) {
				FlowProcTransition procTransition =  it
						.next();
				procTransition.setFlowProcTransaction(null);
			}
			getFlowProcTransitions().clear();
		}
		if (this.getFlowTasks().size() > 0) {
			for (Iterator<FlowTask> it = getFlowTasks().iterator(); it.hasNext();) {
				FlowTask flowTask = (FlowTask) it.next();
				flowTask.setFlowProcTransaction(null);

			}
			getFlowTasks().clear();
		}
	}


	public List<FlowTask> getEntranceTasks() {
		List<FlowTask> result = new ArrayList<FlowTask>();
		for (Iterator<FlowProcTransition> it = getFlowProcTransitions().iterator(); it.hasNext();) {
			FlowProcTransition procTransition = it.next();
			FlowTask ft = this.getFlowProc().getTaskByNode(
					procTransition.getFromNodeID());
			if (ft != null) {
				result.add(ft);
			}
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public HashMap getRangeOfNode(String nodeID) {
		HashMap result = new HashMap();
		for (Iterator<FlowProcTransition> it = getTransitionsByFromNode(nodeID).iterator(); it
				.hasNext();) {
			FlowProcTransition procTransition = (FlowProcTransition) it.next();
			result.put(procTransition, "·��("
					+ procTransition.getWorkflowTransitionID() + ")");
			FlowTask ft = this.getFlowProc().getTaskByNode(
					procTransition.getToNodeID());
			if (ft == null) {
				result.putAll(getRangeOfNode(procTransition.getToNodeID()));
			} else {
				result.put(ft, "����(" + ft.getTaskID() + ")");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug(result);
		}
		return result;
	}

	
	private List<FlowProcTransition> getTransitionsByFromNode(String nodeID) {
		List<FlowProcTransition> result = new ArrayList<FlowProcTransition>();
		for (Iterator<FlowProcTransition> it = getFlowProcTransitions().iterator(); it.hasNext();) {
			FlowProcTransition procTransition = (FlowProcTransition) it.next();
			if (procTransition.getFromNodeID().equals(nodeID)) {
				result.add(procTransition);
			}
		}
		return result;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowProcTransaction)) {
			return false;
		}
		FlowProcTransaction fpt = (FlowProcTransaction) object;
		return new EqualsBuilder().append(this.getTransactionID().toString(),
				fpt.getTransactionID().toString()).append(
				this.getTransactionState(), fpt.getTransactionState())
				.isEquals();
	}

	public int hashCode() { 
		return new HashCodeBuilder(306335803, 307569255).append(
				this.getTransactionID().toString()).append(
				this.getTransactionState()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("TransactionID", this.getTransactionID().toString())
				.append("TransactionState", this.getTransactionState())
				.toString();
	}
}
