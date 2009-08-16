package com.izhi.framework.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="frame_template")
public class FrameTemplate implements Serializable{

	private static final long serialVersionUID = 4684494637426913663L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="template_file_id")
	private int templateId;
	@Column(name="template_name")
	private String name;
	@Column(name="template_title")
	private String title;

	

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

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	
	

}
