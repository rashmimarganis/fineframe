package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cms_category")
public class CmsCategory implements Serializable {

	private static final long serialVersionUID = -2608476475609714159L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="category_id")
	private int categoryId;
	
	private String title;
	
	private String description;
	
	private String keywords;
	
	private boolean show;
	
	
	
	
	
	
}
