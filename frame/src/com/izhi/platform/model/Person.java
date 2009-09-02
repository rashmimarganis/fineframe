package com.izhi.platform.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="p_persons")
public class Person implements Serializable{

	private static final long serialVersionUID = 8000630205062773123L;

	public final static String GENDER_MALE="m";
	public final static String GENDER_FEMALE="f";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="person_id")
	private int personId;

	@Column(nullable=false)
	private String realname;
	@Column
	private String gender;
	@Column
	private int age;
	@Column
	private String address;
	@Column
	private Date birthday;
	@Column(nullable=false)
	private String email;
	
	@Column(name="home_telephone")
	private String homeTelephone;
	
	@Column(name="office_telephone")
	private String officeTelephone;
	@Column
	private String mobilephone;
	@Column
	private int sequence;

	@ManyToOne(cascade={CascadeType.REMOVE})
	@JoinColumn(name="org_id")
	private Org org;
	
	@OneToMany(cascade={CascadeType.REMOVE})
	@JoinColumn(name="person_id")
	private List<User> users;

	public int getPersonId() {
		return personId;
	}


	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	
	
	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getHomeTelephone() {
		return homeTelephone;
	}


	public void setHomeTelephone(String homeTelephone) {
		this.homeTelephone = homeTelephone;
	}


	public String getOfficeTelephone() {
		return officeTelephone;
	}


	public void setOfficeTelephone(String officeTelephone) {
		this.officeTelephone = officeTelephone;
	}


	public String getMobilephone() {
		return mobilephone;
	}


	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}


	public Org getOrg() {
		return org;
	}


	public void setOrg(Org org) {
		this.org = org;
	}
	

	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public int getSequence() {
		return sequence;
	}


	public void setSequence(int sequence) {
		this.sequence = sequence;
	}


	public boolean equals(Object o) {
		if(o instanceof Person){
			Person o_=(Person)o;
			if(o_.getPersonId()==this.getPersonId()){
				return true;
			}
		}
		
		return false;
	}
}
