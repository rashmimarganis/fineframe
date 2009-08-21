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
@Table(name = "flow_user_performer")
public class FlowUserPerformer implements BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7568591504377851842L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "performer_id")
	private Long id = new Long(-1);
	@ManyToOne
	@JoinColumn(name = "node_binding_id")
	private FlowNodeBinding flowNodeBinding;
	@Column(name = "user_id")
	private Long userID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlowNodeBinding getFlowNodeBinding() {
		return flowNodeBinding;
	}

	public void setFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		this.flowNodeBinding = flowNodeBinding;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public boolean equals(Object object) {
		if (!(object instanceof FlowUserPerformer)) {
			return false;
		}
		FlowUserPerformer fup = (FlowUserPerformer) object;
		return new EqualsBuilder().append(this.getFlowNodeBinding(),
				fup.getFlowNodeBinding()).append(this.getUserID(),
				fup.getUserID()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(87635803, 87669255).append(
				this.getFlowNodeBinding().toString()).append(this.getUserID())
				.toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.getId().toString()).append("NodeBinding",
						this.getFlowNodeBinding()).append("UserID",
						this.getUserID()).toString();
	}

}
