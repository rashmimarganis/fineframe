package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.service.IWorkflowDriverService;
@Service
@Scope("prototype")
@Namespace("/workflow/driver/outputparam")
public class FlowDriverOutputParamAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6603098183484515896L;
	@Resource(name="workflowDriverSerivce")
	private IWorkflowDriverService service;
	private WFDriverOutputParam obj;
	private Long id;
	private Long driverId;
	@Action("save")
	public String save(){
		service.doAddDriverOutputParam(driverId.toString(), obj);
		this.out("{'success':true}");
		return null;
	}
	@Action("delete")
	public String delete(){
		service.deleteDriverOutputParam(id.toString());
		this.out("{'success':true}");
		return null;
	}
	@Action("load")
	public String load(){
		String s=JSONObject.fromObject(service.findDriverOutputParamById(id)).toString();
		this.out(s);
		return null;
	}
	@Action("page")
	public String page(){
		String s=JSONObject.fromObject(service.findDriverOutputParamPage(this.getPageParameter(), driverId)).toString();
		this.out(s);
		return null;
	}

	public IWorkflowDriverService getService() {
		return service;
	}

	public void setService(IWorkflowDriverService service) {
		this.service = service;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public WFDriverOutputParam getObj() {
		return obj;
	}

	public void setObj(WFDriverOutputParam obj) {
		this.obj = obj;
	}
	
	

	
}
