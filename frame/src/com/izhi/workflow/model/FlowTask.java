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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.util.TimeUtil;

@Entity
@Table(name="wf_task")
public class FlowTask implements BaseObject {

	private static final long serialVersionUID = -4025794363759421986L;
	protected static Log log = LogFactory.getLog(FlowTask.class);
	public static final String TASK_STATE_FREE = "free";
	public static final String TASK_STATE_LOCKED = "locked";
	public static final String TASK_STATE_FINISHED = "finished";
	public static final String TASK_STATE_ASSIGNED = "assigned";

	public static final String TASK_STATE_NEED_TO_ASSIGN = "need_to_assign";
	public static final String TASK_STATE_REFUSED = "refused";
	public static final Integer NEW_TASK_NOT_EMAILED = new Integer(0);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_id")
	private Long taskID = new Long(-1);
	
	@Column(name="task_state")
	private String taskState = TASK_STATE_FREE;
	
	@Column(name="create_time")
	private String createTime;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="overTime")
	private String overTime;
	
	@Column(name="preview_text")
	private String previewText;

	@ManyToOne
	@JoinColumn(name="proc_transaction_id")
	private FlowProcTransaction flowProcTransaction;
	
	@ManyToOne
	@JoinColumn(name="node_binding_id")
	private FlowNodeBinding flowNodeBinding;
	
	@OneToMany(targetEntity=FlowTaskUser.class)
	@JoinColumn(name="task_user_id")
	private List<FlowTaskUser> taskUsers = new ArrayList<FlowTaskUser>();
	
	@OneToMany(targetEntity=FlowTaskRole.class)
	@JoinColumn(name="task_role_id")
	private List<FlowTaskRole> taskRoles = new ArrayList<FlowTaskRole>();
	
	@OneToMany(targetEntity=NewTask.class)
	@JoinColumn(name="new_task_id")
	private List<NewTask> newTasks = new ArrayList<NewTask>();
	
	@OneToMany(targetEntity=FlowTaskAssigner.class)
	@JoinColumn(name="task_assigner_id")
	private List<FlowTaskAssigner> taskAssigners = new ArrayList<FlowTaskAssigner>();
	
	@OneToMany(targetEntity=FlowTaskRefuse.class)
	@JoinColumn(name="task_refuse_id")
	private List<FlowTaskRefuse> taskRefuses = new ArrayList<FlowTaskRefuse>();

	@Column(name="send_email")
	private Integer sendEmail = NEW_TASK_NOT_EMAILED;


	public Long getTaskID() {
		return taskID;
	}

	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}


	public String getTaskState() {
		return taskState;
	}

	public void free() {
		setTaskState(FlowTask.TASK_STATE_FREE);
	}

	public void lock() {
		setTaskState(FlowTask.TASK_STATE_LOCKED);
	}


	public void finish() {
		setTaskState(FlowTask.TASK_STATE_FINISHED);
		this.setOverTime(TimeUtil.getTimeStamp());

		for (Iterator<NewTask> it = this.getNewTasks().iterator(); it.hasNext();) {
			NewTask nt = (NewTask) it.next();
			nt.setFlowTask(null);
		}
		getNewTasks().clear();
	}

	public void needAsssign(String userID) {
		FlowTaskAssigner flowTaskAssigner = new FlowTaskAssigner();
		flowTaskAssigner.setFlowTask(this);
		flowTaskAssigner.setUserID(userID);
		if (this.getTaskAssigners().indexOf(flowTaskAssigner) == -1) {
			this.getTaskAssigners().add(flowTaskAssigner);
			this.setTaskState(FlowTask.TASK_STATE_NEED_TO_ASSIGN);
		}
	}

	public void refuse(String refuseFor, String refUserID) {
		setTaskState(FlowTask.TASK_STATE_REFUSED);
		FlowTaskRefuse ftr = new FlowTaskRefuse();
		ftr.setFlowTask(this);
		ftr.setRefuseFor(refuseFor);
		ftr.setRefuseUser(refUserID);
		this.getTaskRefuses().add(ftr);
	}

	public void abort(String userID) {
		if (this.getTaskUsers().size() == 1) {
			free();
		}
		FlowTaskUser flowTaskUser = null;
		for (Iterator<FlowTaskUser> it = getTaskUsers().iterator(); it.hasNext();) {
			FlowTaskUser ftu = it.next();
			if (ftu.getUserID().equals(userID)) {
				flowTaskUser = ftu;
				break;
			}
		}
		if (flowTaskUser != null) {
			getTaskUsers().remove(flowTaskUser);
			flowTaskUser.setFlowTask(null);
		} else {
			log.error("�û�[" + userID + "]��������[" + this.getTaskID()
					+ "]�ĳ����ˣ���Ȩ����");
			throw new RuntimeException(
					ExceptionMessage.ERROR_FLOWTASK_INVALID_STATE);
		}
	}

	public void assignToUser(String userID) {
		this.checkOutTask(userID);
		setTaskState(FlowTask.TASK_STATE_ASSIGNED);
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public FlowProcTransaction getFlowProcTransaction() {
		return flowProcTransaction;
	}

	public void setFlowProcTransaction(FlowProcTransaction flowProcTransaction) {
		this.flowProcTransaction = flowProcTransaction;
	}

	public FlowProc getFlowProc() {
		return this.getFlowProcTransaction().getFlowProc();
	}


	public FlowNodeBinding getFlowNodeBinding() {
		return flowNodeBinding;
	}

	public void setFlowNodeBinding(FlowNodeBinding flowNodeBinding) {
		this.flowNodeBinding = flowNodeBinding;
	}

	public List<FlowTaskUser> getTaskUsers() {
		return taskUsers;
	}

	public void checkOutTask(String userID) {
		this.lock();
		this.setStartTime(TimeUtil.getTimeStamp());

		for (Iterator<FlowTaskUser> it = getTaskUsers().iterator(); it.hasNext();) {
			FlowTaskUser taskUser =  it.next();
			if (taskUser.getUserID().equals(userID)) {
				return;
			}
		}
		FlowTaskUser flowTaskUser = new FlowTaskUser();
		flowTaskUser.setFlowTask(this);
		flowTaskUser.setUserID(userID);
		getTaskUsers().add(flowTaskUser);
	}

	public void setTaskUsers(List<FlowTaskUser> taskUsers) {
		this.taskUsers = taskUsers;
	}


	public List<FlowTaskRole> getTaskRoles() {
		return taskRoles;
	}

	public void setTaskRoles(List<FlowTaskRole> taskRoles) {
		this.taskRoles = taskRoles;
	}

	public List<FlowTaskAssigner> getTaskAssigners() {
		return taskAssigners;
	}

	public void setTaskAssigners(List<FlowTaskAssigner> taskAssigners) {
		this.taskAssigners = taskAssigners;
	}


	public List<NewTask> getNewTasks() {
		return newTasks;
	}

	public void setNewTasks(List<NewTask> newTasks) {
		this.newTasks = newTasks;
	}

	public List<FlowTaskRefuse> getTaskRefuses() {
		return taskRefuses;
	}

	public void setTaskRefuses(List<FlowTaskRefuse> taskRefuses) {
		this.taskRefuses = taskRefuses;
	}

	public void addTaskCandidate(String userID) {
		NewTask nt = new NewTask();
		nt.setFlowTask(this);
		nt.setTaskCandidateUserID(userID);
		if (getNewTasks().indexOf(nt) == -1) {
			this.getNewTasks().add(nt);
		}
	}

	public boolean hasTaskCandidate() {
		if (log.isDebugEnabled()) {
			log.debug("����[" + this.getTaskID() + "]hasTaskCandidate["
					+ getNewTasks().size() + "]");
		}
		return getNewTasks().size() > 0;
	}

	/**
	 * @hibernate.property column="VC_SEND_EMAIL" type="int" not-null="true"
	 * @return Integer
	 */
	public Integer getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(Integer sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getPreviewText() {
		return previewText;
	}

	public void setPreviewText(String previewText) {
		this.previewText = previewText;
	}

	public void emailTask() {
		this.setSendEmail(new Integer(1));
	}

	public boolean isEmailed() {
		return this.getSendEmail().intValue() == 1;
	}

	// ------------------------------------------------------------------------------
	public boolean equals(Object object) {
		if (!(object instanceof FlowTask)) {
			return false;
		}
		FlowTask ft = (FlowTask) object;
		return new EqualsBuilder().append(this.getFlowNodeBinding(),
				ft.getFlowNodeBinding()).append(this.getTaskState(),
				ft.getTaskState()).append(this.getCreateTime(),
				ft.getCreateTime()).isEquals();
	}

	public int hashCode() {
		// ���ѡ����������ÿ���಻ͬ
		return new HashCodeBuilder(216335803, 217569255).append(
				this.getFlowNodeBinding().hashCode()).append(
				this.getTaskState()).append(this.getCreateTime()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("NodeBinding", this.getFlowNodeBinding().toString())
				.append("TaskState", this.getTaskState()).append("SendEmail",
						this.getSendEmail()).append("CreateTime",
						this.getCreateTime()).toString();
	}

}
