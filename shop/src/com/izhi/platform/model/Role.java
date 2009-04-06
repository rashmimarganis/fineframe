package com.izhi.platform.model;

import java.io.Serializable;
import java.util.Set;

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
import javax.persistence.Table;

import org.acegisecurity.GrantedAuthority;

@Entity
@Table(name="p_roles")
public class Role implements Serializable,GrantedAuthority {

	private static final long serialVersionUID = -2854904129718773716L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="role_id")
	private int roleId;
	@Column(length=32,name="role_name")
	private String roleName;
	@Column(length=32)
	private String title;
	@Column(length=100)
	private String note;
	@ManyToOne
	@JoinColumn(name="shop_id")
	private Shop shop;
	
	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "p_user_roles", joinColumns = {@JoinColumn(name = "role_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "user_id",insertable=false,updatable=false))
	private Set<User> users;
	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "p_role_functions", joinColumns = {@JoinColumn(name = "role_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "function_id",insertable=false,updatable=false))
	private Set<Function> functions;

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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop org) {
		this.shop = org;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
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
	public Set<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
}
