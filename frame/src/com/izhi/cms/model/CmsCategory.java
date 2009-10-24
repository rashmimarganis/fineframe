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
@Table(name="cms_category")
public class CmsCategory implements Serializable {

	private static final long serialVersionUID = -2608476475609714159L;
	
	public static final String TYPE_ONEPAGE="op";
	public static final String TYPE_CONTENT="co";
	public static final String TYPE_LINK="li";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id")
	private int categoryId;
	
	@Column(nullable=false)
	private String name;
	@Column(nullable=true)
	private String title;
	@Column(columnDefinition = "longtext")
	private String description;
	@Column(nullable=true)
	private String keywords;
	@Column(name="is_show",nullable=false)
	private boolean show;
	@Column(name="allowpost")
	private boolean allowpost=true; 
	
	@Column(name="url",nullable=false)
	private String url;
	@Column(name="sequence",nullable=false)
	private int sequence;
	
	@Column(nullable=false)
	public String type;
	@ManyToOne
	@JoinColumn(name="model_id",nullable=true)
	private CmsModel model;

	@OneToMany
	@JoinColumn(name="parent_id",nullable=true)
	private List<CmsCategory> children;
	
	@ManyToOne
	@JoinColumn(name="parent_id",nullable=true)
	private CmsCategory parent;
	@ManyToOne
	@JoinColumn(name="site_id",nullable=false)
	private CmsSite site;
	@OneToMany
	@JoinColumn(name="category_id",nullable=true)
	private List<CmsCategoryPageTemplate> pageTemplates;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAllowpost() {
		return allowpost;
	}

	public void setAllowpost(boolean allowpost) {
		this.allowpost = allowpost;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public List<CmsCategory> getChildren() {
		return children;
	}

	public void setChildren(List<CmsCategory> children) {
		this.children = children;
	}

	public CmsCategory getParent() {
		return parent;
	}

	public void setParent(CmsCategory parent) {
		this.parent = parent;
	}

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	public CmsSite getSite() {
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}

	public List<CmsCategoryPageTemplate> getPageTemplates() {
		return pageTemplates;
	}

	public void setPageTemplates(List<CmsCategoryPageTemplate> templates) {
		this.pageTemplates = templates;
	}
	
}
