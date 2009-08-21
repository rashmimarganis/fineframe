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
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name="flow_proc")
public class FlowProc implements BaseObject {

	private static final long serialVersionUID = 4909545818213192554L;
	private static Log log = LogFactory.getLog(FlowProc.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="proc_id")
	private Long flowProcID = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="deploy_id",nullable=false)
	private FlowDeploy flowDeploy;
	
	@Column(name="start_time")
	private String startTime;
	
	@ManyToOne
	@JoinColumn(name="link_flow_proc_id")
	private FlowProc linkFlowProc;
	
	@Column(name="starter_user_id")
	private String starterUserID;
	
	@ManyToOne(targetEntity=FlowProcRelativeData.class)
	@JoinColumn(name="flow_proc_id",updatable=false,insertable=false)
	private List<FlowProcRelativeData> flowProcRelativeDatas = new ArrayList<FlowProcRelativeData>();
	
	@ManyToOne(targetEntity=FlowProc.class)
	@JoinColumn(name="linked_proc_id")
	private List<FlowProc> linkedFlowProcs = new ArrayList<FlowProc>();
	
	@ManyToOne(targetEntity=FlowProcTransaction.class)
	@JoinColumn(name="flow_proc_id")
	private List<FlowProcTransaction> flowProcTransactions = new ArrayList<FlowProcTransaction>();
	
	@Column(name="preview_text")
	private String previewText;

	public Long getFlowProcID() {
		return flowProcID;
	}

	public void setFlowProcID(Long flowProcID) {
		this.flowProcID = flowProcID;
	}

	public FlowDeploy getFlowDeploy() {
		return flowDeploy;
	}

	public void setFlowDeploy(FlowDeploy flowDeploy) {
		this.flowDeploy = flowDeploy;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public FlowProc getLinkFlowProc() {
		return linkFlowProc;
	}

	public void setLinkFlowProc(FlowProc linkFlowProc) {
		this.linkFlowProc = linkFlowProc;
	}

	public String getStarterUserID() {
		return starterUserID;
	}

	public void setStarterUserID(String starterUserID) {
		this.starterUserID = starterUserID;
	}

	public List<FlowProcTransition> getFlowProcTransitions() {
		List<FlowProcTransition> result = new ArrayList<FlowProcTransition>();
		for (Iterator<FlowProcTransaction> it = this.getFlowProcTransactions().iterator(); it
				.hasNext();) {
			FlowProcTransaction fpt = (FlowProcTransaction) it.next();
			for (Iterator<FlowProcTransition> it2 = fpt.getFlowProcTransitions().iterator(); it2
					.hasNext();) {
				FlowProcTransition ft = it2.next();
				result.add(ft);
			}
		}
		return result;
	}

	/**
	 * ���ؽ���Ѳ��������
	 * 
	 * @return List
	 */
	public List<FlowTask> getFlowTasks() {
		List<FlowTask> result = new ArrayList<FlowTask>();
		for (Iterator<FlowProcTransaction> it = this.getFlowProcTransactions().iterator(); it
				.hasNext();) {
			FlowProcTransaction fpt = (FlowProcTransaction) it.next();
			for (Iterator<FlowTask> it2 = fpt.getFlowTasks().iterator(); it2.hasNext();) {
				FlowTask ft = (FlowTask) it2.next();
				result.add(ft);
			}
		}
		return result;
	}

	/**
	 * getTaskByNode
	 * 
	 * @param nodeID
	 *            String
	 * @return FlowTask
	 */
	public FlowTask getTaskByNode(String nodeID) {
		for (Iterator<FlowTask> it = getFlowTasks().iterator(); it.hasNext();) {
			FlowTask ft =  it.next();
			if (ft.getFlowNodeBinding().getFlowNodeID().equals(nodeID)) {
				return ft;
			}
		}
		log.warn("WorkflowNode[" + nodeID + "]has no Task in FlowProc["
				+ this.getFlowProcID() + "]");
		return null;
	}

	/**
	 * ���ҽڵ�nodeID��ǰ������
	 * 
	 * @param nodeID
	 *            String
	 * @param transitionIgnore
	 *            FlowProcTransition�������ڴ˽�̵�·��(��ΪҪ�Ͳ���������)
	 * @return FlowProcTransaction
	 */
	public FlowProcTransaction getPreTransactionOfNode(String nodeID,
			FlowProcTransaction transactionIgnore) {
		for (Iterator<FlowProcTransition> it = this.getFlowProcTransitions().iterator(); it
				.hasNext();) {
			FlowProcTransition ft = it.next();
			if (!ft.getFlowProcTransaction().equals(transactionIgnore)
					&& ft.getToNodeID().equals(nodeID)) {
				FlowProcTransaction result = ft.getFlowProcTransaction();
				if (log.isDebugEnabled()) {
					log.debug("FlowProcTransaction["
							+ result.getTransactionID() + "]contains tasks["
							+ result.getFlowTasks().size()
							+ "] and transitions["
							+ result.getFlowProcTransitions().size() + "]");
				}
				return result;
			}
		}
		log.warn("WorkflowNode[" + nodeID
				+ "]has no PreTransaction in FlowProc[" + this.getFlowProcID()
				+ "]");
		return null;
	}


	public FlowProcTransaction getPostTransactionOfNode(String nodeID) {
		for (Iterator<FlowProcTransition> it = this.getFlowProcTransitions().iterator(); it
				.hasNext();) {
			FlowProcTransition ft =  it.next();
			if (ft.getFromNodeID().equals(nodeID)) {
				return ft.getFlowProcTransaction();
			}
		}
		log.warn("WorkflowNode[" + nodeID
				+ "]has no PostTransaction in FlowProc[" + this.getFlowProcID()
				+ "]");
		return null;
	}

	public List<FlowProcRelativeData> getFlowProcRelativeDatas() {
		return flowProcRelativeDatas;
	}

	public HashMap<String,String> getProcState() {
		HashMap<String,String> result = new HashMap<String,String>();
		if (getFlowProcRelativeDatas().size() > 0) {
			for (Iterator<FlowProcRelativeData> it = getFlowProcRelativeDatas().iterator(); it
					.hasNext();) {
				FlowProcRelativeData procRelativeData = (FlowProcRelativeData) it
						.next();
				// if (log.isDebugEnabled()) {
				// log.debug("+---------+[" +
				// procRelativeData.getFlowNodeOutputParamBinding() + "]");
				// }
				if (procRelativeData.getFlowNodeOutputParamBinding() != null) {
					String nodeParamID = procRelativeData
							.getFlowNodeOutputParamBinding()
							.getFlowNodeParamID();
					String nodeParamValue = procRelativeData
							.getCorrespondingNodeParamValue();
					result.put(nodeParamID, nodeParamValue);
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("+++++++++++++ProcState[" + result + "]");
		}
		return result;
	}


	public HashMap<String,String> generateProcStateForDriver(FlowNodeBinding targetNode) {
		HashMap<String,String> result = new HashMap<String,String>();
		if (getFlowProcRelativeDatas().size() > 0) {
			for (Iterator<FlowProcRelativeData> it = getFlowProcRelativeDatas().iterator(); it
					.hasNext();) {
				FlowProcRelativeData procRelativeData = (FlowProcRelativeData) it
						.next();
				FlowNodeOutputParamBinding nodeOutputParamBinding = procRelativeData
						.getFlowNodeOutputParamBinding();
				if (nodeOutputParamBinding == null) {
					log
							.warn("����һ���Ƿ���(û�ж�Ӧ��FlowNodeOutputParamBinding)ProcRelativeData("
									+ procRelativeData + ")");
					continue;
				}
				if (log.isDebugEnabled()) {
					log.debug("procRelativeData[" + procRelativeData
							+ "]��Ӧ��FlowNodeParamID---->"
							+ nodeOutputParamBinding.getFlowNodeParamID());
				}
				WFDriverInputParam driverInputParam = targetNode
						.findDriverInputParamByNodeParamID(nodeOutputParamBinding
								.getFlowNodeParamID());
				if (log.isDebugEnabled()) {
					log.debug("ProcRelativeData[" + procRelativeData
							+ "]��ӦFlowNodeParamID["
							+ nodeOutputParamBinding.getFlowNodeParamID()
							+ "]->��Ӧ�˽ڵ�[" + targetNode.getFlowNodeID()
							+ "]��WFDriverInputParam[" + driverInputParam + "]");
				}
				String driverParamValue = procRelativeData
						.getDriverParamValue();
				if (driverInputParam != null) {
					result.put(driverInputParam.getParamName(),
							driverParamValue);
				} else {
					result.put(nodeOutputParamBinding.getWfDriverOutputParam()
							.getParamName(), driverParamValue);
				}
			}
		}

		return result;
	}

	public void addFlowProcRelativeData(FlowProcRelativeData procRelativeData) {
		procRelativeData.setFlowProc(this);
		getFlowProcRelativeDatas().add(procRelativeData);
	}

	public FlowProcRelativeData findProcRelativeDataByDriverParamName(
			String driverParamName) {
		for (Iterator<FlowProcRelativeData> it = getFlowProcRelativeDatas().iterator(); it.hasNext();) {
			FlowProcRelativeData procRelativeData = (FlowProcRelativeData) it
					.next();
			if (procRelativeData.getFlowNodeOutputParamBinding() != null
					&& procRelativeData.getFlowNodeOutputParamBinding()
							.getWfDriverOutputParam().getParamName().equals(
									driverParamName)) {
				return procRelativeData;
			}
		}
		return null;
	}

	public void setFlowProcRelativeDatas(List<FlowProcRelativeData> flowProcRelativeDatas) {
		this.flowProcRelativeDatas = flowProcRelativeDatas;
	}

	public List<FlowProc> getLinkedFlowProcs() {
		return linkedFlowProcs;
	}

	public void removeLinkedFlowProc(FlowProc flowProc) {
		flowProc.setLinkFlowProc(null);
		this.getLinkedFlowProcs().remove(flowProc);
	}

	public void addLinkedFlowProc(FlowProc flowProc) {
		flowProc.setLinkFlowProc(this);
		getLinkedFlowProcs().add(flowProc);
	}

	public void setLinkedFlowProcs(List<FlowProc> linkedFlowProcs) {
		this.linkedFlowProcs = linkedFlowProcs;
	}

	public List<FlowProcTransaction> getFlowProcTransactions() {
		return flowProcTransactions;
	}

	public void addProcTransaction(FlowProcTransaction procTransaction) {
		procTransaction.setFlowProc(this);
		getFlowProcTransactions().add(procTransaction);
	}

	public void removeProcTransaction(FlowProcTransaction procTransaction) {
		procTransaction.setFlowProc(null);
		getFlowProcTransactions().remove(procTransaction);
	}

	public void setFlowProcTransactions(List<FlowProcTransaction> flowProcTransactions) {
		this.flowProcTransactions = flowProcTransactions;
	}

	/**
	 * ��ɹ¶��Ա���ɾ��
	 */
	public void toOrphan() {
		if (getFlowDeploy() != null) {
			getFlowDeploy().removeFlowProc(this);
		}
		if (getLinkFlowProc() != null) {
			getLinkFlowProc().removeLinkedFlowProc(this);
		}
	}

	public void clear() {
		if (this.getFlowProcRelativeDatas().size() > 0) {
			for (Iterator<FlowProcRelativeData> it = getFlowProcRelativeDatas().iterator(); it
					.hasNext();) {
				FlowProcRelativeData relativeData =  it
						.next();
				relativeData.setFlowProc(null);
			}
			getFlowProcRelativeDatas().clear();
		}
		if (this.getFlowProcTransactions().size() > 0) {
			for (Iterator<FlowProcTransaction> it = getFlowProcTransactions().iterator(); it
					.hasNext();) {
				FlowProcTransaction procTransaction =  it.next();
				procTransaction.setFlowProc(null);
				procTransaction.clear();
			}
			getFlowProcTransactions().clear();
		}
	}

	public String getPreviewText() {
		return previewText;
	}

	public void setPreviewText(String previewText) {
		this.previewText = previewText;
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowProc)) {
			return false;
		}
		FlowProc fp = (FlowProc) object;
		return new EqualsBuilder().append(this.getFlowProcID().toString(),
				fp.getFlowProcID().toString()).append(this.getStartTime(),
				fp.getStartTime()).append(this.getStarterUserID(),
				fp.starterUserID).isEquals();
	}

	public int hashCode() { // ���ѡ����������ÿ���಻ͬ
		return new HashCodeBuilder(286335803, 287569255).append(
				this.getFlowProcID().toString()).append(this.getStartTime())
				.append(this.getStarterUserID()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowProcID", this.getFlowProcID().toString()).append(
						"startTime", this.getStartTime()).append(
						"starterUserID", this.getStarterUserID()).toString();
	}

}
