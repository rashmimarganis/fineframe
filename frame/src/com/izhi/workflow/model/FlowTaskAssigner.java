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

@Entity
@Table(name="wf_task_assigner")
public class FlowTaskAssigner implements BaseObject {

	private static final long serialVersionUID = 7515240135133772011L;
	private static Log log = LogFactory.getLog(FlowTaskAssigner.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="assigner_id")
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

	public boolean equals(Object object) {
		if (log.isDebugEnabled()) {
			log.debug(userID);
		}
		if (!(object instanceof FlowTaskAssigner)) {
			return false;
		}
		FlowTaskAssigner fta = (FlowTaskAssigner) object;
		return new EqualsBuilder()
				.append(this.getUserID(), fta.getUserID()).append(
						this.getFlowTask(), fta.getFlowTask()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(22635803, 22759255).append(
				this.getId().toString()).append(this.getUserID()).append(
				this.getFlowTask().hashCode()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("ID", this.getId().toString()).append("UserID",
						this.getUserID()).toString();
	}
}
