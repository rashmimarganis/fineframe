package com.izhi.cms.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="cms_template_tag")
public class TemplateTag implements Serializable{

	private static final long serialVersionUID = 9108877374407261740L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tag_id")
	private int tagId;
	@Column(name="tag_name")
	private String tagName;
	@ManyToOne
	@JoinColumn(name="model_id")
	private DataModel model;
	@ManyToMany
	@JoinTable(name="cms_tag_field", joinColumns = {@JoinColumn(name = "tag_id",insertable=false,updatable=false)}, inverseJoinColumns = @JoinColumn(name = "field_id"))
	private List<ModelField> fields;
	@ManyToOne
	@JoinColumn(name="template_id")
	private Template template;
	@Column(name="item_count")
	private int itemCount;
	@Column(name="is_paged")
	private boolean paged;
	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public DataModel getModel() {
		return model;
	}

	public void setModel(DataModel model) {
		this.model = model;
	}

	public List<ModelField> getFields() {
		return fields;
	}

	public void setFields(List<ModelField> fields) {
		this.fields = fields;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean isPaged) {
		this.paged = isPaged;
	}
	
	
}
