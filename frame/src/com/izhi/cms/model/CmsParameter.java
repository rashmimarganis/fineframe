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
@Table(name="cms_parameter")
public class CmsParameter implements Serializable{

	private static final long serialVersionUID = 4058237989661902812L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="parameter_id")
	private int parameterId;
	@Column
	private String name;
	@Column
	private String label;
	@Column
	private String value;

	@ManyToOne
	@JoinColumn(name="tag_id")
	private CmsTag tag;
	
	public int getParameterId() {
		return parameterId;
	}

	public void setParameterId(int parameterId) {
		this.parameterId = parameterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CmsTag getTag() {
		return tag;
	}

	public void setTag(CmsTag tag) {
		this.tag = tag;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
