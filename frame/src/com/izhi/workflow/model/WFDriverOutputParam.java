/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="wf_driver_out_param")
public class WFDriverOutputParam implements BaseObject {

	private static final long serialVersionUID = -236726097700506350L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_id")
	private Long driverOutputParamID = new Long(-1);
	@Column(name="param_name",nullable=false)
	private String paramName;
	@Column(name="param_alias",nullable=false)
	private String paramAlias;
	@ManyToOne
	@JoinColumn(name="driver_id")
	private WorkflowDriver workflowDriver;
	@OneToMany(targetEntity=WFDriverOutputParamEnum.class)
	@JoinColumn(name="driver_output_param_id")
	private List<WFDriverOutputParamEnum> driverOutputParamEnumes = new ArrayList<WFDriverOutputParamEnum>();

	public Long getDriverOutputParamID() {
		return driverOutputParamID;
	}

	public void setDriverOutputParamID(Long driverOutputParamID) {
		this.driverOutputParamID = driverOutputParamID;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamAlias() {
		return paramAlias;
	}

	public void setParamAlias(String paramAlias) {
		this.paramAlias = paramAlias;
	}

	/**
	 * @hibernate.many-to-one column="FK_FLOW_DRIVER_ID"
	 *                        class="org.powerstone.workflow.model.WorkflowDriver"
	 * @return WorkflowDriver
	 */
	public WorkflowDriver getWorkflowDriver() {
		return workflowDriver;
	}

	public void setWorkflowDriver(WorkflowDriver workflowDriver) {
		this.workflowDriver = workflowDriver;
	}

	/**
	 * @hibernate.bag name="driverOutputParamEnumes" cascade="all-delete-orphan"
	 *                lazy="true" inverse="true"
	 * @hibernate.collection-key column="FK_DRIVER_OUTPUT_PARAM_ID"
	 * @hibernate.collection-one-to-many class="org.powerstone.workflow.model.WFDriverOutputParamEnume"
	 * @return List
	 */
	public List<WFDriverOutputParamEnum> getDriverOutputParamEnums() {
		return driverOutputParamEnumes;
	}

	public void setDriverOutputParamEnums(List<WFDriverOutputParamEnum> driverOutputParamEnumes) {
		this.driverOutputParamEnumes = driverOutputParamEnumes;
	}

	public void addParamEnume(WFDriverOutputParamEnum wdOutParamEnume) {
		this.getDriverOutputParamEnums().add(wdOutParamEnume);
		wdOutParamEnume.setWfDriverOutputParam(this);
	}

	public void removeParamEnume(WFDriverOutputParamEnum wdOutParamEnume) {
		this.getDriverOutputParamEnums().remove(wdOutParamEnume);
		wdOutParamEnume.setWfDriverOutputParam(null);
	}

	public void removeAllWorkflowMetas() {
		if (getDriverOutputParamEnums().size() > 0) {
			for (Iterator<WFDriverOutputParamEnum> it = getDriverOutputParamEnums().iterator(); it
					.hasNext();) {
				WFDriverOutputParamEnum wdOutParamEnume = (WFDriverOutputParamEnum) it
						.next();
				wdOutParamEnume.setWfDriverOutputParam(null);
			}
			getDriverOutputParamEnums().clear();
		}
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof WFDriverOutputParam)) {
			return false;
		}
		WFDriverOutputParam fdp = (WFDriverOutputParam) object;
		return new EqualsBuilder().append(
				this.getDriverOutputParamID().toString(),
				fdp.getDriverOutputParamID().toString()).append(
				this.getParamName(), fdp.getParamName()).append(
				this.getParamAlias(), fdp.getParamAlias()).isEquals();
	}

	public int hashCode() {
		// ���ѡ����������ÿ���಻ͬ
		return new HashCodeBuilder(1656335803, 167569255).append(
				this.getDriverOutputParamID().toString()).append(
				this.getParamName()).append(this.getParamAlias()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("driverOutputParamID",
						this.getDriverOutputParamID().toString()).append(
						"paramName", this.getParamName()).append("paramAlias",
						this.getParamAlias()).toString();
	}

}
