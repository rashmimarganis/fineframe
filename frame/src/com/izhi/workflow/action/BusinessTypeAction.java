package com.izhi.workflow.action;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.svenson.JSON;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.model.BusinessType;
import com.izhi.workflow.service.IBusinessTypeService;
import com.izhi.workflow.service.IFlowTaskService;
@Service
@Scope("prototype")
@Namespace("/flow/type")
public class BusinessTypeAction extends BasePageAction {

	private static final long serialVersionUID = -900988110197590772L;

	private BusinessType obj;
	@Resource(name="workflowBusinessTypeService")
	private IBusinessTypeService service;
	@Resource(name="workflowFlowTaskService")
	private IFlowTaskService flowTaskService;

	private String ids;

	private Long id;
	
	private String node;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public IBusinessTypeService getService() {
		return service;
	}

	public void setService(IBusinessTypeService service) {
		this.service = service;
	}
	@Action("save")
	public String save() {
		boolean success = false;
		if (obj != null) {
			success = service.saveBusinessType(obj) != null;
		}
		this.out(JSONObject.fromObject("{'success':" + success + "}")
				.toString());
		return null;
	}
	@Action("delete")
	public String delete() {
		boolean success = false;
		int totalCount=0;
		if (ids != null || ids.length() != 0) {
			totalCount=service.deleteBusinessType(ids);
			success = true;
		}
		this.out(JSONObject.fromObject("{'success':"+success+",'totalCount':"+totalCount+"}").toString());
		return null;
	}
	@Action("page")
	public String page() {
		String s = JSONObject.fromObject(
				service.findPage(this.getPageParameter())).toString();
		this.out(s);
		return null;
	}
	@Action("load")
	public String load() {
		String s = JSONObject.fromObject(service.findById(id)).toString();
		this.out(s);
		return null;
	}
	@Action("tree")
	public String tree(){
		String s=JSON.defaultJSON().forValue(service.findTree());
		this.out(s);
		return null;
	}
	@Action("/all")
	public String all() {
		String s = JSON.defaultJSON().forValue(service.findAll());
		this.out(s);
		return null;
	}
	@Action("findNewTaskTypes")
	public String findNewTaskTypes(){
		String userID=SecurityUser.getUserId()+"";
		String s=JSON.defaultJSON().forValue(flowTaskService.findMyNewTasksKinds(userID));
		this.out(s);
		return null;
	}
	@Action("findExecutingTaskTypes")
	public String findExecutingTaskTypes(){
		String userID=SecurityUser.getUserId()+"";
		String s=JSON.defaultJSON().forValue(flowTaskService.findMyExecutingTasksKinds(userID));
		this.out(s);
		return null;
	}
	@Action("findFinishedTaskTypes")
	public String findFinishedTaskTypes(){
		String userID=SecurityUser.getUserId()+"";
		String s=JSON.defaultJSON().forValue(flowTaskService.findMyFinishedTasksKinds(userID));
		this.out(s);
		return null;
	}

	public BusinessType getObj() {
		return obj;
	}

	public void setObj(BusinessType obj) {
		this.obj = obj;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IFlowTaskService getFlowTaskService() {
		return flowTaskService;
	}

	public void setFlowTaskService(IFlowTaskService flowTaskService) {
		this.flowTaskService = flowTaskService;
	}
	
	
}
