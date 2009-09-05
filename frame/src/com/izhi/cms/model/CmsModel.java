package com.izhi.cms.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="cms_model")
public class CmsModel implements Serializable {

	private static final long serialVersionUID = -4278569909316098477L;
	
	public static final String PrimaryKey="modelId";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="model_id")
	private int modelId;
	@Column(name="model_name")
	private String name;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="entity_class")
	private String entityClass;
	@OneToMany(fetch=FetchType.LAZY)
	private List<CmsFunction> functions;
	@Column(name="has_child")
	private boolean hasChild;
	
	@Column(name="is_show")
	private boolean show;
	
	@Column(name="sequence")
	private int sequence;
	
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="model_id",updatable=false,insertable=false,nullable=true)
	private List<CmsAttribute> attributes;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public List<CmsAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<CmsAttribute> attributes) {
		this.attributes = attributes;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<CmsFunction> getFunctions() {
		return functions;
	}
	public void setFunctions(List<CmsFunction> functions) {
		this.functions = functions;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	
	
}
