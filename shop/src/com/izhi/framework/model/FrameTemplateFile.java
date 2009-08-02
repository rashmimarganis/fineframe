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
@Table(name="f_template_file")
public class FrameTemplateFile implements Serializable{

	private static final long serialVersionUID = 4684494637426913663L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="template_file_id")
	private int templateId;
	@Column(name="template_name")
	private String name;
	@Column(name="template_title")
	private String title;
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="suit_id",updatable=true,insertable=true,nullable=true)
	private FrameTemplateSuit suit;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FrameTemplateSuit getSuit() {
		return suit;
	}

	public void setSuit(FrameTemplateSuit suit) {
		this.suit = suit;
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
