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
@Table(name="cms_page_template")
public class CmsCategoryPageTemplate implements Serializable{

	private static final long serialVersionUID = 4221880462748527502L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="page_template_id")
	private int pageTemplateId;
	
	@ManyToOne
	@JoinColumn(name="model_page_id",nullable=true)
	private CmsModelPage modelPage;
	@ManyToOne
	@JoinColumn(name="template_id",nullable=true)
	private CmsTemplate template;
	@ManyToOne
	@JoinColumn(name="category_id",nullable=true)
	private CmsCategory category;
	
	public CmsCategory getCategory() {
		return category;
	}

	public void setCategory(CmsCategory category) {
		this.category = category;
	}

	@Column(name="is_default")
	private boolean isDefault;

	

	public int getPageTemplateId() {
		return pageTemplateId;
	}

	public void setPageTemplateId(int categoryTemplateId) {
		this.pageTemplateId = categoryTemplateId;
	}

	public CmsModelPage getModelPage() {
		return modelPage;
	}

	public void setModelPage(CmsModelPage modelMenu) {
		this.modelPage = modelMenu;
	}

	public CmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(CmsTemplate template) {
		this.template = template;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	

}
