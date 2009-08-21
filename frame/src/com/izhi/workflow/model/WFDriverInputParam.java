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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="wf_driver_in_param")
public class WFDriverInputParam implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3463101187530871636L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_id")
	private Long driverInputParamID = new Long(-1);
	@Column(name="param_name")
	private String paramName;
	@ManyToOne
	@JoinColumn(name="flow_driver_id")
	private WorkflowDriver workflowDriver;
	
	@Column(name="param_alias")
	private String paramAlias;


	public Long getDriverInputParamID() {
		return driverInputParamID;
	}

	public void setDriverInputParamID(Long driverInputParamID) {
		this.driverInputParamID = driverInputParamID;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public WorkflowDriver getWorkflowDriver() {
		return workflowDriver;
	}

	public void setWorkflowDriver(WorkflowDriver workflowDriver) {
		this.workflowDriver = workflowDriver;
	}

	public String getParamAlias() {
		return paramAlias;
	}

	public void setParamAlias(String paramAlias) {
		this.paramAlias = paramAlias;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof WFDriverInputParam)) {
			return false;
		}
		WFDriverInputParam fdp = (WFDriverInputParam) object;
		return new EqualsBuilder().append(
				this.getDriverInputParamID().toString(),
				fdp.getDriverInputParamID().toString()).append(
				this.getParamName(), fdp.getParamName()).append(
				this.getParamAlias(), fdp.getParamAlias()).isEquals();
	}

	public int hashCode() {// ���ѡ����������ÿ���಻ͬ
		return new HashCodeBuilder(1556335803, 157569255).append(
				this.getDriverInputParamID().toString()).append(
				this.getParamName()).append(this.getParamAlias()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("driverInputParamID",
						this.getDriverInputParamID().toString()).append(
						"paramName", this.getParamName()).append("paramAlias",
						this.getParamAlias()).toString();
	}

}
