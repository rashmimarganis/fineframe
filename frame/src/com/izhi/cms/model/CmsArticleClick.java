package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cms_article_click")
public class CmsArticleClick implements Serializable{

	private static final long serialVersionUID = -3991912609322082772L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="click_id")
	private int clickId;
	
	@OneToOne
	@JoinColumn(name="article_id")
	private CmsArticle article;
	
	

	public int getClickId() {
		return clickId;
	}

	public void setClickId(int clickId) {
		this.clickId = clickId;
	}

	public CmsArticle getArticle() {
		return article;
	}

	public void setArticle(CmsArticle article) {
		this.article = article;
	}

	
}
