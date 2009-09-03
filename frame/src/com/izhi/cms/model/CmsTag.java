package com.izhi.cms.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="cms_tag")
public class CmsTag implements Serializable{

	private static final long serialVersionUID = 8541760469390385081L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tag_id")
	private int tagId;
	
	@ManyToOne
	@JoinColumn(name="model_id")
	private CmsModel model;
	@ManyToOne
	@JoinColumn(name="template_id")
	private CmsTemplate template;
	
	@OneToMany
	private List<CmsAttribute> attributes;
	
	@Column(name="paged")
	private boolean paged;
	
	@Column(name="max_result")
	private int maxResult;
	
	
	private CmsCategory category;
	
	private List<CmsParameter> parameters;

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	public CmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(CmsTemplate template) {
		this.template = template;
	}

	public List<CmsAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<CmsAttribute> attributes) {
		this.attributes = attributes;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean paged) {
		this.paged = paged;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public CmsCategory getCategory() {
		return category;
	}

	public void setCategory(CmsCategory category) {
		this.category = category;
	}

	public List<CmsParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<CmsParameter> parameters) {
		this.parameters = parameters;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	
	
	

}
