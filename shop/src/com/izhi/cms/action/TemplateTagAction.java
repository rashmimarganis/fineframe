package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.ITemplateTagService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/templatetag")
public class TemplateTagAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="templateTagService")
	private ITemplateTagService templateTagService;
	private TemplateTag obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)templateTagService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("templateTagId");
		pp.setDir("desc");
		List<TemplateTag> l=templateTagService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new TemplateTag();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=templateTagService.findTemplateTagById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=templateTagService.deleteTemplateTag(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=templateTagService.deleteTemplateTags(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getTagId()==0){
			int i=templateTagService.saveTemplateTag(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=templateTagService.updateTemplateTag(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public ITemplateTagService getTemplateTagService() {
		return templateTagService;
	}
	public void setTemplateTagService(ITemplateTagService templateTagService) {
		this.templateTagService = templateTagService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public TemplateTag getObj() {
		return obj;
	}
	public void setObj(TemplateTag obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
