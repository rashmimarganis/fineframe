package com.izhi.platform.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table(name="p_functions")
public class Function implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7230187936850135248L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="function_id")
	private Integer functionId;
	@Column(length=32,name="function_name")
	private String functionName;
	@Column(length=40,name="function_title")
	private String functionTitle;
	private int sequence;
	@Column(length=255)
	private String url;
	@Column(name="is_show")
	private boolean show;
	@Column(name="is_log")
	private boolean isLog;
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",updatable=true,insertable=true,nullable=true)
	private Function parent;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="p_role_functions" ,joinColumns=@JoinColumn(name="function_id",updatable=false,insertable=false),inverseJoinColumns=@JoinColumn(name="role_id",updatable=false,insertable=false))
	private List<Role> roles;
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@OrderBy("sequence desc")
	@JoinColumn(name="parent_id",updatable=false,insertable=false)
	private List<Function> children;

	@Basic
	@Column(name="open_type")
	private String openType;
	@Column
	private boolean enabled;
	@Column
	private int type;
	
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public Integer getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Integer id) {
		this.functionId = id;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String name) {
		this.functionName = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Function getParent() {
		return parent;
	}
	public void setParent(Function parent) {
		this.parent = parent;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getFunctionTitle() {
		return functionTitle;
	}
	public void setFunctionTitle(String title) {
		this.functionTitle = title;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean getShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public boolean isLog() {
		return isLog;
	}
	public void setLog(Boolean isLog) {
		this.isLog = isLog;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<Function> getChildren() {
		return children;
	}
	public void setChildren(List<Function> children) {
		this.children = children;
	}

	
}
