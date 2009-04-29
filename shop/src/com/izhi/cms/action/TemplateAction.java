package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.Template;
import com.izhi.cms.service.ITemplateService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/template")
public class TemplateAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="templateService")
	private ITemplateService templateService;
	private Template obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)templateService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("templateId");
		pp.setDir("desc");
		List<Template> l=templateService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Template();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=templateService.findTemplateById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=templateService.deleteTemplate(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=templateService.deleteTemplates(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getTemplateId()==0){
			int i=templateService.saveTemplate(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=templateService.updateTemplate(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ITemplateService getTemplateService() {
		return templateService;
	}
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Template getObj() {
		return obj;
	}
	public void setObj(Template obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
