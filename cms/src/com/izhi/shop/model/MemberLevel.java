package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shop_member_level")
public class MemberLevel implements Serializable {

	private static final long serialVersionUID = 7970139707376819670L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="level_id")
	private int memberLevelId;
	@Column(name="level_name")
	private String memberLevelName;
	@Column(name="min_buy_score")
	private int minBuyScore;
	@Column(name="min_business_scrore")
	private int minBusinessScrore;
	@Column(name="mini_money")
	private int minMoney;
	@Column(name="dsicount")
	private double discount;
	@Column(name="default_level")
	private boolean defaultLevel;
	@Column(name="description")
	private String description;
	public int getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(int levelId) {
		this.memberLevelId = levelId;
	}
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String levelName) {
		this.memberLevelName = levelName;
	}
	public int getMinBuyScore() {
		return minBuyScore;
	}
	public void setMinBuyScore(int minBuyScore) {
		this.minBuyScore = minBuyScore;
	}
	public int getMinBusinessScrore() {
		return minBusinessScrore;
	}
	public void setMinBusinessScrore(int minBusinessScrore) {
		this.minBusinessScrore = minBusinessScrore;
	}
	public int getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(int minMoney) {
		this.minMoney = minMoney;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public boolean isDefaultLevel() {
		return defaultLevel;
	}
	public void setDefaultLevel(boolean defaultLevel) {
		this.defaultLevel = defaultLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
