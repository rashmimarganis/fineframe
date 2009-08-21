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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="wf_output_param_binding")
public class FlowNodeOutputParamBinding implements BaseObject {
	
	private static final long serialVersionUID = 974266202682889219L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="param_binding_id")
	private Long paramBindingID = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="node_binding_id",updatable=true,insertable=true,nullable=true)
	private FlowNodeBinding flowNodeBinding;
	
	@ManyToOne
	@JoinColumn(name="driver_output_param_id",updatable=true,insertable=true,nullable=true)
	private WFDriverOutputParam wfDriverOutputParam;
	
	@OneToMany(targetEntity=FlowNodeOutputParamEnumBinding.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="node_output_param_binding_id",updatable=true,insertable=true,nullable=true)
	private List<FlowNodeOutputParamEnumBinding> flowNodeOutputParamEnumeBindings = new ArrayList<FlowNodeOutputParamEnumBinding>();
	
	@Column(name="node_param_id")
	private String flowNodeParamID;


	public Long getParamBindingID() {
		return paramBindingID;
	}

	public void setParamBindingID(Long paramBindingID) {
		this.paramBindingID = paramBindingID;
	}

	
	public FlowNodeBinding getFlowNodeBinding() {
		return flowNodeBinding;
	}

	public void setFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		this.flowNodeBinding = flowNodeBinding;
	}

	
	public WFDriverOutputParam getWfDriverOutputParam() {
		return wfDriverOutputParam;
	}

	public void setWfDriverOutputParam(WFDriverOutputParam wfDriverOutputParam) {
		this.wfDriverOutputParam = wfDriverOutputParam;
	}

	
	public List<FlowNodeOutputParamEnumBinding> getFlowNodeOutputParamEnumeBindings() {
		return flowNodeOutputParamEnumeBindings;
	}

	public void addFlowNodeOutputParamEnumeBinding(
			FlowNodeOutputParamEnumBinding nodeOutputParamEnumeBinding) {
		nodeOutputParamEnumeBinding.setFlowNodeOutputParamBinding(this);
		getFlowNodeOutputParamEnumeBindings().add(nodeOutputParamEnumeBinding);
	}

	public WFDriverOutputParamEnum findDriverOutputParamEnumeByNodeParamEnume(
			String nodeOutputParamEnume) {
		for (Iterator<FlowNodeOutputParamEnumBinding> it = getFlowNodeOutputParamEnumeBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamEnumBinding nodeOutputParamEnumeBinding = (FlowNodeOutputParamEnumBinding) it
					.next();
			if (nodeOutputParamEnumeBinding.getNodeOutputParamEnum().equals(
					nodeOutputParamEnume)) {
				return nodeOutputParamEnumeBinding
						.getWfDriverOutputParamEnum();
			}
		}
		return null;
	}

	public FlowNodeOutputParamBinding findNodeOutputParamEnumeByDriverParamEnume(
			String driverOutputParamEnume) {
		for (Iterator<FlowNodeOutputParamEnumBinding> it = getFlowNodeOutputParamEnumeBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamEnumBinding nodeOutputParamEnumeBinding = (FlowNodeOutputParamEnumBinding) it
					.next();
			if (nodeOutputParamEnumeBinding.getWfDriverOutputParamEnum()
					.equals(driverOutputParamEnume)) {
				return nodeOutputParamEnumeBinding
						.getFlowNodeOutputParamBinding();
			}
		}
		return null;
	}

	public FlowNodeOutputParamEnumBinding findNodeOutputParamEnumeBindingByNodeParamEnume(
			String nodeOutputParamEnume) {
		for (Iterator<FlowNodeOutputParamEnumBinding> it = getFlowNodeOutputParamEnumeBindings().iterator(); it
				.hasNext();) {
			FlowNodeOutputParamEnumBinding nodeOutputParamEnumeBinding = (FlowNodeOutputParamEnumBinding) it
					.next();
			if (nodeOutputParamEnumeBinding.getNodeOutputParamEnum().equals(
					nodeOutputParamEnume)) {
				return nodeOutputParamEnumeBinding;
			}
		}
		return null;
	}

	public void setFlowNodeOutputParamEnumeBindings(
			List<FlowNodeOutputParamEnumBinding> flowNodeOutputParamEnumeBindings) {
		this.flowNodeOutputParamEnumeBindings = flowNodeOutputParamEnumeBindings;
	}

	public String getFlowNodeParamID() {
		return flowNodeParamID;
	}

	public void setFlowNodeParamID(String flowNodeParamID) {
		this.flowNodeParamID = flowNodeParamID;
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowNodeOutputParamBinding)) {
			return false;
		}
		FlowNodeOutputParamBinding fnb = (FlowNodeOutputParamBinding) object;
		return new EqualsBuilder().append(this.getParamBindingID().toString(),
				fnb.getParamBindingID().toString()).append(
				this.getFlowNodeParamID(), fnb.getFlowNodeParamID()).isEquals();
	}

	public int hashCode() {

		return new HashCodeBuilder(266335803, 267569255).append(
				this.getParamBindingID().toString()).append(
				this.getFlowNodeParamID()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("paramBindingID", this.getParamBindingID().toString())
				.append("flowNodeParamID", this.getFlowNodeParamID())
				.toString();
	}

}
