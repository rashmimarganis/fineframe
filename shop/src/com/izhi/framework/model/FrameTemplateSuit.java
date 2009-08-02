package com.izhi.framework.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="f_template_suit")
public class FrameTemplateSuit implements Serializable{

	private static final long serialVersionUID = -2658670253707058593L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="suit_id")
	private int suitId;
	
	@Column(name="package_name")
	private String packageName;
	
	@Column(name="package_title")
	private String title;

	

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSuitId() {
		return suitId;
	}

	public void setSuitId(int suitId) {
		this.suitId = suitId;
	}

	
}
