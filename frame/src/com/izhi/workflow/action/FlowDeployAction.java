package com.izhi.workflow.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.service.IFlowDeployService;
import com.izhi.workflow.service.IFlowMetaService;
@Service
@Scope("prototype")
@Namespace("/flow/deploy")
public class FlowDeployAction extends BasePageAction{

	private static final long serialVersionUID = 6019471491438566420L;
	
	private Long flowMetaId=new Long(0);
	@Resource(name="workflowFlowDeployService")
	private IFlowDeployService flowDeployService;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService flowMetaService;
	
	private FlowDeploy obj;
	private Long node;
	private int type=1;
	private Long flowDeployId;
	
	private List<String> ids;
	
	public Long getDeployId() {
		return flowDeployId;
	}

	public void setDeployId(Long deployId) {
		this.flowDeployId = deployId;
	}
	@Action("save")
	public String save(){
		boolean success=false;
		if(obj!=null){
			if(obj.getFlowDeployID()==-1){
				obj.setCreateTime(new java.util.Date());
				flowMetaService.addFlowDeploy(flowMetaId.toString(), obj);
				success=true;
			}else{
				flowDeployService.updateFlowDeploy(obj);
				success=true;
			}
		}
		this.out("{'success':"+success+"}");
		return null;
	}
	@Action("delete")
	public String delete(){
		Map<String,Object> l=new HashMap<String,Object>();
		if(ids!=null){
			List<Map<String,Object>> l1=new ArrayList<Map<String,Object>>();
			for(String id:ids){
				Map<String,Object> m=new HashMap<String,Object>();
				FlowDeploy flowDeploy = flowDeployService.findFlowDeploy(id);
				if (flowDeploy.getFlowProcs().size() > 0) {
					m.put("sucess", false);
					m.put("msg", "工作流部署【<b>"+flowDeploy.getFlowDeployName()+"</b>】 正在运行中，不能被删除！");
				}else if(flowDeploy.isReady()){
					m.put("sucess", false);
					m.put("msg","工作流部署【<b>"+ flowDeploy.getFlowDeployName()+"</b>】 已启用不能删除，请先停用！");
				}else{
					m.put("sucess", true);
					m.put("msg","工作流部署【<b>"+flowDeploy.getFlowDeployName()+"</b>】 工作流部署删除成功！");
					flowDeployService.deleteFlowDeploy(id);
				}
				l1.add(m);
			}
			int totalCount=flowDeployService.findTotalCount(flowMetaId);
			l.put("result", l1);
			l.put("totalCount", totalCount);
		}
		this.out(JSONObject.fromObject(l).toString());
		return null;
	}
	@Action("enable")
	public String enable(){
		if(ids!=null){
			for(String id:ids){
				flowDeployService.doEnableFlowDeploy(id);
			}
			this.out("{'success':true}");
		}else{
			this.out("{'success':false}");
		}
		
		return null;
	}
	@Action("nodes")
	public String nodes(){
		
		return null;
	}
	@Action("disable")
	public String disable(){
		if(ids!=null){
			for(String id:ids){
				flowDeployService.doDisableFlowDeploy(id);
			}
			this.out("{'success':true}");
		}else{
			this.out("{'success':false}");
		}
		return null;
	}
	
	@Action("load")
	public String load(){
		String s=JSONObject.fromObject(flowDeployService.findById(flowDeployId)).toString();
		this.out(s);
		return null;
	}
	@Action("tree")
	public String tree(){
		this.out(JSONArray.fromObject(flowDeployService.findNodes()).toString());
		return null;
	}

	@Action("page")
	public String page(){
		String s=JSONObject.fromObject(flowDeployService.findPage(this.getPageParameter(), flowMetaId)).toString();
		this.out(s);
		return null;
	}
	public Long getFlowMetaId() {
		return flowMetaId;
	}

	public void setFlowMetaId(Long flowMetaId) {
		this.flowMetaId = flowMetaId;
	}

	public IFlowDeployService getFlowDeployService() {
		return flowDeployService;
	}

	public void setFlowDeployService(IFlowDeployService flowDeployService) {
		this.flowDeployService = flowDeployService;
	}

	public FlowDeploy getObj() {
		return obj;
	}

	public void setObj(FlowDeploy obj) {
		this.obj = obj;
	}

	

	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public IFlowMetaService getFlowMetaService() {
		return flowMetaService;
	}

	public void setFlowMetaService(IFlowMetaService flowMetaService) {
		this.flowMetaService = flowMetaService;
	}

	public Long getFlowDeployId() {
		return flowDeployId;
	}

	public void setFlowDeployId(Long flowDeployId) {
		this.flowDeployId = flowDeployId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
