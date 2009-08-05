package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.AgentLevel;
import com.izhi.shop.service.IAgentLevelService;
@Service
@Scope(value="prototype")
@Namespace("/agentlevel")
public class AgentLevelAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="agentLevelService")
	private IAgentLevelService agentLevelService;
	private AgentLevel obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)agentLevelService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("agentLevelId");
		pp.setDir("desc");
		List<AgentLevel> l=agentLevelService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new AgentLevel();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=agentLevelService.findAgentLevelById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=agentLevelService.deleteAgentLevel(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=agentLevelService.deleteAgentLevels(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getAgentLevelId()==0){
			int i=agentLevelService.saveAgentLevel(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=agentLevelService.updateAgentLevel(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IAgentLevelService getAgentLevelService() {
		return agentLevelService;
	}
	public void setAgentLevelService(IAgentLevelService agentLevelService) {
		this.agentLevelService = agentLevelService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public AgentLevel getObj() {
		return obj;
	}
	public void setObj(AgentLevel obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
