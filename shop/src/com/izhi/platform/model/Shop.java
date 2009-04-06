package com.izhi.platform.model;

import java.io.Serializable;
import java.util.Date;

public class Shop  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437473501614693472L;
	
	private int shopId;
	
	private String shopName;

	private String shopTitle;
	
	private Date startTitle;
	
	private String description;
	
	private int type;
	
	private String logo;
	
	private String theme;
	
	private boolean status;
	
	private User admin;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public Date getStartTitle() {
		return startTitle;
	}

	public void setStartTitle(Date startTitle) {
		this.startTitle = startTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
}
