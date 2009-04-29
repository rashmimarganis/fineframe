package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.ModelField;
import com.izhi.cms.service.IModelFieldService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/modelField")
public class ModelFieldAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="modelFieldService")
	private IModelFieldService modelFieldService;
	private ModelField obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)modelFieldService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("modelFieldId");
		pp.setDir("desc");
		List<ModelField> l=modelFieldService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new ModelField();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=modelFieldService.findModelFieldById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=modelFieldService.deleteModelField(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=modelFieldService.deleteModelFields(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getFieldId()==0){
			int i=modelFieldService.saveModelField(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=modelFieldService.updateModelField(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IModelFieldService getModelFieldService() {
		return modelFieldService;
	}
	public void setModelFieldService(IModelFieldService modelFieldService) {
		this.modelFieldService = modelFieldService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public ModelField getObj() {
		return obj;
	}
	public void setObj(ModelField obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
