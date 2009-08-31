package com.izhi.platform.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="p_orgs")
public class Org implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390001174287826313L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="org_id")
	private int orgId;
	@Column(length=32,name="org_name")
	private String name;
	private String type;
	@ManyToOne(optional=true,fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",insertable=false,updatable=false,nullable=true)
	private Org parent;
	@Column(name="sequence")
	private int sort=0;
	@Transient
	private String oldName;

	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int id) {
		this.orgId = id;
	}

	public Org getParent() {
		return parent;
	}
	public void setParent(Org parent) {
		this.parent = parent;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	
}
