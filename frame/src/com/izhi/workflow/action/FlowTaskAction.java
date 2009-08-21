package com.izhi.workflow.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.service.IFlowTaskService;
@Service
@Scope("prototype")
@Namespace("/workflow/task")
public class FlowTaskAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3158783216795669967L;
	private IFlowTaskService service;
	private String typeId="0";
	@Action("findMyExecutingTasks")
	public String findMyExecutingTasks(){
		Map<String,Object> map=null;
		if(SecurityUser.isAnonymous()){
			map=new HashMap<String,Object>();
		}else{
			String userId=SecurityUser.getUserId()+"";
			map=service.findMyExecutingTasksByType(userId,typeId,this.getPageParameter());
		}
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}
	@Action("abortTask")
	public String abortTask(){
		
		return null;
	}
	@Action("findMyFinishedTasks")
	public String findMyFinishedTasks(){
		Map<String,Object> map=null;
		if(SecurityUser.isAnonymous()){
			map=new HashMap<String,Object>();
		}else{
			String userId=SecurityUser.getUserId()+"";
			map=service.findMyFinishedTasksByType(userId,typeId,this.getPageParameter());
		}
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}
	@Action("findMyNewTasksByType")
	public String findMyNewTasksByType(){
		
		Map<String,Object> map=null;
		if(SecurityUser.isAnonymous()){
			map=new HashMap<String,Object>();
		}else{
			String userId=SecurityUser.getUserId()+"";
			map=service.findMyNewTasksByType(userId,typeId,this.getPageParameter());
		}
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}
	public IFlowTaskService getService() {
		return service;
	}
	public void setService(IFlowTaskService service) {
		this.service = service;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
}
