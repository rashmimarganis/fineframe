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
@Table(name="cms_template")
public class Template implements Serializable{

	private static final long serialVersionUID = 8266919930357454267L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="template_id")
	private int templateId;
	@Column(name="template_name")
	private String templateName;
	@Column(name="file_name")
	private String fileName;
	@ManyToOne
	@JoinColumn(name="suit_id")
	private TemplateSuit suit;
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public TemplateSuit getSuit() {
		return suit;
	}

	public void setSuit(TemplateSuit suit) {
		this.suit = suit;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	
	
}
