package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IFlowTaskService;
@Service
@Scope("prototype")
@Action("/flow/workExecutingTask")
public class WorkExecutingTaskAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777486492005591899L;
	private String typeId;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaService;

	public IFlowTaskService getFlowTaskService() {
		return flowTaskService;
	}

	public void setFlowTaskService(IFlowTaskService flowTaskService) {
		this.flowTaskService = flowTaskService;
	}

	public String execute() {

		String userID = SecurityUser.getUserId() + "";

		int tasksNum = flowTaskService.findMyExecutingTasksNumByType(userID,
				typeId).intValue();

		return null;
	}

	public IFlowMetaService getFlowMetaService() {
		return flowMetaService;
	}

	public void setFlowMetaService(IFlowMetaService flowMetaService) {
		this.flowMetaService = flowMetaService;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
