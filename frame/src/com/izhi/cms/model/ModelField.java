package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="cms_model_field")
public class ModelField implements Serializable{

	private static final long serialVersionUID = -325750182313029716L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="field_id")
	private int fieldId;
	@Column(name="field_name",length=32)
	private String name;
	@Column(name="field_title",length=32)
	private String title;
	@Column(name="field_type")
	private String type;
	@Column(name="required")
	private boolean required;
	@ManyToOne
	@JoinColumn(name="model_id")
	private DataModel model;
	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public DataModel getModel() {
		return model;
	}

	public void setModel(DataModel model) {
		this.model = model;
	}
	
}
