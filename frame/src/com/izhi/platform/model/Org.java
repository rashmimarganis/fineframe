package com.izhi.platform.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="p_shops")
public class Org implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390001174287826313L;
	@Id
	@Basic(fetch=FetchType.EAGER)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="shop_id")
	private int orgId;
	@Column(length=32,name="shop_name")
	private String orgName;
	@Column(length=32)
	private String title;
	private int type;
	@ManyToOne(optional=true,fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",updatable=true,nullable=true)
	private Org parent;
	@Column
	private int sort=0;

	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int id) {
		this.orgId = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String name) {
		this.orgName = name;
	}
	public Org getParent() {
		return parent;
	}
	public void setParent(Org parent) {
		this.parent = parent;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
