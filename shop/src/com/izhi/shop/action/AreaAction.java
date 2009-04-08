package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Area;
import com.izhi.shop.model.AreaType;
import com.izhi.shop.service.IAreaService;
import com.izhi.shop.service.IAreaTypeService;
@Service
@Scope(value="prototype")
@Namespace("/area")
public class AreaAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="areaService")
	private IAreaService areaService;
	@Resource(name="areaTypeService")
	private IAreaTypeService areaTypeService;
	private Area obj;
	private List<Integer> ids;
	
	private List<AreaType> areaTypes;
	
	private int id;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)areaService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("areaId");
		pp.setDir("desc");
		List<Area> l=areaService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Area();
		areaTypes=areaTypeService.findAll();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=areaService.findAreaById(id);
		areaTypes=areaTypeService.findAll();
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=areaService.deleteArea(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=areaService.deleteAreas(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(obj.getAreaId()==0){
			int i=areaService.saveArea(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=areaService.updateArea(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IAreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Area getObj() {
		return obj;
	}
	public void setObj(Area obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public IAreaTypeService getAreaTypeService() {
		return areaTypeService;
	}
	public void setAreaTypeService(IAreaTypeService areaTypeService) {
		this.areaTypeService = areaTypeService;
	}
	public List<AreaType> getAreaTypes() {
		return areaTypes;
	}
	public void setAreaTypes(List<AreaType> areaTypes) {
		this.areaTypes = areaTypes;
	}
	
}
