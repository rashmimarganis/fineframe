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
@Table(name="cms_attribute")
public class CmsAttribute implements Serializable {

	private static final long serialVersionUID = -8276606983944130551L;
	public static final String PrimaryKey="attributeId";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="attribute_id")
	private int attributeId;
	
	@Column(name="attribute_label")
	private String label;
	
	@Column(name="attribute_name")
	private String name;

	@ManyToOne
	@JoinColumn(name="model_id")
	private CmsModel model;

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
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

	public void setLabel(String label) {
		this.label = label;
	}

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}
	
}
