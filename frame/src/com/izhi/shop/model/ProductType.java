package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="shop_product_type")
public class ProductType implements Serializable {

	private static final long serialVersionUID = 4629967038003289674L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="type_id")
	private int productTypeId;
	//Chinese Name
	@Column(name="type_name")
	private String productTypeName;
	//English Name
	@Column(name="type_en_name")
	private String productTypeEnName;
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


	public int getProductTypeId() {
		return productTypeId;
	}


	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}


	public String getProductTypeName() {
		return productTypeName;
	}


	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}


	public String getProductTypeEnName() {
		return productTypeEnName;
	}


	public void setProductTypeEnName(String productTypeEnName) {
		this.productTypeEnName = productTypeEnName;
	}
}
