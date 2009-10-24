package com.izhi.cms.model;

import java.io.Serializable;

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
@Table(name="cms_model_page")
public class CmsModelPage implements Serializable{

	private static final long serialVersionUID = -985701142571445004L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="page_id")
	private int pageId;
	
	@Column(name="page_name")
	private String pageName;

	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="model_id")
	private CmsModel model;

	
	public int getPageId() {
		return pageId;
	}

	public void setPageId(int menuId) {
		this.pageId = menuId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String menuName) {
		this.pageName = menuName;
	}

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

}
