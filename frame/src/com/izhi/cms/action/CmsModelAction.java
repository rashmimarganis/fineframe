package com.izhi.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.CmsModel;
import com.izhi.cms.service.ICmsModelService;
import com.izhi.platform.action.BasePageAction;
@Service
@Scope("prototype")
@Namespace("/cms/model")
public class CmsModelAction extends BasePageAction{

	private static final long serialVersionUID = 3892345222936288848L;
	@Resource(name="cmsModelService")
	private ICmsModelService cmsModelService;
	
	private CmsModel obj;
	
	private List<Integer> ids;
	
	private int id;
	
	@Action("tree")
	
	public String tree(){
		String result=JSONArray.fromObject(cmsModelService.findTree()).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("index")
	public String index(){
		return SUCCESS;
	}
	@Action("all")
	public String all(){
		Map<String,Object> m=new HashMap<String, Object>();
		List<Map<String,Object>> l=cmsModelService.findAll();
		m.put("objs", l);
		m.put("totalCount", l.size());
		String result=JSONObject.fromObject(m).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("list")
	public String list(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("totalCount", cmsModelService.findTotalCount());
		map.put("objs", cmsModelService.findPage(this.getPageParameter()));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj!=null){
			int id=cmsModelService.saveModel(obj);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", id>0);
			map.put("id", id);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("load")
	public String load(){
		if(id!=0){
			List<Map<String,Object>> m=cmsModelService.findJsonById(id);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("data", m);
			map.put("success", true);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		if(ids!=null){
			boolean success=cmsModelService.deleteModels(ids);
			int totalCount=cmsModelService.findTotalCount();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", success);
			map.put("totalCount", totalCount);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	public ICmsModelService getCmsModelService() {
		return cmsModelService;
	}
	public void setCmsModelService(ICmsModelService cmsModelService) {
		this.cmsModelService = cmsModelService;
	}
	public CmsModel getObj() {
		return obj;
	}
	public void setObj(CmsModel obj) {
		this.obj = obj;
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
	
	
	

}
