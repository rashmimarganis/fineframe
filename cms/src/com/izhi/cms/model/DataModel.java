package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cms_datamodel")
public class DataModel implements Serializable{

	private static final long serialVersionUID = -6262029956210832621L;
	
	public static final String TYPE_HIBERNATE="hibernate";
	public static final String TYPE_TABLE="hibernate";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="model_id")
	private int modelId;
	@Column(name="model_name")
	private String name;
	@Column(name="unit")
	private String unit;
	@Column(name="type")
	private String type=TYPE_HIBERNATE;
	@Column(name="class_name")
	private String className;
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
