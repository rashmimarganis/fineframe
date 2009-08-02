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
@Table(name="f_project")
public class FrameProject implements Serializable{

	private static final long serialVersionUID = 2966389089541539271L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="project_id")
	private int projectId;
	@Column(name="title")
	private String title;
	@Column(name="name")
	private String name;
	@Column(name="base_path")
	private String basePath;
	@Column(name="package_name")
	private String packageName;
	@Column(name="encode")
	private String encode;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="template_suit_id",updatable=true,insertable=true,nullable=true)
	private FrameTemplateSuit templateSuit;
	
	public FrameTemplateSuit getTemplateSuit() {
		return templateSuit;
	}
	public void setTemplateSuit(FrameTemplateSuit templateSuit) {
		this.templateSuit = templateSuit;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	
	
}
