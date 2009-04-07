package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="s_brand")
public class Brand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8484373080473324593L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="brand_id")
	private int brandId=0;
	@Column(name="brand_name")
	private String brandName;
	@Column(name="brand_logo")
	private String brandLogo;
	@Column(name="brand_url")
	private String brandUrl;
	@Basic
	private String description;
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public String getBrandUrl() {
		return brandUrl;
	}
	public void setBrandUrl(String brandUrl) {
		this.brandUrl = brandUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
