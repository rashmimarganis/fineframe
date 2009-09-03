package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name="cms_bulletin_content")
public class CmsBulletinContent implements Serializable {

	private static final long serialVersionUID = 3567347911563612335L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="content_id")
	private int contentId;
	@Lob
	private String content;
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
