package com.izhi.cms.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="cms_category")
public class CmsCategory implements Serializable {

	private static final long serialVersionUID = -2608476475609714159L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id")
	private int categoryId;
	
	private String title;
	@Lob
	private String description;
	
	private String keywords;
	
	private boolean show;
	
	@ManyToOne
	@JoinColumn(name="model_id")
	private CmsModel model;

	@OneToMany
	@JoinColumn(name="parent_id")
	private List<CmsCategory> children;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private CmsCategory parent;
	
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
	
}
