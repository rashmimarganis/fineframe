package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BaseAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.service.IFlowTaskService;
@Service
@Scope("prototype")
@Action("/flow/workAssignTask")
public class WorkAssignTaskAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3640894494213776435L;
	private String taskID;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;
	private String userToAssign;
	public String execute(){
		String userID =SecurityUser.getUserId()+"";
		boolean success=true;
		if (flowTaskService.isTaskAssigner(userID, taskID)) {
			flowTaskService.doAssignTask(userToAssign, taskID);
			if (log.isDebugEnabled()) {
				log.debug("�û�[" + userID + "][" + taskID + "]����["
						+ userToAssign + "]");
			}
		} else {
			success=false;
			log.warn("�û�[" + userID + "]��������[" + taskID + "]�ĺϷ�ָ����");
		}
		this.out("{'success':"+success+"}");
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
