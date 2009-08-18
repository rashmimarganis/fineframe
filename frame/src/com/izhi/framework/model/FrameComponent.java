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
@Table(name="frame_component")
public class FrameComponent implements Serializable{

	private static final long serialVersionUID = 4120780168537312804L;
	
	public final static String TYPE_JAVA="java";
	public final static String TYPE_JSP="jsp";
	public final static String TYPE_FTL="ftl";
	public final static String TYPE_CONFIG="xml";
	public final static String TYPE_PROPERTY="properties";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="component_id")
	private int componentId;
	
	@Column(name="name")
	private String name;
	@Column(name="package_name")
	private String packageName;
	
	@Column(name="type")
	private String type;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="template_id",updatable=true,insertable=true,nullable=true)
	private FrameTemplate template;

	@Column(name="enabled")
	private boolean enabled;



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public FrameTemplate getTemplate() {
		return template;
	}


	public void setTemplate(FrameTemplate template) {
		this.template = template;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public int getComponentId() {
		return componentId;
	}


	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	
	
}
