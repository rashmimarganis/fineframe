package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="cms_template_suit")
public class CmsTemplateSuit implements Serializable{

	private static final long serialVersionUID = -2658670253707058593L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="suit_id")
	private int suitId;
	
	@Column(name="package_name")
	private String packageName;
	@Transient
	private String oldPackageName;
	@Column(name="name")
	private String name;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}



	public int getSuitId() {
		return suitId;
	}

	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldPackageName() {
		return oldPackageName;
	}

	public void setOldPackageName(String oldPackageName) {
		this.oldPackageName = oldPackageName;
	}

	
}
