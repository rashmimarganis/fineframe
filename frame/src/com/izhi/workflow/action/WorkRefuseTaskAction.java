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
@Action("/flow/workRefuseTask")
public class WorkRefuseTaskAction extends BaseAction {
	
	private static final long serialVersionUID = 3640894494213776435L;
	private String taskID;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;
	private String[] tasksToRefuse;
	private String refusedFor; 
	
	public String execute(){
		String userID =SecurityUser.getUserId()+"";
		int numRefused = 0;
		if (tasksToRefuse != null && tasksToRefuse.length > 0) {
			numRefused = flowTaskService.doRefuseTasks(taskID, tasksToRefuse,
					refusedFor, userID);
		}
		boolean success=numRefused>0;
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
	public String[] getTasksToRefuse() {
		return tasksToRefuse;
	}
	public void setTasksToRefuse(String[] tasksToRefuse) {
		this.tasksToRefuse = tasksToRefuse;
	}
	public String getRefusedFor() {
		return refusedFor;
	}
	public void setRefusedFor(String refusedFor) {
		this.refusedFor = refusedFor;
	}
}
