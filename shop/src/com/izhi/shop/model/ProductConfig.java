package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="shop_product_config")
public class ProductConfig implements Serializable {

	private static final long serialVersionUID = 2109180986056160319L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_config_id")
	private int productConfigId;
	@Column(name="product_config_name")
	private String productConfigName;
	@Column(name="thumb_width")
	private int thumbWidth;
	@Column(name="thumb_height")
	private int thumbHeight;
	@Column(name="image_width")
	private int imageWidth;
	@Column(name="image_height")
	private int imageHeight;
	@Column(name="money_per_score")
	private int moneyPerScore;
	@Column(name="default_config")
	private boolean defaultConfig;

	public int getProductConfigId() {
		return productConfigId;
	}

	public void setProductConfigId(int productConfigId) {
		this.productConfigId = productConfigId;
	}

	public int getThumbWidth() {
		return thumbWidth;
	}

	public void setThumbWidth(int thumbWidth) {
		this.thumbWidth = thumbWidth;
	}

	public int getThumbHeight() {
		return thumbHeight;
	}

	public void setThumbHeight(int thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getMoneyPerScore() {
		return moneyPerScore;
	}

	public void setMoneyPerScore(int moneyPerScore) {
		this.moneyPerScore = moneyPerScore;
	}

	public boolean isDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public String getProductConfigName() {
		return productConfigName;
	}

	public void setProductConfigName(String productConfigName) {
		this.productConfigName = productConfigName;
	}
	
}
