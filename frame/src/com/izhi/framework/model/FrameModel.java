package com.izhi.framework.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
@Entity
@Table(name="frame_model")
public class FrameModel implements Serializable {

	public final static String TYPE_HIBERNATE="class";
	public final static String TYPE_SQL="table";
	private static final long serialVersionUID = -4278569909316098477L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="model_id")
	private int modelId;
	@Column(name="model_name")
	private String name;
	@Column(name="model_title")
	private String title;
	@Column(name="model_note")
	private String note;
	
	@Column(name="type")
	private String type;
	
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinTable(name = "frame_model_relation", joinColumns = {@JoinColumn(name = "model_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "parent_id"))
	private Set<FrameModel> parents;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Set<FrameModel> getParents() {
		return parents;
	}
	public void setParents(Set<FrameModel> parents) {
		this.parents = parents;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
