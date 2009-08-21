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
@Table(name="flow_output_param_enum_bind")
public class FlowNodeOutputParamEnumBinding implements BaseObject {

	private static final long serialVersionUID = 5153200024122865825L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_enum_bind_id")
	private Long paramEnumeBindingID = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="node_output_param_binding_id",nullable=false)
	private FlowNodeOutputParamBinding flowNodeOutputParamBinding;
	
	@Column(name="output_param_enum_id")
	private String nodeOutputParamEnum;
	@ManyToOne
	@JoinColumn(name="driver_output_param_enum")
	private WFDriverOutputParamEnum wfDriverOutputParamEnum;


	public Long getParamEnumeBindingID() {
		return paramEnumeBindingID;
	}

	public void setParamEnumeBindingID(Long paramEnumeBindingID) {
		this.paramEnumeBindingID = paramEnumeBindingID;
	}

	/**
	 * @hibernate.many-to-one column="FK_PARAM_BINDING_ID"
	 *                        class="org.powerstone.workflow.model.FlowNodeOutputParamBinding"
	 * @return FlowNodeOutputParamBinding
	 */
	public FlowNodeOutputParamBinding getFlowNodeOutputParamBinding() {
		return flowNodeOutputParamBinding;
	}

	public void setFlowNodeOutputParamBinding(
			FlowNodeOutputParamBinding flowNodeOutputParamBinding) {
		this.flowNodeOutputParamBinding = flowNodeOutputParamBinding;
	}

	/**
	 * @hibernate.property column="VC_NODE_OUTPUT_PARAM_ENUME_ID" length="255"
	 *                     type="string" not-null="true"
	 * @return String
	 */
	public String getNodeOutputParamEnum() {
		return nodeOutputParamEnum;
	}

	public void setNodeOutputParamEnum(String nodeOutputParamEnum) {
		this.nodeOutputParamEnum = nodeOutputParamEnum;
	}

	/**
	 * @hibernate.many-to-one column="FKDRIVEROUTPUTPARAMENUMEID"
	 *                        class="org.powerstone.workflow.model.WFDriverOutputParamEnume"
	 * @return WFDriverOutputParamEnume
	 */
	public WFDriverOutputParamEnum getWfDriverOutputParamEnum() {
		return wfDriverOutputParamEnum;
	}

	public void setWfDriverOutputParamEnum(
			WFDriverOutputParamEnum wfDriverOutputParamEnum) {
		this.wfDriverOutputParamEnum = wfDriverOutputParamEnum;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowNodeOutputParamEnumBinding)) {
			return false;
		}
		FlowNodeOutputParamEnumBinding fnb = (FlowNodeOutputParamEnumBinding) object;
		return new EqualsBuilder().append(
				this.getParamEnumeBindingID().toString(),
				fnb.getParamEnumeBindingID().toString()).append(
				this.getNodeOutputParamEnum(), fnb.getNodeOutputParamEnum())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(276335803, 277569255).append(
				this.getParamEnumeBindingID().toString()).append(
				this.getNodeOutputParamEnum()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("paramEnumeBindingID",
						this.getParamEnumeBindingID().toString()).append(
						"nodeOutputParamEnume", this.getNodeOutputParamEnum())
				.toString();
	}

}
