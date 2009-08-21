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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.izhi.workflow.exception.ExceptionMessage;

@Entity
@Table(name="flow_proc_relative_data")
public class FlowProcRelativeData implements BaseObject {
	private static final long serialVersionUID = 1978206512184573311L;
	private static Log log = LogFactory.getLog(FlowProcRelativeData.class);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="data_id")
	private Long id = new Long(-1);
	
	@ManyToOne
	@JoinColumn(name="flow_proc_id",updatable=false,insertable=true)
	private FlowProc flowProc;
	
	@ManyToOne
	@JoinColumn(name="flow_node_output_param_bind_id")
	private FlowNodeOutputParamBinding flowNodeOutputParamBinding;
	
	@Column(name="driver_param_value")
	private String driverParamValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlowProc getFlowProc() {
		return flowProc;
	}

	public void setFlowProc(FlowProc flowProc) {
		this.flowProc = flowProc;
	}


	public FlowNodeOutputParamBinding getFlowNodeOutputParamBinding() {
		return flowNodeOutputParamBinding;
	}

	public void setFlowNodeOutputParamBinding(
			FlowNodeOutputParamBinding flowNodeOutputParamBinding) {
		this.flowNodeOutputParamBinding = flowNodeOutputParamBinding;
	}

	public String getDriverParamValue() {
		return driverParamValue;
	}

	public void setDriverParamValue(String driverParamValue) {
		this.driverParamValue = driverParamValue;
	}


	public String getCorrespondingNodeParamValue() {
		if (getFlowNodeOutputParamBinding()
				.getFlowNodeOutputParamEnumeBindings().size() > 0) {
			FlowNodeOutputParamEnumBinding nodeOutputParamEnumeBinding = getFlowNodeOutputParamBinding()
					.findNodeOutputParamEnumeBindingByNodeParamEnume(
							getDriverParamValue());
			if (nodeOutputParamEnumeBinding != null) {
				return nodeOutputParamEnumeBinding.getNodeOutputParamEnum();
			} else {
				log
						.warn(ExceptionMessage.ERROR_FLOWPROC_UN_MATCHED_OUTPUT_PARAM_VALUE
								+ "[" + getDriverParamValue() + "]" + this);
				return getDriverParamValue();
			}
		} else {
			return this.getDriverParamValue();
		}
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowProcRelativeData)) {
			return false;
		}
		FlowProcRelativeData fpr = (FlowProcRelativeData) object;
		return new EqualsBuilder().append(this.getId().toString(),
				fpr.getId().toString()).append(this.getDriverParamValue(),
				fpr.getDriverParamValue()).isEquals();
	}

	public int hashCode() { 
		return new HashCodeBuilder(296335803, 297569255).append(
				this.getId().toString()).append(this.getDriverParamValue())
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId().toString()).append(
						"driverParamValue", this.getDriverParamValue()).append(
						"id", this.getFlowNodeOutputParamBinding()).toString();
	}
}
