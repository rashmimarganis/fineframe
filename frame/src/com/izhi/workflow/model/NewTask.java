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
@Table(name="wf_new_task")
public class NewTask implements BaseObject {

	private static final long serialVersionUID = -2592347620746811978L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="new_id")
	private Long id = new Long(-1);
	@ManyToOne
	@JoinColumn(name="task_id")
	private FlowTask flowTask;
	
	@Column(name="cnadidate_user_id")
	private String taskCandidateUserID;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public FlowTask getFlowTask() {
		return flowTask;
	}

	public void setFlowTask(FlowTask flowTask) {
		this.flowTask = flowTask;
	}


	public String getTaskCandidateUserID() {
		return taskCandidateUserID;
	}

	public void setTaskCandidateUserID(String taskCandidateUserID) {
		this.taskCandidateUserID = taskCandidateUserID;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof NewTask)) {
			return false;
		}
		NewTask nt = (NewTask) object;
		return new EqualsBuilder().append(this.getFlowTask(), nt.getFlowTask())
				.append(this.getTaskCandidateUserID(),
						nt.getTaskCandidateUserID()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(256335803, 257569255).append(
				this.getId().toString()).append(this.getFlowTask())
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("ID", this.getId().toString()).append("FlowTask",
						this.getFlowTask()).append("CandidateUserID",
						this.getTaskCandidateUserID()).toString();
	}
}
