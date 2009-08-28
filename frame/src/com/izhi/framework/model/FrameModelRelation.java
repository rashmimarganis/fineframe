package com.izhi.framework.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="frame_model_relation")
public class FrameModelRelation implements Serializable{

	private static final long serialVersionUID = -790065212642697805L;
	public static final String PrimaryKey="relationId";
	public static final String RELATION_ONETOMANY="onetomany";
	public static final String RELATION_MANYTOMANY="manytomany";
	public static final String RELATION_MANYTOONE="manytoone";
	public static final String RELATION_ONETOONE="onetoone";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="relation_id")
	private int relationId;
	
	@ManyToOne
	@JoinColumn(name="model_id",insertable=true,updatable=false,nullable=false)
	private FrameModel model;
	@ManyToOne
	@JoinColumn(name="relation_model_id",insertable=true,updatable=false,nullable=false)
	private FrameModel relationModel;
	@Column(name="relation")
	private String relation;

	@Column(name="filed_name",nullable=true)
	private String fieldName;
	

	public int getRelationId() {
		return relationId;
	}

	public void setRelationId(int relationId) {
		this.relationId = relationId;
	}

	public FrameModel getModel() {
		return model;
	}

	public void setModel(FrameModel model) {
		this.model = model;
	}

	public FrameModel getRelationModel() {
		return relationModel;
	}

	public void setRelationModel(FrameModel relationModel) {
		this.relationModel = relationModel;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
}
