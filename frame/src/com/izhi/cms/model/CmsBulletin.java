package com.izhi.cms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.izhi.platform.model.User;
@Entity
@Table(name="cms_bulletin")
public class CmsBulletin implements Serializable{

	private static final long serialVersionUID = -6642985361055404064L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bulletin_id")
	private int bulletinId;
	
	private Date created;
	
	private Date updated;
	
	private User creater;
	
	private User updater;
	
	private String keywords;
	
	private String summary;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private CmsBulletinContent content;

	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public User getUpdater() {
		return updater;
	}

	public void setUpdater(User updater) {
		this.updater = updater;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public CmsBulletinContent getContent() {
		return content;
	}

	public void setContent(CmsBulletinContent content) {
		this.content = content;
	}
	
	
}
