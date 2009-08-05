package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.TemplateSuit;
import com.izhi.cms.service.ITemplateSuitService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/templatesuit")
public class TemplateSuitAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="templateSuitService")
	private ITemplateSuitService templateSuitService;
	private TemplateSuit obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)templateSuitService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("templateSuitId");
		pp.setDir("desc");
		List<TemplateSuit> l=templateSuitService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new TemplateSuit();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=templateSuitService.findTemplateSuitById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=templateSuitService.deleteTemplateSuit(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=templateSuitService.deleteTemplateSuits(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getSuitId()==0){
			int i=templateSuitService.saveTemplateSuit(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=templateSuitService.updateTemplateSuit(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ITemplateSuitService getTemplateSuitService() {
		return templateSuitService;
	}
	public void setTemplateSuitService(ITemplateSuitService templateSuitService) {
		this.templateSuitService = templateSuitService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public TemplateSuit getObj() {
		return obj;
	}
	public void setObj(TemplateSuit obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
