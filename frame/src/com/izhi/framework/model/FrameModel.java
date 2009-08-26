package com.izhi.framework.model;

import java.io.Serializable;
import java.util.List;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="frame_model")
public class FrameModel implements Serializable {

	private static final long serialVersionUID = -4278569909316098477L;
	
	public static final String PrimaryKey="modelId";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="model_id")
	private int modelId;
	@Column(name="model_name")
	private String name;
	@Column(name="model_label")
	private String label;
	@Column(name="model_note")
	private String note;
	
	@OneToMany
	@JoinColumn(name="model_id",updatable=false,insertable=false,nullable=true)
	private List<FrameModelRelation> relations;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="project_id",updatable=true,insertable=true,nullable=true)
	private FrameProject project;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="model_id",updatable=false,insertable=false,nullable=true)
	private List<FrameAttribute> attributes;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public List<FrameModelRelation> getRelations() {
		return relations;
	}
	public void setRelations(List<FrameModelRelation> relations) {
		this.relations = relations;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public FrameProject getProject() {
		return project;
	}
	public void setProject(FrameProject project) {
		this.project = project;
	}
	public List<FrameAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<FrameAttribute> attributes) {
		this.attributes = attributes;
	}
	

}
