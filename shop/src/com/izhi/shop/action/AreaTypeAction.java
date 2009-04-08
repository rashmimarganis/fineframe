package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.AreaType;
import com.izhi.shop.service.IAreaTypeService;
@Service
@Scope(value="prototype")
@Namespace("/areatype")
public class AreaTypeAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="areaTypeService")
	private IAreaTypeService areaTypeService;
	private AreaType obj;
	private List<Integer> ids;
	
	private int id;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)areaTypeService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("areaTypeId");
		pp.setDir("desc");
		List<AreaType> l=areaTypeService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new AreaType();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=areaTypeService.findAreaTypeById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=areaTypeService.deleteAreaType(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=areaTypeService.deleteAreaTypes(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(obj.getAreaTypeId()==0){
			int i=areaTypeService.saveAreaType(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=areaTypeService.updateAreaType(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IAreaTypeService getAreaTypeService() {
		return areaTypeService;
	}
	public void setAreaTypeService(IAreaTypeService areaTypeService) {
		this.areaTypeService = areaTypeService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public AreaType getObj() {
		return obj;
	}
	public void setObj(AreaType obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
