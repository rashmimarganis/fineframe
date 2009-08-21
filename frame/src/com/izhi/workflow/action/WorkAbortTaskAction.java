package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BaseAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.service.IFlowTaskService;
@Service
@Scope("prototype")
@Action("/flow/workAbortTask")
public class WorkAbortTaskAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3640894494213776435L;
	private String taskID;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;
	public String execute(){
		FlowTask task = flowTaskService.findFlowTask(taskID);
		String state="ok";
		if (!task.getTaskState().equals(FlowTask.TASK_STATE_LOCKED)) {
			state="exception";
		}
		String userID = SecurityUser.getUserId()+"";

		if (flowTaskService.isTaskOwner(userID, taskID)) {
			flowTaskService.doAbortTask(userID, taskID);
			if (log.isDebugEnabled()) {
				log.debug("�û�[" + userID + "]��������[" + taskID + "]");
			}
		} else {
			log.warn("�û�[" + userID + "]��ͼ���������������[" + taskID + "]");
			state="denied";
		}
		this.out("{'state':'"+state+"'}");
		return null;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public IFlowTaskService getFlowTaskService() {
		return flowTaskService;
	}
	public void setFlowTaskService(IFlowTaskService flowTaskService) {
		this.flowTaskService = flowTaskService;
	}
}
