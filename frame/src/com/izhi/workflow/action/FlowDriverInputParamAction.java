package com.izhi.workflow.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.service.IWorkflowDriverService;
@Service
@Scope("prototype")
@Namespace("/workflow/driver/inputparam")
public class FlowDriverInputParamAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6083973905532743512L;
	@Resource(name="workflowDriverService")
	private IWorkflowDriverService service;
	
	private WFDriverInputParam obj;
	private Long id;
	private Long driverId;
	public IWorkflowDriverService getService() {
		return service;
	}
	public void setService(IWorkflowDriverService service) {
		this.service = service;
	}
	@Action("save")
	public String save(){
		service.doAddDriverInputParam(driverId.toString(), obj);
		this.out("{'success':true}");
		return null;
	}
	@Action("delete")
	public String delete(){
		service.deleteDriverInputParam(id.toString());
		this.out("{'success':true}");
		return null;
	}
	@Action("load")
	public String load(){
		String s=JSONObject.fromObject(service.findDriverInputParamById(id)).toString();
		this.out(s);
		return null;
	}
	@Action("page")
	public String page(){
		String s=JSONObject.fromObject(service.findDriverInputParamPage(this.getPageParameter(), driverId)).toString();
		this.out(s);
		return null;
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
	public WFDriverInputParam getObj() {
		return obj;
	}
	public void setObj(WFDriverInputParam obj) {
		this.obj = obj;
	}

}
