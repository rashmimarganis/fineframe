package com.izhi.workflow.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flow_file_store")
public class FlowMetaFileStore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "flow_file_id")
	private Long flowFileID;
	@Column(name = "preview_image")
	private Blob previewImage;
	@Column(name = "file_content")
	private Blob workflowFile;

	public Long getFlowFileID() {
		return flowFileID;
	}

	public void setFlowFileID(Long flowFileID) {
		this.flowFileID = flowFileID;
	}

	public Blob getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(Blob previewImage) {
		this.previewImage = previewImage;
	}

	public Blob getWorkflowFile() {
		return workflowFile;
	}

	public void setWorkflowFile(Blob workflowFile) {
		this.workflowFile = workflowFile;
	}
}
