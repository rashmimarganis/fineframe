package com.izhi.platform.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "p_users")
public class User  implements UserDetails {

	private static final long serialVersionUID = -6655187104320740141L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int userId;
	@Column(length = 32)
	private String username;
	@Column(length = 32)
	private String password;
	@Column(name="last_login_time")
	private Date lastLoginTime;
	@Column(name="login_times")
	private int loginTimes=0;
	@Column(length=20,name="last_login_ip")
	private String lastLoginIp;
	@Column(length = 100)
	private String email;
	@ManyToMany( fetch = FetchType.EAGER)
	@JoinTable(name = "p_user_roles", joinColumns = {@JoinColumn(name = "user_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Column(name="credentials_expired")
	private boolean credentialsExpired=true;
	@Column(name="hint_question")
	private String hintQuestion;
	@Column(name="hint_answer")
	private String hintAnswer;
	@Column(name="expired")
	private boolean expired=true;
	@Column(name="locked")
	private boolean locked=true;
	@Basic
	private boolean enabled=true;
	@Column(name="login_attempts_max")
	private int loginAttemptsMax=0;
	@Column(name="login_attempts")
	private int loginAttempts=0;
	@Column(name="concurrent_max")
	private int concurrentMax=7;
	@Column(name="password_code")
	private String passwordCode;
	
	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;
	@Basic
	private String realname;
	@Basic
	private int age;
	@Basic
	private String address;
	@Column(length=1)
	private String gender;
	@Basic
	private String postcode;
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	@Column(name="validate_code")
	private String validateCode;
	@Basic
	private boolean validated=false;
	
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getHintQuestion() {
		return hintQuestion;
	}

	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}

	public String getHintAnswer() {
		return hintAnswer;
	}

	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}


	public boolean getCredentialsNonExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsNonExpired) {
		this.credentialsExpired = credentialsNonExpired;
	}

	public boolean getExpired() {
		return expired;
	}

	public void setExpired(boolean nonExpired) {
		this.expired = nonExpired;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean nonLocked) {
		this.locked = nonLocked;
	}

	public int getLoginAttemptsMax() {
		return loginAttemptsMax;
	}

	public void setLoginAttemptsMax(int loginAttemptsMax) {
		this.loginAttemptsMax = loginAttemptsMax;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public int getConcurrentMax() {
		return concurrentMax;
	}

	public void setConcurrentMax(int concurrentMax) {
		this.concurrentMax = concurrentMax;
	}

	public String getPasswordCode() {
		return passwordCode;
	}

	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		if(roles!=null){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles.size());
    	for(Role role : roles) {
    		grantedAuthorities.add(new GrantedAuthorityImpl(role.getRoleName()));
    	}
        return grantedAuthorities.toArray(new GrantedAuthority[roles.size()]);
		}
		/*GrantedAuthority[] ga=null;
		if(roles!=null){
			ga=roles.toArray(new GrantedAuthority[0]);
			return ga;
		}
		}else{
			
		}*/
		return new GrantedAuthority[0];
	}

	public boolean isEnabled() {
		return this.getEnabled();
	}
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;

		final User user = (User) o;

		if (username != null ? !username.equals(user.getUsername()) : user
				.getUsername() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (username != null ? username.hashCode() : 0);
	}
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.DEFAULT_STYLE).append("username", this.username)
				.append("enabled", this.enabled);
		GrantedAuthority[] auths = this.getAuthorities();
		
		if (auths != null) {
			sb.append("Granted Authorities: ");
			
			for (int i = 0; i < auths.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(auths[i].getAuthority());
				
			}
		} else {
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

}
