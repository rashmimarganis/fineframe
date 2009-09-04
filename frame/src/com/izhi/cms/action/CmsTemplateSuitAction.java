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

import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.cms.service.ICmsTemplateSuitService;
import com.izhi.platform.action.BasePageAction;
@Service
@Scope("prototype")
@Namespace("/cms/suit")
public class CmsTemplateSuitAction extends BasePageAction{

	private static final long serialVersionUID = 3892345222936288848L;
	@Resource(name="cmsTemplateSuitService")
	private ICmsTemplateSuitService cmsTemplateSuitService;
	
	private CmsTemplateSuit obj;
	
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("index")
	public String index(){
		return SUCCESS;
	}
	
	@Action("list")
	public String list(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("totalCount", cmsTemplateSuitService.findTotalCount());
		map.put("objs", cmsTemplateSuitService.findPage(this.getPageParameter()));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("all")
	public String all(){
		int totalCount=(int)cmsTemplateSuitService.findTotalCount();
		List<Map<String,Object>> l=cmsTemplateSuitService.findAll();
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj!=null){
			int id=cmsTemplateSuitService.saveSuit(obj);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", id>0);
			map.put("id", id);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		if(ids!=null){
			boolean success=cmsTemplateSuitService.deleteSuits(ids);
			int totalCount=cmsTemplateSuitService.findTotalCount();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", success);
			map.put("totalCount", totalCount);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("load")
	public String load(){
		if(id!=0){
			List<Map<String,Object>> m=cmsTemplateSuitService.findJsonById(id);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("data", m);
			map.put("success", true);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	public ICmsTemplateSuitService getCmsTemplateSuitService() {
		return cmsTemplateSuitService;
	}
	public void setCmsTemplateSuitService(ICmsTemplateSuitService cmsTemplateSuitService) {
		this.cmsTemplateSuitService = cmsTemplateSuitService;
	}
	public CmsTemplateSuit getObj() {
		return obj;
	}
	public void setObj(CmsTemplateSuit obj) {
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
