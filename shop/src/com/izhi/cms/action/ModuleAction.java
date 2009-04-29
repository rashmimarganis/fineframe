package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.Module;
import com.izhi.cms.service.IModuleService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/module")
public class ModuleAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="moduleService")
	private IModuleService moduleService;
	private Module obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)moduleService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("moduleId");
		pp.setDir("desc");
		List<Module> l=moduleService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Module();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=moduleService.findModuleById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=moduleService.deleteModule(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=moduleService.deleteModules(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getModuleId()==0){
			int i=moduleService.saveModule(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=moduleService.updateModule(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IModuleService getModuleService() {
		return moduleService;
	}
	public void setModuleService(IModuleService moduleService) {
		this.moduleService = moduleService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Module getObj() {
		return obj;
	}
	public void setObj(Module obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
