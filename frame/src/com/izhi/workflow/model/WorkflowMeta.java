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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.izhi.workflow.dao.FlowModelDAO;
import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.exception.FlowMetaException;
import com.izhi.workflow.util.FlowDataField;


@Entity
@Table(name="wf_meta")
public class WorkflowMeta implements BaseObject {

	private static final long serialVersionUID = -7259677971551087585L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="meta_id")
	private Long flowMetaID = new Long(-1);
	@ManyToOne
	@JoinColumn(name="file_inuse_id")
	private FlowMetaFile flowFileInUse;
	
	@OneToMany(targetEntity=FlowMetaFile.class)
	@JoinColumn(name="flow_meta_id")
	private List<FlowMetaFile> flowFileVersions = new ArrayList<FlowMetaFile>();
	
	@Column(name="process_id")
	private String flowProcessID;
	
	@ManyToOne
	@JoinColumn(name="business_type_id")
	private BusinessType businessType;
	@OneToMany(targetEntity=FlowDeploy.class)
	@JoinColumn(name="flow_meta_id")
	private List<FlowDeploy> flowDeploies = new ArrayList<FlowDeploy>();
	
	@Transient
	private FlowModelDAO flowModelDAO;
	private static Logger log = Logger.getLogger(WorkflowMeta.class);

	public Long getFlowMetaID() {
		return flowMetaID;
	}

	public void setFlowMetaID(Long flowMetaID) {
		this.flowMetaID = flowMetaID;
	}

	public FlowMetaFile getFlowFileInUse() {
		return flowFileInUse;
	}

	public void setFlowFileInUse(FlowMetaFile flowFileInUse) {
		this.flowFileInUse = flowFileInUse;
	}

	public List<FlowMetaFile> getFlowFileVersions() {
		return flowFileVersions;
	}

	public void setFlowFileVersions(List<FlowMetaFile> flowFileVersions) {
		this.flowFileVersions = flowFileVersions;
	}

	public int getFileVersionNum() {
		return this.flowFileVersions.size();
	}


	public List<FlowDeploy> getFlowDeploies() {
		return flowDeploies;
	}

	public void setFlowDeploies(List<FlowDeploy> flowDeploies) {
		this.flowDeploies = flowDeploies;
	}

	public String getFlowProcessID() {
		return flowProcessID;
	}

	public void setFlowProcessID(String flowProcessID) {
		this.flowProcessID = flowProcessID;
	}


	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType bt) {
		this.businessType = bt;
	}

	public void joinBusinessType(BusinessType bt) {
		this.businessType = bt;
		bt.getWorkflowMetas().add(this);
	}

	public void updateBusinessType(BusinessType bt) {
		if (this.getBusinessType() != null) {
			this.getBusinessType().removeWorkflowMeta(this);
		}
		this.setBusinessType(bt);
		bt.addWorkflowMeta(this);
	}

	public void addFlowFileVersion(FlowMetaFile flowMetaFile) {
		if (flowMetaFile.getWorkflowMeta() == null) {
			flowMetaFile.setWorkflowMeta(this);
			this.getFlowFileVersions().add(flowMetaFile);
		}
	}

	public void removeFlowFileVersion(FlowMetaFile flowMetaFile) {
		flowMetaFile.setWorkflowMeta(null);
		this.getFlowFileVersions().remove(flowMetaFile);
	}

	public void addFlowDeploy(FlowDeploy flowDeploy) {
		flowDeploy.setWorkflowMeta(this);
		this.getFlowDeploies().add(flowDeploy);
	}

	public void removeFlowDeploy(FlowDeploy flowDeploy) {
		flowDeploy.setWorkflowMeta(null);
		this.getFlowDeploies().remove(flowDeploy);
	}

	public FlowModelDAO getFlowModelDAO() {
//		log.debug("=======FlowFileInUse:"+this.getFlowFileInUse());
		if (flowModelDAO == null) {
			if (this.getFlowFileInUse().getWorkflowFileInput() == null) {
				throw new FlowMetaException(
						ExceptionMessage.ERROR_FLOWMETAFILE_NULL);
			}

			SAXBuilder saxBuilder = new SAXBuilder(false);
			Document doc = null;
			try {
				doc = saxBuilder.build(this.getFlowFileInUse()
						.getWorkflowFileInput());
			} catch (Exception ex) {
				log.warn("!!!!!!!!!!!!!!!!!!!!" + ex.getMessage());
				ex.printStackTrace();
				throw new FlowMetaException(
						ExceptionMessage.ERROR_FLOWMETAFILE_DIGESTER);
			}
			flowModelDAO = new FlowModelDAO(doc);
		}
		return flowModelDAO;
	}

	public String[] getAllNodeIDs() {
		return getFlowModelDAO().getAllNodeIDs(getFlowProcessID());
	}

	public Collection<WorkflowNode> getAllActivityNodesList() {
		String[] nodeIDs = getFlowModelDAO().getAllNodeIDs(getFlowProcessID());
		if (nodeIDs != null && nodeIDs.length > 0) {
			List<WorkflowNode> list = new ArrayList<WorkflowNode>();
			for (int i = 0; i < nodeIDs.length; i++) {
				WorkflowNode theNode = findWorkflowNodeByID(nodeIDs[i]);
				if (!theNode.isRouteNode()) {
					list.add(theNode);
				}
			}
			return list;
		} else {
			return null;
		}
	}

	public WorkflowNode findWorkflowNodeByID(String nodeID) {
		return getFlowModelDAO().findWorkflowNodeByID(getFlowProcessID(),
				nodeID);
	}

	public HashMap<String,FlowDataField> getDataFields() {
		return getFlowModelDAO().getDataFields(getFlowProcessID());
	}

	public String[] getEndNodeIDs() {
		return getFlowModelDAO().getEndNodeIDs(getFlowProcessID());
	}

	public String getStartNodeID() {
		return getFlowModelDAO().getStartNodeID(getFlowProcessID());
	}

	public String[] getFlowVariablesToPreview() {
		return getFlowModelDAO().getFlowVariablesToPreview(getFlowProcessID());
	}

	public FlowProcTransition getTransitionByID(String transitionID) {
		return getFlowModelDAO().getTransitionByID(getFlowProcessID(),
				transitionID);
	}

	public String[] getTransitionIDsFrom(String fromNodeID) {
		return getFlowModelDAO().getTransitionIDsFrom(getFlowProcessID(),
				fromNodeID);
	}

	public String[] getTransitionIDsTo(String toNodeID) {
		return getFlowModelDAO().getTransitionIDsTo(getFlowProcessID(),
				toNodeID);
	}

	public Map<String,String[]> getTypeDeclarations() {
		return getFlowModelDAO().getTypeDeclarations();
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof WorkflowMeta)) {
			return false;
		}
		WorkflowMeta wm = (WorkflowMeta) object;
		return new EqualsBuilder().append(this.getFlowMetaID().toString(),
				wm.getFlowMetaID().toString()).append(this.getFlowProcessID(),
				wm.getFlowProcessID()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(1956335803, 197569255).append(
				this.getFlowMetaID().toString())
				.append(this.getFlowProcessID()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowMetaID", this.getFlowMetaID().toString()).append(
						"flowProcessID", this.getFlowProcessID()).toString();
	}

}
