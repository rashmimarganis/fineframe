package com.izhi.shop.model;

import java.io.Serializable;

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
@Table(name="shop_bulletin")
public class Bulletin implements Serializable{

	private static final long serialVersionUID = 3658073467369286430L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bulletin_id")
	private int bulletinId;
	@Column(name="bulletin_title")
	private String bulletinTitle;
	@Column(name="bulletin_content")
	private String bulletinContent;
	@ManyToOne
	@JoinColumn(name="shop_id")
	private Shop shop;
	public int getBulletinId() {
		return bulletinId;
	}
	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}
	public String getBulletinTitle() {
		return bulletinTitle;
	}
	public void setBulletinTitle(String bulletinTitle) {
		this.bulletinTitle = bulletinTitle;
	}
	public String getBulletinContent() {
		return bulletinContent;
	}
	public void setBulletinContent(String bulletinContent) {
		this.bulletinContent = bulletinContent;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
