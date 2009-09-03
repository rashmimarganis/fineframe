package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name="cms_bulletin_content")
public class CmsBulletinContent implements Serializable {

	private static final long serialVersionUID = 3567347911563612335L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bulletin_id")
	private int bulletinId;
	@Lob
	private String _value;
	
	
	public String getValue() {
		return _value;
	}
	public void setValue(String _value) {
		this._value = _value;
	}
	public int getBulletinId() {
		return bulletinId;
	}
	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}
	
	
	
}
