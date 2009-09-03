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
import javax.persistence.Transient;
@Entity
@Table(name="cms_template")
public class CmsTemplate implements Serializable{

	private static final long serialVersionUID = 4684494637426913663L;
	public static final String PrimaryKey="templateId";
	public final static String TYPE_COMMON="common";
	public final static String TYPE_TAG="tag";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="template_file_id")
	private int templateId;
	@Column(name="template_file_name")
	private String fileName;
	@Column(name="template_name")
	private String name;

	@Column(name="template_type")
	private String type;
	
	@ManyToOne
	@JoinColumn(name="suit_id")
	private CmsTemplateSuit suit;
	
	@Transient
	private String content;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CmsTemplateSuit getSuit() {
		return suit;
	}

	public void setSuit(CmsTemplateSuit suit) {
		this.suit = suit;
	}
	
	

}
