package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cms_template_suit")
public class TemplateSuit implements Serializable {

	private static final long serialVersionUID = 5085888048417484913L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="suit_id")
	private int suitId;
	@Column(name="suit_name")
	private String suitName;
	@Column(name="package_name")
	private String packageName;
	@Column(name="is_default")
	private boolean isDefault=false;
	public int getSuitId() {
		return suitId;
	}
	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}
	public String getSuitName() {
		return suitName;
	}
	public void setSuitName(String suitName) {
		this.suitName = suitName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

}
