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

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="wf_meta_file")
public class FlowMetaFile implements BaseObject {
	private static final long serialVersionUID = 1112353754468282839L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="flow_file_id")
	private Long flowFileID = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="flow_meta_id",updatable=true,insertable=true,nullable=true)
	private WorkflowMeta workflowMeta;
	
	@Transient
	private InputStream workflowFileInput;
	@Transient
	private InputStream previewImageInput;
	
	@Column(name="flow_meta_name")
	private String flowMetaName;
	@Transient
	private Long workflowFileSize;
	@Transient
	private Long previewImageSize;
	@Transient
	private String flowProcessID;
	@Transient
	private String accessLevel;
	
	@Column(name="created_time")
	private String createdTime;


	public Long getFlowFileID() {
		return flowFileID;
	}

	public void setFlowFileID(Long flowFileID) {
		this.flowFileID = flowFileID;
	}

	public WorkflowMeta getWorkflowMeta() {
		return workflowMeta;
	}

	public void setWorkflowMeta(WorkflowMeta workflowMeta) {
		this.workflowMeta = workflowMeta;
	}

	public String getFlowMetaName() {
		return flowMetaName;
	}

	public void setFlowMetaName(String flowMetaName) {
		this.flowMetaName = flowMetaName;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public InputStream getPreviewImageInput() {
		return previewImageInput;
	}

	public void setPreviewImageInput(InputStream previewImageInput) {
		this.previewImageInput = previewImageInput;
	}

	public void setWorkflowFileInput(InputStream workflowFileInput) {
		this.workflowFileInput = workflowFileInput;
	}

	public InputStream getWorkflowFileInput() {
		return workflowFileInput;
	}

	public Long getWorkflowFileSize() {
		return workflowFileSize;
	}

	public void setWorkflowFileSize(Long workflowFileSize) {
		this.workflowFileSize = workflowFileSize;
	}

	public Long getPreviewImageSize() {
		return previewImageSize;
	}

	public void setPreviewImageSize(Long previewImageSize) {
		this.previewImageSize = previewImageSize;
	}

	public String getFlowProcessID() {
		return flowProcessID;
	}

	public void setFlowProcessID(String flowProcessID) {
		this.flowProcessID = flowProcessID;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowMetaFile)) {
			return false;
		}
		FlowMetaFile fmf = (FlowMetaFile) object;
		return new EqualsBuilder().append(this.getFlowFileID(),
				fmf.getFlowFileID()).append(this.getFlowMetaName(),
				fmf.getFlowMetaName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(1256335803, 127569255).append(
				this.getFlowFileID()).append(this.getFlowMetaName())
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("FlowFileID", this.getFlowFileID()).append(
						"FlowMetaName", this.getFlowMetaName()).toString();
	}
}
