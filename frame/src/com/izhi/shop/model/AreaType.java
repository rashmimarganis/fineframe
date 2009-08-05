package com.izhi.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="shop_area_type")
public class AreaType implements Serializable {

	private static final long serialVersionUID = -8116602963786954162L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="area_type_id")
	private int areaTypeId;
	@Column(name="area_type_name")
	private String areaTypeName;
	public int getAreaTypeId() {
		return areaTypeId;
	}
	public void setAreaTypeId(int areaTypeId) {
		this.areaTypeId = areaTypeId;
	}
	public String getAreaTypeName() {
		return areaTypeName;
	}
	public void setAreaTypeName(String areaTypeName) {
		this.areaTypeName = areaTypeName;
	}

}
