package com.izhi.workflow.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IWorkflowDriverService;
@Service
@Scope("prototype")
@Namespace("/workflow/driver")
public class WorkflowDriverAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 779223745857384804L;
	
	public Long flowDriverId=new Long(0);
	public String node="";
	
	public WorkflowDriver obj;
	@Resource(name="workflowDriverService")
	private IWorkflowDriverService workflowDriverService;
	private List<String> ids;

	public IWorkflowDriverService getWorkflowDriverService() {
		return workflowDriverService;
	}

	public void setWorkflowDriverService(IWorkflowDriverService workflowDriverService) {
		this.workflowDriverService = workflowDriverService;
	}
	@Action("contextPath")
	public String contextPath(){
		this.out(JSONArray.fromObject(this.workflowDriverService.findAllDriverContextPath()).toString());
		return null;
	}
	@Action("page")
	public String page(){
		if(node!=null&&!node.equals("")){
			String s=JSONObject.fromObject(this.workflowDriverService.findPage(this.getPageParameter(), node)).toString();
			this.out(s);
		}
		return null;
	}
	@Action("param")
	public String param(){
		
		return null;
	}
	@Action("load")
	public String load(){
		this.out(JSONObject.fromObject(workflowDriverService.findById(flowDriverId)).toString());
		return null;
	}
	@Action("delete")
	public String delete(){
		if(ids!=null){
			int i=0;
			int size=ids.size();
			for(String id:ids){
				this.workflowDriverService.deleteWorkflowDriver(id);
				i++;
			}
			long tc=this.workflowDriverService.findTotalCount(node);
			boolean success=i==size;
			this.out("{'success':"+success+",'totalCount':"+tc+"}");
		}else{
			this.out("{'success':false}");
		}
		return null;
	}
	
	@Action("save")
	public String save(){
		boolean success=false;
		if(obj!=null){
			if(obj.getFlowDriverID()==-1){
				this.workflowDriverService.saveWorkflowDriver(obj);
				success=true;
			}else{
				this.workflowDriverService.updateWorkflowDriver(obj);
				success=true;
			}
		}
		this.out("{'success':"+success+"}");
		return null;
	}
	
	

	public Long getFlowDriverId() {
		return flowDriverId;
	}

	public void setFlowDriverId(Long flowDriverId) {
		this.flowDriverId = flowDriverId;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public WorkflowDriver getObj() {
		return obj;
	}

	public void setObj(WorkflowDriver obj) {
		this.obj = obj;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	
}
