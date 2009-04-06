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
	private int id;
	@Column(length=32)
	private String name;
	@Column(length=32)
	private String title;
	@Column(length=100)
	private String note;
	@ManyToOne
	@JoinColumn(name="org_id")
	private Org org;
	
	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "p_user_roles", joinColumns = {@JoinColumn(name = "role_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "user_id",insertable=false,updatable=false))
	private Set<User> users;
	@ManyToMany( fetch = FetchType.LAZY)
	@JoinTable(name = "p_role_functions", joinColumns = {@JoinColumn(name = "role_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "function_id",insertable=false,updatable=false))
	private Set<Function> functions;

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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAuthority() {
		return this.name;
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
			if(o1.getId()==this.getId()){
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
