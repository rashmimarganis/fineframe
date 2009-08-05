package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="shop_agent_level")
public class AgentLevel implements Serializable {

	private static final long serialVersionUID = 1872615910011782696L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="level_id")
	private int agentLevelId;
	@Column(name="level_name")
	private String agentLevelName;
	@Column(name="discount")
	private double discount;
	@Column(name="max_memeber_number")
	private int maxMemeberNumber;
	@Column(name="max_template_number")
	private int priceTemplateNumber;
	@Column(name="description")
	private String description;
	public int getAgentLevelId() {
		return agentLevelId;
	}
	public void setAgentLevelId(int levelId) {
		this.agentLevelId = levelId;
	}
	public String getAgentLevelName() {
		return agentLevelName;
	}
	public void setAgentLevelName(String levelName) {
		this.agentLevelName = levelName;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getMaxMemeberNumber() {
		return maxMemeberNumber;
	}
	public void setMaxMemeberNumber(int maxMemeberNumber) {
		this.maxMemeberNumber = maxMemeberNumber;
	}
	public int getPriceTemplateNumber() {
		return priceTemplateNumber;
	}
	public void setPriceTemplateNumber(int priceTemplateNumber) {
		this.priceTemplateNumber = priceTemplateNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
