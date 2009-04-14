package com.izhi.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.izhi.platform.model.Shop;
@Entity
@Table(name="shop_product")
public class Product implements Serializable {

	private static final long serialVersionUID = -6413009774720251456L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id")
	private int productId;
	@Column(name="product_name")
	private String productName;
	
	//单位g
	@Basic
	private int weight;
	@ManyToOne
	@JoinColumn(name="category_id",updatable=false)
	private Category category;
	@ManyToOne
	@JoinColumn(name="type_id",updatable=false)
	private ProductType type;
	@Basic
	private String unit;
	@ManyToOne
	@JoinColumn(name="brand_id",updatable=false)
	private Brand brand;
	
	
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	//是否上架
	@Basic
	private boolean online;
	//产品编号
	@Column(name="product_no",length=32)
	private String productNo;
	//库存数
	@Column(name="store_num")
	private int storeNumber;
	
	//市场价
	@Column(name="marker_price")
	private double markerPrice;
	//销售价格
	@Basic
	private double price;
	//成本价格
	@Column(name="cost_price")
	private double costPrice;
	@Basic
	private String description;
	
	private Date upTime;
	
	private boolean enabled;
	
	@ManyToOne
	@JoinColumn(name="shop_id")
	private Shop shop;
	
	private int sequence=0;
	@Column(name="image_path")
	private String imagePath;
	@Column(name="click_times")
	private int clickTimes=0;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getUpTime() {
		return upTime;
	}

	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(int storeNumber) {
		this.storeNumber = storeNumber;
	}

	public double getMarkerPrice() {
		return markerPrice;
	}

	public void setMarkerPrice(double markerPrice) {
		this.markerPrice = markerPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(int clickTimes) {
		this.clickTimes = clickTimes;
	}
	
	

}
