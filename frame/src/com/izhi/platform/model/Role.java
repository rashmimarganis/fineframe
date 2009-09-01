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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;

@Entity
@Table(name="p_roles")
public class Role implements Serializable,GrantedAuthority {

	private static final long serialVersionUID = -2854904129718773716L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	private int roleId;
	@Column(length=32,name="role_name",unique=true)
	private String roleName;
	@Column(name="title")
	private String title;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="org_id")
	private Org org;
	@Column(name="sequence")
	private int sequence;
	@Transient
	private String oldName;
	
	@ManyToMany(mappedBy="roles")
	private List<User> users;
	
	@OneToMany(cascade={CascadeType.REMOVE},fetch = FetchType.LAZY,targetEntity=Function.class)
	@JoinTable(name = "p_role_functions", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = @JoinColumn(name = "function_id"))
	private List<Function> functions;

	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int id) {
		this.roleId = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String name) {
		this.roleName = name;
	}
	
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}
	
	public int compareTo(Object o) {
		if(o!=null&&(o instanceof Role)){
			Role o1=(Role)o;
			if(o1.getRoleId()==this.getRoleId()){
				return 1;
			}
		}
		return 0;
	}
	
	
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public boolean equals(Object o) {
		if(o instanceof Role){
			Role o_=(Role)o;
			if(o_.getRoleId()==this.getRoleId()){
				return true;
			}
		}
		return false;
	}
	
}
