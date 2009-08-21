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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="flow_driver")
public class WorkflowDriver implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6366399036467786239L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="driver_id")
	private Long flowDriverID = new Long(-1);
	@Column(name="driver_name",nullable=false)
	private String flowDriverName;
	@Column(name="memo")
	private String memo;
	@Column(name="read_url")
	private String readURL;
	@Column(name="write_url")
	private String writeURL;
	@Column(name="context_path",nullable=false)
	private String contextPath;
	@OneToMany
	@JoinColumn(name="driver_id")
	private List<WFDriverOutputParam> wfDriverOutputParams = new ArrayList<WFDriverOutputParam>();
	@OneToMany
	@JoinColumn(name="driver_id")
	private List<WFDriverInputParam> wfDriverInputParams = new ArrayList<WFDriverInputParam>();


	public Long getFlowDriverID() {
		return flowDriverID;
	}

	public void setFlowDriverID(Long flowDriverID) {
		this.flowDriverID = flowDriverID;
	}


	public String getFlowDriverName() {
		return flowDriverName;
	}

	public void setFlowDriverName(String flowDriverName) {
		this.flowDriverName = flowDriverName;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getReadURL() {
		return readURL;
	}

	public void setReadURL(String readURL) {
		this.readURL = readURL;
	}


	public String getWriteURL() {
		return writeURL;
	}

	public void setWriteURL(String writeURL) {
		this.writeURL = writeURL;
	}


	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}


	public List<WFDriverOutputParam> getWfDriverOutputParams() {
		return wfDriverOutputParams;
	}

	public void setWfDriverOutputParams(List<WFDriverOutputParam> wfDriverOutputParams) {
		this.wfDriverOutputParams = wfDriverOutputParams;
	}

	public List<WFDriverInputParam> getWfDriverInputParams() {
		return wfDriverInputParams;
	}

	public void setWfDriverInputParams(List<WFDriverInputParam> wfDriverInputParams) {
		this.wfDriverInputParams = wfDriverInputParams;
	}

	public void addOutputParam(WFDriverOutputParam wdOutParam) {
		this.getWfDriverOutputParams().add(wdOutParam);
		wdOutParam.setWorkflowDriver(this);
	}

	public void removeOutputParam(WFDriverOutputParam wdOutParam) {
		this.getWfDriverOutputParams().remove(wdOutParam);
		wdOutParam.setWorkflowDriver(null);
	}

	public void addInputParam(WFDriverInputParam wdInParam) {
		this.getWfDriverInputParams().add(wdInParam);
		wdInParam.setWorkflowDriver(this);
	}

	public void removeInputParam(WFDriverInputParam wdInParam) {
		this.getWfDriverInputParams().remove(wdInParam);
		wdInParam.setWorkflowDriver(null);
	}

	public void removeAllParams() {
		if (getWfDriverInputParams().size() > 0) {
			for (Iterator<WFDriverInputParam> it = getWfDriverInputParams().iterator(); it
					.hasNext();) {
				WFDriverInputParam para = (WFDriverInputParam) it.next();
				para.setWorkflowDriver(null);
			}
			getWfDriverInputParams().clear();
		}
		if (getWfDriverOutputParams().size() > 0) {
			for (Iterator<WFDriverOutputParam> it = getWfDriverOutputParams().iterator(); it
					.hasNext();) {
				WFDriverOutputParam para = (WFDriverOutputParam) it.next();
				para.setWorkflowDriver(null);
			}
			getWfDriverOutputParams().clear();
		}
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof WorkflowDriver)) {
			return false;
		}

		WorkflowDriver fd = (WorkflowDriver) object;
		boolean result = new EqualsBuilder().append(
				this.getFlowDriverID().toString(),
				fd.getFlowDriverID().toString()).append(this.getContextPath(),
				fd.getContextPath()).append(this.getFlowDriverName(),
				fd.getFlowDriverName()).append(this.getMemo(), fd.getMemo())
				.append(this.getWriteURL(), fd.getWriteURL()).isEquals();
		return result;
	}

	public int hashCode() {
		return new HashCodeBuilder(1856335803, 187569255).append(
				this.getFlowDriverID().toString())
				.append(this.getContextPath()).append(this.getFlowDriverName())
				.append(this.getMemo()).append(this.getWriteURL()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowDriverID", this.getFlowDriverID().toString())
				.append("contextPath", this.getContextPath()).append(
						"flowDriverName", this.getFlowDriverName()).append(
						"memo", this.getMemo()).append("writeURL",
						this.getWriteURL()).toString();
	}

}
