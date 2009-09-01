package com.izhi.platform.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	private int sequence;
	@Column(length=255)
	private String url;
	@Column(name="is_menu")
	private Boolean menu;
	@Column(name="is_log")
	private Boolean log;
	@ManyToOne(optional=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="parent_id",updatable=false,insertable=true,nullable=true)
	private Function parent;
	
	
	@OneToMany(cascade={CascadeType.REMOVE},fetch = FetchType.LAZY)
	@JoinTable(name = "p_role_functions", joinColumns = {@JoinColumn(name = "function_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
	List<Role> roles;
	
	@Column
	private boolean enabled;
	
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
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getMenu() {
		return menu;
	}
	public void setMenu(Boolean menu) {
		this.menu = menu;
	}
	public Boolean getLog() {
		return log;
	}
	public void setLog(Boolean log) {
		this.log = log;
	}

	public boolean equals(Object o) {
		if(o instanceof Function){
			Function o_=(Function)o;
			if(o_.getFunctionId()==this.getFunctionId()){
				return true;
			}
		}
		
		return false;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
}
