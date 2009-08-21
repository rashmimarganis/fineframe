package com.izhi.workflow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Entity
@Table(name="wf_bisiness_type")
public class BusinessType implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8381247483204851426L;
	public static final String OTHER_BUSINESS_TYPE_NAME = "其他";
	public static final Long OTHER_BUSINESS_TYPE_ID = new Long(-1);
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="type_id")
	private Long typeID = new Long(-1);
	
	@Column(name="type_name")
	private String typeName;
	
	@OneToMany(targetEntity=WorkflowMeta.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="business_type_id",updatable=false,insertable=false)
	private List<WorkflowMeta> workflowMetas = new ArrayList<WorkflowMeta>();
	
	@Transient	
	private Integer tasksNum;
	
	@Column(name="type_note")
	private String note;
	
	@Column(name="type_sort")
	private int sort=0;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getTypeID() {
		return typeID;
	}

	public void setTypeID(Long typeID) {
		this.typeID = typeID;
	}
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<WorkflowMeta> getWorkflowMetas() {
		return workflowMetas;
	}

	public void setWorkflowMetas(List<WorkflowMeta> workflowMetas) {
		this.workflowMetas = workflowMetas;
	}

	public int getFlowMetasNum() {
		return this.workflowMetas.size();
	}

	public void addWorkflowMeta(WorkflowMeta wm) {
		workflowMetas.add(wm);
		wm.setBusinessType(this);
	}

	public void removeWorkflowMeta(WorkflowMeta fm) {
		this.getWorkflowMetas().remove(fm);
		fm.setBusinessType(null);
	}

	public void removeAllWorkflowMetas() {
		if (getWorkflowMetas().size() > 0) {
			for (Iterator<WorkflowMeta> it = getWorkflowMetas().iterator(); it.hasNext();) {
				WorkflowMeta wm = (WorkflowMeta) it.next();
				wm.setBusinessType(null);
			}
			getWorkflowMetas().clear();
		}
	}

	public Integer getTasksNum() {
		return tasksNum;
	}

	public void setTasksNum(Integer tasksNum) {
		this.tasksNum = tasksNum;
	}

	public void otherType() {
		this.setTypeID(BusinessType.OTHER_BUSINESS_TYPE_ID);
		this.setTypeName(BusinessType.OTHER_BUSINESS_TYPE_NAME);
	}

	public boolean equals(Object object) {
		if (!(object instanceof BusinessType)) {
			return false;
		}
		BusinessType bt = (BusinessType) object;
		return new EqualsBuilder().append(this.getTypeID().toString(),
				bt.getTypeID().toString()).append(this.getTypeName(),
				bt.getTypeName()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(1056335803, 107569255).append(
				this.getTypeID().toString()).append(this.getTypeName())
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("typeID", this.getTypeID().toString()).append(
						"typeName", this.getTypeName()).toString();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
