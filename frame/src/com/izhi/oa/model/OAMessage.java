package com.izhi.oa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.izhi.platform.model.User;
/*
 * 短消息模型
 */
@Entity
@Table(name="oa_message")
public class OAMessage implements Serializable {

	private static final long serialVersionUID = 3509157819670184073L;

	public static final int STATE_READED=0;
	public static final int STATE_NOT_READED=1;
	
	public static final int DIRECT_FROM=0;
	public static final int DIRECT_TO=1;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="msg_id")
	private int messageId;
	@Column(length=100)
	private String title;
	@Column(length=1000)
	private String content;
	@ManyToOne
	@JoinColumn(name="from_id")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_id")
	private User toUser;
	
	private Date created;
	
	private int state;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
