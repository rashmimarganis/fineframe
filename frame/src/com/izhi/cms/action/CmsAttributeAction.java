package com.izhi.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.CmsAttribute;
import com.izhi.cms.service.ICmsAttributeService;
import com.izhi.platform.action.BasePageAction;
@Service
@Scope("prototype")
@Namespace("/cms/attribute")
public class CmsAttributeAction extends BasePageAction{
	private static final long serialVersionUID = 3892345222936288848L;
	@Resource(name="cmsAttributeService")
	private ICmsAttributeService cmsAttributeService;
	
	private CmsAttribute obj;
	
	private List<Integer> ids;
	
	private int id;
	
	private int modelId;
	
	
	@Action("index")
	public String index(){
		return SUCCESS;
	}
	@Action("list")
	public String list(){
		if(modelId!=0){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("totalCount", cmsAttributeService.findTotalCount(modelId));
			map.put("objs", cmsAttributeService.findPage(this.getPageParameter(),modelId));
			String result=JSONObject.fromObject(map).toString();
			this.getRequest().setAttribute("result", result);
		}
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj!=null){
			int id=cmsAttributeService.saveAttribute(obj);
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
			List<Map<String,Object>> m=cmsAttributeService.findJsonById(id);
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
			boolean success=cmsAttributeService.deleteAttributes(ids);
			int totalCount=cmsAttributeService.findTotalCount(modelId);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", success);
			map.put("totalCount", totalCount);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	public ICmsAttributeService getCmsAttributeService() {
		return cmsAttributeService;
	}
	public void setCmsAttributeService(ICmsAttributeService cmsAttributeService) {
		this.cmsAttributeService = cmsAttributeService;
	}
	public CmsAttribute getObj() {
		return obj;
	}
	public void setObj(CmsAttribute obj) {
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
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	
	
	

}
