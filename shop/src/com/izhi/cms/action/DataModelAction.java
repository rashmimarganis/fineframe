package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.DataModel;
import com.izhi.cms.service.IDataModelService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/dataModel")
public class DataModelAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="dataModelService")
	private IDataModelService dataModelService;
	private DataModel obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)dataModelService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("dataModelId");
		pp.setDir("desc");
		List<DataModel> l=dataModelService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new DataModel();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=dataModelService.findDataModelById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=dataModelService.deleteDataModel(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=dataModelService.deleteDataModels(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getModelId()==0){
			int i=dataModelService.saveDataModel(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=dataModelService.updateDataModel(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IDataModelService getDataModelService() {
		return dataModelService;
	}
	public void setDataModelService(IDataModelService dataModelService) {
		this.dataModelService = dataModelService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public DataModel getObj() {
		return obj;
	}
	public void setObj(DataModel obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
