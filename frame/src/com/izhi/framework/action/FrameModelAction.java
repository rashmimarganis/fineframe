package com.izhi.framework.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameModel;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/model")
public class FrameModelAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameModelService")
	private IFrameModelService modelService;
	private FrameModel obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		if(pp.getSort()==null){
			pp.setSort("modelId");
		}
		if(pp.getDir()==null){
			pp.setDir("desc");
		}
		int totalCount=(int)modelService.findTotalCount();
		List<Map<String,Object>> l=modelService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameModel();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", modelService.findJsonById(id));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=modelService.deleteModel(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		boolean i=modelService.deleteModels(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getModelId()==0){
			int i=modelService.saveModel(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=modelService.updateModel(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public IFrameModelService getModelService() {
		return modelService;
	}
	public void setModelService(IFrameModelService templateService) {
		this.modelService = templateService;
	}
	public FrameModel getObj() {
		return obj;
	}
	public void setObj(FrameModel obj) {
		this.obj = obj;
	}
	
	
}
