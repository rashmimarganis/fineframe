package com.izhi.shop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="shop_category")
public class Category implements Serializable {

	private static final long serialVersionUID = 4629967038003289674L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id")
	private int categoryId;
	//Chinese Name
	@Column(name="category_name")
	private String categoryName;
	//English Name
	@Column(name="category_en_name")
	private String categoryEnName;
	@Column(name="image_path")
	private String imagePath;
	@Column(name="sequence")
	private int sequence;
	@Column(name="description")
	private String description;
	//页面关键词
	@Column(name="page_key_kords")
	private String pageKeyWords;
	//页面描述
	@Column(name="page_description")
	private String pageDescription;
	
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@OrderBy("sequence desc")
	@JoinColumn(name="parent_id",updatable=false,insertable=false)
	private List<Category> children;

	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCategoryEnName() {
		return categoryEnName;
	}


	public void setCategoryEnName(String categoryEnName) {
		this.categoryEnName = categoryEnName;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public int getSequence() {
		return sequence;
	}


	public void setSequence(int sequence) {
		this.sequence = sequence;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPageKeyWords() {
		return pageKeyWords;
	}


	public void setPageKeyWords(String pageKeyWords) {
		this.pageKeyWords = pageKeyWords;
	}


	public String getPageDescription() {
		return pageDescription;
	}


	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}


	public Category getParent() {
		return parent;
	}


	public void setParent(Category parent) {
		this.parent = parent;
	}


	public List<Category> getChildren() {
		return children;
	}


	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
