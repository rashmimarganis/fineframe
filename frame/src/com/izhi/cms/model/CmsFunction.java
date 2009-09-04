package com.izhi.cms.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.izhi.platform.model.Role;
@Entity
@Table(name="cms_function")
public class CmsFunction implements Serializable{

	private static final long serialVersionUID = 4684279327731787082L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="function_id")
	private int functionId;
	
	@Column(name="function_name")
	private String name;
	
	@Column(name="url")
	private String url;
	
	
	@ManyToOne
	@JoinColumn(name="model_id")
	private CmsModel model;
	
	@OneToMany
	@JoinTable(name = "cms_role_function", joinColumns = {@JoinColumn(name = "function_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	
}
