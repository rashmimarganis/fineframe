package com.izhi.cms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cms_module")
public class Module implements Serializable {

	private static final long serialVersionUID = -3192961701955775230L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="module_id")
	private int moduleId;
	@Column(name="module_name")
	private String name;
	@Column(name="package_name")
	private String packageName;
	@Basic
	private String version;
	@Column(name="installed")
	private Date installedDate;
	@Column(name="updated")
	private Date updatedDate;
	@Basic
	private boolean enabled;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getInstalledDate() {
		return installedDate;
	}

	public void setInstalledDate(Date installedDate) {
		this.installedDate = installedDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updateedDate) {
		this.updatedDate = updateedDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
