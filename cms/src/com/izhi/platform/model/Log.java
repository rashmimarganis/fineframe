package com.izhi.platform.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="p_logs")
public class Log implements Serializable {

	private static final long serialVersionUID = -5426144134638037407L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="log_id")
	private int logId;
	@ManyToOne(cascade={CascadeType.REMOVE})
	@JoinColumn(name="user_id",insertable=true,updatable=false)
	private User user;
	private Date time;
	private String operation;
	@Column(length=200)
	private String url;
	@Column(length=20)
	private String ip;
	@ManyToOne(cascade={CascadeType.REMOVE})
	@JoinColumn(name="shop_id",insertable=true,updatable=false)
	private Shop shop;
	public int getLogId() {
		return logId;
	}
	public void setLogId(int id) {
		this.logId = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop org) {
		this.shop = org;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
