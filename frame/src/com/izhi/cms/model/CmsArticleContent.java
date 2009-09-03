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
@Table(name="cms_article_content")
public class CmsArticleContent implements Serializable{

	private static final long serialVersionUID = -4078378636446028114L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="content_id")
	private int articleContentId;
	@Lob
	private String content;

	public int getArticleContentId() {
		return articleContentId;
	}
	public void setArticleContentId(int articleContentId) {
		this.articleContentId = articleContentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}
