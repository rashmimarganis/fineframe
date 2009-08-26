package com.izhi.framework.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="frame_attribute")
public class FrameAttribute implements Serializable {

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

	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="control_id",updatable=true,insertable=true,nullable=true)
	private FrameControl control;
	
	@Column(name="is_key")
	private boolean isKey;
	
	@Column(name="required")
	private boolean required=false;
	
	@Column(name="java_class")
	private String javaClass;

	@Column(name="length")
	private int length;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="model_id",updatable=true,insertable=true,nullable=true)
	private FrameModel model;

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

	

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public FrameModel getModel() {
		return model;
	}

	public void setModel(FrameModel model) {
		this.model = model;
	}


	public FrameControl getControl() {
		return control;
	}

	public void setControl(FrameControl control) {
		this.control = control;
	}

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(boolean isKey) {
		this.isKey = isKey;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getJavaClass() {
		return javaClass;
	}

	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}


	
}
