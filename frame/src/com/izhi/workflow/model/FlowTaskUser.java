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
@Table(name="wf_task_user")
public class FlowTaskUser implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3067830034555334332L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_user_id")
	private Long id = new Long(-1);
	
	@Column(name="user_id")
	private String userID;
	
	@ManyToOne
	@JoinColumn(name="task_id")
	private FlowTask flowTask;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}


	public FlowTask getFlowTask() {
		return flowTask;
	}

	public void setFlowTask(FlowTask flowTask) {
		this.flowTask = flowTask;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowTaskUser)) {
			return false;
		}
		FlowTaskUser ftu = (FlowTaskUser) object;
		return new EqualsBuilder()
				.append(this.getUserID(), ftu.getUserID()).append(
						this.getFlowTask(), ftu.getFlowTask()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(24635803, 24759255).append(
				this.getId().toString()).append(this.getUserID()).append(
				this.getFlowTask().hashCode()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("ID", this.getId().toString()).append("UserID",
						this.getUserID()).toString();
	}
}
