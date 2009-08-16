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
@Table(name="frame_control")
public class FrameControl implements Serializable{

	private static final long serialVersionUID = -7064198439793652153L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="control_id")
	private int controlId;
	@Column(name="name")
	private String name;
	@Column(name="label")
	private String label;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="template_id",updatable=true,insertable=true,nullable=true)
	private FrameTemplate template;

	
	public FrameTemplate getTemplate() {
		return template;
	}

	public void setTemplate(FrameTemplate template) {
		this.template = template;
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

	public int getControlId() {
		return controlId;
	}

	public void setControlId(int controlId) {
		this.controlId = controlId;
	}

}
