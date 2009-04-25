package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="shop_link")
public class Link implements Serializable{

	private static final long serialVersionUID = -6666472538150766138L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="link_id")
	private int linkId;
	@Column(name="link_name")
	private String linkName;
	@Column(name="link_url")
	private String linkUrl;
	@Column(name="sequence")
	private int sequence;
	@Column(name="logoUrl")
	private String logo;
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
