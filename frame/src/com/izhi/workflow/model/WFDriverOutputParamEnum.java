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
@Table(name="wf_driver_output_param_enum")
public class WFDriverOutputParamEnum implements BaseObject {

	private static final long serialVersionUID = 2498109867052656189L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_enum_id")
	private Long driverOutputParamEnumID = new Long(-1);
	@ManyToOne
	@JoinColumn(name="driver_output_param_id")
	private WFDriverOutputParam wfDriverOutputParam;
	@Column(name="emnu_value")
	private String driverOutputParamEnumValue;


	public Long getDriverOutputParamEnumID() {
		return driverOutputParamEnumID;
	}

	public void setDriverOutputParamEnumID(Long driverOutputParamEnumeID) {
		this.driverOutputParamEnumID = driverOutputParamEnumeID;
	}


	public WFDriverOutputParam getWfDriverOutputParam() {
		return wfDriverOutputParam;
	}

	public void setWfDriverOutputParam(WFDriverOutputParam wfDriverOutputParam) {
		this.wfDriverOutputParam = wfDriverOutputParam;
	}

	public String getDriverOutputParamEnumValue() {
		return driverOutputParamEnumValue;
	}

	public void setDriverOutputParamEnumValue(
			String driverOutputParamEnumeValue) {
		this.driverOutputParamEnumValue = driverOutputParamEnumeValue;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof WFDriverOutputParamEnum)) {
			return false;
		}
		WFDriverOutputParamEnum fdp = (WFDriverOutputParamEnum) object;
		return new EqualsBuilder().append(
				this.getDriverOutputParamEnumID().toString(),
				fdp.getDriverOutputParamEnumID().toString()).append(
				this.getDriverOutputParamEnumValue(),
				fdp.getDriverOutputParamEnumValue()).isEquals();
	}

	public int hashCode() {
		// ���ѡ����������ÿ���಻ͬ
		return new HashCodeBuilder(1756335803, 177569255).append(
				this.getDriverOutputParamEnumID().toString()).append(
				this.getDriverOutputParamEnumValue()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("driverOutputParamEnumeID",
						this.getDriverOutputParamEnumID().toString()).append(
						"driverOutputParamEnumeValue",
						this.getDriverOutputParamEnumValue()).toString();
	}

}
