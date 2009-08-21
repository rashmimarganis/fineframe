package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
import com.izhi.workflow.service.IFlowTaskService;

@Service
@Scope("prototype")
@Namespace("/flow/workList")
public class WorkListAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6551910076512667351L;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaService;
	@Resource(name="workflowFlowDeployService")
	private IFlowDeployService flowDeployService;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;
	public IFlowMetaService getFlowMetaService() {
		return flowMetaService;
	}
	public void setFlowMetaService(IFlowMetaService flowMetaService) {
		this.flowMetaService = flowMetaService;
	}
	public IFlowDeployService getFlowDeployService() {
		return flowDeployService;
	}
	public void setFlowDeployService(IFlowDeployService flowDeployService) {
		this.flowDeployService = flowDeployService;
	}
	public IFlowTaskService getFlowTaskService() {
		return flowTaskService;
	}
	public void setFlowTaskService(IFlowTaskService flowTaskService) {
		this.flowTaskService = flowTaskService;
	}
	@Action("listExecutingTasks")
	public String listExecutingTasks(){
		
		return null;
	}
	@Action("listFinishedTasks")
	public String listFinishedTasks(){
		
		return null;
	}
	@Action("listTasksKinds")
	public String listTasksKinds(){
		
		return null;
	}
	@Action("listNewTasks")
	public String listNewTasks(){
		
		return null;
	}
	@Action("checkOutTasks")
	public String checkOutTasks(){
		
		return null;
	}
	@Action("getBackTask")
	public String getBackTask(){
		
		return null;
	}
	@Action("listTasksToAssign")
	public String listTasksToAssign(){
		
		return null;
	}
	@Action("listTasksToRefuse")
	public String listTasksToRefuse(){
		
		return null;
	}
	@Action("listMyRefusedTasks")
	public String listMyRefusedTasks(){
		
		return null;
	}
	
	@Action("showExecutingTask")
	public String showExecutingTask(){
		
		return null;
	}
	@Action("searchToDistribute")
	public String searchToDistribute(){
		
		return null;
	}
	@Action("searchToAssign")
	public String searchToAssign() throws Exception {
		return null;
	}
	@Action("assignTask")
	public String assignTask() throws Exception {
		return null;
	}
	@Action("distributeTask")
	public String distributeTask() throws Exception {
		
		return null;
	}
	@Action("generatePersonDict")
	public String generatePersonDict() throws Exception {
		
		return null;
	}
}
