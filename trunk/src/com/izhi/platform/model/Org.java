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
@Table(name="p_orgs")
public class Org implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390001174287826313L;
	@Id
	@Basic(fetch=FetchType.EAGER)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(length=32)
	@Basic(fetch=FetchType.EAGER)
	private String name;
	@Column(length=32)
	@Basic(fetch=FetchType.EAGER)
	private String title;
	@Column(name="full_title", length=250)
	@Basic(fetch=FetchType.EAGER)
	private String fullTitle;
	@Column(name="path",length=250)
	@Basic(fetch=FetchType.EAGER)
	private String orgPath;
	@Basic(fetch=FetchType.EAGER)
	private int type;
	@Basic(fetch=FetchType.EAGER)
	private int depth;
	@ManyToOne(optional=true,fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",updatable=true,nullable=true)
	private Org parent;
	@Column
	private int sort=0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Org getParent() {
		return parent;
	}
	public void setParent(Org parent) {
		this.parent = parent;
	}
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
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
	public String getOrgPath() {
		return orgPath;
	}
	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	/**
	 * @return the fullTitle
	 */
	public String getFullTitle() {
		return fullTitle;
	}
	/**
	 * @param fullTitle the fullTitle to set
	 */
	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}
	
}
