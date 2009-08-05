package com.izhi.platform.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="p_regions")
public class Region implements Serializable{

	
	private static final long serialVersionUID = 7492546664910610704L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(length=32,unique=true,nullable=false)
	private String code;
	@Column(length=32,unique=true,nullable=false)
	private String name;
	@Column(nullable=false)
	private int type;
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",nullable=true)
	private Region parent;
	
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

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Region getParent() {
		return parent;
	}
	public void setParent(Region parent) {
		this.parent = parent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/*public Set<Region> getChildren() {
		return children;
	}
	public void setChildren(Set<Region> children) {
		this.children = children;
	}*/
}
